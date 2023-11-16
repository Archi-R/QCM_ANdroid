package com.example.qcm_android.ui.qcm;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class QCM {
    private static HashMap<String, QCM> instances = new HashMap<>();
    private List<Question> questions;
    private Question currentQuestion;

    private JSONObject json; // maybe suppr

    private QCM(File jsonFile) {
        questions = new ArrayList<>();
        this.loadQuestionsFromJson(jsonFile);
    }

    public static QCM getInstance(String qcmName) {
        if (!instances.containsKey(qcmName)) {
            File jsonFile = new File("chemin/vers/le/fichier/" + qcmName + ".json"); // TODO Remplacez par le chemin de fichier
            instances.put(qcmName, new QCM(jsonFile));
        }
        return instances.get(qcmName);
    }

    private void loadQuestionsFromJson(File file) {
        try {
            String content = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            }
            assert content != null;
            JSONObject jsonObject = new JSONObject(content);
            // récupérer la liste des questions
            JSONArray questionsJson = jsonObject.getJSONArray("liste_questions");

            // création des questions
            for (int i = 0; i < questionsJson.length(); i++) {
                JSONObject questionJson = questionsJson.getJSONObject(i);
                String question = questionJson.getString("question");
                String reponseA = questionJson.getString("reponse_a");
                String reponseB = questionJson.getString("reponse_b");
                String reponseC = questionJson.getString("reponse_c");
                String reponseD = questionJson.getString("reponse_d");
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
    public void nextQuestion() {
        int index = questions.indexOf(currentQuestion);
        if (index < questions.size() - 1) {
            this.currentQuestion = questions.get(index + 1);
        }
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

