package com.elf.appstore.http;


import android.telecom.Call;

import com.elf.appstore.http.data.BaseHttpResultData;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.StringReader;

/**
 * Created by antino on 18-3-26.
 */

public class BaseJsonParseCallback<T> implements IHttpCallback {
    DataResponse<T> response;
    Class<T> classOfType;
    public BaseJsonParseCallback(DataResponse<T> response, Class<T> classOfType) {
        this.response = response;
        this.classOfType = classOfType;
    }
    @Override
    public void onError(RequetError error) {
        if (response != null) {
            response.onErrorResponse(error);
        }
    }
    @Override
    public void onRespone(String result) {
        parseBaseResultData(result, classOfType);
    }
    private void parseBaseResultData(String result, Class<T> classOfType) {
        if (response != null) {
            Gson gson = new Gson();
            try {
                StringReader sr = new StringReader(result);
                BaseHttpResultData baseResult = gson.fromJson(sr, BaseHttpResultData.class);
                if (baseResult != null) {
                    if (baseResult.getRetCode() == HttpConstant.RESULT_CODE_SUCCESS) {
                        String body = baseResult.getBody().toString();
                        T data = gson.fromJson(body, classOfType);

                        if (data != null) {
                            response.value = data;
                            response.onResponse(response.value);
                        } else {
                            response.onErrorResponse(new RequetError(
                                    RequetError.ERROR_JSONPARSE, "data json parse error"));
                        }
                    } else {
                        response.onErrorResponse(new RequetError(
                                RequetError.ERROR_OTHER, "result code is "
                                + baseResult.getRetCode(), String.valueOf(baseResult.getRetCode())));
                    }
                } else {
                    response.onErrorResponse(new RequetError(
                            RequetError.ERROR_JSONPARSE, "base json parse error"));
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                response.onErrorResponse(new RequetError(
                        RequetError.ERROR_JSONPARSE, "json parse error"));
            } catch (java.lang.Exception e) {
                e.printStackTrace();
                response.onErrorResponse(new RequetError(
                        RequetError.ERROR_JSONPARSE, "json parse error"));
            }
        }
    }
}
