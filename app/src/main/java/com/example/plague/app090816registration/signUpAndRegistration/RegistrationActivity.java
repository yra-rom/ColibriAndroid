package com.example.plague.app090816registration.signUpAndRegistration;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.plague.app090816registration.R;

public class RegistrationActivity extends AppCompatActivity {

    public static final String TAG = "RegistrationActivity";
    private EditText etName;
    private EditText etEmail;
    private EditText etPass;
    private EditText etConfPass;
    private Button btnReg;
    private TextView tvWrongConfPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        initViews();
    }

    private void initViews() {
        etName = (EditText) findViewById(R.id.reg_etName);
        etEmail = (EditText) findViewById(R.id.reg_etEmail);
        etPass = (EditText) findViewById(R.id.sign_etPassword);
        etConfPass = (EditText) findViewById(R.id.reg_etConfirmPassword);
        tvWrongConfPass = (TextView) findViewById(R.id.reg_tvWrongConfPass);
        btnReg = (Button) findViewById(R.id.reg_btnRegister);
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etEmail.setTextColor(etName.getTextColors());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        btnReg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tvWrongConfPass.setText("");
                if(etName.getText().toString().equals("")){etName.setHintTextColor(Color.RED);}
                else if(etEmail.getText().toString().equals("")){etEmail.setHintTextColor(Color.RED);}
                else if(etPass.getText().toString().equals("")){etPass.setHintTextColor(Color.RED);}
                else if(etConfPass.getText().toString().equals("")){etConfPass.setHintTextColor(Color.RED);}
                else if(!etPass.getText().toString().equals(etConfPass.getText().toString())){tvWrongConfPass.setText(R.string.passNotEqual);}
                else{

                    if(checkEmailIsFree()){
                        registerUser();
                        Intent intent = new Intent();
                        intent.putExtra("NICK", etName.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    else{
                        tvWrongConfPass.setText(R.string.emailIsUsed);
                        etEmail.setTextColor(Color.RED);
                    }
                }
            }
        });

    }

    private boolean checkEmailIsFree() {
        //TO DO
        //connect with server
        //check if email is not used
        return true;
    }

    private void registerUser() {
        //TO DO
        //connect with server
        //save new user
    }
}
