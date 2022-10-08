package etu.toptip.model.factory.factorytype;

import etu.toptip.model.Place;
import etu.toptip.model.factory.PlaceFactory;
import etu.toptip.model.factory.placetype.Bakery;
import etu.toptip.model.factory.placetype.Butchery;
import etu.toptip.model.factory.placetype.FishShop;
import etu.toptip.model.factory.placetype.Grocery;


public class SpecificPlanFactory  extends PlaceFactory {

    public static final int GROCERY = 3;
    public static final int BUTECHERY =4 ;
    public static final int BAKERY = 5 ;
    public static final int FISHSHOP = 6 ;


    @Override
    public Place build(String name, int type,  String image, String ville, String codeP, String adresse, String id) throws Throwable {

            switch (type){
                case GROCERY: return new Grocery(name,image,ville, codeP, adresse, id) ;
                case BUTECHERY: return new Butchery(name,image,ville, codeP, adresse, id) ;
                case BAKERY: return new Bakery(name,image,ville, codeP, adresse, id) ;
                case FISHSHOP: return new FishShop(name,image,ville, codeP, adresse, id) ;
                default: throw new Throwable("not made");
            }
        }
}
