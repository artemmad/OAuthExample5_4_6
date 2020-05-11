package com.example.oauthexample5_4_6;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetUserInfo {
    static InputStream is =null;
    static JSONObject jObj = null;
    static String json = "";

    public GetUserInfo(){}

    OkHttpClient httpClient;
    Request request;

    public JSONObject getuserinfo(String adress, JSONObject jsonToken){
        try{
            String tok = jsonToken.getString("access_token");
            String user_id = jsonToken.getString("user_id");
            String fields = "uid,first_name,last_name,screen_name,sex,bdate,photo_big";

            httpClient = new OkHttpClient();
            RequestBody requestBody =  new FormBody.Builder()
                    .add("uids", user_id)
                    .add("fields", fields)
                    .add("access_token", tok)
                    .build();

            request = new Request.Builder()
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .url(adress)
                    .post(requestBody)
                    .build();
            Response response = httpClient.newCall(request).execute();
            is = response.body().byteStream();
        } catch (Exception e){
            e.printStackTrace();
        }

        //Преобразование ответа сервера InputStream в строку
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString(); //Информация о пользователе в JSON строке
        } catch (Exception e) {
            e.getMessage();
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        // Строку преобразуем в JSON Object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        // Return JSON объект
        return jObj;

    }
}
