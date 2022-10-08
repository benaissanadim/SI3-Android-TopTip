package etu.toptip.model.factory.placetype;

import android.os.Parcel;

import etu.toptip.model.Place;

public class Bakery  extends Place {
    public Bakery (String name, String image, String ville, String codeP, String adresse, String id) {
        super(name, 5,image, ville, codeP, adresse, id);
    }
    protected Bakery (Parcel in) {
        super(in);
    }

    @Override
    public String toString() {
        return "Boulangerie";
    }
}
