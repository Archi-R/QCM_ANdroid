package com.example.qcm_android.ui.qcm.questions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.qcm_android.databinding.FragmentQuestionBinding;
import com.example.qcm_android.ui.qcm.QCM;
import com.example.qcm_android.ui.qcm.Question;

public class QuestionFragment extends Fragment {
    private FragmentQuestionBinding binding;
    private QCM qcm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Récupération du nom du QCM et obtention de l'instance de QCM
        Bundle bundle = getArguments();
        if (bundle != null) {
            String qcmName = bundle.getString("qcm_name");
            this.qcm = QCM.getInstance(qcmName);
        }

        // Création de l'interface utilisateur
        this.binding = FragmentQuestionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView questionTextView = binding.TVQuestion;
        final Button btn_a = this.binding.btnA;
        final Button btn_b = this.binding.btnB;
        final Button btn_c = this.binding.btnC;
        final Button btn_d = this.binding.btnD;

        // Mettre à jour l'interface utilisateur avec la question courante de QCM
        if (this.qcm != null && this.qcm.getCurrentQuestion() != null) {
            Question currentQuestion = this.qcm.getCurrentQuestion();
            questionTextView.setText(currentQuestion.getQuestion());
            btn_a.setText(currentQuestion.getReponseA());
            btn_b.setText(currentQuestion.getReponseB());
            btn_c.setText(currentQuestion.getReponseC());
            btn_d.setText(currentQuestion.getReponseD());
        }

        // Définir les écouteurs de clic pour chaque bouton
        btn_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAnswer("a");
            }
        });
        btn_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAnswer("b");
            }
        });
        btn_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAnswer("c");
            }
        });
        btn_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAnswer("d");
            }
        });

        return root;
    }

    private void handleAnswer(String answer) {
        // Logique pour gérer la réponse ici
        // Par exemple, vérifier si la réponse est correcte, afficher un message, etc.
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}