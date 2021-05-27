package com.yantrQA.base;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class APIHeaders {
    Map<String, String> defaultHeaders;

    public APIHeaders(){
        defaultHeaders = Maps.newHashMap();
        defaultHeaders.put("accept","application");
        defaultHeaders.put("Content-Type","application/json");
    }

    public Map<String, String> appendHeaders(String headersList){
        Map<String, String> newHeaders = Splitter
                .on(",")
                .omitEmptyStrings()
                .trimResults()
                .withKeyValueSeparator("=")
                .split(headersList);
        defaultHeaders.putAll(newHeaders);
        return defaultHeaders;
    }

}
