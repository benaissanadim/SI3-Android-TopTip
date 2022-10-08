package etu.toptip.model.factory.placetype;

import android.os.Parcel;

import etu.toptip.model.Place;

public class Grocery extends Place {
    public Grocery(String name, String image, String ville, String codeP, String adresse, String id) {
        super(name, 3,image,ville, codeP, adresse, id);
    }

    protected Grocery(Parcel in) {
        super(in);
    }

    @Override
    public String toString() {
        return "epecerie";
    }
}
