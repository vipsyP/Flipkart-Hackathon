package com.vipsy.flipkarthackathon.municipal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vipsy.flipkarthackathon.R;

import java.util.List;


public class MunicipalRVAdapter extends RecyclerView.Adapter<MunicipalRVAdapter.PersonViewHolder>{

    List<MunicipalPerson> persons;

    public MunicipalRVAdapter(List<MunicipalPerson> persons){
        this.persons = persons;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.municipal_card_view, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.personName.setText(persons.get(position).name);
        holder.personComplaint.setText(persons.get(position).complaint_title);
        holder.personComplaint.setText(persons.get(position).complaint_body);
        holder.personStatus.setText(persons.get(position).status);
        holder.personTime.setText(persons.get(position).time);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout cv;
        TextView    personName;
        TextView    personComplaint;
        TextView   personStatus;
        TextView   personTime;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (RelativeLayout)itemView.findViewById(R.id.cv);
            personName=                (TextView)itemView.findViewById(R.id.TVName);
            personComplaint   =                 (TextView)itemView.findViewById(R.id.TVComplaint);
            personStatus   =                 (TextView)itemView.findViewById(R.id.TVStatus);
            personTime   =                 (TextView)itemView.findViewById(R.id.TVTime);

        }
    }

}