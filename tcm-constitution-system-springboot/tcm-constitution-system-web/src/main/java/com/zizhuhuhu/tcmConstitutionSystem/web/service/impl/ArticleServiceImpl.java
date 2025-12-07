package com.zizhuhuhu.tcmConstitutionSystem.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.dos.*;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.domain.mapper.*;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.enums.ResponseCodeEnum;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.exception.BizException;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.PageResponse;
import com.zizhuhuhu.tcmConstitutionSystem.module.common.utils.Response;
import com.zizhuhuhu.tcmConstitutionSystem.moudle.admin.even.ReadArticleEvent;
import com.zizhuhuhu.tcmConstitutionSystem.web.convert.ArticleConvert;
import com.zizhuhuhu.tcmConstitutionSystem.web.markdown.MarkdownHelper;
import com.zizhuhuhu.tcmConstitutionSystem.web.model.vo.article.*;
import com.zizhuhuhu.tcmConstitutionSystem.web.model.vo.category.FindCategoryListRspVO;
import com.zizhuhuhu.tcmConstitutionSystem.web.model.vo.tag.FindTagListRspVO;
import com.zizhuhuhu.tcmConstitutionSystem.web.service.ArticleService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticalMapper articalMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleCategoryRelMapper articleCategoryRelMapper;
    @Autowired
    private ArticleTagRelMapper articleTagRelMapper;
    @Autowired
    private ArticleContentMapper articleContentMapper;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    //获取首页文章分页数据

    @Override
    public Response findArticlePageList(FindIndexArticlePageListReqVO findIndexArticlePageListReqVO) {
        Long current = findIndexArticlePageListReqVO.getCurrent();
        Long size = findIndexArticlePageListReqVO.getSize();
        //第一步，分页查询文章主体记录
        Page<ArticleDO> articleDOPage = articalMapper.selectPageList(current, size, null, null, null);
        //返回的分页数据
        List<ArticleDO> articleDOS = articleDOPage.getRecords();
        List<FindIndexArticlePageListRspVO> vos = null;
        if(!CollectionUtils.isEmpty(articleDOS)) {
            //文章DO转VO
            vos = articleDOS.stream()
                    .map(articleDO -> ArticleConvert.INSTANCE.convertDO2VO(articleDO))
                    .collect(Collectors.toList());
            //拿到所有文章ID集合
            List<Long> articleIds = articleDOS.stream().map(ArticleDO::getId).collect(Collectors.toList());

            //第二步：设置文章所属分类
            //查询所有分类
            List<CategoryDO> categoryDOS = categoryMapper.selectList(Wrappers.emptyWrapper());
            //转Map, 方便后续根据分类Id拿到对应的分类名称
            Map<Long, String> categoryIdNameMap = categoryDOS.stream().collect(Collectors.toMap(CategoryDO::getId, CategoryDO::getName));

            //根据文章ID批量查询所有关联记录
            List<ArticleCategoryRelDO> articleCategoryRelDOS = articleCategoryRelMapper.selectByArticleIds(articleIds);

            vos.forEach(vo -> {
                Long currArticleId = vo.getId();
                //过滤出当前文章对应的关联数据
                Optional<ArticleCategoryRelDO> optional = articleCategoryRelDOS.stream().filter(rel -> Objects.equals(rel.getArticleId(), currArticleId)).findAny();
                //若不为空
                if(optional.isPresent()) {
                    ArticleCategoryRelDO articleCategoryRelDO = optional.get();
                    Long categoryId = articleCategoryRelDO.getCategoryId();
                    //通过分类ID从map中拿到对应的分类名称
                    String categoryName = categoryIdNameMap.get(categoryId);

                    FindCategoryListRspVO findCategoryListRspVO = FindCategoryListRspVO.builder()
                            .id(categoryId)
                            .name(categoryName)
                            .build();
                    //设置到当前vo类中
                    vo.setCategory(findCategoryListRspVO);
                }

            });
            //第三步，设置文章标签
            //查询所有标签
            List<TagDO>  tagDOS = tagMapper.selectList(Wrappers.emptyWrapper());
            //转Map,方便后续根据标签ID,拿到对应的标签名称
            Map<Long, String> mapIdNameMap = tagDOS.stream().collect(Collectors.toMap(TagDO::getId, TagDO::getName));

            //拿到所有文章的标签关联记录
            List<ArticleTagRelDO> articleTagRelDOS = articleTagRelMapper.selectByArticleIds(articleIds);
            vos.forEach(vo -> {
                Long currArticleId = vo.getId();
                //过滤出当前文章的标签关联记录
                List<ArticleTagRelDO> articleTagRelDOSList = articleTagRelDOS.stream().filter(rel -> Objects.equals(rel.getArticleId(), currArticleId)).collect(Collectors.toList());

                List<FindTagListRspVO> findTagListRspVOS = Lists.newArrayList();
                //将关联记录DO转Vo,并设置对应标签名
                articleTagRelDOSList.forEach(articleTagRelDO -> {
                    Long tagId = articleTagRelDO.getTagId();
                    String tagName = mapIdNameMap.get(tagId);

                    FindTagListRspVO findTagListRspVO = FindTagListRspVO.builder()
                            .id(tagId)
                            .name(tagName)
                            .build();
                    findTagListRspVOS.add(findTagListRspVO);
                });
                //设置转换后的标签数据
                vo.setTags(findTagListRspVOS);
            });
        }
        return PageResponse.success(articleDOPage, vos);


    }

    @Override
    public Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO) {
        Long articleId = findArticleDetailReqVO.getArticleId();

        ArticleDO articleDO = articalMapper.selectById(articleId);

        // 判断文章是否存在
        if (Objects.isNull(articleDO)) {
            log.warn("==> 该文章不存在, articleId: {}", articleId);
            throw new BizException(ResponseCodeEnum.ARTICLE_NOT_FOUND);
        }

        // 查询正文
        ArticleContentDO articleContentDO = articleContentMapper.selectByArticleId(articleId);

        // DO 转 VO
        FindArticleDetailRspVO vo = FindArticleDetailRspVO.builder()
                .title(articleDO.getTitle())
                .createTime(articleDO.getCreateTime())
                .content(MarkdownHelper.convertMarkdown2Html(articleContentDO.getContent()))
                .readNum(articleDO.getReadNum())
                .build();

        // 查询所属分类
        ArticleCategoryRelDO articleCategoryRelDO = articleCategoryRelMapper.selectByArticleId(articleId);
        CategoryDO categoryDO = categoryMapper.selectById(articleCategoryRelDO.getCategoryId());
        vo.setCategoryId(categoryDO.getId());
        vo.setCategoryName(categoryDO.getName());

        // 查询标签
        List<ArticleTagRelDO> articleTagRelDOS = articleTagRelMapper.selectByArticleId(articleId);
        List<Long> tagIds = articleTagRelDOS.stream().map(ArticleTagRelDO::getTagId).collect(Collectors.toList());
        List<TagDO> tagDOS = tagMapper.selectByIds(tagIds);

        // 标签 DO 转 VO
        List<FindTagListRspVO> tagVOS = tagDOS.stream()
                .map(tagDO -> FindTagListRspVO.builder().id(tagDO.getId()).name(tagDO.getName()).build())
                .collect(Collectors.toList());
        vo.setTags(tagVOS);

        // 上一篇
        ArticleDO preArticleDO = articalMapper.selectPreArticle(articleId);
        if (Objects.nonNull(preArticleDO)) {
            FindPreNextArticleRspVO preArticleVO = FindPreNextArticleRspVO.builder()
                    .articleId(preArticleDO.getId())
                    .articleTitle(preArticleDO.getTitle())
                    .build();
            vo.setPreArticle(preArticleVO);
        }

        // 下一篇
        ArticleDO nextArticleDO = articalMapper.selectNextArticle(articleId);
        if (Objects.nonNull(nextArticleDO)) {
            FindPreNextArticleRspVO nextArticleVO = FindPreNextArticleRspVO.builder()
                    .articleId(nextArticleDO.getId())
                    .articleTitle(nextArticleDO.getTitle())
                    .build();
            vo.setNextArticle(nextArticleVO);
        }
        //发布文章被阅读事件
        eventPublisher.publishEvent(new ReadArticleEvent(this, articleId));
        return Response.success(vo);
    }
}

