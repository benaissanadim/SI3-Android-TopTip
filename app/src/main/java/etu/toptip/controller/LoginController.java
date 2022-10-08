package etu.toptip.controller;

import etu.toptip.activities.LoginActivity;
import etu.toptip.model.LoginModel;

public class LoginController {

    private LoginModel loginModel;
    private LoginActivity loginActivity;  //View

    public LoginController(LoginActivity loginActivity) {
        this.loginModel = new LoginModel(this);
        this.loginActivity = loginActivity;
    }

    public void OnLogin(String email, String password) {     //Envoie model
        this.loginModel.LoginModel2(email, password);
    }

    public void OnLoginError2(String error,Boolean connect, String id) {   //Recois du model et envoie au view
        this.loginActivity.showError(error, connect, id);
    }


}
