package com.tml.libs.cutils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

//public interface APICallListener<TFailedResult, TSuccessResult> {
//    void onAPIFailed(@NotNull TFailedResult result);
//    void onAPISuccess(@NotNull TSuccessResult result);
//}

public interface APICallListener {
    void onSuccess(String result);
    void onFailed(String message);
    void onFailedCode(String message, String errcode);
    void onSuccessJSONObject(JSONObject obj );
    void onSuccessJSONArray(JSONArray obj);
    void onSuccessByteArray(byte[] data);
}

