package etu.toptip.model;

public class BonPlan {
    private String date;
    private String image;
    private String idUser;
    private String idPlace;

    public BonPlan(String date, String image, String idUser, String idPlace, String description) {
        this.date = date;
        this.image = image;
        this.idUser = idUser;
        this.idPlace = idPlace;
        this.description = description;
    }

    private String description;

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdPlace() {
        return idPlace;
    }

    public String getDescription() {
        return description;
    }
}
