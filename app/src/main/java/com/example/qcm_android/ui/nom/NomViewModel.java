package com.example.qcm_android.ui.nom;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class NomViewModel {
    private final MutableLiveData<String> name;


    public NomViewModel() {
        name = new MutableLiveData<>();
        name.setValue("Nom");
    }

    public LiveData<String> getText() {
        return name;
    }
}
