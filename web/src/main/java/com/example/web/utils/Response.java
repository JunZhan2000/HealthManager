package com.example.web.utils;

import lombok.Data;

@Data
public class Response {
    private Integer code;
    private Object data;

    public static Response success(Object data){
        Response response = new Response();
        response.setCode(200);
        response.setData(data);

        return response;
    }

    public static Response fail(Object data){
        Response response = new Response();
        response.setCode(403);
        response.setData(data);

        return response;
    }
}
