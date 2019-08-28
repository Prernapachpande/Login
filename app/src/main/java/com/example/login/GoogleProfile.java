package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

public class GoogleProfile extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    Button sign_out;
    TextView name,email,id;
    ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_profile);

        sign_out = (Button) findViewById(R.id.log_out);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        id = (TextView) findViewById(R.id.id);
        photo = (ImageView) findViewById(R.id.photo);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(GoogleProfile.this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            name.setText("Name: " + personName);
            email.setText("Email: " + personEmail);
            id.setText("ID: " + personId);
            Picasso.with(this).load(personPhoto).into(photo);
        }
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete( Task<Void> task) {
                        Toast.makeText(GoogleProfile.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(GoogleProfile.this, MainActivity.class));
                        finish();
                    }
                });
    }

}

