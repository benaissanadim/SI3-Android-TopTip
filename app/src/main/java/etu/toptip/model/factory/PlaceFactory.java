package etu.toptip.model.factory;

import etu.toptip.model.Place;

public abstract class PlaceFactory {

    public abstract Place build(String name, int type, String image,String ville, String codeP, String adresse, String id) throws Throwable ;
}
