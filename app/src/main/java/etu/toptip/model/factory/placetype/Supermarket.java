package etu.toptip.model.factory.placetype;

import android.os.Parcel;

import etu.toptip.model.Place;

public class Supermarket extends Place {
    public Supermarket(String name, String image, String ville, String codeP, String adresse, String id) {
        super(name, 0, image, ville, codeP, adresse, id);
    }
    protected Supermarket(Parcel in) {
        super(in);
    }

    @Override
    public String toString() {
        return "supermarchés";
    }
}
