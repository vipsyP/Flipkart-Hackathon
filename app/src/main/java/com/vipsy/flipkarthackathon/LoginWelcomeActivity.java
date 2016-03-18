package com.vipsy.flipkarthackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class LoginWelcomeActivity extends AppCompatActivity {

TextView tv_name, tv_email, tv_role;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in=new Intent(LoginWelcomeActivity.this,MainActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_welcome);

        tv_name= (TextView)findViewById(R.id.tv_name);
        tv_email= (TextView)findViewById(R.id.tv_email);
        tv_role = (TextView)findViewById(R.id.tv_role);
        Intent i = getIntent();
        tv_name.setText(i.getStringExtra("Name"));
        tv_email.setText(i.getStringExtra("Email"));
        tv_role.setText(i.getStringExtra("Role"));
    }
}
