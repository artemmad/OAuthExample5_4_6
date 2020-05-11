package com.example.oauthexample5_4_6;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SetToServer {
    OkHttpClient client;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String setPOST(String address, JSONObject jsonPOST) {
        Gson gson = new Gson();
        VkUser vkUser;
        client = new OkHttpClient();
        String reponseString = "";
        JSONObject json_row = null;
        JSONArray data = null;
        try {
            data = jsonPOST.getJSONArray("response");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            json_row = data.getJSONObject(0);
            vkUser = gson.fromJson(json_row.toString(), VkUser.class);
            reponseString = post(address, gson.toJson(vkUser));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reponseString;
    }
}
