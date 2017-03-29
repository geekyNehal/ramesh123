package com.example.ravitejakorlakunta.fire2;

import android.app.Dialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    Button b1;
    EditText mail;
    EditText pass;
    TextView t1;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            Intent intent=new Intent(this,profile.class);
            startActivity(intent);
            finish();
        }
        b1=(Button)findViewById(R.id.button1);
        mail=(EditText)findViewById(R.id.editText1);
        pass=(EditText)findViewById(R.id.editText2);
        t1=(TextView)findViewById(R.id.text);
        progressDialog=new ProgressDialog(this);

        b1.setOnClickListener(this);
        t1.setOnClickListener(this);
    }
    public void regeiter()
    {
        String mai=mail.getText().toString().trim();
        String pas=pass.getText().toString().trim();
        if(mai.isEmpty())
        {
            Toast.makeText(this,"ENTER MAIL ID",Toast.LENGTH_SHORT).show();
        return;
        }
        if(pas.isEmpty())
        {
        Toast.makeText(this,"ENTER PASSWORD",Toast.LENGTH_SHORT).show();
        return;
        }
        progressDialog.setMessage("please wait");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(mai,pas).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "SUCCESSFUL REGISTERD", Toast.LENGTH_SHORT).show();
                    email();
                    Intent intent = new Intent(MainActivity.this, loginpage.class);
                    startActivity(intent);
                    finish();
                }

                else
                {
                    progressDialog.cancel();
                    Toast.makeText(MainActivity.this,"COULD NOT REGISTERD",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    public void email()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task)
            {
                if (task.isSuccessful()) {
                    String TAG="haii";
                    Log.d(TAG, "Email sent.");
                }
            }
        });
    }

    public void onClick(View view)
    {
        if(view==b1)
        {
        regeiter();
        }
        else
        {
            Intent intent=new Intent(this,loginpage.class);
            startActivity(intent);
            finish();
        }

    }
}
