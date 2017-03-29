package com.example.ravitejakorlakunta.fire2;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profile extends AppCompatActivity  implements View.OnClickListener
{
    Button b1,b2;
    TextView t1;
    EditText e1,e2;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        b1=(Button)findViewById(R.id.button2);
        b2=(Button)findViewById(R.id.button3);
        t1=(TextView)findViewById(R.id.textView2);
        e1=(EditText)findViewById(R.id.editText3);
        e2=(EditText)findViewById(R.id.editText4);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
         user=firebaseAuth.getCurrentUser();
        t1.setText("USERNAME :"+user.getEmail());
    }
    public void submit()
{
    String s1=e1.getText().toString();
    String s2=e2.getText().toString();
    userpermisson userpermisson1=new userpermisson(s1,s2);
    databaseReference.child(user.getUid()).setValue(userpermisson1);
    Toast.makeText(this,"information saved",Toast.LENGTH_SHORT).show();
}
    public void onClick(View view)
    {

        if(view==b1) {
            firebaseAuth.signOut();
            Intent intent = new Intent(this, loginpage.class);
            startActivity(intent);
            finish();
        }
        else
        {
            submit();
        }
    }
}
