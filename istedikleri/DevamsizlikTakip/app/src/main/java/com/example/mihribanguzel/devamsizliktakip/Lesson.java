package com.example.mihribanguzel.devamsizliktakip;

public class Lesson {

    private String ders_adi;
    private String dersdakika;
    private String derssaati;
    private int Devamsiz_H;
    private String gun;
    private String k_id;
    private String ders_id;
    private int Y_Devamsiz;
    private String d_id;
    private String d_adi;
    private int Devamsiz_Hak;

    public int getY_Devamsiz() {
        return Y_Devamsiz;
    }

    public void setY_Devamsiz(int y_Devamsiz) {
        Y_Devamsiz = y_Devamsiz;
    }

    public int getDevamsiz_H() {
        return Devamsiz_H;
    }

    public void setDevamsiz_H(int devamsiz_H) {
        Devamsiz_H = devamsiz_H;
    }

    public int getDevamsiz_Hak() {
        return Devamsiz_Hak;
    }

    public void setDevamsiz_Hak(int devamsiz_Hak) {
        Devamsiz_Hak = devamsiz_Hak;
    }

    public String getDers_id() {
        return ders_id;
    }

    public void setDers_id(String ders_id) {
        this.ders_id = ders_id;
    }

    public Lesson(){

    }

    public String getDers_adi() {
        return ders_adi;
    }

    public void setDers_adi(String ders_adi) {
        this.ders_adi = ders_adi;
    }

    public String getDersdakika() {
        return dersdakika;
    }

    public void setDersdakika(String dersdakika) {
        this.dersdakika = dersdakika;
    }

    public String getDerssaati() {
        return derssaati;
    }

    public void setDerssaati(String derssaati) {
        this.derssaati = derssaati;
    }





    public void setY_Devamsiz(String Y_Devamsiz) {
        Y_Devamsiz = Y_Devamsiz;
    }

    public String getGun() {
        return gun;
    }

    public void setGun(String gun) {
        this.gun = gun;
    }

    public String getK_id() {
        return k_id;
    }


    public String getD_adi() {
        return d_adi;
    }

    public void setD_adi(String d_adi) {
        this.d_adi = d_adi;
    }

    public String getD_id() {
        return d_id;
    }

    public void setD_id(String ders_adi) {
        this.d_id = d_id;
    }


    public void setK_id(String k_id) {
        this.k_id = k_id;
    }
}
