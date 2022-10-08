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

import etu.toptip.model.BonPlan;
import etu.toptip.model.Place;
import etu.toptip.model.factory.FactoryManager;

public class ListBonPlanThread extends AsyncTask<String, Integer, JSONObject> {

    public static ArrayList<BonPlan> listBonPlan = new ArrayList<>();

    public ListBonPlanThread() throws Throwable {
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        listBonPlan.clear();
        String date, image, description, idUser, idPlace;

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

            System.out.println(jsonarray.toString());
            for (int i = 0; i < jsonarray.length(); i++) {
                date = ((JSONObject) jsonarray.get(i)).getString("dateExpiration");
                image = ((JSONObject) jsonarray.get(i)).getString("imageUrl");
                idUser = ((JSONObject) jsonarray.get(i)).getString("idUser");
                idPlace = ((JSONObject) jsonarray.get(i)).getString("idLieu");
                description = ((JSONObject) jsonarray.get(i)).getString("description");
                listBonPlan.add(new BonPlan(date, image, idUser, idPlace, description));
            }


        } catch (Exception e) {
            Log.d("Emile", "Error: " + e.toString());
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public static ArrayList<BonPlan> getListBonPlan() {
        return listBonPlan;
    }

    public static synchronized ArrayList<BonPlan> getBPByIdLieu(String id) {
        ArrayList<BonPlan> listBonPlan2 = new ArrayList<>();
        for (BonPlan bonPlan : listBonPlan) {
            if (bonPlan.getIdUser().equals(id)) {
                listBonPlan2.add(bonPlan);
            }
        }
        return listBonPlan2;
    }
}
