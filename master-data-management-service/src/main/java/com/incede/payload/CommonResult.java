//package com.incede.payload;
//
//
//public class CommonResult<T> {
////    private long code;
////    private String message;
////    private T data;
////
////    protected CommonResult() {
////    }
////
////    protected CommonResult(long code, String message, T data) {
////        this.code = code;
////        this.message = message;
////        this.data = data;
////    }
////
////    public static <T> CommonResult<T> success(T data) {
////        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
////    }
////
////    public static <T> CommonResult<T> success(T data, String message) {
////        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, data);
////    }
////
////    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
////        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
////    }
////
////    public static <T> CommonResult<T> failed(IErrorCode errorCode,String message) {
////        return new CommonResult<T>(errorCode.getCode(), message, null);
////    }
////
////    public static <T> CommonResult<T> failed(String message) {
////        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, null);
////    }
////
////    public static <T> CommonResult<T> failed() {
////        return failed(ResultCode.FAILED);
////    }
////
////    public static <T> CommonResult<T> validateFailed() {
////        return failed(ResultCode.VALIDATE_FAILED);
////    }
////
////    public static <T> CommonResult<T> validateFailed(String message) {
////        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
////    }
////
////    public static <T> CommonResult<T> unauthorized(T data) {
////        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
////    }
////
////    public static <T> CommonResult<T> forbidden(T data) {
////        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
////    }
////
////    public long getCode() {
////        return code;
////    }
////
////    public void setCode(long code) {
////        this.code = code;
////    }
////
////    public String getMessage() {
////        return message;
////    }
////
////    public void setMessage(String message) {
////        this.message = message;
////    }
////
////    public T getData() {
////        return data;
////    }
////
////    public void setData(T data) {
////        this.data = data;
////    }
////    
//}
package com.incede.payload;

public class CommonResult<T> {
    private long code;
    private String status;
    private T data;
    private String message;

    protected CommonResult() {
    }

    protected CommonResult(long code, String status, T data, String message) {
        this.code = code;
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), "success", data, "Successfully retrieved");
    }

    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), "success", data, message);
    }

    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(errorCode.getCode(), "failed", null, errorCode.getMessage());
    }

    public static <T> CommonResult<T> failed(IErrorCode errorCode, String message) {
        return new CommonResult<T>(errorCode.getCode(), "failed", null, message);
    }

    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), "failed", null, message);
    }

    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    public static <T> CommonResult<T> validateFailed() {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), "failed", null, ResultCode.VALIDATE_FAILED.getMessage());
    }

    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), "failed", null, message);
    }

    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), "unauthorized", data, ResultCode.UNAUTHORIZED.getMessage());
    }

    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), "forbidden", data, ResultCode.FORBIDDEN.getMessage());
    }

    // Getters and Setters
    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
