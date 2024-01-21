package com.greenatom.utils.url;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public class GenerateUrl {

    public static UriComponentsBuilder generateUrl(Map<String,String> params, UriComponentsBuilder builder){
        params.forEach((key, value) -> {
            if(value != null){
                builder.queryParam(key,value);
            }
        });
        return builder;
    }
}
