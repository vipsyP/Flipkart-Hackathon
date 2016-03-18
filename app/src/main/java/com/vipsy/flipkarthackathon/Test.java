package com.vipsy.flipkarthackathon;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by MEGHANA on 18-03-2016.
 */
public class Test extends Activity
{
    static String urlString;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        urlString = "http://192.168.169.29:5000/FKPrep/Test";
        MyTask m = new MyTask();
        m.execute(urlString);
    }

    class MyTask extends AsyncTask<String, String, String> {
        String response;

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            response = ConnectionClass.send(urlString);
            Log.d("not", "doInBackground");
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            tv = (TextView)findViewById(R.id.textView);
            Log.d("result is ",result);
            tv.setText(result);
        }
    }

}
