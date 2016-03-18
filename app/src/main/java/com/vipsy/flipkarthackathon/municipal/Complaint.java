package com.vipsy.flipkarthackathon.municipal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.vipsy.flipkarthackathon.R;

import org.w3c.dom.Text;

public class Complaint extends AppCompatActivity{
    TextView TVComplainerName, TVComplainerEmail, TVComplaintTitle, TVComplaintBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.municipal_complaint);
        Intent i = getIntent();
        TVComplainerName = (TextView)findViewById(R.id.TVComplainerName);
        TVComplainerEmail = (TextView)findViewById(R.id.TVComplainerEmail);
        TVComplaintTitle = (TextView)findViewById(R.id.TVComplaintTitle);
        TVComplaintBody = (TextView)findViewById(R.id.TVComplaintBody);

        TVComplainerName.setText(i.getStringExtra("Name"));
        TVComplaintTitle.setText(i.getStringExtra("Complaint Title"));
        TVComplaintBody.setText(i.getStringExtra("Complaint Body"));

    }
}
