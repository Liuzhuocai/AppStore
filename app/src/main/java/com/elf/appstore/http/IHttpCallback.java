package com.elf.appstore.http;

/**
 * Created by antino on 18-3-26.
 */

public interface IHttpCallback {
    void onError(RequetError e);
    void onRespone(String result);
}
