package etu.toptip.activities;

import androidx.appcompat.app.AppCompatActivity;

import etu.toptip.R;
import etu.toptip.controller.LoginController;
import etu.toptip.helper.Infologin;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {
    public EditText email, password;
    TextView titre;
    LoginController loginController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.loginController = new LoginController(this);

        this.email = findViewById(R.id.email);
        this.password = findViewById(R.id.mdp);
        this.titre = findViewById(R.id.idErreur);

        Button login = findViewById(R.id.idBtnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("Emile", "onClickLogin");
                loginController.OnLogin(email.getText().toString(), password.getText().toString());
            }
        });

        Button goRegister = (Button) findViewById(R.id.idBtnGoRegister);
        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), SignUpActivity.class);
                startActivity(myIntent);
            }
        });
    }

    public void showError(String error, Boolean connect, String id) {
//        Toast toast = Toast.makeText(this, error, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.TOP | Gravity.CENTER, 20, 30);
//        toast.show();
        if (connect) {
            this.titre.setTextColor(getResources().getColor(R.color.greenAuth));
        }
        this.titre.setText(error);
        if (connect) {
            Infologin.setIdUser(id);
            Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(myIntent);
        }

    }
}