package com.example.pabrand.projet_android;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class Creer_rdv extends AppCompatActivity {

    private TextView affDate;
    private DatePickerDialog.OnDateSetListener dateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_rdv);
    }

    public void choisirDate(View view){
        affDate = (TextView) findViewById(R.id.Texte_Date);
        Log.d("Creer_rdv", "choisirDate");

        Calendar cal = Calendar.getInstance();
        int annee = cal.get(Calendar.YEAR);
        int mois = cal.get(Calendar.MONTH);
        int jour = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dateDial = new DatePickerDialog(Creer_rdv.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateListener, annee, mois, jour);
        dateDial.setTitle("Date de rdv");
        dateDial.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dateDial.show();
        Log.d("Creer_rdv", "datedial.show");

        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int annee_set, int mois_set, int jour_set) {
                Log.d("Creer_rdv", "Entrée DPDialog");
                mois_set ++;
                affDate.setText(jour_set + "/" + mois_set + "/" + annee_set);
            }
        };
    }
}
