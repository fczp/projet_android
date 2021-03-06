package com.example.pabrand.projet_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BDRdv {

    private final static int VERSION_BDD = 1;
    private SQLiteDatabase bdd;
    private MaBaseSQLite maBaseSQLite;

    public BDRdv(Context context){
        maBaseSQLite = new MaBaseSQLite(context,"livres",null,VERSION_BDD);
    }

    public void open(){//On ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){//On ferme la BDD
        bdd.close();
    }

    public SQLiteDatabase getBdd(){
        return bdd;
    }

    public long insertRdv(RendezVous rdv){//Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        values.put("date", rdv.getDate_sql());
        values.put("heure", rdv.getHeure_sql());
        values.put("contacts", rdv.getContacts_sql());
        values.put("etat", rdv.getEtat());
        values.put("latitude", rdv.getLatitude());
        values.put("longitude", rdv.getLongitude());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert("rendezvous",null,values);
    }

    public Contact getContactDenomination(String titre){//Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD
        Cursor cursor = null;
        //Cursor cursor = bdd.query();
        return cursorToLivre(cursor);
    }

    public Contact cursorToLivre(Cursor cursor){
        if(cursor.getCount() == 0){
            return null;
        }
        return null;
    }

}
