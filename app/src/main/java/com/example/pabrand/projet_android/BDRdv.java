package com.example.pabrand.projet_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BD_rdv {

    private final static int VERSION_BDD = 1;
    private SQLiteDatabase bdd;
    private MaBaseSQLite maBaseSQLite;

    public BD_rdv(Context context){
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
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert("livres",null,values);
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
