package com.ebonilla.product_service.infrastructure.adapters.input.rest.response;

public class BaseResponse<T> {
    private Boolean success;
    private T data;
    private ErrorResponse error;

    public BaseResponse(boolean success, T data, ErrorResponse error){
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(true, data, null);
    }

    public static <T> BaseResponse<T> error(ErrorResponse error){
        return new BaseResponse<>(false, null, error);
    }

    public boolean isSuccess() { return success; }

    public T getData() { return data; }

    public ErrorResponse getError() { return error; }
}
