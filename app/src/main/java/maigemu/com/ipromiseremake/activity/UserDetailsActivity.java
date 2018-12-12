package maigemu.com.ipromiseremake.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import maigemu.com.ipromiseremake.R;
import maigemu.com.ipromiseremake.data.AppStatus;
import maigemu.com.ipromiseremake.data.User;

public class UserDetailsActivity extends AppCompatActivity {

    private EditText inputFirstName, inputLastName, inputPhone;
    private Button btnSignIn, btnUploadUserDetails, btnResetPassword;
    private ProgressBar progressBar;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;
    private RadioButton inputIndigene, inputMaritalStatus;
    private RadioGroup radioIndigeneGroup;
    private RadioGroup radioMaritalGroup;
    int selectedIndigene;
    int selectedMarital;
    private FirebaseAuth auth;
    private String email;
    private FirebaseAuth.AuthStateListener authListener;


    private Spinner spinnerLGA, spinnerAge, spinnerOccupation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        addItemsOnSpinner2();
        addItemsOnSpinner3();
        addItemsOnSpinner4();

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(UserDetailsActivity.this, LoginActivity.class));
                    finish();
                }

                else {

                }
            }
        };

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");


        spinnerLGA = (Spinner) findViewById(R.id.LGA_Spinner);
        spinnerOccupation = (Spinner) findViewById(R.id.occupation_Spinner);
        spinnerAge = (Spinner) findViewById(R.id.Age_Spinner);
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnUploadUserDetails = (Button) findViewById(R.id.upload_details);
        progressBar = (ProgressBar) findViewById(R.id.progressBarUser);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        inputFirstName = (EditText) findViewById(R.id.firstName);
        inputLastName = (EditText) findViewById(R.id.lastName);
        inputPhone = (EditText) findViewById(R.id.phone);
        radioIndigeneGroup = (RadioGroup) findViewById(R.id.radioIndigene);
        radioMaritalGroup = (RadioGroup) findViewById(R.id.radioMaritalStatus);


        btnUploadUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                progressBar.setVisibility(View.VISIBLE);

                selectedIndigene = radioIndigeneGroup.getCheckedRadioButtonId();
                selectedMarital = radioMaritalGroup.getCheckedRadioButtonId();
                inputIndigene = (RadioButton) findViewById(selectedIndigene);
                inputMaritalStatus = (RadioButton) findViewById(selectedMarital);


                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    email = user.getEmail();

                }


                final String firstName = inputFirstName.getText().toString().trim();
                final String lastName = inputLastName.getText().toString().trim();
                final String LGA = String.valueOf(spinnerLGA.getSelectedItem());
                final String age = String.valueOf(spinnerAge.getSelectedItem());
                final String occupation = String.valueOf(spinnerOccupation.getSelectedItem());
                final String phone = inputPhone.getText().toString().trim();
                final String indigene = (String) inputIndigene.getText();
                final String marital = (String) inputMaritalStatus.getText();


                if (TextUtils.isEmpty(firstName)) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Enter your first name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(lastName)) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Enter your last name", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(phone)) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;

                }



                if (AppStatus.getInstance(UserDetailsActivity.this).isOnline()) {

                    createUser(email, firstName, lastName, phone, occupation,
                            LGA, indigene, marital, age);


                    AlertDialog alertDialog = new AlertDialog.Builder(
                            UserDetailsActivity.this).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Registration Complete");

                    // Setting Dialog Message
                    alertDialog.setMessage("Your registration on Plateau Voices is complete. You should get an email  to the address you entered in the Sign Up form asking you to verify your email address.");

                    // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.logo_one);

                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            startActivity(new Intent(UserDetailsActivity.this, MainActivity.class));

                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();


                } else {

                    Toast t = Toast.makeText(UserDetailsActivity.this,"It appears your internet connection is missing.Try again Later",Toast.LENGTH_LONG); t.show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
                //create user




            }
        });

    }


    private void addItemsOnSpinner4() {
        spinnerOccupation = (Spinner) findViewById(R.id.occupation_Spinner);
        List<String> list = new ArrayList<String>();

        list.add("Accounting");
        list.add("Politics");
        list.add("Manufacturing");
        list.add("Agriculture");
        list.add("Admin & Clerical");
        list.add("Franchise");
        list.add("Nonprofit");
        list.add("Banking & Finance");
        list.add("Civil Service");
        list.add("HealthCare");
        list.add("Retail");
        list.add("Private Business, Contract & Freelance");
        list.add("Hospitality");
        list.add("Sales & Marketing");
        list.add("Customer Service");
        list.add("Human Resources");
        list.add("Science & Biotech");
        list.add("Information Technology");
        list.add("Transportation");
        list.add("Engineering");
        list.add("Security");
        list.add("Student");
        list.add("Other");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOccupation.setAdapter(dataAdapter);
    }

    private void addItemsOnSpinner3() {
        spinnerAge = (Spinner) findViewById(R.id.Age_Spinner);
        List<String> list = new ArrayList<String>();
        list.add("16-20");
        list.add("21-30");
        list.add("31-40");
        list.add("41-50");
        list.add("51-60");
        list.add("61 and above");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(dataAdapter);
    }

    private void addItemsOnSpinner2() {

        spinnerLGA = (Spinner) findViewById(R.id.LGA_Spinner);
        List<String> list = new ArrayList<String>();
        list.add("Non-Indigene");
        list.add("Barkin Ladi");
        list.add("Bassa");
        list.add("Bokkos");
        list.add("Jos-East");
        list.add("Jos-North");
        list.add("Jos-South");
        list.add("Kanam");
        list.add("Kanke");
        list.add(" Langtang North");
        list.add(" Langtang South");
        list.add("Mangu");
        list.add("Mikang");
        list.add("Pankshin");
        list.add("Qua'an Pan");
        list.add("Ryom");
        list.add("Shendam");
        list.add("Wase");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLGA.setAdapter(dataAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private void createUser( String email, String firstName, String lastName, String phone, String occupation,
                             String LGA, String indegeneStatus, String maritalStatus, String ageCategory) {


        btnUploadUserDetails.setEnabled(false);
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            userId = user.getUid();

            User userAdd = new User( email, firstName, lastName, phone, occupation,
                    LGA, indegeneStatus, maritalStatus, ageCategory);

            mFirebaseDatabase.child(userId).setValue(userAdd).addOnCompleteListener(UserDetailsActivity.this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Log.i("byJeff","Yes");
                        Intent settingsIntent = new Intent(UserDetailsActivity.this, MainActivity.class);
                        startActivity(settingsIntent);
                    }else {
                        Log.i("byJeff","No"+task.getException());
                    }
                }
            });


        } else {
            // No user is signed in
        }

        progressBar.setVisibility(View.GONE);
        btnUploadUserDetails.setEnabled(true);
        sendVerificationEmail();

        // addUserChangeListener();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void sendVerificationEmail()
    {
        FirebaseUser user= auth.getCurrentUser();
        if(user!=null){
            user.sendEmailVerification().addOnCompleteListener(UserDetailsActivity.this,new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.i("Success","Yes");

                    }
                    else {
                        Log.i("Success","No"+task.getException());
                    }
                }
            });
        }
    }

}
