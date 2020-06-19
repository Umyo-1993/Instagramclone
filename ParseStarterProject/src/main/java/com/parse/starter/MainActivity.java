/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  EditText Username,password;
  Button SignUp;
  TextView changeSignUpmodeTextview;
  boolean signUpmodeActive=true;
public void ShowUserList()
{

 Intent intent=new Intent(getApplicationContext(),UserListActivity.class);
 startActivity(intent);

}

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Username= (EditText) findViewById(R.id.user_name);
    password= (EditText) findViewById(R.id.user_password);
    SignUp= (Button) findViewById(R.id.signUpbutton);
    changeSignUpmodeTextview=(TextView)findViewById(R.id.changeSignUpmodetextview);
    changeSignUpmodeTextview.setOnClickListener(this);
    setTitle("Insta clone");
    ShowUserList();
    ParseAnalytics.trackAppOpenedInBackground(getIntent());



      SignUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (Username.getText().toString().matches("") || password.getText().toString().matches("")) {
            Toast.makeText(MainActivity.this, "Please don't make anyfield empty...", Toast.LENGTH_SHORT).show();
          } else {
            if (signUpmodeActive) {
              ParseUser user = new ParseUser();
              user.setUsername(Username.getText().toString());
              user.setPassword(password.getText().toString());
              user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                  if (e == null) {
                    Toast.makeText(MainActivity.this, "Sign Up successfull", Toast.LENGTH_SHORT).show();

                  } else {
                    Toast.makeText(MainActivity.this, "Error" + e, Toast.LENGTH_SHORT).show();
                  }
                }
              });
            }
            else
            {

              ParseUser.logInInBackground(Username.getText().toString(), password.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                  if(user!=null)
                  {
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    ShowUserList();
                  }
                  else
                  {

                    Toast.makeText(MainActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                  }
                }
              });


            }

          }


        }
      });


  }

  @Override
  public void onClick(View v) {

    if(v.getId()==R.id.changeSignUpmodetextview)
    {
      Button signUp= (Button) findViewById(R.id.signUpbutton);

            if(signUpmodeActive)
            {

              signUpmodeActive=false;
              signUp.setText("LogIn");
              changeSignUpmodeTextview.setText("Or,SignUp");
            }
            else {
              signUpmodeActive=true;
              signUp.setText("SignUp");
              changeSignUpmodeTextview.setText("Or,LogIn");
            }
    }


  }
}