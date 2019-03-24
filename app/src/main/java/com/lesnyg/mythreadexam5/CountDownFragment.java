package com.lesnyg.mythreadexam5;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CountDownFragment extends Fragment {

    private OnCountDownFragmentClick mListener;
    private TextView mCountText;


    public CountDownFragment() {
    }

    public void setCount(int count){
        mCountText.setText(count+"");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_count_down, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCountText = view.findViewById(R.id.text_count);

        view.findViewById(R.id.btn_start).setOnClickListener(v -> mListener.onStartButtonClicked());
        view.findViewById(R.id.btn_init).setOnClickListener(v -> mListener.onInitButtonClicked());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCountDownFragmentClick) {
            mListener = (OnCountDownFragmentClick) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCountDownFragmentClick {
        void onStartButtonClicked();
        void onInitButtonClicked();
    }
}
