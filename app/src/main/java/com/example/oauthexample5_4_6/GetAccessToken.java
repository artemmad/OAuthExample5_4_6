package com.example.oauthexample5_4_6;

import android.util.Log;
import android.view.textclassifier.TextLinks;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetAccessToken {
    static InputStream isT = null;
    static JSONObject jObjT = null;
    static String jsonT = "";

    public GetAccessToken(){}


    public JSONObject gettoken(String address,String token, String client_id, String client_secret, String redirect_uri){
        OkHttpClient httpClient = new OkHttpClient();

        try{
            //Making request
            RequestBody formBody = new FormBody.Builder()
                    .add("code", token)
                    .add("client_id", client_id)
                    .add("client_secret", client_secret).
                            build();

            Request request= new Request.Builder()
                    .url(address)
                    .addHeader("Content-Type","application/x-www-form-urlencoded" )
                    .post(formBody)
                    .build();
            Response response = httpClient.newCall(request).execute();
            isT = response.body().byteStream();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(isT, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            isT.close();
            jsonT = sb.toString();
            Log.e("JSONStr", jsonT);
        } catch (Exception e) {
            e.getMessage();
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        // Parse the String to a JSON Object
        try {
            jObjT = new JSONObject(jsonT);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        // Return JSON String
        return jObjT;
    }
}
