package etu.toptip.helper;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InfoCompteThread extends AsyncTask<String, Integer, JSONObject> {

    public InfoCompteThread() {
    }
//    public void InfoCompteModel2() {
//
//        OkHttpClient client = new OkHttpClient();
//
//        String url = "http://90.8.219.224:3000/api/user/" + Infologin.getIdUser();
////        String url = "http://192.168.1.14:3000/api/user/" + Infologin.getIdUser();
//
//        Request request = new Request.Builder()
////                    .url("http://192.168.1.14:3000/api/user/auth/signup")
//                .url(url)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//
//                try (ResponseBody responseBody = response.body()) {
//
//                    JSONObject jsonObj = new JSONObject(responseBody.string());
//                    Thread.sleep(2000);
//
//                    String pseuso;
//                    String mail;
//
//                    pseuso = jsonObj.getString("userName");
//                    mail = jsonObj.getString("email");
//
//                    Log.d("Emile", "1 " + jsonObj.getString("userName"));
//                    Log.d("Emile", "2 " + jsonObj.getString("email"));
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        String apiResponse = "";
        JSONObject reader = null;
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
            }else{
                Log.d("Emile", "BUUUUUUUG code 404, l'utilisateur n'est pas enregistrer");
            }
            conn.disconnect();

            reader = new JSONObject(apiResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reader;
    }
}
