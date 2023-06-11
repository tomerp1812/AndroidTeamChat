package com.example.teamchat;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;

public class TermsWatcher implements View.OnFocusChangeListener {

    private final View etv;
    private final View underEtv;
    private final View aboveEtv;


    public TermsWatcher(View aboveEtv1, View etv1, View underEtv1) {
        etv = etv1;
        underEtv = underEtv1;
        aboveEtv = aboveEtv1;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            etv.setVisibility(View.INVISIBLE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) underEtv.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, aboveEtv.getId());
            underEtv.setLayoutParams(params);
        } else{
            etv.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) underEtv.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, etv.getId());
            underEtv.setLayoutParams(params);
        }
    }

}
