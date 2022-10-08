package etu.toptip.model.factory.placetype;

import android.os.Parcel;

import etu.toptip.model.Place;

public class Hypermarket extends Place {
    public Hypermarket(String name, String image, String ville, String codeP, String adresse, String id) {
        super(name, 1, image, ville, codeP, adresse, id);
    }

    protected Hypermarket(Parcel in) {
        super(in);
    }

    @Override
    public String toString() {
        return "hypermarché";
    }
}