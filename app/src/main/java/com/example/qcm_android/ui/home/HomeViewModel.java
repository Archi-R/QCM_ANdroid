package com.example.qcm_android.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<File>> fileList;

    public HomeViewModel() {
        fileList = new MutableLiveData<>();
        loadJSONs();
    }


    public void loadJSONs() {
        // TODO Lire les fichiers JSON et mettre à jour fileList
        List<File> jsonFiles = new ArrayList<>();
        File directory = getJsonFilesDirectory(); // Remplacez par votre logique de chemin de dossier

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".json")) {
                    jsonFiles.add(file);
                }
            }
        }
        fileList.postValue(jsonFiles);
    }

    private File getJsonFilesDirectory() {
        // Retournez le chemin du dossier contenant les fichiers JSON
        // Par exemple, pour le stockage interne: context.getFilesDir();
        // Assurez-vous de gérer les permissions si vous utilisez le stockage externe
        File directory = new File("chemin/vers/le/dossier"); // TODO Remplacez par votre logique de chemin de dossier
        if (!directory.exists()) {
            throw new RuntimeException("Le dossier n'existe pas");
        }
        return directory;
    }

    public LiveData<List<File>> getFileList() {
        return fileList;
    }
}