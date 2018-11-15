package com.example.pabrand.projet_android;

public class Contact {

    private String nom;
    private String numero;

    public Contact (String nom, String numero){
        this.nom = nom;
        this.numero = numero;
    }

    public Contact (String numero){
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
