package com.elf.appstore.http;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by antino on 18-3-26.
 */

public class OKHttpJsonCallback implements Callback {
    BaseJsonParseCallback baseJsonParseCallback;
    Call reCall;
    public OKHttpJsonCallback(){

    }

    public OKHttpJsonCallback callback(BaseJsonParseCallback callback){
        baseJsonParseCallback = callback;
        return this;
    }
    public OKHttpJsonCallback setReCall(Call call){
        reCall = call;
        return  this;
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        //TODO:antino Maybe,Other exceptions should be catched
        if(response.isSuccessful()){
            baseJsonParseCallback.onRespone(response.body().string());
        }else {
            if (response.code() == 408) {
                //time out
                baseJsonParseCallback.onError(new RequetError("Time out"));
            }
            if (response.code() == 504) {
                //We need try again.
                call.execute();
                if(response.isSuccessful()){
                    baseJsonParseCallback.onRespone(response.body().string());
                }else{
                    baseJsonParseCallback.onError(new RequetError("NetWork Error"));
                }

            }
        }
        response.close();
    }

    @Override
    public void onFailure(Call call, IOException e) {
        if(e!=null){
            if(e instanceof SocketTimeoutException){
                baseJsonParseCallback.onError(new RequetError("Time out connection."));
            }else{
                baseJsonParseCallback.onError(new RequetError(e.getMessage()));
            }

        }
    }
}
