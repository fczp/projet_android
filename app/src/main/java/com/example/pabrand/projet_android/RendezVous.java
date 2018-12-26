package com.example.pabrand.projet_android;

import java.util.List;

public class RendezVous {

    static int numero = 1;

    private int id;
    private int annee;
    private int mois;
    private int jour;
    private int heure;
    private int minute;
    private List<Contact> contacts;
    private String etat;
    private String latitude;
    private String longitude;

    public RendezVous(int id, int annee, int mois, int jour, int heure, int minute, List<Contact> contacts, String etat, String latitude, String longitude){
        this.id = id;
        this.annee = annee;
        this.mois = mois;
        this.jour = jour;
        this.heure = heure;
        this.minute = minute;
        this.contacts = contacts;
        this.etat = etat;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public RendezVous(int annee, int mois, int jour, int heure, int minute, List<Contact> contacts, String etat, String latitude, String longitude){
        this.id = numero ++;
        this.annee = annee;
        this.mois = mois;
        this.jour = jour;
        this.heure = heure;
        this.minute = minute;
        this.contacts = contacts;
        this.etat = etat;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void ajouterContact(Contact contact){
        List<Contact> contacts_temp = this.getContacts();
        contacts_temp.add(contact);
        this.setContacts(contacts_temp);
    }

    public String getDate_sql(){
        return this.getJour() + "/" + this.getMois() + "/" + this.getJour();
    }

    public String getHeure_sql(){
        return this.getHeure() + ":" + this.getMinute();
    }

    public String getContacts_sql(){
        String res = "";
        List<Contact> l_contacts = this.getContacts();
        for(int i = 0; i< l_contacts.size(); ++i){
            if (l_contacts.get(i).getNom().equals(null)){
                res += l_contacts.get(i).getNumero();
            } else {
                res += l_contacts.get(i).getNom();
            }
        }
        return res;
    }

    public static int getNumero() {
        return numero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }

    public int getHeure() {
        return heure;
    }

    public void setHeure(int heure) {
        this.heure = heure;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setLatitude (String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude(){
        return latitude;
    }

    public void setLongitude (String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude(){
        return longitude;
    }

}
