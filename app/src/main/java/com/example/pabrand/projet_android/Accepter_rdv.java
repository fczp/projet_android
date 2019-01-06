package com.example.pabrand.projet_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;

public class Accepter_rdv extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepter_rdv);
    }

    public void accepter(View view){
        envoyerReponse("Ok", recupererNumero());
    }

    public void refuser(View view){
        envoyerReponse("Va mourir", recupererNumero());
    }

    private void envoyerReponse(String msg, String num){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(num, null, msg, null, null);
    }

    private String recupererNumero(){
        /*
         * Récupère le numéro qui a envoyé le sms de demande de rdv
         */
        return "";
    }

}
