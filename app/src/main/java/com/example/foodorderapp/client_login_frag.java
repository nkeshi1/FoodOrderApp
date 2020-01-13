package com.example.foodorderapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class client_login_frag extends Fragment {
    public static API_INTERFACE api_interface;
    private View view;
    private Button loginBtn;
    private ProgressDialog dialog;
    private TextInputLayout typedPassword;
    private ArrayList<String> usernames = new ArrayList<>();
    private ArrayList<String> passwords = new ArrayList<>();
    private String client_passwordText,  client_usernameText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.client_login_frag, container, false);
        loginBtn = view.findViewById(R.id.client_loginBtn);

        TextView signUpBtn = view.findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), signup.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typedPassword = view.findViewById(R.id.client_username);
                client_usernameText = typedPassword.getEditText().getText().toString();

                typedPassword = view.findViewById(R.id.client_password);
                client_passwordText = typedPassword.getEditText().getText().toString();

                dialog = new ProgressDialog(getContext());
                dialog.setTitle("Checking credentials");
                dialog.setMessage("Please wait a minute...");
                dialog.show();

                api_interface = API_CLIENT.getApiClient().create(API_INTERFACE.class);
                Login_Register_Credentials loginCredentials = new Login_Register_Credentials();
                loginCredentials.setClient_username(client_usernameText);
                loginCredentials.setClient_password(client_passwordText);

                Call<Login_Register_Credentials> login_details  = api_interface.login_details(loginCredentials);
                login_details.enqueue(new Callback<Login_Register_Credentials>() {
                    @Override
                    public void onResponse(Call<Login_Register_Credentials> call, Response<Login_Register_Credentials> response) {
                        Toast.makeText(getContext(), response.body().getResponse(), Toast.LENGTH_LONG).show();
                        if(response.body().getResponse().equals("true")){
                            Toast.makeText(getContext(), "Welcome "+ response.body().getFirstname() + " "+ response.body().getLastname(), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            dialog.dismiss();
                        }else{
                            Toast.makeText(getContext(), "Invalid credentials. Try again", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Login_Register_Credentials> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        return view;
    }
}
