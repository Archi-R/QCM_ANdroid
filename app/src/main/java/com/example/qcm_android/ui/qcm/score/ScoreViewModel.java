package com.example.qcm_android.ui.qcm.score;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ScoreViewModel {
    private int reponses_correctes;
    private int reponses_fausses;
    private int reponses_vides;

    public ScoreViewModel(int reponses_correctes, int reponses_fausses, int reponses_vides) {
        this.reponses_correctes = reponses_correctes;
        this.reponses_fausses = reponses_fausses;
        this.reponses_vides = reponses_vides;
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

    public JSONObject loadJsonFromFile(File JsonFile) {
        JSONObject json = new JSONObject();
        try {
            FileInputStream fis = new FileInputStream(JsonFile);
            byte[] data = new byte[(int) JsonFile.length()];
            fis.read(data);
            fis.close();
            String jsonStr = new String(data, "UTF-8");
            json = new JSONObject(jsonStr);
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
