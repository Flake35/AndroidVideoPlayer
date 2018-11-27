package fr.enssat.hemerylievin.androidvideoplayer;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import fr.enssat.hemerylievin.androidvideoplayer.models.Chapitre;

public abstract class JsonLoader {

    public static ArrayList<Chapitre> getChapitres() {

        ArrayList<Chapitre> chapitres = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(MainActivity.getInstance().getApplicationContext()));
            JSONArray m_jArry = obj.getJSONArray("chapitres");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                int numero = jo_inside.getInt("numero");
                String titre = jo_inside.getString("titre");
                int time = jo_inside.getInt("timestamp");
                chapitres.add(new Chapitre(numero, titre, time, 0));
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

    private static String loadJSONFromAsset(Context context) {
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
}
