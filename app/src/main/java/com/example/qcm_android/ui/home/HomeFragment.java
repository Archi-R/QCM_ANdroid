package com.example.qcm_android.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.qcm_android.R;
import com.example.qcm_android.databinding.FragmentHomeBinding;
import com.example.qcm_android.ui.qcm.QCM;
import com.example.qcm_android.ui.qcm.questions.QuestionFragment;

import java.io.File;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ListView listView;
    private List<File> fileList; // Variable de champ pour la liste des fichiers



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = root.findViewById(R.id.listView);

        // Observer pour les changements de la liste des fichiers
        homeViewModel.getFileList().observe(getViewLifecycleOwner(), this::onChanged);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Ouvrir un nouveau QuestionFragment avec le fichier JSON sélectionné
                File selectedFile = fileList.get(position);
                String qcmName = selectedFile.getName();

                if(QCM.getInstance(qcmName).isFinished()) {
                    QCM.getInstance(qcmName).reset();
                }
                // Créer un nouveau QuestionFragment
                QuestionFragment questionFragment = new QuestionFragment();

                // Passer le nom du fichier JSON sélectionné au QuestionFragment
                Bundle bundle = new Bundle();
                bundle.putString("qcm_name", qcmName);
                questionFragment.setArguments(bundle);

                // Afficher QuestionFragment
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, questionFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onChanged(List<File> fileList) {
        this.fileList = fileList;
        ArrayAdapter<File> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, fileList);
        listView.setAdapter(adapter);
    }
}