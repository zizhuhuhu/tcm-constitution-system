package com.example.weblog.module.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuestionEnum {
        SINGLE_CHOICE(1, "单选题"),
        MULTIPLE_CHOICE(2, "多选题"),
        JUDGMENT(3, "判断题"),
        FILL_IN_THE_BLANK(4, "填空题"),
        SHORT_ANSWER(5, "简答题");

        private Integer code;
        private String message;
    // 根据 code 获取枚举实例
    public static QuestionEnum getByCode(Integer code) {
        if (code == null) return null;
        for (QuestionEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
    // 根据 code 获取 message
    public static String getMessageByCode(Integer code) {
        QuestionEnum type = getByCode(code);
        return type != null ? type.getMessage() : "";
    }
}
