package com.example.apppro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    EditText emailET;
    EditText passEt;
    EditText nameet;
    FirebaseDatabase database;
    FirebaseAuth auth;
    DatabaseReference refrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailET = findViewById(R.id.emails);
        passEt = findViewById(R.id.passs);
        nameet= findViewById(R.id.names);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        refrence = database.getReference();


    }
    public void signupBtn(View view) {

        final String name = nameet.getText().toString();

        final String email = emailET.getText().toString();
        final String pass = passEt.getText().toString();



        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    String key = auth.getCurrentUser().getUid();
                    Log.e("key", key);
                    User user=new User(name,email,pass);
                    refrence.child(key).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(Signup.this,MainActivity.class);
//                                intent.putExtra("name",name);
//                                intent.putExtra("pass",pass);
//                                intent.putExtra("email",email);
                                startActivity(intent);}
                            else{
                                Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {
                    Toast.makeText(Signup.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}
