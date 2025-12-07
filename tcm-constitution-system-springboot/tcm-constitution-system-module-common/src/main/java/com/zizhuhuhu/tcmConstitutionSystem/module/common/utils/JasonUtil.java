package com.zizhuhuhu.tcmConstitutionSystem.module.common.utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JasonUtil {
    private static final ObjectMapper INSTANCE = new ObjectMapper();
    public static String toJsonString(Object obj){
        try {
            return INSTANCE.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return obj.toString();
        }
    }

}
