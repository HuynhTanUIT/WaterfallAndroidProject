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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
import static com.example.tan_pc.navigationdraweractivity.MainActivity.PROJECTDATABASE;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView txtColorHome;
    TextView txtBinaryImageViewHome;
    TextView txtPickImageFromHome;
    TextView txtSaveSend;
    TextView txtDatabaseHome;

    ImageView imageViewColorImageHome;
    ImageView imageViewBinaryImageHome;

    Button btnGalleryHome;
    Button btnCameraHome;
    Button btnSaveHome;
    Button btnConvertHome;
    Button btnSendHome;

    GridView gridviewHome;

    private Uri picUri;
    final int CROP_PIC = 2;

    private CountDownTimer countDownTimer;
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
            btnGalleryHome.setOnClickListener(btnClickListener);
            btnCameraHome.setOnClickListener(btnClickListener);
            btnSaveHome.setOnClickListener(btnClickListener);
            btnConvertHome.setOnClickListener(btnClickListener);
            btnSendHome.setOnClickListener(btnClickListener);
            gridviewHome.setOnItemClickListener(onItemClickListener);
            //gridviewHome.setCon
            LoadImageToGridView();
        }catch (Exception e){
            ToastShow(e.getMessage().toString());
        }
    }

    public void LoadImageToGridView() {
        try {
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
            GridAdapterHome apdaterHome = new GridAdapterHome(getContext(), R.layout.row, imageArray);
//      gridviewHome.setAdapter(apdaterHome);
            gridviewHome.setAdapter(apdaterHome);
            //gridviewHome.setAdapter(null);
        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }
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
            try {
                switch (view.getId()) {
                    case R.id.btnGalleryHome:
                        ButtonGalleryClicked();
                        break;
                    case R.id.btnCameraHome:
                        ButtonCameraClicked();
                        break;
                    case R.id.btnSaveHome:
                        ButtonSaveClicked();
                        break;
                    case R.id.btnConvertHome:
                        break;
                    case R.id.btnSendHome:
                        break;
                }
            } catch (Exception e) {
                ToastShow(e.getMessage().toString());
            }

        }
    };

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
    public void ButtonGalleryClicked() {
        try {
            Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            gallery.setType("image/*");
            gallery.putExtra("crop", "true");
            //gallery.putExtra("scale", true);
            gallery.putExtra("return-data", true);
            startActivityForResult(gallery, 1111);
            /***
             *Intent intent = new Intent();
             *intent.setType("image/*");
             *intent.setAction(Intent.ACTION_GET_CONTENT);
             * startActivityForResult(Intent.createChooser(intent, "Select Image"), 1111);
             *   *Luu ANh dang URI*
             */
        } catch (Exception e) {
            ToastShow("This device doesn't support this action!");
        }
    }

    public void ButtonCameraClicked() {
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
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
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
                    //                imageViewColorImageHome.setImageURI(data.getData()); //Luu anh dang URI
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
                }

            }

        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }
    }

    private void ReflectAndListener(View v) {
        try {
            txtColorHome = (TextView) v.findViewById(R.id.txtColorHome);
            txtBinaryImageViewHome = (TextView) v.findViewById(R.id.txtBinaryImageViewHome);
            txtPickImageFromHome = (TextView) v.findViewById(R.id.txtPickImageFromHome);
            txtSaveSend = (TextView) v.findViewById(R.id.txtSaveSend);
            txtDatabaseHome = (TextView) v.findViewById(R.id.txtDatabaseHome);

            imageViewColorImageHome = (ImageView) v.findViewById(R.id.imageViewColorImageHome);
            imageViewBinaryImageHome = (ImageView) v.findViewById(R.id.imageViewBinaryImageHome);

            btnGalleryHome = (Button) v.findViewById(R.id.btnGalleryHome);
            btnCameraHome = (Button) v.findViewById(R.id.btnCameraHome);
            btnSaveHome = (Button) v.findViewById(R.id.btnSaveHome);
            btnConvertHome = (Button) v.findViewById(R.id.btnConvertHome);
            btnSendHome = (Button) v.findViewById(R.id.btnSendHome);

            gridviewHome = (GridView) v.findViewById(R.id.gridviewHome);

            imageArray = new ArrayList<ImageSingelHome>();
            //adapter=new ArrayAdapter<ImageSingelHome>(getContext(),R.layout.row,imageArray);
        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }

    }

    //khi xoay man hinh thi khong bi giu nguyen layout
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        try {
            super.onConfigurationChanged(newConfig);
            BackupComponen();
            ViewGroup rootView = (ViewGroup) getView();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View newview = inflater.inflate(R.layout.fragment_home, rootView, false);
            rootView.removeAllViews();
            rootView.addView(newview);
            //Restore Values
            InitializeComponent(newview);
            RecoverValuesComponent();
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
