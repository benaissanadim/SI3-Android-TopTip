package etu.toptip.model;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import etu.toptip.controller.ModifPasswordController;
import etu.toptip.controller.ModifProfilController;
import etu.toptip.helper.Infologin;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ModifProfilModel {

    OkHttpClient client;

    public ModifProfilController modifProfilController;

    public ModifProfilModel(ModifProfilController controller) {
        this.modifProfilController = controller;
    }

    public void ModifPasswordModel2(String userName) {

        if (TextUtils.isEmpty(userName)) {
            modifProfilController.OnModifProfilError2("Veuillez rentrer votre nouveau nom d'utilisateur", false);
        } else {

            client = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("userName", userName)
                    .build();

            String url = "http://90.8.219.224:3000/api/user/" + Infologin.getIdUser();

            Request request = new Request.Builder()
                    .url(url)
                    .put(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("Emile", "ERROR onFailure" + e.toString());
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    try (ResponseBody responseBody = response.body()) {

                        JSONObject jsonObj = new JSONObject(responseBody.string());

                        if (jsonObj.getString("correct").equals("true"))
                            modifProfilController.OnModifProfilError2(jsonObj.getString("message"), true);
                        else
                            modifProfilController.OnModifProfilError2(jsonObj.getString("message"), false);
                    } catch (JSONException e) {
                        Log.d("Emile", "ERROR onResponse" + e.toString());
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
