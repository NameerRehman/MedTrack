package com.example.nameer.medtrack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MedAdaptar extends RecyclerView.Adapter<MedAdaptar.ViewHolder> {

    private Context mCtx;
    private List<MedItem> medList;


    public MedAdaptar(Context mCtx, List<MedItem> medList) { //constructor to get context of main activity and medList data from main activity
        this.mCtx = mCtx;
        this.medList = medList;
    }

    //following method is called whenever new med is added, instance of ViewHolder class is created - each view holder represents a med and info about the med (i.e a single card)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View v = inflater.inflate(R.layout.med_item, parent, false);
        return new ViewHolder(v);
    }

    //following method binds data to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MedItem medItem = medList.get(position);
        holder.textViewMedName.setText(medItem.getMedName());
        holder.textViewStartDate.setText(medItem.getStartDate());
        holder.textViewEndDate.setText(medItem.getEndDate());
        holder.textViewCondition.setText((medItem.getCondition()));
        holder.textViewNotes.setText(medItem.getNotes());
    }

    @Override
    public int getItemCount() {

        return medList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public TextView textViewMedName;
        public TextView textViewStartDate;
        public TextView textViewEndDate;
        public TextView textViewCondition;
        public TextView textViewNotes;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewMedName = (TextView) itemView.findViewById(R.id.medName);
            textViewStartDate = (TextView) itemView.findViewById(R.id.startDate);
            textViewEndDate = (TextView) itemView.findViewById(R.id.endDate);
            textViewCondition = (TextView) itemView.findViewById(R.id.condition);
            textViewNotes = (TextView) itemView.findViewById(R.id.notes);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v){
            Toast.makeText(mCtx, "click", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onLongClick(View v){
            Toast.makeText(mCtx, "onLongclick", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
