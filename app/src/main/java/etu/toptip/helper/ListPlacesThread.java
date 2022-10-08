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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import etu.toptip.model.Place;
import etu.toptip.model.factory.FactoryManager;

public class ListPlacesThread extends AsyncTask<String, Integer, JSONObject> {

    public static ArrayList<Place> listPlaces = new ArrayList<>();

    public ListPlacesThread() throws Throwable { }

    @Override
    protected JSONObject doInBackground(String... urls) {
        String adresse, codepostal, ville, nomDuLieu, imageUrl, idLieu;
        int typeBonPlan;

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


            if (listPlaces.size() != jsonarray.length()) {
                listPlaces.clear();
                for (int i = 0; i < jsonarray.length(); i++) {
                    imageUrl = ((JSONObject) jsonarray.get(i)).getString("imageUrl");
                    adresse = ((JSONObject) jsonarray.get(i)).getString("nomDuLieu");
                    codepostal = ((JSONObject) jsonarray.get(i)).getString("codepostal");
                    typeBonPlan = Integer.parseInt(((JSONObject) jsonarray.get(i)).getString("typeBonPlan"));
                    ville = ((JSONObject) jsonarray.get(i)).getString("ville");
                    nomDuLieu = ((JSONObject) jsonarray.get(i)).getString("nomDuLieu");
                    idLieu = ((JSONObject) jsonarray.get(i)).getString("_id");
                    listPlaces.add(FactoryManager.build(nomDuLieu, typeBonPlan, imageUrl, ville, codepostal, adresse, idLieu));
                }
//                listPlaces.add(FactoryManager.build("test", 2, "https://www.pagesjaunes.fr/media/agc/a7/8c/4d/00/00/43/c5/1d/0a/c0/5fa1a78c4d000043c51d0ac0/5fa1a78c4d000043c51d0ac1.jpg", "Antibes", "", ""));
            }


        } catch (Exception e) {
            Log.d("Emile", "Error: " + e.toString());
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public static synchronized ArrayList<Place> getPlaces() {
        return listPlaces;
    }


    public static synchronized Place getPlaceByName(String name) {
        for (Place place : listPlaces) {
            if (place.getName() == name) return place;
        }
        return null;
    }

    public static synchronized Place getPlaceById(String id) {
        System.out.println("je recherche: " + id);
        for (Place place : listPlaces) {
            System.out.println(place.getName() + " " + place.getAdresse() + ":" + place.getId());
            if (place.getId().equals(id)) return place;
        }
        return null;
    }

    public static synchronized ArrayList<String> getNames() {
        ArrayList<String> set = new ArrayList<>();
        for (Place place : listPlaces) {
            set.add(place.getName());
        }
        return set;
    }
}
