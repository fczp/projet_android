package com.example.pabrand.projet_android;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class Creer_rdv extends AppCompatActivity {

    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    private TextView affDate;
    private DatePickerDialog.OnDateSetListener dateListener;
    private String numero_manuel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_rdv);
    }

    public void pickContact(View view) {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        // Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                Uri contactUri = resultIntent.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);

                TextView t1 = findViewById(R.id.contact);
                String texte = t1.getText().toString();
                texte += number + ";";
                t1.setText(texte);
            }
        }
    }

    public void choisirDate(View view) {
        affDate = findViewById(R.id.Texte_Date);
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
                mois_set++;
                affDate.setText(jour_set + "/" + mois_set + "/" + annee_set);
            }
        };
    }

    public void creerRdv(View view) {
        double longitude = 0;
        double latitude = 0;
        String[] tabNumero;
        TextView t1 = findViewById(R.id.contact);
        String numeros = t1.getText().toString();
        tabNumero = numeros.split(";");
        /*for(int i=0; i< tabNumero.length;i++){
            Toast toast = Toast.makeText(getApplicationContext(), tabNumero[i], Toast.LENGTH_SHORT);
            toast.show();
        }*/

        LocationManager lm = null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            lm.getLastKnownLocation(lm.GPS_PROVIDER);
        }
        Location lo = null;
        lo.getLatitude();

        Toast toast = Toast.makeText(getApplicationContext(), "Lat : " + lo.getLatitude() +"\nLon : " + lo.getLongitude(), Toast.LENGTH_SHORT);
        toast.show();
    }

    public void ajout_manuel(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter un numéro");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                numero_manuel = input.getText().toString();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                numero_manuel = input.getText().toString();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (numero_manuel.equals("")) {
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Numéro non précisé !", Toast.LENGTH_SHORT);
                    toast1.show();
                }else{
                    TextView t1 = findViewById(R.id.contact);
                    String texte = t1.getText().toString();
                    texte += numero_manuel + ";";
                    t1.setText(texte);
                    numero_manuel = "";
                }
            }
        });

        builder.show();
    }

}
