package com.nrinfinity.nameer.medtrack;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.List;

public class MedAdaptar extends RecyclerView.Adapter<MedAdaptar.ViewHolder> {

    private Context mCtx;
    private List<MedItem> mMedList; // Cached copy of medList
    private MedItem currentPosition;
    public static final int EDIT_MEDITEM_REQUEST_CODE = 1;

    //constructor to get context of main activity and medList data from main activity
    public MedAdaptar(Context mCtx) {
        this.mCtx = mCtx;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            currentPosition = mMedList.get(getLayoutPosition());
            Intent i = new Intent(mCtx, MedEdit.class);
            i.putExtra("position", currentPosition);
            ((MainActivity) mCtx).startActivityForResult(i,2);
            //Toast.makeText(mCtx, Integer.toString(currentPosition), Toast.LENGTH_SHORT).show();

        }
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
        if (mMedList != null) {
            MedItem medItem = mMedList.get(position);
            holder.textViewMedName.setText(medItem.getMedName());
            holder.textViewStartDate.setText(medItem.getStartDate());
            holder.textViewEndDate.setText(medItem.getEndDate());
            holder.textViewCondition.setText((medItem.getCondition()));
        } else { // Covers the case of data not being ready yet.
            holder.textViewMedName.setText("no input");
            holder.textViewStartDate.setText("no input");
            holder.textViewEndDate.setText("no input");
            holder.textViewCondition.setText("no input");
            holder.textViewNotes.setText("no input");
        }
    }

    void setMedList(List<MedItem> medList){
        mMedList = medList;
        notifyDataSetChanged();
    }


    public MedItem getPosition(){
        return currentPosition;
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mMedList != null) {
            return mMedList.size();
        } else {
            return 0;
        }
    }


}
