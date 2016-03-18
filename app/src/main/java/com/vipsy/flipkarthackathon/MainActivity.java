package com.vipsy.flipkarthackathon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    ViewFlipper viewFlipper;
    Button bt_login, bt_signup;
    Button bt_sign_in, bt_create;
    EditText et_name, et_email, et_password;
    Spinner spinner;
    LinearLayout lt_name;
    ArrayList<String> name_al= new ArrayList<>();
    ArrayList<String> email_al= new ArrayList<>();
    ArrayList<String> password_al= new ArrayList<>();
    ArrayList<String> role_al = new ArrayList<>();
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor sharededitor;
    String myPref = "myPref";
    Set<String> NameSet;
    Set<String> EmailSet;
    Set<String> PasswordSet;
    Set<String> ContactSet;
    String information=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);


        spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("User");
        categories.add("Municipal officer");
        categories.add("Garbage collector");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_signup = (Button) findViewById(R.id.bt_signup);
        bt_sign_in = (Button) findViewById(R.id.bt_sign_in);
        bt_create = (Button) findViewById(R.id.bt_create);
        bt_login.setOnClickListener(this);
        bt_signup.setOnClickListener(this);
        bt_sign_in.setOnClickListener(this);
        bt_create.setOnClickListener(this);
        lt_name= (LinearLayout) findViewById(R.id.lt_name);
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bt_login:
                lt_name.setVisibility(View.INVISIBLE);
                viewFlipper.setDisplayedChild(0);
                break;
            case R.id.bt_signup:
                lt_name.setVisibility(View.VISIBLE);
                viewFlipper.setDisplayedChild(1);
                break;


            //Sign in!!
            case R.id.bt_sign_in: {
                String t_email, t_password;

                t_email = et_email.getText().toString();
                t_password = et_password.getText().toString();
                Log.v("Username and password", et_email + " / " + et_password);
                LoginTask loginTask = new LoginTask();
                loginTask.execute(t_email,t_password);
                break;
            }
            //Create profile!!
            case R.id.bt_create: {boolean check_valid = true;
                String t_name, t_email, t_password, t_role;
                t_name = et_name.getText().toString();
                t_email = et_email.getText().toString();
                t_password = et_password.getText().toString();
                //t_role = et_phone.getText().toString();
                t_role = spinner.getSelectedItem().toString();
                RegistrationTask registrationTask=new RegistrationTask();
                registrationTask.execute(t_name,t_email,t_password,t_role);

            }
            break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    }

class LoginTask extends AsyncTask<String , String , String>{
    @Override
    protected String doInBackground(String... strings) {
        String email=strings[0];
        String password=strings[1];
        Uri builduri= Uri.parse("http://192.168.169.29:5000/FKPrep/Login?").buildUpon().appendQueryParameter("EmailId",email)
                .appendQueryParameter("Password",password).build();
        ConnectionClass conn=new ConnectionClass();
        return conn.send(builduri.toString());
    }
    protected void onPostExecute(String result) {
        //calling the other activity(User, Garbage Collector,Municipal Officer)
        Log.v("Login completed",result);
        if(result.equals("User")){
            Log.d("login","user");
        }
        else if(result.equals("Garbage collector")){
            Log.d("login","garbage collector");
        }
        else if(result.equals("Municipal officer")){
            Log.d("login","municipal officer");
        }
        else{
            Log.d("login","failed");
        }

    }
}

class RegistrationTask extends AsyncTask<String , String , String>{
    @Override
    protected String doInBackground(String... strings) {
        String name=strings[0];
        String email=strings[1];
        String password=strings[2];
        String role=strings[3];
        Log.v("credentials",name+" "+email+" "+password+" "+role);
        Uri builduri= Uri.parse("http://192.168.169.29:5000/FKPrep/Registration?").buildUpon().appendQueryParameter("EmailId", email)
                .appendQueryParameter("Password", password).appendQueryParameter("Name", name).appendQueryParameter("Role", role).build();
        ConnectionClass conn=new ConnectionClass();
        return conn.send(builduri.toString());
    }
    protected void onPostExecute(String result) {
        //calling the other activity
        Log.v("registration success",result);
        if(result.equals("0")){
            Log.d("regi","user");
        }
        else if(result.equals("1")){
            Log.d("regi","garbage collector");
        }
        else if(result.equals("2")){
            Log.d("regi","municipal officer");
        }
        else{
            Log.d("regi","failed");
        }

    }
}





