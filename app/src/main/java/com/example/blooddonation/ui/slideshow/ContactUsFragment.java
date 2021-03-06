package com.example.blooddonation.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.blooddonation.R;

public class ContactUsFragment extends Fragment {

    private ContactUsViewModel contactUsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactUsViewModel =
                new ViewModelProvider(this).get(ContactUsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contact_us, container, false);
        final TextView textView = root.findViewById(R.id.txt_contact_us);
        contactUsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}