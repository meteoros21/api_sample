package net.ion.sst.api_sample.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.ion.sst.api_sample.controller.api.ApiException;

public class StringUtil
{
    public static String convertObjectToJson(Object object) throws JsonProcessingException
    {
        if (object == null)
            return null;

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static String regenMobile(String mobile)
    {
        if (mobile == null || mobile.length() == 0)
            return null;

        // 01021342345
        String temp = mobile.replaceAll("[^0-9]", "");

        mobile = temp.substring(0, 3) + "-";
        if (temp.length() == 11)
        {
            mobile += temp.substring(3, 7) + "-";
            mobile += temp.substring(7);
        }
        else if (temp.length() == 10)
        {
            mobile += temp.substring(3, 6) + "-";
            mobile += temp.substring(6);
        }
        else
        {
            mobile = null;
        }

        return mobile;
    }

    public static int parseInt(String noString)
    {
        String temp = noString.replaceAll("\\(", "");
        temp = temp.replaceAll("\\)", "");

        try
        {
            return Integer.parseInt(temp);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ApiException(1, "잘못된 숫자 형식입니다.");
        }
    }
}
