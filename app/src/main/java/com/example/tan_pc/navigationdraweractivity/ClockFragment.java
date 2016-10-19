package com.example.tan_pc.navigationdraweractivity;


import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClockFragment extends Fragment {

    boolean btnActiveSTatusB=false;
    LinearLayout CLockLinearLayout;

    private TextView txtClockWillShowAfter;
    private TextView txtFormatTime;
    private TextView txtClockSize;
    private TextView txtTextPercent;
    private TextView txtSendingProgress;
    private TextView txtShowAfterSec;

    private Spinner spinClockSize;
    private Spinner spinFormatTime;

    private EditText edtShowAfterSec;
    private ProgressBar progressBarClock;
    private TextClock textClock;
    private Button btnClockActive;

    private AnalogClock analogClock;

    TextView txtClockWillShowAfterB;
    Button btnClockActiveB;
    TextView txtSendingProgressB;
    TextView txtShowAfterSecB;
    TextView txtFormatTimeB;
    TextView txtClockSizeB;
    EditText edtShowAfterSecB;



    public ClockFragment() {
        // Required empty public constructor
    }
        private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.CLockLinearLayout:
                    hidekeyboard(edtShowAfterSec);
                    break;
                case R.id.btnClockActive:
                    ButtonActiveClockClickedListner();
                    break;
                case R.id.textClock:
                    break;

                default:
                    break;
            }
        }

        };
    private View.OnFocusChangeListener edtFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

//            switch (v.getId()) {
//                case R.id.edt2Rows:
            if (!hasFocus) {
                try {
                    hidekeyboard(edtShowAfterSec);
                }
                catch (Exception e)
                {

                }
            }
        }
    };
    private View.OnKeyListener edtOnKeyListener= new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(event.getAction()==KeyEvent.ACTION_DOWN){
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
                        hidekeyboard(edtShowAfterSec);
                        return true;
                }
            }
            return false;
        }
    };
   private void hidekeyboard(EditText edt)
    {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt.getWindowToken(), 0);
    }


    private void ButtonActiveClockClickedListner() {
        btnActiveSTatusB=!btnActiveSTatusB;
        if(btnActiveSTatusB==false) {
            btnClockActive.setText("ACTIVATE");
            btnClockActive.setBackgroundColor(Color.parseColor("#269999"));
            edtShowAfterSec.setEnabled(true);

            spinFormatTime.setEnabled(true);
            spinClockSize.setEnabled(true);

            edtShowAfterSec.setTextColor(Color.parseColor("#269999"));
            txtFormatTime.setTextColor(Color.parseColor("#0f4545"));
            txtClockSize.setTextColor(Color.parseColor("#0f4545"));
            txtShowAfterSec.setTextColor(Color.parseColor("#0f4545"));

            txtClockWillShowAfter.setTextColor(Color.parseColor("#d3d3d3"));
            txtSendingProgress.setTextColor(Color.parseColor("#d3d3d3"));

            txtTextPercent.setVisibility(getView().GONE);
            progressBarClock.setVisibility(getView().GONE);
        }
        else
        {
            btnClockActive.setText("DEACTIVATE");
            btnClockActive.setBackgroundColor(Color.parseColor("#0f4545"));

            txtClockWillShowAfter.setTextColor(Color.parseColor("#0f4545"));
            txtSendingProgress.setTextColor(Color.parseColor("#0f4545"));
            txtTextPercent.setTextColor(Color.parseColor("#0f4545"));

            txtFormatTime.setTextColor(Color.parseColor("#d3d3d3"));
            txtClockSize.setTextColor(Color.parseColor("#d3d3d3"));
            edtShowAfterSec.setEnabled(false);
            edtShowAfterSec.setTextColor(Color.parseColor("#d3d3d3"));
            progressBarClock.setVisibility(getView().VISIBLE);
            txtTextPercent.setVisibility(getView().VISIBLE);

            spinFormatTime.setEnabled(false);
            spinClockSize.setEnabled(false);
            //edt2Rows.setTextColor(Color.parseColor("#D3D3D3"));
            //txtSendingProgress.setText("%");
////            txtSendingProgress.setVisibility(getView().VISIBLE);
////            progressBarClock.setVisibility(getView().VISIBLE);
//            txtTextPercent.setText("50%");
//            progressBarClock.setProgress(50);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_clock, container, false);
        InitializeComponent(v);
        return v;
    }

    private void InitializeComponent(View v) {
        ReflectAndListener(v);
    }

    private void ReflectAndListener(View v) {
        CLockLinearLayout=(LinearLayout)v.findViewById(R.id.CLockLinearLayout) ;

        txtFormatTime=(TextView)v.findViewById(R.id.txtFormatTime) ;
        txtClockSize=(TextView)v.findViewById(R.id.txtClockSize);
        txtTextPercent=(TextView)v.findViewById(R.id.txtTextPercent);
        txtSendingProgress=(TextView)v.findViewById(R.id.txtSendingProgress);
        txtShowAfterSec=(TextView)v.findViewById(R.id.txtShowAfterSec) ;
        txtClockWillShowAfter =(TextView)v.findViewById(R.id.txtClockWillShowAfter);

        edtShowAfterSec=(EditText)v.findViewById(R.id.edtShowAfterSec);
        spinClockSize=(Spinner)v.findViewById(R.id.spinClockSize);
        spinFormatTime=(Spinner)v.findViewById(R.id.spinFormatTime);
        progressBarClock=(ProgressBar)v.findViewById(R.id.progressBarClock);
        analogClock=(AnalogClock)v.findViewById(R.id.analogClock);
        textClock=(TextClock)v.findViewById(R.id.textClock);
        btnClockActive=(Button)v.findViewById(R.id.btnClockActive);

        btnClockActive.setOnClickListener(btnClickListener);
        CLockLinearLayout.setOnClickListener(btnClickListener);
        edtShowAfterSec.setOnClickListener(btnClickListener);
        edtShowAfterSec.setOnKeyListener(edtOnKeyListener);
        edtShowAfterSec.setOnFocusChangeListener(edtFocusChange);
        textClock.setOnClickListener(btnClickListener);

    }
    public void ActiveClicked(boolean b) {
//        edt2Rows.setFocusableInTouchMode(true);
//        edt2Rows.requestFocus();
        //HideKKKK();
        //ClearAllFocus();
//        spinValves.setEnabled(b);
//        edt2Rows.setEnabled(b);
//        edt2Images.setEnabled(b);
//        edtThreshold.setEnabled(b);
//        edtIP.setEnabled(b);
//        edtPort.setEnabled(b);
//
//        //edt2Rows.setCursorVisible(b);
//        edt2Rows.setFocusableInTouchMode(false);
//        edt2Rows.setFocusable(false);
//
//        // edt2Images.setCursorVisible(b);
//        edt2Images.setFocusableInTouchMode(false);
//        edt2Images.setFocusable(false);
//
//        // edtThreshold.setCursorVisible(b);
//        edtThreshold.setFocusableInTouchMode(false);
//        edtThreshold.setFocusable(false);
//
//        // edtIP.setCursorVisible(b);
//        edtIP.setFocusableInTouchMode(false);
//        edtIP.setFocusable(false);
//
//        //edtPort.setCursorVisible(b);
//        edtPort.setFocusableInTouchMode(false);
//        edtPort.setFocusable(false);
    }

    //khi xoay man hinh thi khong bi giu nguyen layout
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        BackupComponen();
        ViewGroup rootView = (ViewGroup) getView();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View newView = inflater.inflate(R.layout.fragment_clock, rootView, false);
        //ViewGroup rootView = (ViewGroup) getView();
        // Remove all the existing views from the root view.
        // This is also a good place to recycle any resources you won't need anymore
        rootView.removeAllViews();
        rootView.addView(newView);
        InitializeComponent(newView);
        RecoverValuesComponent();
        // Log.i("myLogs", "Rotation");
    }

    //Save status of all component Setting Fragment Before Screen Configchanged
    void BackupComponen() {
        ColorDrawable buttonColor = (ColorDrawable) btnClockActive.getBackground();
        txtClockWillShowAfterB = txtClockWillShowAfter;
        txtSendingProgressB = txtSendingProgress;
        edtShowAfterSecB = edtShowAfterSec;
        edtShowAfterSecB.setTextColor(edtShowAfterSec.getTextColors());
        txtShowAfterSecB =txtShowAfterSec;
        txtFormatTimeB=txtFormatTime;
        txtClockSizeB=txtClockSize;
        btnClockActiveB=btnClockActive;
        btnClockActive.setBackgroundColor(buttonColor.getColor());
    }
    void RecoverValuesComponent()
    {
        ColorDrawable buttonColor = (ColorDrawable) btnClockActiveB.getBackground();
        txtClockWillShowAfter.setTextColor(txtClockWillShowAfterB.getTextColors());
        txtSendingProgress.setTextColor(txtSendingProgressB.getTextColors());
        txtShowAfterSec.setTextColor(txtShowAfterSecB.getTextColors());
        txtFormatTime.setTextColor(txtFormatTimeB.getTextColors());
        txtClockSize.setTextColor(txtClockSizeB.getTextColors());
        edtShowAfterSec.setText(edtShowAfterSecB.getText());
        edtShowAfterSec.setEnabled(edtShowAfterSecB.isEnabled());
        edtShowAfterSec.setTextColor(edtShowAfterSecB.getTextColors());

        btnClockActive.setText(btnClockActiveB.getText());
        btnClockActive.setBackgroundColor(buttonColor.getColor());
    }


}

