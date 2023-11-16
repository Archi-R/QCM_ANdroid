package com.example.qcm_android.ui.qcm.questions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class QuestionViewModel {
    private String question;
    private String reponse_a;
    private String reponse_b;
    private String reponse_c;
    private String reponse_d;

    public QuestionViewModel(File JSONFile) throws JSONException {

        JSONObject json = loadJsonFromFile(JSONFile);
        this.question = json.getString("question");
        this.reponse_a = json.getString("a");
        this.reponse_b = json.getString("b");
        this.reponse_c = json.getString("c");
        this.reponse_d = json.getString("d");
    }
    public String getQuestion() {
        return this.question;
    }
    public String getReponseA() {
        return this.reponse_a;
    }
    public String getReponseB() {
        return this.reponse_b;
    }
    public String getReponseC() {
        return this.reponse_c;
    }
    public String getReponseD() {
        return this.reponse_d;
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
