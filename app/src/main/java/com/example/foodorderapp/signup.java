package com.example.foodorderapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signup extends AppCompatActivity {
    public static API_INTERFACE api_interface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        api_interface = API_CLIENT.getApiClient().create(API_INTERFACE.class);


        final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Registering user, please wait...");
        progressDialog.setTitle("Register User");
        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progressDialog.show();

                TextInputLayout firstnameText = findViewById(R.id.firstnameText);
                String firstname = firstnameText.getEditText().getText().toString();

                TextInputLayout lastnameText = findViewById(R.id.lastnameText);
                String lastname = lastnameText.getEditText().getText().toString();

                TextInputLayout usernameText = findViewById(R.id.usernameText);
                String username = usernameText.getEditText().getText().toString();

                TextInputLayout emailText = findViewById(R.id.emailText);
                String email = emailText.getEditText().getText().toString();

                TextInputLayout passwordText = findViewById(R.id.passwordText);
                String password = passwordText.getEditText().getText().toString();

                TextInputLayout retypepasswordText = findViewById(R.id.retypePasswordText);
                String retypepassword = retypepasswordText.getEditText().getText().toString();

                if(firstname.equals("") || lastname.equals("") || username.equals("") || email.equals("") || password.equals("") || retypepassword.equals("")){
//                    progressDialog.dismiss();
                    firstnameText.setError("must not be empty");
                    lastnameText.setError("must not be empty");
                    usernameText.setError("must not be empty");
                    emailText.setError("must not be empty");
                    passwordText.setError("must not be empty");
                    retypepasswordText.setError("must not be empty");
                }else if(!(password.equals(retypepassword))){
//                    progressDialog.dismiss();
                    passwordText.setError("passwords must match");
                    retypepasswordText.setError("passwords must match");
                }else{
                    //Send user details to the database
                    Login_Register_Credentials register_credentials = new Login_Register_Credentials();
                    register_credentials.setEmail(email);
                    register_credentials.setFirstname(firstname);
                    register_credentials.setLastname(lastname);
                    register_credentials.setPassword(password);
                    register_credentials.setUsername(username);

                    Call<Login_Register_Credentials> credentialsCall = api_interface.register_user(register_credentials);
                    credentialsCall.enqueue(new Callback<Login_Register_Credentials>() {
                        @Override
                        public void onResponse(Call<Login_Register_Credentials> call, Response<Login_Register_Credentials> response) {
                            Toast.makeText(getApplicationContext(), response.body().getRegistery_response(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<Login_Register_Credentials> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
//                    progressDialog.dismiss();

                    ContextThemeWrapper con = new ContextThemeWrapper( signup.this, R.style.AlertS );
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder( con );
                    alertDialog.setView(R.layout.custom_register_success_alert);
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getApplicationContext(), LoginPage.class));
                        }
                    });
                    AlertDialog successAlert = alertDialog.create();
                    successAlert.show();
//                    TextView congratMessage = findViewById(R.id.congrat_message);
                }
            }
        });
    }
}
