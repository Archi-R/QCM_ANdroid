package com.example.qcm_android.ui.qcm.questions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.qcm_android.R;
import com.example.qcm_android.databinding.FragmentQuestionBinding;
import com.example.qcm_android.ui.qcm.QCM;
import com.example.qcm_android.ui.qcm.Question;
import com.example.qcm_android.ui.qcm.score.ScoreFragment;

import java.util.Objects;

public class QuestionFragment extends Fragment {

    private QuestionViewModel questionViewModel;
    Bundle bundle;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        // Initialiser le ViewModel
        questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);

        // Obtenez le nom du QCM du Bundle
        this.bundle = getArguments();
        if (this.bundle != null) {
            String qcmName = this.bundle.getString("qcm_name");
            QCM qcm = QCM.getInstance(qcmName);
            questionViewModel.setQCM(qcm);
        }
        TextView questionTextView = view.findViewById(R.id.TVQuestion);
        Button answerButtonA = view.findViewById(R.id.btn_a);
        Button answerButtonB = view.findViewById(R.id.btn_b);
        Button answerButtonC = view.findViewById(R.id.btn_c);
        Button answerButtonD = view.findViewById(R.id.btn_d);

        // Observez les changements dans le ViewModel
        questionViewModel.getQCM().observe(getViewLifecycleOwner(), new Observer<QCM>() {

            @Override
            public void onChanged(QCM qcm) {
                // Obtenez la question actuelle
                Question question = qcm.getCurrentQuestion();

                questionTextView.setText(question.getQuestion());
                answerButtonA.setText(question.getReponseA());
                answerButtonB.setText(question.getReponseB());
                answerButtonC.setText(question.getReponseC());
                answerButtonD.setText(question.getReponseD());
            }
        });

        // Définir les écouteurs d'événements pour les boutons
        answerButtonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action pour le bouton A
                handleAnswer("a");
            }
        });

        answerButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action pour le bouton B
                handleAnswer("b");
            }
        });

        answerButtonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action pour le bouton C
                handleAnswer("c");
            }
        });

        answerButtonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action pour le bouton D
                handleAnswer("d");
            }
        });

        return view;
    }

    private void handleAnswer(String answer) {
        // Obtenez le QCM actuel
        QCM qcm = questionViewModel.getQCM().getValue();
        assert qcm != null;
        qcm.putReponse(answer);
        String qcmName = Objects.requireNonNull(this.bundle).getString("qcm_name");

        // Si le QCM est terminé, affichez le fragment de résultats
        Fragment nextFragment;
        boolean isFinished = qcm.isFinished();
        if (isFinished) {
            nextFragment = new ScoreFragment();
        } else {
            nextFragment = new QuestionFragment();
        }
        passToNextFragment(nextFragment, qcmName);

    }

    private void passToNextFragment(Fragment fragment, String qcmName){
        Bundle bundle = new Bundle();
        bundle.putString("qcm_name", qcmName);
        fragment.setArguments(bundle);

        // Afficher le Fragment
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}