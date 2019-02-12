package com.example.nisha.saveit.user_sign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nisha.saveit.MainActivity;
import com.example.nisha.saveit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogIn;
    private TextInputLayout inputEmail,inputPass;
    private FirebaseAuth fAuth;
    private DatabaseReference fUserDatabase;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail = findViewById(R.id.input_log_email);
        inputPass = findViewById(R.id.input_log_pass);
        btnLogIn = findViewById(R.id.btn_log);
        fAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inEmail = inputEmail.getEditText().getText().toString().trim();
                String inPass = inputPass.getEditText().getText().toString().trim();
                if(!TextUtils.isEmpty(inEmail)&&!TextUtils.isEmpty(inPass))
                LogIn(inEmail,inPass);

            }
        });

    }

    private void LogIn(String email, String pass){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging, please wait...");
        progressDialog.show();
        fAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                    if(task.isSuccessful()){
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();

                        Toast.makeText(LoginActivity.this, "Sign In successful " , Toast.LENGTH_SHORT).show();

                    }
                        else{
                        Toast.makeText(LoginActivity.this, "ERROR : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                    }
                });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
