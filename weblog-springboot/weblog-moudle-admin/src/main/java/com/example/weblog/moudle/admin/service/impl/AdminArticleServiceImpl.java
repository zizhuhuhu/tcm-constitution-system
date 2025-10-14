package com.example.weblog.moudle.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.weblog.module.common.aspect.ApiOperationLog;
import com.example.weblog.module.common.domain.dos.*;
import com.example.weblog.module.common.domain.mapper.*;
import com.example.weblog.module.common.enums.ResponseCodeEnum;
import com.example.weblog.module.common.exception.BizException;
import com.example.weblog.module.common.utils.PageResponse;
import com.example.weblog.module.common.utils.Response;
import com.example.weblog.moudle.admin.convert.ArticleDetailConvert;
import com.example.weblog.moudle.admin.even.DeleteArticleEvent;
import com.example.weblog.moudle.admin.even.PublishArticleEvent;
import com.example.weblog.moudle.admin.even.UpdateArticleEvent;
import com.example.weblog.moudle.admin.model.vo.artical.*;
import com.example.weblog.moudle.admin.service.AdminArticleService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminArticleServiceImpl implements AdminArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleContentMapper articleContentMapper;
    @Autowired
    private ArticleCategoryRelMapper articleCategoryRelMapper;
    @Autowired
    private ArticleTagRelMapper articleTagRelMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Override
    @Transactional(rollbackFor = Exception.class)
    @ApiOperationLog(description = "新增文章")
    public Response publishArticle(PublishArticleReqVO publishArticleReqVO) {
        //VO转ArticleDO,并保存
        ArticleDO articleDO = ArticleDO.builder()
                .title(publishArticleReqVO.getTitle())
                .cover(publishArticleReqVO.getCover())
                .summary(publishArticleReqVO.getSummary())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        articleMapper.insert(articleDO);
        //拿到插入记录的主键ID
        Long articleId = articleDO.getId();
        //VO转ArticleContentDO,并保存
        ArticleContentDO articleContentDO = ArticleContentDO.builder()
                .articleId(articleId)
                .content(publishArticleReqVO.getContent())
                .build();
        articleContentMapper.insert(articleContentDO);
        //处理文章关联的分类
        Long categoryId = publishArticleReqVO.getCategoryId();
        //校验提交的分类是否真实存在
        CategoryDO categoryDO = categoryMapper.selectById(categoryId);
        if(Objects.isNull(categoryDO)) {
            log.warn("==>分类不存在，categoryId:{}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
        }
        ArticleCategoryRelDO articleCategoryRelDO = ArticleCategoryRelDO.builder()
                .articleId(articleId)
                .categoryId(categoryId)
                .build();
        articleCategoryRelMapper.insert(articleCategoryRelDO);

        //保存文章关联的标签集合
        List<String> publishTags = publishArticleReqVO.getTags();
        insertTags(publishTags, articleId);
        //发送文章发布事件
        eventPublisher.publishEvent(new PublishArticleEvent(this, articleId));
        return Response.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response deleteArticle(DeleteArticleReqVO deleteArticleReqVO) {
        Long articleId = deleteArticleReqVO.getId();

        //删除文章
        articleMapper.deleteById(articleId);
        //删除文章内容
        articleContentMapper.deleteByArticleId(articleId);
        //删除文章-标签关联记录
        articleTagRelMapper.deleteByArticleId(articleId);
        //闪出文章-分类关联记录
        articleCategoryRelMapper.deleteByArticleId(articleId);
        eventPublisher.publishEvent(new DeleteArticleEvent(this, articleId));
        return Response.success();
    }

    @Override
    public Response findArticlePageList(FindArticlePageListReqVO findArticlePageListReqVO) {
        Long current = findArticlePageListReqVO.getCurrent();
        Long size = findArticlePageListReqVO.getSize();
        String title = findArticlePageListReqVO.getTitle();
        LocalDate startDate = findArticlePageListReqVO.getStartDate();
        LocalDate endDate = findArticlePageListReqVO.getEndDate();
        //执行分页查询
        Page<ArticleDO> articleDOPage = articleMapper.selectPageList(current, size, title, startDate, endDate);
        List<ArticleDO> articleDOS = articleDOPage.getRecords();
        //DO转VO
        List<FindArticlePageListRspVO> vos = null;
        if(!CollectionUtils.isEmpty(articleDOS)){
            vos = articleDOS.stream()
                    .map(articleDO -> FindArticlePageListRspVO.builder()
                    .id(articleDO.getId())
                    .cover(articleDO.getCover())
                    .title(articleDO.getTitle())
                    .createTime(articleDO.getCreateTime()).build())
                    .collect(Collectors.toList());
        }
        return PageResponse.success(articleDOPage, vos);

    }

    //查询文章详情
    @Override
    public Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO) {
        Long articleId = findArticleDetailReqVO.getId();
        ArticleDO articleDO = articleMapper.selectById(articleId);
        if(Objects.isNull(articleDO)) {
            log.warn("==>查询的文章不存在，articleId: {}", articleId);
            throw new BizException(ResponseCodeEnum.ARTICLE_NOT_FOUND);
        }
        ArticleContentDO articleContentDO = articleContentMapper.selectByArticleId(articleId);
        //对应标签
        List<ArticleTagRelDO> articleTagRelDOS = articleTagRelMapper.selectByArticleId(articleId);
        //获取对应标签ID集合·
        List<Long> tagIds = articleTagRelDOS.stream().map(ArticleTagRelDO::getTagId).collect(Collectors.toList());
        //所属分类
        ArticleCategoryRelDO articleCategoryRelDO = articleCategoryRelMapper.selectByArticleId(articleId);
        //DO转VO
        FindArticleDetailRspVO vo = ArticleDetailConvert.INSTANCE.convertDO2VO(articleDO);
        vo.setContent(articleContentDO.getContent());
        vo.setTagIds(tagIds);
        vo.setCategory(articleCategoryRelDO.getCategoryId());
        return Response.success(vo);


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response updateArticle(UpdateArticleReqVO updateArticleReqVO) {
        Long articleId = updateArticleReqVO.getId();
        //VO转ArticleDO,并更新
        ArticleDO articleDO = ArticleDO.builder()
                .id(articleId)
                .title(updateArticleReqVO.getTitle())
                .cover(updateArticleReqVO.getCover())
                .summary(updateArticleReqVO.getSummary())
                .updateTime(LocalDateTime.now())
                .build();
        int count = articleMapper.updateById(articleDO);
        //根据更新是否成功，来判断文章是否存在
        if(count == 0){
            log.warn("==>该文章不存在，articleId: {}", articleId);
            throw new BizException(ResponseCodeEnum.ARTICLE_NOT_FOUND);
        }
        //2、VO转ArticleContentDO,并更新
        ArticleContentDO articleContentDO = ArticleContentDO.builder()
                .articleId(articleId)
                .content(updateArticleReqVO.getContent())
                .build();
        articleContentMapper.updateByArticleId(articleContentDO);
        //更新文章分类
        Long categoryId = updateArticleReqVO.getCategoryId();
        //校验提交的分类是否真实存在
        CategoryDO categoryDO = categoryMapper.selectById(categoryId);
        if(Objects.isNull(categoryDO)) {
            log.warn("==> 分类不存在，categoryId: {}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
        }
        //先删除该文章关联的分类记录，再插入新的关联记录
        articleCategoryRelMapper.deleteByArticleId(articleId);
        ArticleCategoryRelDO articleCategoryRelDO = ArticleCategoryRelDO.builder()
                .categoryId(categoryId)
                .articleId(articleId)
                .build();
        articleCategoryRelMapper.insert(articleCategoryRelDO);
        //保存文章关联的标签集合
        //先删除该文章对应的标签
        articleTagRelMapper.deleteByArticleId(articleId);
        List<String> publishTags = updateArticleReqVO.getTags();
        insertTags(publishTags, articleId);
        // 发布文章修改事件
        eventPublisher.publishEvent(new UpdateArticleEvent(this, articleId));
        return Response.success();
    }

    private void insertTags(List<String> publishTags, Long articleId) {
        //筛选提交的标签（表中不存在的标签）
        List<String> notExistTags = null;
        //筛选提交的标签（表中已存在的标签）
        List<String> existedTags = null;
        //查询出所有标签
        List<TagDO> tagDOS = tagMapper.selectList(null);
        //如果表中还没有添加任何标签
        if (CollectionUtils.isEmpty(tagDOS)) {
            notExistTags = publishTags;
        } else {
            List<String> tagIds = tagDOS.stream().map(tagDO -> String.valueOf(tagDO.getId())).collect(Collectors.toList());
            //表中已添加相关标签，则需要筛选
            //通过标签ID来筛选，包含对应的ID则表示提交的标签是表中存在的
            existedTags = publishTags.stream().filter(publishTag -> tagIds.contains(publishTag)).collect(Collectors.toList());
            //否则是不存在的
            notExistTags = publishTags.stream().filter(publishTag -> !tagIds.contains(publishTag)).collect(Collectors.toList());
            //补充：
            //若按字符串名称提交上来的标签，有可能是表中已经存在的，比如表中已经有了Java标签，用户提交Java小写标签，需要内部转换
            Map<String, Long> tagNameIdMap = tagDOS.stream().collect(Collectors.toMap(tagDO -> tagDO.getName().toLowerCase(), TagDO::getId));
            //使用迭代器进行删除操作
            Iterator<String> iterator = notExistTags.iterator();
            while (iterator.hasNext()) {
                String notExistTag = iterator.next();
                //转小写，若Map中相同的Key，则表示该标签是重复标签
                if (tagNameIdMap.containsKey(notExistTag.toLowerCase())) {
                    //从不存在的标签集合中清除
                    iterator.remove();
                    //并将对应的ID添加到已经存在的标签集合
                    existedTags.add(String.valueOf(tagNameIdMap.get(notExistTag.toLowerCase())));
                }
            }
        }
        //将提交上来的，已经存在于表中的标签，文章-标签关联关系入库
        if (!CollectionUtils.isEmpty(existedTags)) {
            List<ArticleTagRelDO> articleTagRelDOS = Lists.newArrayList();
            existedTags.forEach(tagId -> {
                ArticleTagRelDO articleTagRelDO = ArticleTagRelDO.builder()
                        .articleId(articleId)
                        .tagId(Long.valueOf(tagId))
                        .build();
                articleTagRelDOS.add(articleTagRelDO);
            });
            //批量插入
            articleTagRelMapper.insertBatchSomeColumn(articleTagRelDOS);
        }
        //将提交上来的，不存在于表中的标签，入库保存
        if(!CollectionUtils.isEmpty(notExistTags)) {
            //需要先将标签入库，拿到对应标签ID后，再把文章-标签关联表入库
            List<ArticleTagRelDO> articleTagRelDOS = Lists.newArrayList();
            notExistTags.forEach(tagName -> {
                TagDO tagDO = TagDO.builder()
                        .name(tagName)
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build();
                tagMapper.insert(tagDO);
                //拿到保存的标签ID
                Long tagId = tagDO.getId();
                //文章-标签关联关系
                ArticleTagRelDO articleTagRelDO = ArticleTagRelDO.builder()
                        .articleId(articleId)
                        .tagId(Long.valueOf(tagId))
                        .build();
                articleTagRelDOS.add(articleTagRelDO);
            });
            //批量插入
            articleTagRelMapper.insertBatchSomeColumn(articleTagRelDOS);

        }
    }
}
