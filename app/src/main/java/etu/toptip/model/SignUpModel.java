package etu.toptip.model;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import etu.toptip.activities.LoginActivity;
import etu.toptip.controller.LoginController;
import etu.toptip.controller.SignUpController;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SignUpModel {

    OkHttpClient client;

    public SignUpController signUpController;

    public SignUpModel(SignUpController controller) {
        this.signUpController = controller;
    }

    public void SignUpModel2(String email, String userName, String password, String validationPassword) {

        if (TextUtils.isEmpty(userName))
            signUpController.OnSignUpError2("Veuillez rentrer un nom d'utilisateur", false);
        else if (TextUtils.isEmpty(email))
            signUpController.OnSignUpError2("Veuillez rentrer un e-mail", false);
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            signUpController.OnSignUpError2("Veuillez rentrer un e-mail valide", false);
        else if (TextUtils.isEmpty(password))
            signUpController.OnSignUpError2("Veuillez rentrer un mot de passe", false);
        else if (password.length() <= 6)
            signUpController.OnSignUpError2("Veuillez rentrer un mot de passe d'une longueur > 6", false);
        else if (!validationPassword.equals(password))
            signUpController.OnSignUpError2("Les deux mots de passes ne sont pas identiques", false);
        else {

            client = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("email", email)
                    .add("userName", userName)
                    .add("password", password)
                    .build();

            Request request = new Request.Builder()
//                    .url("http://192.168.1.14:3000/api/user/auth/signup")
                    .url("http://90.8.219.224:3000/api/user/auth/signup")
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) {

                    try (ResponseBody responseBody = response.body()) {

                        Log.d("Emile", "Code de la requette: " + String.valueOf(response.code()));
                        JSONObject jsonObj = new JSONObject(responseBody.string());

                        if (jsonObj.getString("correct").equals("true"))
                            signUpController.OnSignUpError2(jsonObj.getString("message"), true);

                        else {
                            try {
                                if (jsonObj.getString("correct").equals("false") && jsonObj.getJSONObject("error").getString("message").substring(0, 68)
                                        .equals("User validation failed: email: Error, expected `email` to be unique."))
                                    signUpController.OnSignUpError2("Adresse mail déjà utilisée", false);

                                else if (jsonObj.getString("correct").equals("false"))
                                    signUpController.OnSignUpError2(jsonObj.getJSONObject("error").getString("message"), false);

                            } catch (Exception e) {
                                if (jsonObj.getString("correct").equals("false"))
                                    signUpController.OnSignUpError2(jsonObj.getJSONObject("error").getString("message"), false);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
