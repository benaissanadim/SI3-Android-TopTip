package etu.toptip.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;

import etu.toptip.R;
import etu.toptip.controller.SignUpController;

public class SignUpActivity extends AppCompatActivity {
    EditText email, password, userName, secondPassword;
    SignUpController signUpController;
    TextView titre;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.signUpController = new SignUpController(this);

        this.userName = findViewById(R.id.idEdtUserName);
        this.secondPassword = findViewById(R.id.ConfirmPassword);
        this.titre = findViewById(R.id.idTVHeaderErreur);
        this.email = findViewById(R.id.Email);
        this.password = findViewById(R.id.idEdtPassword);
        Button next = findViewById(R.id.idBtnRegister);

        Button goLogin = (Button) findViewById(R.id.idBtnGoLogin);
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(myIntent);
            }
        });

        this.signUpController = new SignUpController(this);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("Emile", "onClickSignUp");
                signUpController.OnSignUp(email.getText().toString(), userName.getText().toString(), password.getText().toString(), secondPassword.getText().toString());
            }
        });

    }

    public void showError(String error, Boolean create) {
//        Toast toast = Toast.makeText(this, error, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.TOP | Gravity.CENTER, 20, 30);
//        toast.show();
        if (create) {
            this.titre.setTextColor(getResources().getColor(R.color.greenAuth));
        }
        this.titre.setText(error);
        if (create) {
            Intent myIntent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(myIntent);
        }
    }
}