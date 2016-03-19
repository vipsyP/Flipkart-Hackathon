package com.vipsy.flipkarthackathon.user;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vipsy.flipkarthackathon.ConnectionClass;
import com.vipsy.flipkarthackathon.R;
import com.vipsy.flipkarthackathon.RecyclerItemClickListener;
import com.vipsy.flipkarthackathon.municipal.Complaint;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class ResolvedFragment extends Fragment {

    RecyclerView rv;
    ResolvedRVAdapter adapter;
    List<ResolvedPerson> persons;
    View rootView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(ResolvedFragment.class.getSimpleName(), "Works till onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(ResolvedFragment.class.getSimpleName(), "Works 2");
        String userid=getActivity().getIntent().getStringExtra("userid");
        rootView = inflater.inflate(R.layout.resolved_fragment, container, false);
        UserResolvedTask m = new UserResolvedTask();
        m.execute(userid);
        initializeData();


        rv = (RecyclerView)rootView.findViewById(R.id.RV);
        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getActivity(), Complaint.class);
                        i.putExtra("Complain ID", ""+persons.get(position).complain_id);
                        i.putExtra("Name", ""+persons.get(position).name);
                        i.putExtra("Complaint Title", ""+persons.get(position).complaint_title);
                        i.putExtra("Complaint Body", ""+persons.get(position).complaint_body);
                        startActivity(i);
                    }
                })
        );
        adapter = new ResolvedRVAdapter(persons);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);

        return rootView;
    }
    private void initializeData(){
        //Dummy data
        persons = new ArrayList<>();
        persons.add(new ResolvedPerson("1", "Vimal", "Complaint title", "Complaint body", "Resolved", "02:45"));
        /*persons.add(new ResolvedPerson("2", "Yesh", "Complaint title", "Complaint body", "Resolved", "02:45"));
        persons.add(new ResolvedPerson("3", "Megh", "Complaint title", "Complaint body", "Resolved", "02:45"));
        persons.add(new ResolvedPerson("4", "JSP", "Complaint title", "Complaint body", "Resolved", "02:45"));
        persons.add(new ResolvedPerson("5", "Denzil", "Complaint title", "Complaint body", "Resolved", "02:45"));
        persons.add(new ResolvedPerson("6", "Animesh", "Complaint title", "Complaint body", "Resolved", "02:45"));
        persons.add(new ResolvedPerson("7", "Sai", "Complaint title", "Complaint body", "Resolved", "02:45"));
    */}
    class UserResolvedTask extends AsyncTask<String , String , String> {
        String userid;
        @Override
        protected String doInBackground(String... strings) {
            userid=strings[0];
            Uri builduri= Uri.parse("FKPrep/GetUserComplaintFiles?").buildUpon().appendQueryParameter("EmailId", userid).appendQueryParameter("type","2")
                    .build();
            ConnectionClass conn=new ConnectionClass();
            return conn.send(builduri.toString());
        }
        protected void onPostExecute(String result) {
            //calling the other activity(User, Garbage Collector,Municipal Officer)
            Log.v("Login completed", result);
            try {
                JSONArray info = new JSONArray(result);
                String cid=null;
                String cname=null;
                String cdes=null;
                String date=null;
                String status="Resolved";
                persons = new ArrayList<>();
                for(int i=0;i<info.length();i++)
                {
                    if(info.getJSONObject(i).has("cid")){
                        cid=info.getJSONObject(i).getString("cid");
                    }
                    if(info.getJSONObject(i).has("cname")){
                        cname=info.getJSONObject(i).getString("cname");
                    }
                    if(info.getJSONObject(i).has("cdesc")){
                        cdes=info.getJSONObject(i).getString("cdesc");
                    }
                    if(info.getJSONObject(i).has("time")){
                        date=info.getJSONObject(i).getString("time");
                    }
                    if(info.getJSONObject(i).has("userid")){
                        userid=info.getJSONObject(i).getString("userid");
                    }
                    persons.add(new ResolvedPerson(cid, userid, cname, cdes, status, date));
                }

            }
            catch(JSONException je)
            {
                Log.e("json error",je.getMessage());
            }
            rv = (RecyclerView)rootView.findViewById(R.id.RV);
            rv.addOnItemTouchListener(
                    new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(), Complaint.class);
                            i.putExtra("Complain ID", "" + persons.get(position).complain_id);
                            i.putExtra("Name", "" + persons.get(position).name);
                            i.putExtra("Complaint Title", "" + persons.get(position).complaint_title);
                            i.putExtra("Complaint Body", "" + persons.get(position).complaint_body);
                            startActivity(i);
                        }
                    })
            );
            adapter = new ResolvedRVAdapter(persons);
            rv.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            rv.setLayoutManager(llm);
            rv.setAdapter(adapter);

        }
    }
}
