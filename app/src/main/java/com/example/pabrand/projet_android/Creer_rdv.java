package com.example.pabrand.projet_android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        activerBouton();
    }

    public void pickContact(View view) {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
        activerBouton();
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
                activerBouton();
            }
        };
    }

    public void creerRdv(View view){

        if (obtenirDate().equals("") || obtenirNumeros().equals("")){
            Toast.makeText(this, "Veuillez spécifier un numéro et une date !", Toast.LENGTH_LONG).show();
            return;
        }

        String message_probleme = "";

        if (ContextCompat.checkSelfPermission(Creer_rdv.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            message_probleme = "Veuillez autoriser le GPS";
        }

        if (ContextCompat.checkSelfPermission(Creer_rdv.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            if (message_probleme.equals("")) message_probleme = "Veuillez autoriser l'envoi de SMS";
            else message_probleme += " et l'envoi de SMS";
        }

        if (!message_probleme.equals("")){
            Toast.makeText(this, message_probleme, Toast.LENGTH_LONG).show();
            return;
        } else {

            if(!monGPS.dispoGPS){
                Toast.makeText(this, "Veuillez activer le GPS.", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(i, 1);
            } else {
                monGPS.getLocation();
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(obtenirNumeros(), null, "Voulez-vous me rencontrer le " + obtenirDate() + " en " + monGPS.getLatitude() + " , " + monGPS.getLongitude() + "\nrdv://clic", null, null);}
        }
    }

    public void ajout_manuel(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajout manuel");

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
                    activerBouton();
                }
            }
        });
        builder.show();
    }

    private String obtenirNumeros(){
        TextView numeros = (TextView) findViewById(R.id.contact);
        return numeros.getText().toString();
    }

    private String obtenirDate(){
        TextView date = (TextView) findViewById(R.id.Texte_Date);
        return date.getText().toString();
    }

    @SuppressLint("ResourceAsColor")
    private void activerBouton(){
        Button bouton = (Button) findViewById(R.id.btn_creer);
        if(obtenirNumeros().equals("") || obtenirDate().equals("")) {
            bouton.setClickable(false);
            bouton.setBackgroundColor(Color.TRANSPARENT);
            //bouton.setBackgroundColor(R.color.rougePerso);
        } else {
            bouton.setClickable(true);
            //bouton.setBackgroundColor(R.color.vertPerso);
            bouton.setBackgroundColor(Color.LTGRAY);
        }
    }

}
