package com.lazycoder.quickaid.SignIn;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.lazycoder.quickaid.DashBoard.DashBoard;
import com.lazycoder.quickaid.ForgetPassword.Reset;
import com.lazycoder.quickaid.RegistrationActivity;
import com.lazycoder.quickaid.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignIn extends AppCompatActivity {

    TextView forgotTv,socialTv;
    ImageView LogoImage,plus,facebook,twitter;
    Button signIn,signUp;
    EditText EtEmail,EtPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;


    @Override
    protected void attachBaseContext(Context newBase) {
//
//        //17.04.19
//        MultiDex.install(this);
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Arkhip_font.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Make to run your application only in portrait mode
        requestWindowFeature(1);
        getWindow().setFlags(1024,1024);

        //Get firebase auth instance
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()!= null){
            //startActivity(new Intent( ));
        }
        setContentView(R.layout.activity_sign_in);

        forgotTv = (TextView)findViewById(R.id.text_forgot);
        socialTv = (TextView)findViewById(R.id.social_signIN);

        LogoImage = (ImageView)findViewById(R.id.imageView);
        plus = (ImageView)findViewById(R.id.plus);
        facebook = (ImageView)findViewById(R.id.facebook);
        twitter = (ImageView)findViewById(R.id.twitter);

        signIn = (Button)findViewById(R.id.signin);
        signUp = (Button)findViewById(R.id.signup);

        EtEmail =(EditText)findViewById(R.id.username);
        EtPassword = (EditText)findViewById(R.id.password);



        forgotTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, Reset.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SignIn.this, RegistrationActivity.class));
                Intent intent = new Intent(SignIn.this, RegistrationActivity.class);
                startActivity(intent);
                return;
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EtEmail.getText().toString().trim();
                final String password =EtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

               // progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //if sign in fails,display a message to the user
                                //the auth state listener will be notified and logic handle the
                                //signed in user can be handled in the listener
                               // progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()){
                                    //there was an error
                                    if (password.length()<6){
                                        EtPassword.setError(getString(R.string.input_error_password_length));
                                    }else {
                                        Toast.makeText(SignIn.this, getString(R.string.input_error_password), Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Intent intent = new Intent(SignIn.this, DashBoard.class);
                                    startActivity(intent);
                                    return;
                                }
                            }
                        });
            }
        });



    }
}
