package com.davidm.lynch.acinstaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

public class LogInPage extends AppCompatActivity implements View.OnClickListener{

//    ParseUser user;
    Button btnSignUp, btnLogIn;
    EditText edtUsername, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        // Save the current Installation to Back4App
        ParseInstallation.getCurrentInstallation().saveInBackground();

        setTitle("Log In");

        edtUsername = findViewById(R.id.edtLIUsername);
        edtPassword = findViewById(R.id.edtLIPassword);
        btnLogIn = findViewById(R.id.btnLILogIn);
        btnSignUp = findViewById(R.id.btnLISignUp);
        btnSignUp.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);

        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnSignUp);
                }
                return false;
            }
        });



        if(ParseUser.getCurrentUser() != null){
          //  ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }

    }

    @Override
    public void onClick(View view) {
        final String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        switch(view.getId()){
            case R.id.btnLISignUp:
                if(ParseUser.getCurrentUser() != null){
                    ParseUser.getCurrentUser().logOut();
                }
//                MainActivity.this.reset();
//                findViewById(R.id.edtEmail).setText("");
                finish();
            break;
            case R.id.btnLILogIn:
                if (username.equals("")) {
                    Toast.makeText(LogInPage.this, "Please enter a Username to proceed", Toast.LENGTH_LONG).show();
                } else if (password.equals("")) {
                    Toast.makeText(LogInPage.this, "Please enter a password to proceed", Toast.LENGTH_LONG).show();
                } else {
                    final ProgressDialog progressDialog= new ProgressDialog(this);
                    progressDialog.setMessage(username + "is being logged in.");
                    progressDialog.show();

                    if(ParseUser.getCurrentUser() != null){
                        ParseUser.getCurrentUser().logOut();
                    }

                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if(e !=null){
                                Toast.makeText(LogInPage.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(LogInPage.this, username+ " is logged in.", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                transitionToSocialMediaActivity();
                            }

                        }
                    });
                    progressDialog.dismiss();
                 }
             break;

        }


    }
    public void rootLayoutTappedLI (View view){
        try{
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void transitionToSocialMediaActivity(){
        Intent intent = new Intent(LogInPage.this, SocialMediaActivity.class);
        startActivity(intent);
    }
}
