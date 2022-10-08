package etu.toptip.model.factory.factorytype;

import etu.toptip.model.Place;
import etu.toptip.model.factory.PlaceFactory;
import etu.toptip.model.factory.placetype.Hypermarket;
import etu.toptip.model.factory.placetype.Restaurant;
import etu.toptip.model.factory.placetype.Supermarket;

public class MultiplePlanFactory extends PlaceFactory {
    public static final int SUPERMARKET = 0;
    public static final int HYPERMARKETS = 1;
    public static final int RESTAURANT = 2;


    @Override
    public Place build(String name, int type, String image, String ville, String codeP, String adresse, String id) throws Throwable {
        switch (type) {
            case RESTAURANT:
                return new Restaurant(name, image, ville, codeP, adresse, id);
            case SUPERMARKET:
                return new Supermarket(name, image, ville, codeP, adresse, id);
            case HYPERMARKETS:
                return new Hypermarket(name, image, ville, codeP, adresse, id);
            default:
                throw new Throwable("not made");
        }
    }
}
