package com.example.plague.app090816registration.LogInAndRegistration;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.plague.app090816registration.R;
import com.example.plague.app090816registration.clients.LogInThread;
import com.example.plague.app090816registration.clients.SendKeys;

import java.util.HashMap;
import java.util.Map;

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
                etName.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDefaultHint));
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
                etEmail.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDefaultHint));
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
                etPass.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDefaultHint));
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
                etConfPass.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDefaultHint));
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
//                        registerUser(name, mail, pass);
//                        Intent intent = new Intent();
//                        intent.putExtra(SendKeys.EMAIL, mail);
//                        setResult(RESULT_OK, intent);
//                        finish();

                        Intent intent = new Intent();
                        intent.putExtra(SendKeys.EMAIL, mail);
                        setResult(registerUser(name, mail, pass) ? RESULT_OK : RESULT_CANCELED, intent);
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
        Map map = new HashMap<String, String>();
        map.put(SendKeys.TITLE, SendKeys.CHECK_MAIL);
        map.put(SendKeys.EMAIL, email);

        LogInThread signThread = new LogInThread(map);
        signThread.start();
        while(signThread.getAnswer()==null); // potential dead loop!!!
        Log.d(TAG, "Server's answer is: " + signThread.getAnswer());

        signThread.close();
        signThread.interrupt();

        return !signThread.getAnswer();
    }

    private Boolean registerUser(String name, String email, String pass) {
        Map map = new HashMap<String, String>();
        map.put(SendKeys.TITLE, SendKeys.REGISTER);
        map.put(SendKeys.NICK, name);
        map.put(SendKeys.EMAIL, email);
        map.put(SendKeys.PASS, pass);


        LogInThread signThread = new LogInThread(map);
        signThread.start();
        while(signThread.getAnswer()==null); // potential dead loop!!!
        Log.d(TAG, "Server's answer is: " + signThread.getAnswer());

        signThread.close();
        signThread.interrupt();

        return signThread.getAnswer();

        //TO DO
        //connect with server
        //save new user
    }
}
