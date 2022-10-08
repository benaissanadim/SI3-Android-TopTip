package etu.toptip.model.factory;

import etu.toptip.model.Place;
import etu.toptip.model.factory.factorytype.MultiplePlanFactory;
import etu.toptip.model.factory.factorytype.SpecificPlanFactory;

public class FactoryManager {

    public static Place build(String name, int type, String image, String ville, String codeP, String adresse, String id) throws Throwable {
        if(type<=3){
            MultiplePlanFactory multiplePlanFactory = new MultiplePlanFactory();
            return multiplePlanFactory.build(name,type,image,ville,codeP, adresse, id);
        }
        else {
            SpecificPlanFactory specificPlanFactory = new SpecificPlanFactory();
            return specificPlanFactory.build(name,type,image,ville,codeP, adresse, id);
        }
    }
}
