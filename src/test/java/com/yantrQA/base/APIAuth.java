package com.yantrQA.base;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class APIAuth {

    String authToken = null;

    public APIAuth(){

    }

    public String getAuthToken(String authType){
        String token = "xyz"; //replace this to get the auth token from smwhere
        return token;
    }

}
