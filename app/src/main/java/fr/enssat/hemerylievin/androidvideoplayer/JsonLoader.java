package fr.enssat.hemerylievin.androidvideoplayer;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import fr.enssat.hemerylievin.androidvideoplayer.models.Chapitre;
import fr.enssat.hemerylievin.androidvideoplayer.models.WebUrl;

public abstract class JsonLoader {

    public static ArrayList<Chapitre> getChapitres() {

        ArrayList<Chapitre> chapitres = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadChaptersFromAsset(MainActivity.getInstance().getApplicationContext()));
            JSONArray m_jArry = obj.getJSONArray("chapitres");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                int numero = jo_inside.getInt("numero");
                String titre = jo_inside.getString("titre");
                int time = jo_inside.getInt("timestamp");
                chapitres.add(new Chapitre(numero, titre, time, 99999999));
                if ( i > 0 ) {
                    chapitres.get(i - 1).setNextMarque(time);
                }
            }
        } catch (JSONException e) {
            System.out.println("Impssible de charger le fichier json");
            e.printStackTrace();
        }

        return chapitres;
    }

    public static ArrayList<WebUrl> getWebUrls() {
        ArrayList<WebUrl> webUrls = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadUrlsFromAsset(MainActivity.getInstance().getApplicationContext()));
            JSONArray m_jArry = obj.getJSONArray("weburl");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String url = jo_inside.getString("url");
                int startTime = jo_inside.getInt("startTime");
                int stopTime = jo_inside.getInt("startTime");
                webUrls.add(new WebUrl(url, startTime, stopTime));
            }
        } catch (JSONException e) {
            System.out.println("Impssible de charger le fichier json");
            e.printStackTrace();
        }

        return webUrls;
    }

    private static String loadChaptersFromAsset(Context context) {
        String json;
        try {
            InputStream is = context.getAssets().open("summary.json");
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private static String loadUrlsFromAsset(Context context) {
        String json;
        try {
            InputStream is = context.getAssets().open("weburl.json");
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
