package com.assignment_chuyennt.models;

public class Hotel_model {
    private String name, owner, city, licensenumber, address, image, totalfloor, id;

    public Hotel_model(String name, String owner, String city, String licensenumber, String address, String image, String totalfloor, String id) {
        this.name = name;
        this.owner = owner;
        this.city = city;
        this.licensenumber = licensenumber;
        this.address = address;
        this.image = image;
        this.totalfloor = totalfloor;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLicensenumber() {
        return licensenumber;
    }

    public void setLicensenumber(String licensenumber) {
        this.licensenumber = licensenumber;
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

    public String getTotalfloor() {
        return totalfloor;
    }

    public void setTotalfloor(String totalfloor) {
        this.totalfloor = totalfloor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}