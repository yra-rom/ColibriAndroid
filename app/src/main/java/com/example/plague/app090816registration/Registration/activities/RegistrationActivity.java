package com.example.plague.app090816registration.Registration.activities;

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
import com.example.plague.app090816registration.Registration.clients.RegistrationThread;
import com.example.plague.app090816registration.connection_defaults.Constants.SendKeys;
import com.example.plague.app090816registration.connection_defaults.UserPak.User;
import com.example.plague.app090816registration.connection_defaults.UserPak.UserBuilder;
import com.example.plague.app090816registration.connection_defaults.chekers.Check;

public class RegistrationActivity extends AppCompatActivity {
    public static final String TAG = "RegistrationActivity";

    private EditText etName;
    private EditText etEmail;
    private EditText etPass;
    private EditText etConfPass;
    private Button btnReg;
    private TextView tvRegInfo;

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
        tvRegInfo = (TextView) findViewById(R.id.reg_tvRegInfo);
        btnReg = (Button) findViewById(R.id.reg_btnRegister);

        addTextChangeListeners();

        btnReg.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  String name = etName.getText().toString();
                  String email = etEmail.getText().toString();
                  String pass = etPass.getText().toString();
                  String confPass = etConfPass.getText().toString();

                  Check ch = Check.getInstance();

                  tvRegInfo.setText("");
                  if(name.equals("")){
                      etName.setHintTextColor(Color.RED);
                      tvRegInfo.setText(R.string.inputRName);
                  } else if(! ch.checkEmailLocal(email)){
                      etEmail.setHintTextColor(Color.RED);
                      tvRegInfo.setText(R.string.inputREmail);
                  } else if(! ch.checkPasswordLocal(pass)){
                      etPass.setHintTextColor(Color.RED);
                      tvRegInfo.setText(R.string.inputRPass);
                      tvRegInfo.append("\n");
                      tvRegInfo.append(getResources().getString(R.string.infoAboutRightPass));
                  } else if(! ch.checkPasswordLocal(confPass)){
                      etConfPass.setHintTextColor(Color.RED);
                      tvRegInfo.setText(R.string.inputRConfPass);
                  } else if(!pass.equals(confPass)){
                      tvRegInfo.setText(R.string.passNotEqual);
                  } else{
                      if(ch.checkEmailIsFree(email)){
                          Intent intent = new Intent();
                          intent.putExtra(SendKeys.EMAIL, email);
                          User user = new UserBuilder().name(name).email(email).pass(pass).build();
                          setResult( registerUser(user) ? RESULT_OK : RESULT_CANCELED, intent);
//                          setResult( registerUser(name, email, pass) ? RESULT_OK : RESULT_CANCELED, intent);
                          finish();
                      }else{
                          tvRegInfo.setText(R.string.emailIsUsed);
                          etEmail.setTextColor(Color.RED);
                      }
                  }
              }
          }
        );
    }

    private void addTextChangeListeners() {
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
    }

    private Boolean registerUser(User user) {
        RegistrationThread registration = new RegistrationThread(user);
        registration.start();

//        Map map = new HashMap<String, String>();
//        map.put(SendKeys.TITLE, SendKeys.REGISTER);
//        map.put(SendKeys.NICK, name);
//        map.put(SendKeys.EMAIL, email);
//        map.put(SendKeys.PASS, pass);
//
//
//        CheckThread signThread = new CheckThread(map);
//        signThread.start();

        while(registration.getAnswer() == null); // potential dead loop!!! activity blocks until no answer
        Boolean answer = registration.getAnswer();
        Log.d(TAG, "Server's answer is: " + answer);

        registration.close();
        registration.interrupt();

        return answer;
    }
}
