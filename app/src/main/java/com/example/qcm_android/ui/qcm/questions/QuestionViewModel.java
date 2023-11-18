package com.example.qcm_android.ui.qcm.questions;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.qcm_android.ui.qcm.QCM;

public class QuestionViewModel extends AndroidViewModel {
    private MutableLiveData<QCM> qcm = new MutableLiveData<>();

    public QuestionViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<QCM> getQCM() {
        return qcm;
    }

    public void setQCM(QCM qcm) {
        this.qcm.setValue(qcm);
    }
}
