package com.example.qcm_android.ui.qcm.score;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qcm_android.R;
import com.example.qcm_android.databinding.FragmentResultsBinding;
import com.example.qcm_android.ui.qcm.QCM;
import com.example.qcm_android.ui.qcm.Question;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

public class ScoreFragment extends Fragment{
    private FragmentResultsBinding binding;
    private QCM qcm;

    private ScoreViewModel scoreViewModel;

    private List<String> reponses_user;
    private List<String> reponsescorrectes;

    private List<Integer> resultats;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Récupération du nom du QCM et obtention de l'instance de QCM
        Bundle bundle = getArguments();
        if (bundle != null) {
            String qcmName = bundle.getString("qcm_name");
            this.qcm = QCM.getInstance(qcmName);
        }

        // Récupération des réponses du QCM
        assert bundle != null;
        String qcmName = bundle.getString("qcm_name");
        try {
            this.scoreViewModel = new ScoreViewModel(qcmName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Récupération des réponses de l'utilisateur
        this.reponses_user = qcm.getReponses();


        // Comparaison des réponses
        this.scoreViewModel.compareReponses(this.reponses_user);

        // Création de l'interface utilisateur
        this.binding = FragmentResultsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView reponsesok = binding.TVRepOk;
        final TextView reponsesf = binding.TVRepFaux;
        final TextView reponsesv = binding.TVRepVides;

        reponsesok.setText(String.valueOf(scoreViewModel.getReponsesCorrectes()));
        reponsesf.setText(String.valueOf(scoreViewModel.getReponsesFausses()));
        reponsesv.setText(String.valueOf(scoreViewModel.getReponsesVides()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
