package com.example.pabrand.projet_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.Settings;
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

    boolean started = false;
    boolean enabled = false;

    MyGPS monGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_rdv);
        monGPS = new MyGPS(this);
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

                // Get the URI that points to the selected contact
                Uri contactUri = resultIntent.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using <code><a href="/reference/android/content/CursorLoader.html">CursorLoader</a></code> to perform the query.
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);

                TextView t1 = (TextView) findViewById(R.id.contact);
                String texte = t1.getText().toString();
                texte += number +";";
                t1.setText(texte);
            }
        }
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

    public void creerRdv(View view){
        String[] tabNumero;
        TextView t1 = (TextView) findViewById(R.id.contact);
        String numeros = t1.getText().toString();
        tabNumero = numeros.split(";");

        if(!monGPS.dispoGPS){
            Toast.makeText(this, "Activez le GPS pour ce service.", Toast.LENGTH_LONG).show();
            Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(i, 1);
        }
        else if(monGPS.dispoLoc)
            monGPS.getLocation();
        Toast.makeText(this, monGPS.getLatitude() +" , " +monGPS.getLongitude(), Toast.LENGTH_LONG).show();

    }

    public void ajout_manuel(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

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
                    TextView t1 = (TextView) findViewById(R.id.contact);
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
