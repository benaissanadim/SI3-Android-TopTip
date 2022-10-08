package etu.toptip.controller;

import etu.toptip.activities.LoginActivity;
import etu.toptip.fragments.ModifProfilFragment;
import etu.toptip.model.ModifPasswordModel;
import etu.toptip.model.ModifProfilModel;

public class ModifProfilController {

    private ModifProfilModel ModifProfilModel;
    private ModifProfilFragment modifProfilActivity;  //View  ???

    public ModifProfilController(ModifProfilFragment modifProfilActivity) {
        this.ModifProfilModel = new ModifProfilModel(this);
        this.modifProfilActivity = modifProfilActivity;
    }

    public void OnModifProfil(String userName) {     //Envoie model
        this.ModifProfilModel.ModifPasswordModel2(userName);
    }

    public void OnModifProfilError2(String error,Boolean connect) {   //Recois du model et envoie au view
        this.modifProfilActivity.showError(error, connect);
    }



}
