package com.karasluo.libcommon.retrofit;

/**
 * Created by Hongliang Luo on 2019/8/21.
 **/
public class DefaultResponseBean<T> {

    /**
     * code : 404
     * is_success : false
     * result : {}
     */

    private int code;
    private boolean is_success;
    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isIs_success() {
        return is_success;
    }

    public void setIs_success(boolean is_success) {
        this.is_success = is_success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
