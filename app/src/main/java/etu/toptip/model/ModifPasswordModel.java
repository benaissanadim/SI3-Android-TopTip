package etu.toptip.model;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import etu.toptip.controller.LoginController;
import etu.toptip.controller.ModifPasswordController;
import etu.toptip.helper.Infologin;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ModifPasswordModel {

    OkHttpClient client;

    public ModifPasswordController modifPasswordController;

    public ModifPasswordModel(ModifPasswordController controller) {
        this.modifPasswordController = controller;
    }

    public void ModifPasswordModel2(String oldPassword, String newPassword) {

        if (TextUtils.isEmpty(oldPassword)) {
            modifPasswordController.OnModifPasswordError2("Veuillez rentrer votre ancien mot de passe", false);
        } else if (TextUtils.isEmpty(newPassword)) {
            modifPasswordController.OnModifPasswordError2("Veuillez rentrer votre nouveau mot de passe", false);
        } else {

            client = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("oldPassword", oldPassword)
                    .add("newPassword", newPassword)
                    .build();

            String url ="http://90.8.219.224:3000/api/user/"+ Infologin.getIdUser() + "/password";

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
                            modifPasswordController.OnModifPasswordError2(jsonObj.getString("message"), true);
                        else
                            modifPasswordController.OnModifPasswordError2(jsonObj.getString("message"), false);
                    } catch (JSONException e) {
                        Log.d("Emile", "ERROR onResponse" + e.toString());
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
