package com.example.qcm_android.ui.nom;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qcm_android.R;
import com.example.qcm_android.databinding.FragmentNomBinding;
import com.example.qcm_android.ui.home.HomeFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class NomFragment extends Fragment{
        private FragmentNomBinding binding;
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {

            binding = FragmentNomBinding.inflate(inflater, container, false);
            View root = binding.getRoot();
            final EditText editText = binding.editTextText;
            final Button button_start = binding.buttonStart;
            button_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        nameRegister(editText.getText().toString());
                        HomeFragment homeFragment = new HomeFragment();
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, homeFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            return root;
        }
        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }



        /**
         * Enregistre le nom de l'utilisateur dans un fichier
         * @param nom nom de l'utilisateur
         */
        private void nameRegister(String nom) throws IOException {
            String filename = "userName.txt";
            // test if file exists
            File file = new File(requireActivity().getFilesDir(), filename);
            Context context = getActivity();
            assert context != null;
            if (file.exists()) {
                FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
                fos.write(nom.getBytes());
            }else{
                //create file
                file.createNewFile();
                FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
                fos.write(nom.getBytes());
            }
        }
}
