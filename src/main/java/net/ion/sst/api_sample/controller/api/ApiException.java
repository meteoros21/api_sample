package net.ion.sst.api_sample.controller.api;

import lombok.Data;

@Data
public class ApiException extends RuntimeException
{
    int code;
    String status;

    public ApiException(String message)
    {
        super(message);
        code = 9;
    }

    public ApiException(int code, String message)
    {
        super(message);
        this.code = code;
    }
}
