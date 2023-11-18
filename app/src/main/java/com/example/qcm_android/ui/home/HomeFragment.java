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

import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private ListView listView;
    private List<File> fileList; // Variable de champ pour la liste des fichiers



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(HomeViewModel.class);
        viewModel = homeViewModel;

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
                String qcmName = selectedFile.getName(); // Ou une autre méthode pour obtenir un identifiant unique pour le QCM
                QuestionFragment questionFragment = new QuestionFragment();

                Bundle bundle = new Bundle();
                bundle.putString("qcm_name", qcmName);
                questionFragment.setArguments(bundle);

                // Afficher QuestionFragment
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, questionFragment);
                //transaction.replace(R.id.nav_host_fragment, questionFragment);
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