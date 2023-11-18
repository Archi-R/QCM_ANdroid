package com.example.qcm_android.ui.home;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qcm_android.ui.qcm.QCM;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<File>> fileList;
    private Context context;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        fileList = new MutableLiveData<>();
        loadJSONs();
        QCM.setAssetManager(application.getAssets());
    }


    public void loadJSONs() {
        List<File> jsonFiles = new ArrayList<>();

        try {
            // Obtenez le gestionnaire d'assets
            AssetManager assetManager = getApplication().getAssets();

            // Liste des fichiers dans le dossier "questions" dans le dossier "assets"
            String[] files = assetManager.list("questions");

            if (files != null) {
                for (String file : files) {
                    if (file.endsWith(".json")) {
                        jsonFiles.add(new File("questions/" + file));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.fileList.setValue(jsonFiles);
    }


    public LiveData<List<File>> getFileList() {
        return fileList;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}