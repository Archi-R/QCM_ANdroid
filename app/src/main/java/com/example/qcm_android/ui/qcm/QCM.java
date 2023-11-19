package com.example.qcm_android.ui.qcm;

import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class QCM {
    private static final HashMap<String, QCM> instances = new HashMap<>();
    private final List<Question> questions;
    private final List<String> reponses;
    private String name;
    private Question currentQuestion;
    private boolean isFinished = false;

    private static AssetManager assetManager;


    private QCM(File jsonFile) {
        this.questions = new ArrayList<>();
        this.reponses= new ArrayList<>();
        this.loadQuestionsFromJson(jsonFile);
    }

    public static QCM getInstance(String qcmName) {
        if (!instances.containsKey(qcmName)) {
            try {
               // Liste des fichiers dans le dossier "questions" dans le dossier "assets"
                String[] files = assetManager.list("questions");

                if (files != null) {
                    for (String file : files) {
                        if (file.endsWith(".json")) {
                            if (file.equals(qcmName)) {
                                File jsonFile = new File("questions/" + file);
                                QCM qcm = new QCM(jsonFile);
                                qcm.name = qcmName;
                                instances.put(qcmName, new QCM(jsonFile));
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instances.get(qcmName);
    }

    private void loadQuestionsFromJson(File file) {
        try {
            InputStream is = assetManager.open(file.getPath());
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String content = new String(buffer, StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(content);
            // récupérer la liste des questions
            JSONArray questionsJson = jsonObject.getJSONArray("liste_questions");

            // création des questions
            for (int i = 0; i < questionsJson.length(); i++) {
                JSONObject questionJson = questionsJson.getJSONObject(i);
                String question = questionJson.getString("question");
                JSONObject reponsesJson = questionJson.getJSONObject("reponses");
                String reponseA = reponsesJson.getString("a");
                String reponseB = reponsesJson.getString("b");
                String reponseC = reponsesJson.getString("c");
                String reponseD = reponsesJson.getString("d");
                questions.add(new Question(question, reponseA, reponseB, reponseC, reponseD));
            }

            // placement de la question courante à la première question
            if (!questions.isEmpty()) {
                currentQuestion = questions.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthodes pour accéder aux questions
    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    // Méthodes pour naviguer entre les questions
    private void nextQuestion() {
        int index = questions.indexOf(currentQuestion);
        if (index < questions.size() - 1) {
            this.currentQuestion = questions.get(index + 1);
        }
    }

    public void putReponse(String reponse) {
        int index = questions.indexOf(currentQuestion);
        this.reponses.add(index, reponse);
        this.nextQuestion();
    }
    public List<String> getReponses() {
        return reponses;
    }

    public String getName() {
        return name;
    }

    public static void setAssetManager(AssetManager assetManager) {
        QCM.assetManager = assetManager;
    }

    public boolean isFinished() {
        if (this.isFinished){
            return true;
        }
        int sizeQ = questions.size();
        int sizeR = reponses.size();
        this.isFinished = sizeQ <= sizeR;
        return this.isFinished;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void reset() {
        this.reponses.clear();
        this.isFinished = false;
        this.currentQuestion = questions.get(0);
    }
}

