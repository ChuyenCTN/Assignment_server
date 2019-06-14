package com.assignment_chuyennt.models;

public class Room_model {
    public String numberroom, image, floor, singleroom, price, detail, hotelid,status;

    public Room_model(String numberroom, String image, String floor, String singleroom, String price, String detail, String hotelid, String status) {
        this.numberroom = numberroom;
        this.image = image;
        this.floor = floor;
        this.singleroom = singleroom;
        this.price = price;
        this.detail = detail;
        this.hotelid = hotelid;
        this.status = status;
    }

    public String getNumberroom() {
        return numberroom;
    }

    public void setNumberroom(String numberroom) {
        this.numberroom = numberroom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getSingleroom() {
        return singleroom;
    }

    public void setSingleroom(String singleroom) {
        this.singleroom = singleroom;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getHotelid() {
        return hotelid;
    }

    public void setHotelid(String hotelid) {
        this.hotelid = hotelid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}