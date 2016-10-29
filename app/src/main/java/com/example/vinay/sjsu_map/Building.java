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

    public Building(String name, String image, String address, String abbreviation){
        name = name;
        image = image;
        address = address;
        abbreviation = abbreviation;
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


}
