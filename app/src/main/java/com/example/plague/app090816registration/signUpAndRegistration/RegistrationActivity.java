package com.example.plague.app090816registration.signUpAndRegistration;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.plague.app090816registration.R;
import com.example.plague.app090816registration.clients.SendKeys;
import com.example.plague.app090816registration.clients.SignThread;

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

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etName.setTextColor(Color.BLACK);
                etName.setHintTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etEmail.setTextColor(Color.BLACK);
                etEmail.setHintTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        etPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etPass.setTextColor(Color.BLACK);
                etPass.setHintTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        etConfPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etConfPass.setTextColor(Color.BLACK);
                etConfPass.setHintTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        btnReg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String name = etName.getText().toString();
                String mail = etEmail.getText().toString();
                String pass = etPass.getText().toString();
                String confPass = etConfPass.getText().toString();

                tvWrongConfPass.setText("");
                if(name.equals("")){etName.setHintTextColor(Color.RED);}
                else if(mail.equals("")){etEmail.setHintTextColor(Color.RED);}
                else if(pass.equals("")){etPass.setHintTextColor(Color.RED);}
                else if(confPass.equals("")){etConfPass.setHintTextColor(Color.RED);}
                else if(!pass.equals(confPass)){tvWrongConfPass.setText(R.string.passNotEqual);}
                else{
                    if(checkEmailIsFree(mail)){
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

    private boolean checkEmailIsFree(String email) {
        //TO DO
        //connect with server
        //check if email is not used
        SignThread signThread = new SignThread(SendKeys.EMAIL, email);
        signThread.start();
        while(signThread.getAnswer()==null); // potential dead loop!!!
        Log.d(TAG, "Server's answer is: " + signThread.getAnswer());

        signThread.close();
        signThread.interrupt();

        return signThread.getAnswer();
    }

    private void registerUser() {
        //TO DO
        //connect with server
        //save new user
    }
}
