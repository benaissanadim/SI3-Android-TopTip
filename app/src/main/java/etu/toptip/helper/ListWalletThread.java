package etu.toptip.helper;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import etu.toptip.model.Wallet;

public class ListWalletThread extends AsyncTask<String, Integer, JSONObject> {

    public static ArrayList<Wallet> listWallet = new ArrayList<>();

    public ListWalletThread() {
    }

    public static synchronized ArrayList<Wallet> getListWallet() {
        return listWallet;
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        String idLieu, image;

        String apiResponse = "";
        JSONArray jsonarray = null;

        try {
            URL url = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int status = conn.getResponseCode();
            if (status == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                apiResponse = sb.toString();
            } else {
                Log.d("Emile", "BUUUUUUUG code 400, je sais pas trop");
            }
            conn.disconnect();

            jsonarray = new JSONArray(apiResponse);

            System.out.println("JE SUIS DANS LE WALLET: " + jsonarray.toString());
            if (listWallet.size() != jsonarray.length()) {  //Alors nouveau favori
                listWallet.clear();
                for (int i = 0; i < jsonarray.length(); i++) {
                    idLieu = ((JSONObject) jsonarray.get(i)).getString("idLieu");
                    image = ((JSONObject) jsonarray.get(i)).getString("imageUrl");
                    System.out.println(idLieu);
                    listWallet.add(new Wallet(ListPlacesThread.getPlaceById(idLieu).getName(), image));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
