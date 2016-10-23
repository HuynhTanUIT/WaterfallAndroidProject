package com.example.tan_pc.navigationdraweractivity;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import adapter.GridAdapterHome;
import adapter.ImageSingelHome;
import classConvertBinary.ConvertBinary;
import classConvertBinary.ConvertBinary;

import static SettingsSQLite.SqliteHelper.TABLE_BINARY_192;
import static SettingsSQLite.SqliteHelper.TABLE_HOME;
import static SettingsSQLite.SqliteHelper.TABLE_SETTINGS;
import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;
import static com.example.tan_pc.navigationdraweractivity.MainActivity.PROJECTDATABASE;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    //Give Action ApplyClick
    private Button btnApply;
    private CountDownTimer countDownTimer;

    TextView txtChooseImageFrom;
    TextView txtConvertHome;
    //TextView txtThreholdHome;
    TextView txtWxH;
    TextView txt192x;

    TextView txtView;
    TextView txtSendToHardware;
    TextView txtRepeatTimeHome;
    TextView txtRepeatAfterHome;
    TextView txtSendingProgressHome;
    TextView txtpercentTextHome;

    ImageView imageViewColorImageHome;
    ImageView imageViewBinaryImageHome;

    Button btnChooseHome;
    Button btnConvertHome;
    Button btnSendHome;

    RadioButton radioGallery;
    RadioButton radioCamera;

    //EditText edtThreholdHome;
    EditText edtHeightHome;
    EditText edtRepeatTimeHome;
    EditText edtRepeatAfterHome;

    Switch switchConfigSizeHome;
    Switch switchActiveHome;

    CheckBox checkboxConvertAndSaveHome;

    ProgressBar progressBarHome;
    ProgressBar progressBarConvertHome;

    LinearLayout HomeLinearLayout;


    //Backup Component Varible
    RadioButton radioGalleryB;
    RadioButton radioCameraB;
    Switch switchConfigSizeHomeB;
    Switch switchActiveHomeB;
    EditText edtHeightHomeB;
    EditText edtRepeatTimeHomeB;
    EditText edtRepeatAfterHomeB;

    TextView txtRepeatTimeHomeB;
    TextView txtRepeatAfterHomeB;
    TextView txtSendingProgressHomeB;

    CheckBox checkboxConvertAndSaveHomeB;

    ImageView imageViewColorImageHomeB;
    ImageView imageViewBinaryImageHomeB;


    ProgressBar progressBarHomeB;
    ProgressBar progressBarConvertHomeB;

    ConvertBinary convertBinary;

    //truong hop bam choose nhieu hon 1 lan
    //boolean choose=true;
    //GridView gridviewHome;

    private Uri picUri;
    final int CROP_PIC = 2;


    ArrayList<ImageSingelHome> imageArray;
    ArrayAdapter<ImageSingelHome> adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        InitializeComponent(v);
        return v;
    }
    private TextWatcher edtTextChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
          EnableButton(btnConvertHome,true);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void InitializeComponent(View v) {
        try {
            ReflectAndListener(v);
            switchConfigSizeHome.setOnCheckedChangeListener(checkedChangedListenner);

            switchActiveHome.setOnCheckedChangeListener(checkedChangedListenner);
            //txt192x.setOnFocusChangeListener(txtFocusChange);
            HomeLinearLayout.setOnClickListener(btnClickListener);

            checkboxConvertAndSaveHome.setOnCheckedChangeListener(checkedChangedListenner);

            btnChooseHome.setOnClickListener(btnClickListener);
            btnConvertHome.setOnClickListener(btnClickListener);
            btnSendHome.setOnClickListener(btnClickListener);


            radioGallery.setOnClickListener(btnClickListener);
            radioCamera.setOnClickListener(btnClickListener);

            edtHeightHome.setOnClickListener(btnClickListener);
            edtHeightHome.setOnFocusChangeListener(edtFocusChange);
            edtHeightHome.addTextChangedListener(edtTextChangeListener);

            edtRepeatTimeHome.setOnClickListener(btnClickListener);
            edtRepeatTimeHome.setOnFocusChangeListener(edtFocusChange);


            edtRepeatAfterHome.setOnClickListener(btnClickListener);
            edtRepeatAfterHome.setOnFocusChangeListener(edtFocusChange);

            //LOad Values Threshold and WidthSize from database
            LoadHomeValues();
            LoadThresholdValves();
            StartTimer();

            //gridviewHome.setOnItemClickListener(onItemClickListener);
            //gridviewHome.setCon
            //LoadImageToGridView();
            // Get intent, action and MIME type
//            if(bundle.getInt("resultApply")==1){
//                LoadThresholdValves();
//            }//
        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }
    }

    private CompoundButton.OnCheckedChangeListener checkedChangedListenner = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.switchConfigSizeHome:
                    switchConfigSizeHomecheck(switchConfigSizeHome.isChecked());
                    break;
                case R.id.switchActiveHome:
                    switchActiveHomecheck(switchActiveHome.isChecked());
                    //EnableEditText(edtRepeatAfterHome,true);
                    break;
                case R.id.checkboxConvertAndSaveHome:
                    EnableButton(btnConvertHome,true);
                    break;
            }
        }
    };

    public void switchActiveHomecheck(boolean b) {
        if (b == true) {
            EnableTextView(txtRepeatTimeHome, true);
            EnableTextView(txtRepeatAfterHome, true);
            EnableTextView(txtSendingProgressHome, true);

            EnableEditText(edtRepeatTimeHome, true);
            EnableEditText(edtRepeatAfterHome, true);
            EnableButton(btnSendHome,true);


            progressBarHome.setVisibility(getView().VISIBLE);
            txtpercentTextHome.setVisibility(getView().VISIBLE);


        } else {
            EnableTextView(txtRepeatTimeHome, false);
            EnableTextView(txtRepeatAfterHome, false);
            EnableTextView(txtSendingProgressHome, false);

            EnableEditText(edtRepeatTimeHome, false);
            EnableEditText(edtRepeatAfterHome, false);
            EnableButton(btnSendHome,true);



            progressBarHome.setVisibility(getView().GONE);
            txtpercentTextHome.setVisibility(getView().GONE);


        }
    }
    private void EnableButton(Button btn, boolean b) {
        if (b == true) {

            btn.setBackgroundColor(Color.parseColor("#269999"));
            btn.setEnabled(true);

        } else {
            btn.setBackgroundColor(Color.parseColor("#d3d3d3"));
            btn.setEnabled(false);
        }
    }

    public void switchConfigSizeHomecheck(boolean b) {
        if (b == true) {
            EnableEditText(edtHeightHome, true);
            edtHeightHome.setFocusable(false);
//            edtHeightHome.requestFocus();
//            edtHeightHome.setImeOptions((EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI));
        } else {
            EnableEditText(edtHeightHome, false);
        }
        try {
            int iH = imageViewColorImageHome.getDrawable().getIntrinsicHeight();//H original
            int iW = imageViewColorImageHome.getDrawable().getIntrinsicWidth();//W original
            // int ih = imageViewColorImageHome.getMeasuredHeight();//height of imageView
            Cursor cursorCT = PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_SETTINGS);
            while (cursorCT.moveToNext()) {
                int id = cursorCT.getInt(1);
                int NumVal = NumberOfValves(id);
                txt192x.setText(String.valueOf(NumVal) + " x ");
                int newHeight = (int) (((float) iH / iW) * NumVal);
                if(newHeight>999){
                    edtHeightHome.setText(String.valueOf(999));
                }
                else {
                    edtHeightHome.setText(String.valueOf(newHeight));
                }
               // edtHeightHome.setText(String.valueOf(newHeight));
//                    //txtSendToHardware.setText(String.valueOf(iH)+" "+String.valueOf(iW)+ " "+ String.valueOf((float)iH/iW)  +" "+newHeight);
//                    ToastShow(String.valueOf(iH)+" "+String.valueOf(iW)+ " "+ String.valueOf((float)iH/iW)  +" "+newHeight);
            }//
//                ToastShow(String.valueOf(iH) +" "+ String.valueOf((float)(iH/iW)*NumberValves));
        } catch (Exception e) {
            edtHeightHome.setText("");
            //edtHeightHome.setEnabled(false);
        }
    }

    private void EnableTextView(TextView txv, boolean b) {
        if (b == true) {
            txv.setTextColor(Color.parseColor("#125656"));
        } else {
            txv.setTextColor(Color.parseColor("#d3d3d3"));
        }

    }

    private void EnableEditText(EditText edt, boolean b) {
        if (b == true) {
            edt.setEnabled(true);
            edt.setTextColor(Color.parseColor("#125656"));
            edt.setFocusable(false);

        } else {
            edt.setEnabled(false);
            edt.setTextColor(Color.parseColor("#d3d3d3"));
            edt.setFocusable(false);
        }
    }

    private void LoadHomeValues() {

        Cursor cursorCT = PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_HOME);
        while (cursorCT.moveToNext()) {
            edtRepeatTimeHome.setText(String.valueOf(cursorCT.getInt(1)));
            edtRepeatAfterHome.setText(String.valueOf(cursorCT.getInt(2)));
            // txt192x.setText(String.valueOf(NumberOfValves(cursorCT.getInt(1))) + " x ");

            //edtThreholdHome.setText(String.valueOf(cursorCT.getInt(4)));
        }
        PROJECTDATABASE.close();

    }

    //Load Threshold and valve from database to TextView
    private void LoadThresholdValves() {
        try {
            Cursor cursorCT = PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_SETTINGS);
            while (cursorCT.moveToNext()) {
                txt192x.setText(String.valueOf(NumberOfValves(cursorCT.getInt(1))) + " x ");
                //edtThreholdHome.setText(String.valueOf(cursorCT.getInt(4)));
            }
            PROJECTDATABASE.close();
        } catch (Exception e) {

        }
    }

//    private View.OnFocusChangeListener LinearFocusChange = new View.OnFocusChangeListener() {
//        @Override
//        public void onFocusChange(View v, boolean hasFocus) {
////            switch (v.getId()) {
////                case R.id.edt2Rows:
//            try {
//                if (!hasFocus) {
//                }
//                Log.e("LINEARHOME FOCUS", "onFocusChange: " );
//            } catch (Exception e) {
//            }
//
//        }
//    };

    public void LoadImageToGridView() {

        imageArray.clear();
        Cursor image = PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_BINARY_192);
        while (image.moveToNext()) {
            imageArray.add(new ImageSingelHome(
                    image.getString(1),
                    image.getLong(2),
                    image.getLong(3),
                    image.getBlob(4)
            ));
        }
//            GridAdapterHome apdaterHome = new GridAdapterHome(getContext(), R.layout.row, imageArray);
//            gridviewHome.setAdapter(apdaterHome);

    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
//                PROJECTDATABASE.DETELE__BINARY_192Valves((int)id);
//                LoadImageToGridView();
            } catch (Exception e) {
//                ToastShow(e.getMessage().toString());
            }
        }
    };

    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SetEditTextFocus();
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            switch (view.getId()) {
                case R.id.edtHeightHome:
                    edtHeightHome.setFocusable(true);
                    edtHeightHome.setFocusableInTouchMode(true);
                    edtHeightHome.requestFocus();
                    edtHeightHome.setImeOptions((EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI));
                    break;
                case R.id.edtRepeatTimeHome:
                    edtRepeatTimeHome.setFocusable(true);
                    edtRepeatTimeHome.setFocusableInTouchMode(true);
                    edtRepeatTimeHome.requestFocus();
                    edtRepeatTimeHome.setImeOptions((EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI));
                    break;
                case R.id.edtRepeatAfterHome:
                    edtRepeatAfterHome.setFocusable(true);
                    edtRepeatAfterHome.setFocusableInTouchMode(true);
                    edtRepeatAfterHome.requestFocus();
                    edtRepeatAfterHome.setImeOptions((EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI));
                    break;
                case R.id.btnChooseHome:
                    btnChooseHome.setEnabled(false);
                    ButtonChooseHomeClicked();
                    break;
                case R.id.btnConvertHome:

                    ButtonConvertHomeClicked();
                    if(checkboxConvertAndSaveHome.isChecked()){
                        SaveToDatabase();
                    }
                    break;
                case R.id.btnSendHome:
                    break;
                //RADIO CLICK
                case R.id.radioGallery:
                    RadioGalleryClicked();
                    break;
                case R.id.radioCamera:
                    RadioCameraClicked();
                    break;
                case R.id.HomeLinearLayout:
//                    edtIP.setError(null);
                    ClearEditTextFocus();

                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                    InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getApplicationWindowToken(), 0);

                    LoadThresholdValves();
                    break;
            }
        }
    };

    private void ButtonConvertHomeClicked() {
        try {
            //Resized Imaged Color
            Bitmap resized = null;
            Bitmap resizedGrayscale = null;
            Bitmap resizedBinary = null;
            Bitmap bitmap = ((BitmapDrawable) imageViewColorImageHome.getDrawable()).getBitmap();
            int iH = Integer.parseInt(edtHeightHome.getText().toString());//H original
            int iW = bitmap.getWidth();//W original
            Cursor cursorCT = PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_SETTINGS);
            while (cursorCT.moveToNext()) {
                int id = cursorCT.getInt(1);
                int newWidth = NumberOfValves(id);
                int newHeight = iH;//(int) (((float) iH / iW) * newWidth);//newWidth=valves
                resized = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
                //imageViewBinaryImageHome.setImageBitmap(resized);

               // ToastShow(String.valueOf(resized.getWidth()) + " " + String.valueOf(resized.getHeight()));
                //txtSendToHardware.setText(String.valueOf(iH)+" "+String.valueOf(iW)+ " "+ String.valueOf((float)iH/iW)  +" "+newWidth);
                //ToastShow(String.valueOf(iH) + " " + String.valueOf(iW) + " " + String.valueOf((float) iH / iW) + " " + newWidth);
            }//
            //convert to Gray Image
            resizedGrayscale = ConvertToGrayscale(resized);
            //ConvertToBinary
            resizedBinary = GrayscaletoBinary(resizedGrayscale);
            imageViewBinaryImageHome.setImageBitmap(resizedBinary);
            //progressBarConvertHome.setVisibility(getView().GONE);
            //btnConvertHome.setEnabled(true);

            ClearEditTextFocus();
            EnableButton(btnConvertHome,false);

        } catch (Exception e) {
            if (e.getMessage().toString().contains("null object")) {
                ToastShow("Choose Image First! ");
//                progressBarConvertHome.setVisibility(getView().GONE);

            }
            //ToastShow(e.getMessage().toString());
        }
//        BitmapDrawable bm=(BitmapDrawable)imageViewColorImageHome.getDrawable();
    }


    //ConVert Grayscale To binary
    public Bitmap GrayscaletoBinary(Bitmap bmpOriginal) {
        int width, height, threshold;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        threshold = 127;
//
//
        Bitmap bmpBinary = Bitmap.createBitmap(bmpOriginal);

        for (int x = 0; x < width; ++x) {
            // progressBarConvertHome.setVisibility(getView().VISIBLE);
            for (int y = 0; y < height; ++y) {
                // get one pixel color
                int pixel = bmpOriginal.getPixel(x, y);
                int red = Color.red(pixel);

                //get binary value
                if (red < threshold) {
                    bmpBinary.setPixel(x, y, 0xFF000000);
                } else {
                    bmpBinary.setPixel(x, y, 0xFFFFFFFF);
                }

            }
        }
        return bmpBinary;
    }

    //Convert To GrayScale
    public Bitmap ConvertToGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    private View.OnFocusChangeListener edtFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

//            switch (v.getId()) {
//                case R.id.edt2Rows:
            try {
                if (!hasFocus) {
                    //Switch1check(switch1.isChecked());
                    ClearEditTextFocus();
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                    InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getApplicationWindowToken(), 0);
                }
            } catch (Exception e) {
                ToastShow(e.getMessage().toString());
            }

        }
    };

    private void SetEditTextFocus() {
        edtHeightHome.setFocusable(true);
        edtHeightHome.setFocusableInTouchMode(true);

        edtRepeatAfterHome.setFocusable(true);
        edtRepeatAfterHome.setFocusableInTouchMode(true);

        edtRepeatTimeHome.setFocusable(true);
        edtRepeatAfterHome.setFocusableInTouchMode(true);
    }

    private void ClearEditTextFocus() {
        edtHeightHome.setFocusable(false);
        edtRepeatAfterHome.setFocusable(false);
        edtRepeatTimeHome.setFocusable(false);
    }

    private void ButtonChooseHomeClicked() {
        if (radioCamera.isChecked() == true) {
            CameraMethod();
        } else if (radioGallery.isChecked() == true) {
            GalleryMethod();
            //btnChooseHome.setEnabled(true);
        }
        //
    }

    private void RadioGalleryClicked() {
        radioCamera.setChecked(false);
        radioGallery.setChecked(true);
    }

    private void RadioCameraClicked() {
        radioGallery.setChecked(false);
        radioCamera.setChecked(true);
    }


    public void SaveToDatabase() {
        try {
//            String s=String.valueOf(PROJECTDATABASE.CountImage192Valves());
            long yourmilliseconds = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss");
            Date resultdate = new Date(yourmilliseconds);
            System.out.println(sdf.format(resultdate));
            //ToastShow(s);
            PROJECTDATABASE.INSERT_BINARY_192Valves(
                    "Image192_" + sdf.format(resultdate),
                    192,
                    192,
                    ImageView_To_Byte(imageViewBinaryImageHome)
            );
            LoadImageToGridView();
            ToastShow("Binary Image Has Been Saved!");
        } catch (Exception e) {
            if (e.getMessage().toString().contains("UNIQUE")) {
                ToastShow("This image already exists! ");
            } else if (e.getMessage().toString().contains("Attempt to invoke virtual method 'android.graphics.Bitmap")) {
                ToastShow("Nothing to save!");
            } else
                ToastShow("Failed When Saving Image. Error Detail:\n\t" + e.getMessage().toString());
        }
    }

    //    public void RefreshGridview(){
////        Cursor image=PROJECTDATABASE.GetData("SELECT * FROM "+TABLE_BINARY_192);
////        while (image.moveToNext()){
////            imageArray.add(new ImageSingelHome(
////                    image.getString(1),
////                    image.getLong(2),
////                    image.getLong(3),
////                    image.getBlob(4)
////            ));
////        }
//        GridAdapterHome apdaterHome= new GridAdapterHome(getContext(),R.layout.row,imageArray);
////      gridviewHome.setAdapter(apdaterHome);;
//        gridviewHome.setAdapter(apdaterHome);
//    }
    public void GalleryMethod() {
        try {
            Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            gallery.setType("image/*");
            gallery.putExtra("crop", "true");
            //gallery.putExtra("scale", true);
            gallery.putExtra("return-data", true);
            //startActivityForResult(gallery, 1111);
            startActivityForResult(Intent.createChooser(gallery, "Select Image"), 1111);

//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.putExtra("return-data", toString());
////            intent.putExtra("scale", true);
////            intent.putExtra("crop", "true");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(Intent.createChooser(intent, "Select Image"), 1111);
            // Luu ANh dang URI*

        } catch (Exception e) {
            ToastShow("This device doesn't support this action!");
        }
    }

    public void CameraMethod() {
        try {
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            camera.putExtra("scale", true);
            camera.putExtra("crop", "true");
            camera.putExtra("return-data", true);
            startActivityForResult(camera, 8888);
        } catch (Exception e) {
            ToastShow("This device doesn't support Camera!");
        }

    }

    /**
     * this function does the crop operation.
     */
    private void performCrop() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
//            cropIntent.putExtra("aspectX", 1);
//            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
//            cropIntent.putExtra("outputX", 256);
//            cropIntent.putExtra("outputY", 256);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, CROP_PIC);
        }
        // respond to users whose devices do not support the crop action
        catch (Exception e) {
            Toast toast = Toast
                    .makeText(getContext(), "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == 1111) {
                    Bitmap photo = (Bitmap) data.getParcelableExtra("data");
                    imageViewColorImageHome.setImageBitmap(photo);
                    imageViewBinaryImageHome.setImageBitmap(null);
//                    picUri = data.getData();
//                    performCrop();
                    //imageViewColorImageHome.setImageURI(data.getData()); //Luu anh dang URI
                } else if (requestCode == 8888) {
//                Bitmap photo = (Bitmap)data.getExtras().get("data");
//            imageViewColorImageHome.setImageBitmap(photo);
                    picUri = data.getData();
                    performCrop();
                    imageViewBinaryImageHome.setImageBitmap(null);

                } else if (requestCode == CROP_PIC) {
                    // get the returned data
                    Bundle extras = data.getExtras();
                    // get the cropped bitmap
                    Bitmap thePic = extras.getParcelable("data");
                    imageViewColorImageHome.setImageBitmap(thePic);
                    imageViewBinaryImageHome.setImageBitmap(null);

                } else {
                    picUri = data.getData();
                    performCrop();
                    imageViewBinaryImageHome.setImageBitmap(null);
                    imageViewColorImageHome.setImageURI(data.getData()); //Luu anh dang URI
                }
            }
            ///////////////////////////////////////////////////////////////
            EnableButton(btnConvertHome,true);
            btnChooseHome.setEnabled(true);
            switchConfigSizeHome.setChecked(false);
            switchConfigSizeHomecheck(false);
            try {
                int iH = imageViewColorImageHome.getDrawable().getIntrinsicHeight();//H original
                int iW = imageViewColorImageHome.getDrawable().getIntrinsicWidth();//W original

                // int ih = imageViewColorImageHome.getMeasuredHeight();//height of imageView

                Cursor cursorCT = PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_SETTINGS);
                while (cursorCT.moveToNext()) {

                    int id = cursorCT.getInt(1);
                    int NumVal = NumberOfValves(id);
                    txt192x.setText(String.valueOf(NumVal) + " x ");
                    int newHeight = (int) (((float) iH / iW) * NumVal);

                    if(newHeight>999){
                        edtHeightHome.setText(String.valueOf(999));
                    }
                    else {
                        edtHeightHome.setText(String.valueOf(newHeight));
                    }

                    //fdgfhgjhk
                    //edtHeightHome.setText(String.valueOf(newWidth));
                    //txtSendToHardware.setText(String.valueOf(iH)+" "+String.valueOf(iW)+ " "+ String.valueOf((float)iH/iW)  +" "+newWidth);
                    //ToastShow(String.valueOf(iH) + " " + String.valueOf(iW) + " " + String.valueOf((float) iH / iW) + " " + newWidth);
                }
//
//                ToastShow(String.valueOf(iH) +" "+ String.valueOf((float)(iH/iW)*NumberValves));
            } catch (Exception e) {

            }


        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }
    }


    private void ReflectAndListener(View v) {
        try {
            HomeLinearLayout = (LinearLayout) v.findViewById(R.id.HomeLinearLayout);

            txtChooseImageFrom = (TextView) v.findViewById(R.id.txtChooseImageFrom);
            //txtThreholdHome = (TextView) v.findViewById(R.id.txtThreholdHome);
            txtWxH = (TextView) v.findViewById(R.id.txtWxH);
            txt192x = (TextView) v.findViewById(R.id.txt192x);
            txtView = (TextView) v.findViewById(R.id.txtView);
            txtSendToHardware = (TextView) v.findViewById(R.id.txtSendToHardware);
            txtRepeatTimeHome = (TextView) v.findViewById(R.id.txtRepeatTimeHome);
            txtRepeatAfterHome = (TextView) v.findViewById(R.id.txtRepeatAfterHome);
            txtSendingProgressHome = (TextView) v.findViewById(R.id.txtSendingProgressHome);
            txtpercentTextHome = (TextView) v.findViewById(R.id.txtpercentTextHome);
            txtConvertHome = (TextView) v.findViewById(R.id.txtConvertHome);

            imageViewColorImageHome = (ImageView) v.findViewById(R.id.imageViewColorImageHome);
            imageViewBinaryImageHome = (ImageView) v.findViewById(R.id.imageViewBinaryImageHome);

            btnChooseHome = (Button) v.findViewById(R.id.btnChooseHome);
            btnConvertHome = (Button) v.findViewById(R.id.btnConvertHome);
            btnSendHome = (Button) v.findViewById(R.id.btnSendHome);

            radioGallery = (RadioButton) v.findViewById(R.id.radioGallery);
            radioCamera = (RadioButton) v.findViewById(R.id.radioCamera);

            //edtThreholdHome = (EditText) v.findViewById(R.id.edtThreholdHome);
            edtHeightHome = (EditText) v.findViewById(R.id.edtHeightHome);
            edtRepeatTimeHome = (EditText) v.findViewById(R.id.edtRepeatTimeHome);
            edtRepeatAfterHome = (EditText) v.findViewById(R.id.edtRepeatAfterHome);

            switchConfigSizeHome = (Switch) v.findViewById(R.id.switchConfigSizeHome);
            switchActiveHome = (Switch) v.findViewById(R.id.switchActiveHome);

            checkboxConvertAndSaveHome = (CheckBox) v.findViewById(R.id.checkboxConvertAndSaveHome);

            progressBarHome = (ProgressBar) v.findViewById(R.id.progressBarHome);
            progressBarConvertHome = (ProgressBar) v.findViewById(R.id.progressBarConvertHome);


            // gridviewHome = (GridView) v.findViewById(R.id.gridviewHome);
            convertBinary = new ConvertBinary();
            imageArray = new ArrayList<ImageSingelHome>();
            adapter = new ArrayAdapter<ImageSingelHome>(getContext(), R.layout.row, imageArray);
        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }
    }

    //Load Database After
    private void StartTimer() {
        countDownTimer = new CountDownTimer(1000, 500) {
            @Override
            public void onTick(long l) {
                try {
                    //LoadThresholdValves();
                    // isValvesChange
                    Bundle isValvesChange = getActivity().getIntent().getExtras();
                    if (isValvesChange != null) {
//                        if(isValvesChange.getString("ValvesChange"))
//                        Log.e("bulble Nullllllll ","NUll ROi");
//                        String txt192xBK=txt192x.getText().toString().trim();
                        txt192x.setText(isValvesChange.getString("ValvesChange").trim() + " x ");
//                        String txtTemp =txt192x.getText().toString().trim();
//                        if(txt192xBK!=txtTemp){
//                            int iH = imageViewColorImageHome.getDrawable().getIntrinsicHeight();//H original
//                            int iW = imageViewColorImageHome.getDrawable().getIntrinsicWidth();//W original
//                            int NumVal = Integer.parseInt(isValvesChange.getString("ValvesChange").trim()) ;
//                            // int ih = imageViewColorImageHome.getMeasuredHeight();//height of imageView
//                            int newWidth = (int) (((float) iH / iW) * NumVal);
//                            edtHeightHome.setText(String.valueOf(newWidth));
//                            switchConfigSizeHome.setChecked(false);
//                            }
                    }
                    isValvesChange.clear();

                } catch (Exception e) {

                }
            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();
                countDownTimer = null;
                StartTimer();
            }
        };
        countDownTimer.start();
    }

    //khi xoay man hinh thi khong bi giu nguyen layout
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        try {

            ClearEditTextFocus();
            super.onConfigurationChanged(newConfig);
            BackupComponen();
            ViewGroup rootView = (ViewGroup) getView();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View newview = inflater.inflate(R.layout.fragment_home, rootView, false);
            rootView.removeAllViews();
            rootView.addView(newview);
//            //Restore Values
//
            InitializeComponent(newview);
            RecoverValuesComponent();
        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }

    }

    private void BackupComponen() {
        radioGalleryB = radioGallery;
        radioCameraB = radioCamera;

        switchConfigSizeHomeB = switchConfigSizeHome;
        switchActiveHomeB = switchActiveHome;

        edtHeightHomeB = edtHeightHome;
        edtHeightHomeB = edtHeightHome;

        edtRepeatTimeHomeB = edtRepeatTimeHome;
        edtRepeatTimeHomeB = edtRepeatTimeHome;
        edtRepeatAfterHomeB = edtRepeatAfterHome;
        edtRepeatAfterHomeB = edtRepeatAfterHome;

        txtRepeatTimeHomeB = txtRepeatTimeHome;
        txtRepeatAfterHomeB = txtRepeatAfterHome;
        txtSendingProgressHomeB = txtSendingProgressHome;
        checkboxConvertAndSaveHomeB = checkboxConvertAndSaveHome;

        imageViewColorImageHomeB = imageViewColorImageHome;

        imageViewBinaryImageHomeB = imageViewBinaryImageHome;

         progressBarHomeB=progressBarHome;
         progressBarConvertHomeB=progressBarConvertHome;

    }

    private void RecoverValuesComponent() {
        radioGallery.setChecked(radioGalleryB.isChecked());
        radioCamera.setChecked(radioCameraB.isChecked());

        switchConfigSizeHome.setChecked(switchConfigSizeHomeB.isChecked());
        switchActiveHome.setChecked(switchActiveHomeB.isChecked());

        edtHeightHome.setEnabled(edtHeightHomeB.isEnabled());
        edtHeightHome.setText(edtHeightHomeB.getText());

        edtRepeatTimeHome.setEnabled(edtRepeatTimeHomeB.isEnabled());
        edtRepeatTimeHome.setText(edtRepeatTimeHomeB.getText());
        edtRepeatAfterHome.setEnabled(edtRepeatAfterHomeB.isEnabled());
        edtRepeatAfterHome.setText(edtRepeatAfterHomeB.getText());

        txtRepeatTimeHome.setTextColor(txtRepeatTimeHomeB.getTextColors());
        txtRepeatAfterHome.setTextColor(txtRepeatAfterHomeB.getTextColors());
        txtSendingProgressHome.setTextColor(txtSendingProgressHomeB.getTextColors());
        checkboxConvertAndSaveHome.setChecked(checkboxConvertAndSaveHomeB.isChecked());

        imageViewColorImageHome.setImageDrawable(imageViewColorImageHomeB.getDrawable());

        imageViewBinaryImageHome.setImageDrawable(imageViewBinaryImageHomeB.getDrawable());

        progressBarHome.setProgress(progressBarHome.getProgress());
        progressBarConvertHome.setProgress(progressBarConvertHome.getProgress());

    }


    public byte[] ImageView_To_Byte(ImageView imgv) {
        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void ToastShow(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    public int NumberOfValves(int id) {
        int s = 0;
        switch (id) {
            case 0:
                s = 192;
                break;
            case 1:
                s = 160;
                break;
            case 2:
                s = 128;
                break;
            case 3:
                s = 96;
                break;
            case 4:
                s = 64;
                break;
            case 5:
                s = 32;
                break;
            case 6:
                s = 16;
                break;
            case 7:
                s = 8;
                break;

        }
        return s;
    }


}
