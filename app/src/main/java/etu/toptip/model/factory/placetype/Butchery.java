package etu.toptip.model.factory.placetype;

import android.os.Parcel;

import etu.toptip.model.Place;

public class Butchery extends Place {
    public Butchery(String name, String image, String ville, String codeP, String adresse, String id) {
        super(name, 4, image,ville, codeP, adresse, id);
    }

    protected Butchery(Parcel in) {
        super(in);
    }

    @Override
    public String toString() {
        return "Boucherie";
    }
}
