package com.example.tan_pc.navigationdraweractivity;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import adapter.AdapterGrid;

/**
 * A simple {@link Fragment} subclass.
 */

public class PaintFragment extends Fragment {
    TextView response;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear;

    //khi xoay man hinh thi khong bi giu nguyen layout
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ViewGroup rootView = (ViewGroup) getView();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View newview = inflater.inflate(R.layout.fragment_paint, rootView, false);
        //ViewGroup rootView = (ViewGroup) getView();
        // Remove all the existing views from the root view.
        // This is also a good place to recycle any resources you won't need anymore
        rootView.removeAllViews();
        rootView.addView(newview);
        //InitializeComponent(newview);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_paint, container, false);
        //InitializeComponent(view);
        return view;
    }

    private void InitializeComponent(View v) {
        //load into Valves
        //ReflectAndListener(v);
    }

    public void ReflectAndListener(View v) {

        editTextAddress = (EditText) v.findViewById(R.id.addressEditText);
        editTextPort = (EditText) v.findViewById(R.id.portEditText);
        buttonConnect = (Button) v.findViewById(R.id.connectButton);
        buttonClear = (Button) v.findViewById(R.id.clearButton);
        response = (TextView) v.findViewById(R.id.responseTextView);

       // SwitchTextCheck(false);
    }

    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonConnect:
                    break;
                case R.id.radioWordByWord:
                    RadioWordByWordCheck(true);
                    break;
                case R.id.TextLinearLayout:
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                    InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getApplicationWindowToken(), 0);
                    break;
                case R.id.edtSendText:
//                    edtSendText.setFocusableInTouchMode(true);
//                    edtSendText.setFocusable(true);
//                    edtSendText.requestFocus();
//                    edtSendText.setImeOptions((EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI));

                    break;
            }
        }
    };
    private CompoundButton.OnCheckedChangeListener checkedChangedListenner = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

          //  SwitchTextCheck(switchActiveTextDisplay.isChecked());
        }
    };
    private View.OnFocusChangeListener edtFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                //Switch1check(switch1.isChecked());
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getApplicationWindowToken(), 0);
            }
        }
    };

    private void SwitchTextCheck(boolean b) {
        if (b == true) {
//            radioLetterByLetter.setEnabled(true);
//            radioWordByWord.setEnabled(true);
//            radioLetterByLetter.setTextColor(Color.parseColor("#125656"));
//            radioWordByWord.setTextColor(Color.parseColor("#125656"));
//            edtSendText.setEnabled(true);
//            edtSendText.setFocusableInTouchMode(true);
//            edtSendText.setFocusable(true);

        } else if (b == false) {
//            edtSendText.setFocusableInTouchMode(false);
//            edtSendText.setFocusable(false);
//            edtSendText.setEnabled(false);
//            edtSendText.setText(null);
//
//            radioLetterByLetter.setEnabled(false);
//            radioWordByWord.setEnabled(false);
//            radioLetterByLetter.setTextColor(Color.parseColor("#d3d3d3"));
//            radioWordByWord.setTextColor(Color.parseColor("#d3d3d3"));
//
//            btnSendText.setEnabled(false);
//            btnSendText.setBackgroundColor(Color.parseColor("#d3d3d3"));

        }
    }

    private void RadioLetterByLetterCheck(boolean b) {
//
//        radioWordByWord.setChecked(false);
//        //radioLetterByLetter.setChecked(false);
//        radioLetterByLetter.setChecked(true);
//        //radioLetterByLetter.setBa
//        edtSendText.setEnabled(b);
    }
    private void RadioWordByWordCheck(boolean b) {
//        radioLetterByLetter.setChecked(false);
//        //radioWordByWord.setChecked(false);
//        radioWordByWord.setChecked(true);
//        edtSendText.setEnabled(b);
    }

    public PaintFragment() {
        // Required empty public constructor
    }

    private View.OnKeyListener edtOnKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            //String s=edtSendText.getText().toString().trim();
            if (((event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_DEL))) {
//                btnSendText.setEnabled(false);
//                btnSendText.setBackgroundColor(Color.parseColor("#d3d3d3"));
                //return true;

            }
            return false;
        }
    };
//    void hidekeyboard()
//    {
//        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(edt.getWindowToken(), 0);
//    }


}

