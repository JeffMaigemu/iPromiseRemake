package maigemu.com.ipromiseremake.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import maigemu.com.ipromiseremake.R;
import maigemu.com.ipromiseremake.data.AppStatus;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnSignUp.setEnabled(false);

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    btnSignUp.setEnabled(true);
                    Toast toast = Toast.makeText(getApplicationContext(),"Enter email address!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 80);
                    toast.show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    btnSignUp.setEnabled(true);
                    Toast toast = Toast.makeText(getApplicationContext(),"Enter password!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 80);
                    toast.show();
                    return;
                }

                if (password.length() < 6) {
                    btnSignUp.setEnabled(true);
                    Toast toast = Toast.makeText(getApplicationContext(),"Password too short, enter minimum 6 characters.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 80);
                    toast.show();
                    return;


                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    btnSignUp.setEnabled(true);

                    Toast toast = Toast.makeText(getApplicationContext(), "Please, enter a valid email address", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 80);
                    toast.show();
                    // Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {

                                    if (AppStatus.getInstance(SignupActivity.this).isOnline()) {

                                        Toast.makeText(SignupActivity.this, "You are Online", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        btnSignUp.setEnabled(true);

                                    } else {

                                        Toast.makeText(SignupActivity.this, "You are Not Online", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        btnSignUp.setEnabled(true);

                                    }

                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(SignupActivity.this, "User with this email already exists.", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        btnSignUp.setEnabled(true);
                                    }
                                    else {
                                        Toast toast2 = Toast.makeText(SignupActivity.this,"Authentication failed. Check your Internet Connection", Toast.LENGTH_LONG);
                                        toast2.setGravity(Gravity.TOP| Gravity.CENTER, 0, 80);
                                        toast2.show();
                                        progressBar.setVisibility(View.GONE);
                                        btnSignUp.setEnabled(true);
                                    }


                                } else {
                                    Toast toast = Toast.makeText(SignupActivity.this,"Your new Smartweb Account Has been  Created!", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 80);
                                    toast.show();
                                    progressBar.setVisibility(View.GONE);

                                    startActivity(new Intent(SignupActivity.this, UserDetailsActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }


}

