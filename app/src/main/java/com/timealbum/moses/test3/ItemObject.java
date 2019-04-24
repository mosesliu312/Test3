package com.timealbum.moses.test3;

public class ItemObject {
    private String name;
    private String address;
    private int photoID;

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

    public int getPhotoID() {
        return photoID;
    }

    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }

    public ItemObject(String name, String address, int photoID) {
        this.name = name;
        this.address = address;
        this.photoID = photoID;
    }
}
