package etu.toptip.model;

import java.util.ArrayList;
import java.util.Arrays;

public class ListWallet {


    public static ArrayList<Wallet> listWallet = new ArrayList<>(Arrays.asList(
            new Wallet("Lidl", "https://www.pagesjaunes.fr/media/agc/a7/8c/4d/00/00/43/c5/1d/0a/c0/5fa1a78c4d000043c51d0ac0/5fa1a78c4d000043c51d0ac1.jpg"),
            new Wallet("Carrefour", "https://www.ametzondoshopping.com/-/media/images/b2c/france/bayonne/logo-stores/carrefour/carrefour-logo.ashx"),
            new Wallet("Carrefour2", "https://www.supercasino.fr/wp-content/uploads/sites/5/2019/01/logo-super-casino.png")));

    public ListWallet() {
    }

    public ArrayList<Wallet> getWallet(){
        return listWallet ;
    }
}
