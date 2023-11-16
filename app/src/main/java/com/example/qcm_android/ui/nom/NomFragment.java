package com.example.qcm_android.ui.nom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.qcm_android.databinding.FragmentNomBinding;

public class NomFragment extends Fragment{
        private FragmentNomBinding binding;
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {

            binding = FragmentNomBinding.inflate(inflater, container, false);
            View root = binding.getRoot();
            final EditText editText = binding.editTextText;

            return root;
        }
        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
}
