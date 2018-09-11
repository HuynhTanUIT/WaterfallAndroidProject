package com.example.tan_pc.navigationdraweractivity.Reference;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AnalogClock;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tan_pc.navigationdraweractivity.R;
import com.example.tan_pc.navigationdraweractivity.Reference.WaterImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static SettingsSQLite.SqliteHelper.TABLE_SETTINGS;
import static com.example.tan_pc.navigationdraweractivity.Reference.DeviceInfo.DisplayRow;
import static com.example.tan_pc.navigationdraweractivity.Reference.DeviceInfo.viewToBitmap;
import static com.example.tan_pc.navigationdraweractivity.Activity.MainActivity.PROJECTDATABASE;
import static com.example.tan_pc.navigationdraweractivity.Activity.MainActivity.mTcpClient;
import static com.example.tan_pc.navigationdraweractivity.Activity.MainActivity.milliseconds;
import static com.example.tan_pc.navigationdraweractivity.Activity.MainActivity.serialCom;
import static com.example.tan_pc.navigationdraweractivity.Activity.MainActivity.thresholeNumber;
import static com.example.tan_pc.navigationdraweractivity.Activity.MainActivity.vanNumber;
import static com.example.tan_pc.navigationdraweractivity.R.id.cancel_action;
import static com.example.tan_pc.navigationdraweractivity.R.id.radioAnalogClock;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClockFragment extends Fragment {

    boolean btnActiveSTatusB=false;
    SeekBar seekBarCLockSize;
    LinearLayout CLockLinearLayout;
    boolean invertClock =false;
    Bitmap imageClock;
    WaterImage waterImage;
    private LinearLayout layoutFormatTimeClock;
    private FrameLayout layoutClockGeneral;
    private RelativeLayout layoutAnalogClock;
    private LinearLayout layoutDigitalClock;
    private CheckBox CheckboxAutoShowAfterCLock;
    private CheckBox checkboxClickToShowClock;
//    private Spinner spinClockValves;
    private Spinner spinCLockFormatTime;
    private Switch swClockInver;
    private TextClock textClock;
//    private EditText edtRdelayClock;
    private EditText edtShowAfterSec;
    private Button btnClockSend;
    private RadioGroup radioGroup;
    private RadioGroup radioGroupOnOff;
    private RadioButton radioOn;
    private RadioButton radioOff;
    private RadioButton radioButtonDigitalClock;
    private RadioButton radioButtonAnalogClock;
    private AnalogClock analogClock;
    private String[] valves;
    private String[] formatClock;
//    private Spanned[] spannedStringsValvesClock = new Spanned[12];
    private Spanned[] spannedStringsFormatClock = new Spanned[5];
    protected Bitmap image;
    protected WaterImage imgpaintImage;
    int between2Clock=5;
    boolean isStop=false;
    boolean isAutoShow=false;
    boolean checkInto=false;
    boolean sendOnValve=false;
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
        layoutFormatTimeClock=(LinearLayout)v.findViewById(R.id.LayoutFormatTimeClock);
        layoutClockGeneral=(FrameLayout) v.findViewById(R.id.layoutClockGeneral);

        layoutAnalogClock=(RelativeLayout)v.findViewById(R.id.layoutAnalogClock);
        layoutDigitalClock=(LinearLayout)v.findViewById(R.id.layoutDigitalClock);
        analogClock=(AnalogClock)v.findViewById(R.id.simpleAnalogClock);
        radioGroupOnOff=(RadioGroup)v.findViewById(R.id.radioOnOffBw2CLock) ;
        radioOn=(RadioButton)v.findViewById(R.id.radioOnBw2CLock);
        radioOff=(RadioButton)v.findViewById(R.id.radioOffBw2CLock);
        swClockInver=(Switch)v.findViewById(R.id.swInvertClock) ;
        CheckboxAutoShowAfterCLock=(CheckBox)v.findViewById(R.id.CheckboxAutoShowAfterCLock) ;
        checkboxClickToShowClock=(CheckBox)v.findViewById(R.id.checkboxClickToShowClock) ;
//        edtRdelayClock=(EditText)v.findViewById(R.id.edtBw2RowClock) ;
        edtShowAfterSec=(EditText)v.findViewById(R.id.edtShowAfterSec);
//        spinClockValves=(Spinner)v.findViewById(R.id.spinerClockValves);
        spinCLockFormatTime=(Spinner)v.findViewById(R.id.spinerClockFormatime);
        radioGroup=(RadioGroup)v.findViewById(R.id.radioGroupClock);
        radioButtonAnalogClock=(RadioButton)v.findViewById(radioAnalogClock);
        radioButtonDigitalClock=(RadioButton)v.findViewById(R.id.radioDigitalClock);
        analogClock=(AnalogClock)v.findViewById(R.id.simpleAnalogClock);
        textClock=(TextClock)v.findViewById(R.id.textClock);
        btnClockSend=(Button)v.findViewById(R.id.btnClockSend);
        CLockLinearLayout.setOnClickListener(btnClickListener);
        edtShowAfterSec.setOnClickListener(btnClickListener);
        edtShowAfterSec.setOnKeyListener(edtOnKeyListener);
//        edtRdelayClock.setOnKeyListener(edtOnKeyListener);
        edtShowAfterSec.setOnFocusChangeListener(edtFocusChange);
//        edtRdelayClock.setOnFocusChangeListener(edtFocusChange);
        seekBarCLockSize =(SeekBar)v.findViewById(R.id.seekBarCLockSize);
        seekBarCLockSize.setProgress(50);
        seekBarCLockSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                try {
//                    imageSizeView.getLayoutParams().height = (progress+1) * 10;
//                    imageSizeView.getLayoutParams().width = 100;
//                    mPaint.setStrokeWidth(((progress+1) * 10));
//                    imageSizeView.requestLayout();
//                    txtReviewDisplayText.setLineSpacing(progress*4,1f);
                    textClock.setTextSize((float)1.3*(progress+20));
                    layoutDigitalClock.invalidate();
                    layoutClockGeneral.invalidate();

                }catch (Exception e){
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        radioGroup.clearCheck();
        radioGroupOnOff.clearCheck();
        radioGroup.setOnCheckedChangeListener(radioCheckedChange);
        radioGroupOnOff.setOnCheckedChangeListener(radioCheckedChange);
        radioGroupOnOff.check(R.id.radioOffBw2CLock);
        radioGroup.check(R.id.radioDigitalClock);
        edtShowAfterSec.setText("5");
        spinnerLoadClock(true);
        swClockInver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                invertClock = isChecked;
                imageClock= new DeviceInfo().viewToBitmap(layoutDigitalClock);
            }
        });
        spinCLockFormatTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @SuppressLint("NewApi")
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                spinCLockFormatTime.setSelection(arg2);
                switch (arg2)
                {
                    case 0:
                        textClock.setFormat12Hour("hh:mm");
                        break;
                    case 1:
                        textClock.setFormat12Hour("hh:mm a");
                        break;
                    case 2:
                        textClock.setFormat12Hour("hh:mm:ss");
                        break;
                    case 3:
                        textClock.setFormat12Hour("hh:mm:ss a");
                        break;
                    case 4:
                        textClock.setFormat12Hour("dd-MM-yyyy");
                        break;

                    default:
                        textClock.setFormat12Hour("hh:mm");
                        break;
                }
                // sizePen.setText("Pen Size: "+spinSizePen.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        btnClockSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CheckboxAutoShowAfterCLock.isChecked()) {
                    Log.i("Send Clicked: ", "showing SendingDialog ");
                    isStop=false;
                    isAutoShow=true;
                    between2Clock=700*Integer.parseInt(edtShowAfterSec.getText().toString().trim());
                    SendImage();
                }else
                {
                    isAutoShow=false;
                    between2Clock=3;
                     SendImage();
                }
            }
        });
        layoutClockGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showSendingDialog();
                Log.i("Send Clicked: ", "showing SendingDialog ");
                try {
                    if(checkboxClickToShowClock.isChecked()) {
                        SendImage();
                    }
                }catch (Exception e)
                {

                }
//                ToastShow("Layout Clicked! +analog+ digital: "+ radioButtonAnalogClock.isChecked()+ radioButtonDigitalClock.isChecked());
            }
        });
        CheckboxAutoShowAfterCLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                }else {
                }
            }
        });
    }
    private void SendImage()
    {

        try{
            Log.i("Send Clicked: ", "Send Cliked Listener");
            Log.i("Send Clicked: ", "Datasend.Size>0 ");
            if (mTcpClient != null ) {
                Log.i("Send Clicked: ", "mTcpClient !=null ");
                if(mTcpClient.isConnect()==true) {
//                                if(isAutoShow)
//                                {
//                                    showSendingDialog_auto();
//                                }else {
//                                    showSendingDialog();
//                                }
                                new SendPaint().execute();

                }else
                {
                    Toast.makeText(getActivity(), "Check your connection!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Please connect to Server and try again!!", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e)
        {
            Toast.makeText(getActivity(),e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    private class SendPaint extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(isAutoShow)
            {
                showSendingDialog_auto();
            }else {
                showSendingDialog();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
//            ToastShow("Done");
//            Log.e("da xong:", " "+ "Chuyen XOng");
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                getSettingData();
                if(isAutoShow==false)
                {
                    isAutoShow=false;
                    ShowPaint();
                    publishProgress(100);
                }
                while(isAutoShow&&isStop==false) {
                    ShowPaint();
                    publishProgress(100);
                    try {
                        Thread.sleep(between2Clock);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    checkInto=true;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            dismissSenddingDialog();
            dismissSenddingDialog_auto();
            Toast.makeText(getActivity(), "Sent Successful!", Toast.LENGTH_SHORT).show();
        }
    }
    private void ShowPaint() throws IOException {
        try{
            image= viewToBitmap(layoutDigitalClock);
            //image = DeviceInfo.viewToBitmap(frameLayout);
            Log.e("CLICKED", "Height , WIDTH: " +image.getHeight() + " " + image.getWidth());
            Log.e("CLICKED", "Height , Valnumber: " +vanNumber+ " "+thresholeNumber + ""+ " ");

            Bitmap reBitmap = WaterImage.ResizeImage(image,vanNumber);
            Log.e("ReBit Map", "Height , WIDTH: " +reBitmap.getHeight() + " " + reBitmap.getWidth());
            Bitmap bwBitmap = reBitmap;
//                 Log.e("CLICKED", "Height , WIDTH: " +reBitmap.getHeight() + " " + reBitmap.getWidth());
            imgpaintImage = new WaterImage(reBitmap);
            if (invertClock == false){
                bwBitmap = imgpaintImage.GetBitmapInvert(reBitmap,vanNumber,thresholeNumber);

            }
            else
            {
                bwBitmap = imgpaintImage.GetBitmap(reBitmap,vanNumber,thresholeNumber);
            }
//            try {
//                saveImageToExternal(DeviceInfo.getCurrentTime(), bwBitmap, getActivity());
//
//            } catch (OutOfMemoryError e) {
//                e.printStackTrace();
//                ToastShow("Out of memory!");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            Log.e("bwBit Map", "Height , WIDTH: " +bwBitmap.getHeight() + " " + bwBitmap.getWidth()+" "+vanNumber);
            imgpaintImage = new WaterImage(bwBitmap);


            boolean _stop = false;
            int byteRow;
            // Log.e("Watering ", "Sending Image: "+ i );
            byteRow=imgpaintImage.getImg().getWidth()/8;
            Bitmap img =imgpaintImage.getImg();
            Log.e("Befor Send BitMap", "Height , WIDTH: " +img.getHeight() + " " + img.getWidth()+" "+vanNumber);
            if (!DisplayPaint(img))
            {
                _stop = true;
            }
            CurtainSleep(byteRow,3);
        }catch (Exception e)
        {
            ToastShow(e.toString());
        }
    }
    private boolean DisplayPaint(Bitmap img) throws IOException {
        //  Log.i("Watering: ", "Da vao Display Image ");
        imgpaintImage = new WaterImage(img);
        Bitmap bmp = imgpaintImage.getImg();
        int a = bmp.getRowBytes();
        int b = bmp.getByteCount();
        serialCom = new DeviceInfo();

        if (!DeviceInfo.TxCmdStart()) {
            return false;
        }
        int times = 1;
        for (int i = 0; i < times; i++) {
            for (int k = img.getHeight() - 1; k >= 0; k--) {
                Log.e("DisplayImage ", "Display row: " + k);

                byte[] data = imgpaintImage.RowToByte(k);
                // Log.e("DisplayImage ", "Data of row: "+ Arrays.toString(data));
                if (!DisplayRow(data, milliseconds))
                    return false;

            }
        }
        return serialCom.TxCmdStop();
    }

    private boolean CurtainSleep( int bytes,int sec) throws IOException {
        byte [] temp= new byte [bytes];
        for (int i=0; i< bytes; i++) {
            if (sendOnValve) {
                temp[i] = (byte) 0x00;
            }else
            {
                temp[i] = (byte) 0xFF;
            }
        }

        for (int i = 0; i < sec; i++)
        {

            if (!serialCom.TxCmdData(temp))
            {
                //MainForm._myForm.UpdateStatus("Fail put device to sleep!");
                return false;
            }
            //Thread.Sleep(5);
        }
        return true;
    }
    private  void saveImageToExternal(String imgName, Bitmap bm, Context context) throws IOException {


        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath + "/folder_name/");
        dir.mkdirs();
        File imageFile = new File(dir, imgName + ".png");

        FileOutputStream out = new FileOutputStream(imageFile);
        try {
            bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
            out.flush();
            out.close();

            // immediately available to the user.
            MediaScannerConnection.scanFile(context, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    Log.i("ExternalStorage", "Scanned " + path + ":");
                    Log.i("ExternalStorage", "-> uri=" + uri);
//                    addImage(path);
                }
            });
        } catch (Exception e) {
            throw new IOException();
        }
    }
    private ProgressDialog progress_auto;//Sending
    public void showSendingDialog_auto(){
        if (progress_auto == null) {
            progress_auto = new ProgressDialog(getActivity());
            progress_auto.setTitle("Clock Task" +
                    "" +
                    "" +
                    "");
            progress_auto.setMessage("Showing...");
            //progress_auto.setCancelable(false);
            progress_auto.setCanceledOnTouchOutside(false);
            progress_auto.setCancelable(false);
            progress_auto.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            progress_auto.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress_auto.setButton(DialogInterface.BUTTON_NEGATIVE, "Stop", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    isStop=true;
                    isAutoShow=false;
                }
            });
        }
        progress_auto.show();
    }
       private void dismissSenddingDialog_auto() {
        if (progress_auto != null && progress_auto.isShowing()) {
            progress_auto.dismiss();
        }
    }
    private ProgressDialog progress2;//Sending
    private void dismissSenddingDialog() {
        if (progress2 != null && progress2.isShowing()) {
            progress2.dismiss();
        }
    }
    public void showSendingDialog() {

        if (progress2 == null) {
            progress2 = new ProgressDialog(getActivity(),R.style.Theme_IAPTheme);
            progress2.setTitle("Clock Task" +
                    "" +
                    "" +
                    "");
            progress2.setMessage("Showing...");
            progress2.setCancelable(false);
            progress2.setCanceledOnTouchOutside(false);
        }
        progress2.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        progress2.show();
    }
    private RadioGroup.OnCheckedChangeListener radioCheckedChange = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            RadioButton rb = (RadioButton) group.findViewById(checkedId);
            if (null != rb && checkedId > -1) {

                switch (checkedId) {
                    case R.id.radioOnBw2CLock:
                        sendOnValve=true;
                        break;
                    case R.id.radioOffBw2CLock:
                        sendOnValve=false;
                        break;
                    case R.id.radioDigitalClock:
                        layoutAnalogClock.setVisibility(View.GONE);
                        //layoutFormatTimeClock.setVisibility(View.VISIBLE);
                        layoutDigitalClock.setVisibility(View.VISIBLE);
                        try{
                            if(layoutDigitalClock.getWidth()>0 && layoutDigitalClock.getHeight()>0) {
                                //ToastShow("Width, Height: "+layoutClockGeneral.getWidth()+", "+layoutClockGeneral.getHeight());
                                imageClock = viewToBitmap(layoutDigitalClock);
                            }

                        }catch (Exception e)
                        {

                            ToastShow(e.toString());
                        }

                        break;
                    case R.id.radioAnalogClock:
                        layoutDigitalClock.setVisibility(View.GONE);
                        //layoutFormatTimeClock.setVisibility(View.GONE);
                        layoutAnalogClock.setVisibility(View.VISIBLE);
                        try {

                            if(layoutAnalogClock.getWidth()>0 && layoutAnalogClock.getHeight()>0){
                               // ToastShow(" Width, Height: "+layoutClockGeneral.getWidth()+", "+layoutClockGeneral.getHeight());
                                imageClock=viewToBitmap(layoutAnalogClock);
                            }
                        }catch (Exception e){
                            ToastShow(e.toString());
                        }
                        break;
                }
            }
        }
    };
    private void spinnerLoadClock(boolean boo) {
        try {
            if (boo == true) {
                //Load Valves enanble Color
//                valves = getActivity().getResources().getStringArray(R.array.valvesEnable);
                formatClock = getActivity().getResources().getStringArray(R.array.FormatClockEnable);
                //Load Languages enable Color
            } else {
                //Load Valves Disable Color
//                valves = getActivity().getResources().getStringArray(R.array.valvesDisable);
                formatClock = getActivity().getResources().getStringArray(R.array.FormatClockEnable);
                //Load Languages Disable Color
            }

            for (int i=0; i< formatClock.length;i++)
            {
                spannedStringsFormatClock[i] = Html.fromHtml(formatClock[i]);
            }

            spinCLockFormatTime.setAdapter(new ArrayAdapter<CharSequence>(getActivity(),R.layout.spinner_item_valves,spannedStringsFormatClock));
            //languages Adapter

        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }

    }
    private void getSettingData() {
        Cursor cursorCT = PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_SETTINGS);
        while (cursorCT.moveToNext()) {
            vanNumber = Integer.parseInt(cursorCT.getString(1));
            thresholeNumber = Integer.parseInt(cursorCT.getString(4));
            milliseconds = Integer.parseInt(cursorCT.getString(2));
        }
    }
    public void ToastShow(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

}

