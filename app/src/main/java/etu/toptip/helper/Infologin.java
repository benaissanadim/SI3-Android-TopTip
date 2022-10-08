package etu.toptip.helper;

public class Infologin {

    public static String idUser = null;

    public static synchronized String getIdUser() {
        return idUser;
    }

    public static synchronized void setIdUser(String id) {
        idUser = id;
    }

    public static synchronized void deconnexion() {
        idUser = null;
    }

}
