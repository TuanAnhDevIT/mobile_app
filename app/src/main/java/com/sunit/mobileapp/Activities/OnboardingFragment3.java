package com.sunit.mobileapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.sunit.mobileapp.MainActivity;
import com.sunit.mobileapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OnboardingFragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnboardingFragment3 extends Fragment {

    private Button btnGetStart;
    private View mView;

    public OnboardingFragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_onboarding3, container, false);

        btnGetStart = mView.findViewById(R.id.btn_get_start);
        btnGetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return mView;
    }
}