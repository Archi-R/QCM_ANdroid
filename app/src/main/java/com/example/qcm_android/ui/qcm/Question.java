package com.example.qcm_android.ui.qcm;

public class Question {
    private final String question;
    private final String reponseA;
    private final String reponseB;
    private final String reponseC;
    private final String reponseD;

    public Question(String question, String reponseA, String reponseB, String reponseC, String reponseD) {
        this.question = question;
        this.reponseA = reponseA;
        this.reponseB = reponseB;
        this.reponseC = reponseC;
        this.reponseD = reponseD;
    }

    // Getters
    public String getQuestion() {
        return question;
    }

    public String getReponseA() {
        return reponseA;
    }

    public String getReponseB() {
        return reponseB;
    }

    public String getReponseC() {
        return reponseC;
    }

    public String getReponseD() {
        return reponseD;
    }
}
