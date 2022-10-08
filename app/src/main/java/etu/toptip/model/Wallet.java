package etu.toptip.model;

public class Wallet {
    private String name;
    private String image;

    public Wallet(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

}
