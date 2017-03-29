package com.example.ravitejakorlakunta.fire2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;

public class loginpage extends AppCompatActivity implements View.OnClickListener
{
    ProgressDialog pd;
    Button b2;
    EditText e1,e2;
    TextView t1;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        firebaseAuth=FirebaseAuth.getInstance();
        b2=(Button)findViewById(R.id.button4);
        e1=(EditText)findViewById(R.id.editText8);
        e2=(EditText)findViewById(R.id.editText9);
        t1=(TextView)findViewById(R.id.textView6);
        pd=new ProgressDialog(this);
        b2.setOnClickListener(this);
        t1.setOnClickListener(this);
    }
    public void login()
    {
        final String s1=e1.getText().toString().trim();
        final String s2=e2.getText().toString().trim();
        if(s1.isEmpty())
        {
            Toast.makeText(this,"ENTER MAIL ID",Toast.LENGTH_SHORT).show();
            return;

        }
        if(s2.isEmpty())
        {
            Toast.makeText(this,"ENTER PASSWORD",Toast.LENGTH_SHORT).show();
            return;

        }
        pd.setMessage("PLEASE WAIT..!");
        pd.show();

        firebaseAuth.fetchProvidersForEmail(s1);

    }
    public void check(String s1 ,String s2)
    {
        firebaseAuth.signInWithEmailAndPassword(s1,s2).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    pd.cancel();
                    emailverified();
                }
                else
                {    pd.cancel();
                    Toast.makeText(loginpage.this, "Entered password is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void emailverified()
    {
        user=FirebaseAuth.getInstance().getCurrentUser();
        if(user.isEmailVerified()==true)
        {
            Intent intent=new Intent(loginpage.this,profile.class);
            startActivity(intent);
            finish();

        }
        else
        {
            pd.cancel();
            Toast.makeText(loginpage.this, "Verify email send from us", Toast.LENGTH_SHORT).show();
            return;
        }


    }
    public void onClick(View view)
    {
        if(view == b2)
        {
            login();
        }
        else
        {
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();

        }

    }
}
