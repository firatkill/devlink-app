package com.worksOnLocal.DevLink.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ApiResponse<T>(
        boolean success,
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String error,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        T data
) {

    public static <T> ApiResponse<T> success(String message,T data) {
        return new ApiResponse<>(true,message,null,data);

    }
    public static <T> ApiResponse<T> failure(String message,String error){
        return new ApiResponse<>(false,message,error,null);
    }
}
