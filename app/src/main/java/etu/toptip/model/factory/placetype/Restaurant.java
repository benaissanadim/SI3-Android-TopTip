package etu.toptip.model.factory.placetype;

import android.os.Parcel;

import etu.toptip.model.Place;

public class Restaurant extends Place {
    public Restaurant(String name, String image, String ville, String codeP, String adresse, String id) {
        super(name,2, image,ville, codeP, adresse, id);
    }

    protected Restaurant(Parcel in) {
        super(in);
    }


    @Override
    public String toString() {
        return "restaurants";
    }
}
