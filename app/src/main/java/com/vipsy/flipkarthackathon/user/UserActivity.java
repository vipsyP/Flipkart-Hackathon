package com.vipsy.flipkarthackathon.user;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

//import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.vipsy.flipkarthackathon.MainActivity;
import com.vipsy.flipkarthackathon.R;
import com.vipsy.flipkarthackathon.user.UnresolvedFragment;

public class UserActivity extends AppCompatActivity {

    static FragmentManager manager;
    static FragmentTransaction transaction;
    UnresolvedFragment u;
    ResolvedFragment newFragment1;
    TrashCanFragment newFragment2;
    TextView tv_name, tv_email, tv_role;
    //android.app.FragmentManager manager;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in=new Intent(UserActivity.this,MainActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_content_main);

        if (savedInstanceState == null) {
            /*manager= getSupportFragmentManager();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new UnresolvedFragment())
                    .commit();
*/
            u = new UnresolvedFragment();
            manager = getFragmentManager();
            transaction = manager.beginTransaction();
            transaction.add(R.id.container, u);
            transaction.commit();
        }


        tv_name= (TextView)findViewById(R.id.tv_name);
        tv_email= (TextView)findViewById(R.id.tv_email);
        tv_role = (TextView)findViewById(R.id.tv_role);
        Intent i = getIntent();
        tv_name.setText(i.getStringExtra("Name"));
        tv_email.setText(i.getStringExtra("Email"));
        tv_role.setText(i.getStringExtra("Role"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing_menu, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ComplainsFiled:
                Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                // Create new fragment and transaction
                newFragment1 = new ResolvedFragment();

                manager = getFragmentManager();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.container, newFragment1);
                transaction.commit();
                Log.e(UserActivity.class.getSimpleName(), "Unresolved");

                return true;
            case R.id.ResolvedComplaints:
                Toast.makeText(getApplicationContext(),"Item 2 Selected",Toast.LENGTH_LONG).show();
                // Create new fragment and transaction
                newFragment1 = new ResolvedFragment();

                manager = getFragmentManager();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.container, newFragment1);
                transaction.commit();
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
                //transaction1.replace(R.id.LLRV, newFragment1);


// Commit the transaction
            //    transaction1.commit();
                Log.e(UserActivity.class.getSimpleName(), "Resolved");

                return true;
            case R.id.FindTrashCan:
                Toast.makeText(getApplicationContext(),"Item 2 Selected",Toast.LENGTH_LONG).show();
                // Create new fragment and transaction
                newFragment2 = new TrashCanFragment();

                manager = getFragmentManager();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.container, newFragment2);
                transaction.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
