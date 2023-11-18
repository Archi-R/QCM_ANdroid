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
import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

public class ScoreFragment extends Fragment{
    private FragmentResultsBinding binding;
    private QCM qcm;

    private ScoreViewModel scoreViewModel;

    private List<String> reponses;
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

        // Création de l'interface utilisateur
        this.binding = FragmentResultsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        reponses = qcm.getReponses();
        reponsescorrectes = getReponsesFromFichierReponses();

        ScoreViewModel scoreViewModel = new ScoreViewModel(resultats.get(0), resultats.get(1), resultats.get(2));

        final EditText reponsesok = this.binding.editTextNumber;
        final EditText reponsesf = this.binding.editTextNumber2;
        final EditText reponsesv = this.binding.editTextNumber3;

        reponsesok.setText(scoreViewModel.getReponsesCorrectes());
        reponsesf.setText(scoreViewModel.getReponsesFausses());
        reponsesv.setText(scoreViewModel.getReponsesVides());


        return root;
    }


    private List<String> getReponsesFromFichierReponses() {
        Bundle bundle = getArguments();
        JSONObject json = new JSONObject();
        if (bundle != null) {
            String qcmName = bundle.getString("qcm_name");
            json = scoreViewModel.loadJsonFromFile(new File("app/src/main/java/com/example/qcm_android/reponses/" + qcmName + ".json"));
            try {
                JSONArray arrayreponses = json.getJSONArray("liste_questions");
                for (int i = 0; i < arrayreponses.length(); i++) {
                    JSONObject reponse = arrayreponses.getJSONObject(i);
                    String rep = reponse.getString("reponse");
                    reponsescorrectes.add(rep);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private List<Integer> compareReponses(){
        List<Integer> resultats = null;
        Integer reponsesok = 0;
        Integer reponsesf = 0;
        Integer reponsesv = 0;
        for (int i = 0; i< reponsescorrectes.size(); i++){
            if (Objects.equals(reponsescorrectes.get(i), reponses.get(i))){
                reponsesok++;
            }
            else if (reponses.get(i) == null){
                reponsesv++;
            }
            else{
                reponsesf++;
            }
        }
        resultats.add(reponsesok);
        resultats.add(reponsesf);
        resultats.add(reponsesv);
        return resultats;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
