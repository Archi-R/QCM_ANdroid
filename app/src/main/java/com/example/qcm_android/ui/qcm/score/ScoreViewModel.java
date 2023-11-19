package com.example.qcm_android.ui.qcm.score;

import android.content.res.AssetManager;

import com.example.qcm_android.ui.qcm.QCM;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScoreViewModel {
    private int reponses_correctes = 0;
    private int reponses_fausses = 0;
    private int reponses_vides = 0;
    private final List<String> reponses = new ArrayList<>();


    public ScoreViewModel(String qcmName) throws IOException {
        loadReponsesFromName(qcmName);
    }

    public int getReponsesCorrectes() {
        return this.reponses_correctes;
    }

    public int getReponsesFausses() {
        return this.reponses_fausses;
    }

    public int getReponsesVides() {
        return this.reponses_vides;
    }

    public void loadReponsesFromName(String qcmName) throws IOException {
        //get the json file
        JSONObject json = new JSONObject();
        AssetManager assetManager = QCM.getInstance(qcmName).getAssetManager();
        String[] files = assetManager.list("reponses");
        if (files != null) {
            for (String file : files) {
                if (file.endsWith(".json")) {
                    if (file.equals(qcmName)) {
                        try {
                            InputStream is = assetManager.open("reponses/" + file);
                            int size = is.available();
                            byte[] buffer = new byte[size];
                            is.read(buffer);
                            is.close();

                            String content = new String(buffer, StandardCharsets.UTF_8);
                            json = new JSONObject(content);
                        } catch (IOException | JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        //get the list of answers
        try {
            JSONArray JAreponses = json.getJSONArray("liste_questions");
            for (int i = 0; i < JAreponses.length(); i++) {
                JSONObject reponse = JAreponses.getJSONObject(i);
                String rep = reponse.getString("reponse");
                this.reponses.add(rep);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void compareReponses(List<String> reponses_user){
        for (int i = 0; i< this.reponses.size(); i++){
            if (Objects.equals(this.reponses.get(i), reponses_user.get(i))){
                this.reponses_correctes++;
            }
            else if (reponses_user.get(i) == null || reponses_user.get(i).isEmpty()){
                this.reponses_vides++;
            }
            else{
                this.reponses_fausses++;
            }
        }
    }


}
