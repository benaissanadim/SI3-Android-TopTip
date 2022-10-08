package etu.toptip.model;

import static java.lang.Integer.parseInt;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import etu.toptip.model.Place;
import etu.toptip.model.factory.FactoryManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class ListPlaces {

    public static ArrayList<Place> listPlaces = new ArrayList<>();

    public ListPlaces() throws Throwable {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        listPlaces.clear();


        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://90.8.219.224:3000/api/lieu")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) {
                    String adresse, codepostal, ville, nomDuLieu, imageUrl, idLieu;
                    int typeBonPlan;

                    try (ResponseBody responseBody = response.body()) {
                        JSONArray jsonarray = new JSONArray(responseBody.string());

                        for (int i = 0; i < jsonarray.length(); i++) {
                            imageUrl = ((JSONObject) jsonarray.get(i)).getString("imageUrl");
                            adresse = ((JSONObject) jsonarray.get(i)).getString("nomDuLieu");
                            codepostal = ((JSONObject) jsonarray.get(i)).getString("codepostal");
                            typeBonPlan = Integer.parseInt(((JSONObject) jsonarray.get(i)).getString("typeBonPlan"));
                            ville = ((JSONObject) jsonarray.get(i)).getString("ville");
                            nomDuLieu = ((JSONObject) jsonarray.get(i)).getString("nomDuLieu");
                            idLieu = ((JSONObject) jsonarray.get(i)).getString("_id");
                            System.out.println(imageUrl);
                            listPlaces.add(FactoryManager.build(nomDuLieu, typeBonPlan, imageUrl, ville, codepostal, adresse,idLieu));
                        }
                        listPlaces.add(FactoryManager.build("Picare", 0, "https://www.pagesjaunes.fr/media/agc/a7/8c/4d/00/00/43/c5/1d/0a/c0/5fa1a78c4d000043c51d0ac0/5fa1a78c4d000043c51d0ac1.jpg", "Nice", "06600", "1770 Rte de Grasse","1234"));

                    } catch (Exception e) {
                        e.printStackTrace();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

//        listPlaces.remove(0);
    }

    public ArrayList<Place> getPlaces() {
        return listPlaces;
    }

    public Place getPlaceByName(String name) {
        for (Place place : listPlaces
        ) {
            if (place.getName() == name) return place;
        }
        return null;
    }


}
