package etu.toptip.controller;

import etu.toptip.activities.LoginActivity;
import etu.toptip.fragments.ModifPasswordFragment;
import etu.toptip.model.LoginModel;
import etu.toptip.model.ModifPasswordModel;

public class ModifPasswordController {

    private ModifPasswordModel modifPasswordModel;
    private ModifPasswordFragment modifPasswordActivity;  //View  ???

    public ModifPasswordController(ModifPasswordFragment modifPasswordActivity) {
        this.modifPasswordModel = new ModifPasswordModel(this);
        this.modifPasswordActivity = modifPasswordActivity;
    }

    public void OnModifPassword(String oldPassword, String newPassword) {     //Envoie model
        this.modifPasswordModel.ModifPasswordModel2(oldPassword, newPassword);
    }

    public void OnModifPasswordError2(String error,Boolean connect) {   //Recois du model et envoie au view
        this.modifPasswordActivity.showError(error, connect);
    }


}
