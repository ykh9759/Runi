package com.example.runi.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Func {

    private static final ObjectMapper objectMapper = new ObjectMapper()
                                                        .registerModule(new JavaTimeModule())
                                                        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    
    //유효성 체크
    public static Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
    
        /* 유효성 검사에 실패한 필드 목록을 받음 */
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }
    

    public static String toJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static String toJsonString(List<?> list) throws JsonProcessingException {
        return objectMapper.writeValueAsString(list);
    }

    public static JSONObject toJsonObject(Object object) throws JsonProcessingException, ParseException {
        String json =  objectMapper.writeValueAsString(object);

        JSONParser parser = new JSONParser();

        JSONObject obj = (JSONObject) parser.parse(json);

        return obj;
    }


}
