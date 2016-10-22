package com.example.tan_pc.navigationdraweractivity;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
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

import adapter.GridAdapterHome;
import adapter.ImageSingelHome;

import static SettingsSQLite.SqliteHelper.TABLE_BINARY_192;
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

    LinearLayout HomeLinearLayout;

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
        InitializeComponent(v);
        return v;
    }

    private void InitializeComponent(View v) {
        try {
            ReflectAndListener(v);


            //txt192x.setOnFocusChangeListener(txtFocusChange);
//            HomeLinearLayout.setOnFocusChangeListener(LinearFocusChange);

            btnChooseHome.setOnClickListener(btnClickListener);
            btnConvertHome.setOnClickListener(btnClickListener);
            btnSendHome.setOnClickListener(btnClickListener);


            radioGallery.setOnClickListener(btnClickListener);
            radioCamera.setOnClickListener(btnClickListener);
            //LOad Values Threshold and WidthSize from database
            StartTimer();

            //gridviewHome.setOnItemClickListener(onItemClickListener);
            //gridviewHome.setCon
            //LoadImageToGridView();
            // Get intent, action and MIME type
            v.setEnabled(false);


//            if(bundle.getInt("resultApply")==1){
//                LoadThresholdValves();
//            }
//
        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }
    }


    //Load Threshold and valve from database to TextView
    private void LoadThresholdValves() {
        try {
            Cursor cursorCT = PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_SETTINGS);
            while (cursorCT.moveToNext()) {
                txt192x.setText(NumberOfValves(cursorCT.getInt(1)) + " x ");
                //edtThreholdHome.setText(String.valueOf(cursorCT.getInt(4)));
            }
        }catch (Exception e){

        }
    }


    private View.OnFocusChangeListener edtFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

//            switch (v.getId()) {
//                case R.id.edt2Rows:
            try {
                if (!hasFocus) {
                    //Switch1check(switch1.isChecked());
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                    InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getApplicationWindowToken(), 0);
                }
            } catch (Exception e) {
                ToastShow(e.getMessage().toString());
            }

        }
    };
//    private View.OnFocusChangeListener LinearFocusChange = new View.OnFocusChangeListener() {
//        @Override
//        public void onFocusChange(View v, boolean hasFocus) {
////            switch (v.getId()) {
////                case R.id.edt2Rows:
//            try {
//                if (!hasFocus) {
//                    LoadThresholdValves();
//                }
//            } catch (Exception e) {
//                ToastShow(e.getMessage().toString());
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
            switch (view.getId()) {
//                case R.id.btnSaveHome:
//                    ButtonSaveClicked();
//                    break;
                //BUTTON CLICK
                case R.id.btnChooseHome:
                    btnChooseHome.setEnabled(false);
                    ButtonChooseHomeClicked();
                    break;
                case R.id.btnConvertHome:
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
            }
        }
    };

    private void ButtonChooseHomeClicked() {
        if (radioCamera.isChecked() == true) {
            CameraMethod();
            btnChooseHome.setEnabled(true);
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


    public void ButtonSaveClicked() {
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
                    ImageView_To_Byte(imageViewColorImageHome)
            );
            //LoadImageToGridView();
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
//                    picUri = data.getData();
//                    performCrop();
                    //imageViewColorImageHome.setImageURI(data.getData()); //Luu anh dang URI
                } else if (requestCode == 8888) {
//                Bitmap photo = (Bitmap)data.getExtras().get("data");
//            imageViewColorImageHome.setImageBitmap(photo);
                    picUri = data.getData();
                    performCrop();
                } else if (requestCode == CROP_PIC) {
                    // get the returned data
                    Bundle extras = data.getExtras();
                    // get the cropped bitmap
                    Bitmap thePic = extras.getParcelable("data");
                    imageViewColorImageHome.setImageBitmap(thePic);
                } else {
                    picUri = data.getData();
                    performCrop();
                    imageViewColorImageHome.setImageURI(data.getData()); //Luu anh dang URI
                }
            }
            btnChooseHome.setEnabled(true);
            try {
                int iH = imageViewColorImageHome.getDrawable().getIntrinsicHeight();
                int ih = imageViewColorImageHome.getMeasuredHeight();//height of imageView
                ToastShow(String.valueOf(iH) +" "+ String.valueOf(ih));
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


            // gridviewHome = (GridView) v.findViewById(R.id.gridviewHome);

            imageArray = new ArrayList<ImageSingelHome>();
            adapter = new ArrayAdapter<ImageSingelHome>(getContext(), R.layout.row, imageArray);
        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }

    }

    //Load Database After
    private void StartTimer() {
        countDownTimer = new CountDownTimer(1000, 100) {
            @Override
            public void onTick(long l) {
                LoadThresholdValves();
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
            super.onConfigurationChanged(newConfig);
//            BackupComponen();
            ViewGroup rootView = (ViewGroup) getView();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View newview = inflater.inflate(R.layout.fragment_home, rootView, false);
            rootView.removeAllViews();
            rootView.addView(newview);
//            //Restore Values
            InitializeComponent(newview);
//            RecoverValuesComponent();
        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }

    }

    private void BackupComponen() {

    }

    private void RecoverValuesComponent() {

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

    public String NumberOfValves(int id) {
        String s = "";
        switch (id) {
            case 0:
                s = "192";
                break;
            case 1:
                s = "160";
                break;
            case 2:
                s = "128";
                break;
            case 3:
                s = "96";
                break;
            case 4:
                s = "64";
                break;
            case 5:
                s = "32";
                break;
            case 6:
                s = "16";
                break;
            case 7:
                s = "8";
                break;

        }
        return s;
    }
//    private void start() {
//        cancel();
//        countDownTimer = new CountDownTimer(92000 * 1000, 3000) {
//            @Override
//            public void onTick(long l) {
//                loadImage();
//                //timetext.setText(""+l/1000);
//            }
//
//            @Override
//            public void onFinish() {
//                // timetext.setText("Done");
//                //cancel();
//            }
//        };
//        countDownTimer.start();
//    }
//
//    private void cancel() {
//        if (countDownTimer != null) {
//            countDownTimer.cancel();
//            countDownTimer = null;
//        }
//    }
//
//    private void loadImage() {
//        ArrayList<Integer> arrayimg = new ArrayList<Integer>();
//        arrayimg.add(R.drawable.a1);//0
//        arrayimg.add(R.drawable.a2);//1
//        arrayimg.add(R.drawable.a3);//2
//        arrayimg.add(R.drawable.a4);//0
//        arrayimg.add(R.drawable.a5);//1
//        arrayimg.add(R.drawable.a6);//2arrayimg.add(R.drawable.a1);//0
//        arrayimg.add(R.drawable.a7);//1
//        arrayimg.add(R.drawable.a8);//2arrayimg.add(R.drawable.a1);//0
//        arrayimg.add(R.drawable.a9);//1
//        arrayimg.add(R.drawable.a10);//2arrayimg.add(R.drawable.a1);//0
//        arrayimg.add(R.drawable.a11);//1
//        arrayimg.add(R.drawable.a12);//2arrayimg.add(R.drawable.a1);//0
//        arrayimg.add(R.drawable.a13);//1
//        arrayimg.add(R.drawable.a14);//2arrayimg.add(R.drawable.a1);//0
//        arrayimg.add(R.drawable.a15);//1
//        arrayimg.add(R.drawable.a16);//2
//        arrayimg.add(R.drawable.a17);//2
//        arrayimg.add(R.drawable.a18);//2
//        arrayimg.add(R.drawable.a19);//2
//        arrayimg.add(R.drawable.a20);//2
//        arrayimg.add(R.drawable.a21);//2
//        arrayimg.add(R.drawable.a22);//2
//        Random r = new Random();
//        int ran = r.nextInt(arrayimg.size());
//        img.setImageResource(arrayimg.get(ran));
//        Glide
//                .with(this)
//                .load(ran)
//                .centerCrop()
//
//                .into(img);
    // background.setBackgroundResource(R.drawable.icon);
//    }


}
