package com.davidm.lynch.acinstaclone;



// Player1 123, Player2 12345, User password

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnSignUp, btnLogIn;
    private EditText edtUserName, edtPassword, edtEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Save the current Installation to Back4App
        ParseInstallation.getCurrentInstallation().saveInBackground();

        setTitle("Sign Up");

        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtUserName = findViewById(R.id.edtUsername);
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
           // ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }

    }

    public void reset() {
        edtUserName.setText("");
        edtPassword.setText("");
        edtEmail.setText("");

    }
    @Override
    public void onClick(View view) {
        String email = edtEmail.getText().toString();
        final String username = edtUserName.getText().toString();
        String password = edtPassword.getText().toString();
        if (view == btnLogIn){
            //switch to Log In Page
            Intent intent = new Intent(MainActivity.this, LogInPage.class);
            startActivity(intent);
            reset();
        }
        else if(view == btnSignUp){
            if (email.equals("")){
                Toast.makeText(MainActivity.this, "Please enter an email to proceed", Toast.LENGTH_LONG).show();
            }else if (username.equals("")){
                Toast.makeText(MainActivity.this, "Please enter a username to proceed", Toast.LENGTH_LONG).show();
            }else if (password.equals("")){
                Toast.makeText(MainActivity.this, "Please enter a password to proceed", Toast.LENGTH_LONG).show();
            }else {
                final ParseUser user = new ParseUser();
                user.setEmail(email);
                user.setUsername(username);
                user.setPassword(password);

                final ProgressDialog progressDialog= new ProgressDialog(this);
                progressDialog.setMessage(username + "is being signed up.");
                progressDialog.show();


                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            Toast.makeText(MainActivity.this,  "Welcome, " + username + ".  You are now signed in", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            transitionToSocialMediaActivity();
                        }else{
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        }

    }
    public void rootLayoutTapped (View view){
        try{
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch(Exception e){
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    private void transitionToSocialMediaActivity(){
        Intent intent = new Intent(MainActivity.this, SocialMediaActivity.class);
        startActivity(intent);
    }
}
