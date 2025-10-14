package com.example.weblog.moudle.admin.model.vo.blogsettings;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @TableName("t_blog_settings")
        public class UpdateBlogSettingsReqVO {
        @NotBlank(message = "博客LOGO不能为空")
        private String logo;
        @NotBlank(message = "博客名称不能为空")
        private String name;
        @NotBlank(message = "博客作者不能为空")
        private String author;
        @NotBlank(message = "博客介绍语不能为空")
        private String introduction;
        @NotBlank(message = "博客头像不能为空")
        private String avatar;

        private String githubHomepage;

        private String csdnHomepage;

        private String giteeHomepage;

        private String zhihuHomepage;
        }
