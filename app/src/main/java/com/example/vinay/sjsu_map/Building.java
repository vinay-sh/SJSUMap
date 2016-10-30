package com.example.vinay.sjsu_map;

import java.util.UUID;

/**
 * Created by Rayan on 10/23/16.
 */

public class Building {
    private UUID uuid;
    private String name;
    private String address;
    private String image;
    private String abbreviation;
    private int color;

    public Building(String name, String image, String address, String abbreviation, int color){
        this.name = name;
        this.image = image;
        this.address = address;
        this.abbreviation = abbreviation;
        this.color = color;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
