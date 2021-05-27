package com.yantraQA.base;

import lombok.Getter;
import lombok.ToString;

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
