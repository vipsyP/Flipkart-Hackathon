package com.vipsy.flipkarthackathon.municipal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.vipsy.flipkarthackathon.MainActivity;
import com.vipsy.flipkarthackathon.R;

public class MunicipalActivity extends AppCompatActivity {

TextView tv_email;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in=new Intent(MunicipalActivity.this,MainActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_content_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MunicipalFragment())
                    .commit();

        }

        tv_email= (TextView)findViewById(R.id.tv_email);
        Intent i = getIntent();
        tv_email.setText(i.getStringExtra("userid"));
        }
}
