package net.ion.sst.api_sample.controller.api;

public class ApiResult
{
    public int code;
    public String status;
    public Object data;

    public ApiResult(int code, String status)
    {
        this.code = code;
        this.status = status;
        this.data = null;
    }

    public ApiResult(int code, String status, Object data)
    {
        this.code = code;
        this.status = status;
        this.data = data;
    }
}
