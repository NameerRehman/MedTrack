package com.example.nameer.medtrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MedEdit extends AppCompatActivity {

    private MedItem medEdit;
    private TextView EselectStart;
    private TextView EselectEnd;
    private EditText EselectMed;
    private EditText EsetCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_edit);

        EselectStart = (TextView) findViewById(R.id.EselectStart);
        EselectEnd = (TextView) findViewById(R.id.EselectEnd);
        EselectMed = (EditText) findViewById(R.id.EselectMed);
        EsetCondition = (EditText) findViewById(R.id.EsetCondition);

        medName();
    }

    public MedEdit(MedItem medEdit) {
        this.medEdit = medEdit;
    }



    public void medName(){
        EselectMed.setText(medEdit.getMedName());
    }
}
