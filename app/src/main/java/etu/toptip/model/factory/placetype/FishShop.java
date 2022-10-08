package etu.toptip.model.factory.placetype;

import android.os.Parcel;

import etu.toptip.model.Place;

public class FishShop  extends Place {
    public FishShop (String name, String image, String ville, String codeP, String adresse, String id) {
        super(name, 6, image, ville, codeP, adresse, id);
    }
    protected FishShop (Parcel in) {
        super(in);
    }

    @Override
    public String toString() {
        return "poissonerie";
    }
}