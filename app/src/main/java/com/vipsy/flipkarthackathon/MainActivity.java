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
        //et_phone = (EditText) findViewById(R.id.et_phone);


        sharedpreferences = getSharedPreferences(myPref, Context.MODE_PRIVATE);
        sharededitor = sharedpreferences.edit();

        //Retrieve Login details from SharedPreferences into Sets
        NameSet = sharedpreferences.getStringSet("Name", null);
        EmailSet = sharedpreferences.getStringSet("Email", null);
        PasswordSet = sharedpreferences.getStringSet("Password", null);
        ContactSet = sharedpreferences.getStringSet("Contact", null);

        //Convert Sets to ArrayLists
        name_al.clear();
        email_al.clear();
        password_al.clear();
        role_al.clear();
        try {
            name_al = new ArrayList<String>(NameSet);
            email_al = new ArrayList<String>(EmailSet);
            password_al = new ArrayList<String>(PasswordSet);
            role_al = new ArrayList<String>(ContactSet);
        }catch(NullPointerException e) {
            Log.e(MainActivity.class.getSimpleName(), "No data found: " + e.toString());
            name_al.clear();
            email_al.clear();
            password_al.clear();
            role_al.clear();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
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

                //Check if email is registered
                if(email_al.contains(t_email)) {
                    //Get ArrayList index corresponding to this email.
                    int index= email_al.indexOf(t_email);
                    //check password stored at this index
                    if(password_al.get(index).equals(t_password)) {

                        Log.v("Username and password", et_email + " / " + et_password);
                        LoginTask loginTask=new LoginTask();
                        loginTask.execute(t_email,t_password);
                       /* Intent i = new Intent(MainActivity.this, LoginWelcomeActivity.class);
                        i.putExtra("Name", name_al.get(index));
                        i.putExtra("Email", email_al.get(index));
                        i.putExtra("Role", role_al.get(index));
                        startActivity(i);
                        */
                    }
                    else {
                        Toast.makeText(this, "Did you forget your password?", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "Email not registered!", Toast.LENGTH_SHORT).show();
                }

            }
            break;

            //Create profile!!
            case R.id.bt_create: {
                boolean check_valid = true;
                String t_name, t_email, t_password, t_role;
                t_name = et_name.getText().toString();
                t_email = et_email.getText().toString();
                t_password = et_password.getText().toString();
                //t_role = et_phone.getText().toString();
                t_role = spinner.getSelectedItem().toString();

                //Check name
                {
                    Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(t_name);
                    if (t_name.length() < 1 || m.find()) {
                        check_valid = false;
                        Toast.makeText(this, "Check name", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                //Check email
                {
                    Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                    Matcher m = p.matcher(t_email);
                    if (!m.matches()) {
                        check_valid = false;
                        Toast.makeText(this, "Check email", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                //Check password
                {
                    Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(t_password);
                    if (t_password.length() >= 6 && t_password.matches(".*\\d.*")
                            && t_password.matches(".*[A-Z].*")
                            && t_password.matches(".*[a-z].*")
                            && m.find()) {
                    } else {
                        check_valid = false;
                        Toast.makeText(this, "Check password", Toast.LENGTH_SHORT).show();
                        break;
                    }

                }
                RegistrationTask registrationTask=new RegistrationTask();
                registrationTask.execute(t_email,t_password,t_name,t_role);
                //Check phone
                /*{
                *//*Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                Matcher m = p.matcher(t_role);*//*
                    if (t_role.length() == 10 && t_role.matches("\\d+")) {
                    } else {
                        check_valid = false;
                        Toast.makeText(this, "Check phone", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }*/
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
    private class LoginTask extends AsyncTask<String , Void , String> {
        @Override
        protected String doInBackground(String... strings) {
            String email=strings[0];
            String password=strings[1];
            HttpURLConnection urlConnection=null;
            BufferedReader reader = null;
            try {
                Uri builduri= Uri.parse("http://192.168.169.11:5000/FKPrep/Login?").buildUpon().appendQueryParameter("EmailId",email)
                        .appendQueryParameter("Password",password).build();

                URL url= new URL(builduri.toString());
                urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                information = buffer.toString();
                Log.v("got it",information);
                return information;
            } catch (Exception e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final Exception e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
        }
        protected void onPostExecute(String[] strings) {
            //calling the other activity
            Log.v("information",information);
            if(information=="0")
            {
                //user
                Log.v("login success","user");

            }
            else if(information=="1")
            {
                //authority
                Log.v("login success","authority");
            }
            else if(information=="2")
            {
                //collector
                Log.v("login success","collector");
            }
            else
            {
                //incorrect username and password
                Log.e("login failed","incorrect mail and password");
            }

        }
        }

    private class RegistrationTask extends AsyncTask<String , Void , String>{
        @Override
        protected String doInBackground(String... strings) {
            String name=strings[0];
            String email=strings[1];
            String password=strings[2];
            String role=strings[3];
            HttpURLConnection urlConnection=null;
            BufferedReader reader = null;
            try {
                Log.v("credentials",name+" "+email+" "+password+" "+role);
                Uri builduri= Uri.parse("http://192.168.169.11:5000/FKPrep/Registration?").buildUpon().appendQueryParameter("EmailId", email)
                        .appendQueryParameter("Password", password).appendQueryParameter("Name", name).appendQueryParameter("Role",role).build();

                URL url= new URL(builduri.toString());
                urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                InputStream  inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                information = buffer.toString();
                Log.v("got it",information);
                return information;
            } catch (Exception e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final Exception e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
        }
        protected void onPostExecute(String[] strings) {
            //calling the other activity
            if(information=="0")
            {
                //user
                Log.v("login success","user");

            }
            else if(information=="1")
            {
                //authority
                Log.v("login success","authority");
            }
            else if(information=="2")
            {
                //collector
                Log.v("login success","collector");
            }
            else
            {
                //incorrect username and password
                Log.e("login failed","incorrect mail and password");
            }


            Log.v("information",information);
        }
    }
    }


