package com.vipsy.flipkarthackathon.user;

import android.content.Intent;
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

import com.vipsy.flipkarthackathon.R;
import com.vipsy.flipkarthackathon.RecyclerItemClickListener;
import com.vipsy.flipkarthackathon.municipal.Complaint;

import java.util.ArrayList;
import java.util.List;


public class TrashCanFragment extends Fragment {

    RecyclerView rv;
    TrashCanRVAdapter adapter;
    List<TrashCanPerson> persons;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TrashCanFragment.class.getSimpleName(), "Works till onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TrashCanFragment.class.getSimpleName(), "Works 2");
        View rootView = inflater.inflate(R.layout.resolved_fragment, container, false);
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
        adapter = new TrashCanRVAdapter(persons);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);

        return rootView;
    }
    private void initializeData(){
        //Dummy data
        persons = new ArrayList<>();
        persons.add(new TrashCanPerson("1", "Vimal", "Complaint title", "Complaint body", "TrashCan", "02:45"));
        persons.add(new TrashCanPerson("2", "Yesh", "Complaint title", "Complaint body", "TrashCan", "02:45"));
        persons.add(new TrashCanPerson("3", "Megh", "Complaint title", "Complaint body", "TrashCan", "02:45"));
        persons.add(new TrashCanPerson("4", "JSP", "Complaint title", "Complaint body", "TrashCan", "02:45"));
        persons.add(new TrashCanPerson("5", "Denzil", "Complaint title", "Complaint body", "TrashCan", "02:45"));
        persons.add(new TrashCanPerson("6", "Animesh", "Complaint title", "Complaint body", "TrashCan", "02:45"));
        persons.add(new TrashCanPerson("7", "Sai", "Complaint title", "Complaint body", "TrashCan", "02:45"));
    }
}
