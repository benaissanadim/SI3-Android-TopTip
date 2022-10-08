package etu.toptip.model;

import etu.toptip.R;
import etu.toptip.controller.LoginController;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginModel {

    OkHttpClient client;

    public LoginController loginController;

    public LoginModel(LoginController controller) {
        this.loginController = controller;
    }

    public void LoginModel2(String email, String password) {

        if (TextUtils.isEmpty(email)) {
            loginController.OnLoginError2("Veuillez rentrer un e-mail", false, null);
        } else if ((!Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            loginController.OnLoginError2("Veuillez rentrer un e-mail valide", false, null);
        } else if (TextUtils.isEmpty(password)) {
            loginController.OnLoginError2("Veuillez rentrer un mot de passe", false, null);
        } else {

            client = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("email", email)
                    .add("password", password)
                    .build();

            Request request = new Request.Builder()
                    .url("http://90.8.219.224:3000/api/user/auth/login")
//                    .url("http://192.168.1.14:3000/api/user/auth/login")
                    .post(requestBody)
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
                            loginController.OnLoginError2(jsonObj.getString("message"), true, jsonObj.getString("user"));
                        else
                            loginController.OnLoginError2(jsonObj.getString("message"), false, null);
                    } catch (JSONException e) {
                        Log.d("Emile", "ERROR onResponse" + e.toString());
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
