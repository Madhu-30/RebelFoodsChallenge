package com.example.rebelfoodschallenge;

public class Model {

    private String abv, ibu, name, style;
    private int id;
    private double ounces;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Model(){}

    public Model(String abv, String ibu, String name, String style, int id, double ounces, String image) {
        this.abv = abv;
        this.ibu = ibu;
        this.name = name;
        this.style = style;
        this.ounces = ounces;
        this.id = id;
        this.image = image;
    }

    public String getIbu() {
        return ibu;
    }

    public void setIbu(String ibu) {
        this.ibu = ibu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public double getOunces() {
        return ounces;
    }

    public void setOunces(double ounces) {
        this.ounces = ounces;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
