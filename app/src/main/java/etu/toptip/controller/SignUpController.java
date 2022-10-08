package etu.toptip.controller;

import etu.toptip.activities.SignUpActivity;
import etu.toptip.model.SignUpModel;

public class SignUpController {

    private SignUpModel signUpModel;
    private SignUpActivity signUpActivity;  //View

    public SignUpController(SignUpActivity signUpActivity) {
        this.signUpModel = new SignUpModel(this);
        this.signUpActivity = signUpActivity;
    }

    public void OnSignUp(String email, String userName, String password, String validationPassword) {     //Envoie model
        this.signUpModel.SignUpModel2(email, userName, password, validationPassword);
    }

    public void OnSignUpError2(String error, Boolean create) {   //Recois du model et envoie au view
        this.signUpActivity.showError(error, create);
    }
}
