package com.vipsy.flipkarthackathon.municipal;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vipsy.flipkarthackathon.ConnectionClass;
import com.vipsy.flipkarthackathon.MainActivity;
import com.vipsy.flipkarthackathon.R;

import org.w3c.dom.Text;

public class Complaint extends AppCompatActivity{
    TextView TVComplainerName, TVComplainerEmail, TVComplaintTitle, TVComplaintBody,TVComplaintId;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.municipal_complaint);
        i=getIntent();
        TVComplainerName = (TextView)findViewById(R.id.TVComplainerName);
       TVComplainerEmail = (TextView)findViewById( R.id.TVComplainerEmail);
        TVComplaintTitle = (TextView)findViewById(R.id.TVComplaintTitle);
        TVComplaintBody = (TextView)findViewById(R.id.TVComplaintBody);
        TVComplaintId=(TextView)findViewById(R.id.TVComplainerID);
        TVComplainerName.setText(i.getStringExtra("Name"));
        TVComplaintTitle.setText(i.getStringExtra("Complaint Title"));
        TVComplaintBody.setText(i.getStringExtra("Complaint Body"));
        TVComplaintId.setText(i.getStringExtra("Complain ID"));

    }
    public void onClickResolving(View v){
        Log.d("asd","asd");
        MunicipalResolvingTask municipalResolvingTask=new MunicipalResolvingTask();
        municipalResolvingTask.execute(i.getStringExtra("Complain ID"));

    }

    class MunicipalResolvingTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String cid = strings[0];
            Uri builduri = Uri.parse("FKPrep/ChangeAuthorityResolveStatus?").buildUpon()
                    .appendQueryParameter("cid", cid).appendQueryParameter("val","1").build();
            ConnectionClass conn = new ConnectionClass();
            return conn.send(builduri.toString());
        }

        protected void onPostExecute(String result) {
            //calling the other activity(User, Garbage Collector,Municipal Officer)
            Toast.makeText(getBaseContext(),"Now resolving",Toast.LENGTH_SHORT);
            finish();
        }
    }

}
