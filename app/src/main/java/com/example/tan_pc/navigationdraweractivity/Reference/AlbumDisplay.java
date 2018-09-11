package com.example.tan_pc.navigationdraweractivity.Reference;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.camera.DefaultCameraModule;
import com.esafirm.imagepicker.features.camera.OnImageReadyListener;
import com.example.tan_pc.navigationdraweractivity.Activity.MainActivity;
import com.example.tan_pc.navigationdraweractivity.Reference.DeviceInfo;
import com.example.tan_pc.navigationdraweractivity.Reference.HomeAdapter;
import com.example.tan_pc.navigationdraweractivity.Reference.OnClickListenerRecycler3;
import com.example.tan_pc.navigationdraweractivity.R;
import com.example.tan_pc.navigationdraweractivity.Reference.WaterImage;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import adapter.ImageSingelHome;

import static SettingsSQLite.SqliteHelper.TABLE_IMAGE;
import static SettingsSQLite.SqliteHelper.TABLE_IMAGES_DIRECTORY;
import static SettingsSQLite.SqliteHelper.TABLE_SETTINGS;
import static android.app.Activity.RESULT_OK;
import static com.example.tan_pc.navigationdraweractivity.Reference.DeviceInfo.DisplayRow;
import static com.example.tan_pc.navigationdraweractivity.Activity.MainActivity.PROJECTDATABASE;
import static com.example.tan_pc.navigationdraweractivity.Activity.MainActivity.delayImage;
import static com.example.tan_pc.navigationdraweractivity.Activity.MainActivity.mTcpClient;
import static com.example.tan_pc.navigationdraweractivity.Activity.MainActivity.milliseconds;
import static com.example.tan_pc.navigationdraweractivity.Activity.MainActivity.serialCom;
import static com.example.tan_pc.navigationdraweractivity.Activity.MainActivity.thresholeNumber;
import static com.example.tan_pc.navigationdraweractivity.Activity.MainActivity.vanNumber;

//import static SettingsSQLite.SqliteHelper.TABLE_BINARY_192;

//import adapter.GridAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumDisplay extends Fragment implements OnClickListenerRecycler3 {
    boolean isStop=false;
    boolean isRepeat=false;
    boolean SendingDetail =false;
    DefaultCameraModule cameraModule = new DefaultCameraModule(); // or ImmediateCameraModule

    // Context context;
    ArrayList <ImageSingelHome>imageArray;
//    ArrayAdapter<ImageSingelHome> adapter;
    GridView gridviewAlbum;
    ImageButton btnRefreshAlbum;
    ImageView folderImport;
    ImageView repeatImport;
    ImageView addImageImport;
    ImageView deleteImageImport;
    Switch swInvertImport;
    RadioGroup radioGroupSendingTypeImport;
    RadioButton radioSingleImport;
    RadioButton radioAllImport;
    Button btnsendAllImport;
    ImagePicker imagePicker = null;
    public int indexImageSingle=0;
//    GridViewAdapter         adapter;
    private boolean isAllowSending=true;
    private boolean isStopSending =false;
    private boolean isSendAll=false;
    private ConvertImage convertingProcess;
    private WaterImage imgTextImage;
    public String Imagefoler_dir;
    public int indexImage=0;
    public int repeatedTime=0;
//    public String Imagefoler_dir_temp="";
    public static ProgressBar progressbar_DetailImport;
//    public static ArrayList<Model_imagebr;
    private String[]        FilePathStrings;
    private String[]        FilePathStrings_fromAlbum;

    private File[]          listFile;
    GridView                grid;
//    GridViewAdapter         adapter;
    File                    file;
    public static Bitmap    bmp = null;
    ImageView               imageview;
    private static final int REQUEST_PERMISSIONS = 100;
    Bitmap bitmapToSend;

    RecyclerView recyclerView;
    List<Bitmap> listImage1 = new ArrayList<>();
    List<CheckBox>listcheckbox=new ArrayList<>();
    HomeAdapter home;
    boolean isLongClick =false;
    public AlbumDisplay() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_import, container, false);
        InitializeComponent(v);
        return v;
    }

    private void InitializeComponent(View v){
        ReflectAndListener(v);

    }
    private void ReflectAndListener(View v) {
        recyclerView =(RecyclerView)v.findViewById(R.id.recycleViewImport) ;
        btnRefreshAlbum = (ImageButton) v.findViewById(R.id.btnRefreshAlbum);
        btnRefreshAlbum.setOnClickListener(btnClickListener);
        folderImport = (ImageView) v.findViewById(R.id.folderImport);
        addImageImport = (ImageView) v.findViewById(R.id.addImageImport);
        deleteImageImport = (ImageView) v.findViewById(R.id.deleteImageImport);
        repeatImport  = (ImageView) v.findViewById(R.id.repeatImport);
//        progressbar_DetailImport = (ProgressBar) v.findViewById(R.id.progressbar_DetailImport);
        swInvertImport=(Switch) v.findViewById(R.id.swInvertImport);
        radioGroupSendingTypeImport =(RadioGroup)v.findViewById(R.id.radioGroupSendingTypeImport);
        radioSingleImport =(RadioButton)v.findViewById(R.id.radioSingleImport);
        radioAllImport =(RadioButton)v.findViewById(R.id.radioAllImport);
        btnsendAllImport =(Button) v.findViewById(R.id.btnsendAllImport);

        radioGroupSendingTypeImport.clearCheck();
        radioGroupSendingTypeImport.setOnCheckedChangeListener(radioCheckedChange);
        radioGroupSendingTypeImport.check(R.id.radioSingleImport);
//        btnsendAllImport.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));

        home = new HomeAdapter(listImage1, listcheckbox, 3);
        recyclerView.setAdapter(home);
        home.setOnClickListenerRecycle3r(this);
//        recyclerView.requestLayout();
//        recyclerView.invalidate();

        Cursor cursorCT = PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_IMAGES_DIRECTORY);
        while (cursorCT.moveToNext()) {
            Imagefoler_dir =cursorCT.getString(1);
        }
        cursorCT.close();
        PROJECTDATABASE.close();
        new LoadImage_dir().execute();
        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ToastShow("Long CLick!");
                return false;
            }
        });
        folderImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    buttonDelete(false);
                    DialogProperties properties = new DialogProperties();
                    properties.selection_mode = DialogConfigs.SINGLE_MODE;
                    properties.selection_type = DialogConfigs.DIR_SELECT;
                    properties.root = new File(DialogConfigs.DIRECTORY_SEPERATOR);
                    properties.error_dir = new File(DialogConfigs.STORAGE_DIR);
                    properties.offset = new File(DialogConfigs.DIRECTORY_SEPERATOR);
                    properties.extensions = null;

                    FilePickerDialog dialog = new FilePickerDialog(getContext(),properties);
                    dialog.setTitle("Select a Directory");
                    dialog.show();
                    dialog.setDialogSelectionListener(new DialogSelectionListener() {
                        @Override
                        public void onSelectedFilePaths(String[] files) {
                            Imagefoler_dir = files[0].toString();
//                            ToastShow(files[0].toString());
                            PROJECTDATABASE.updateDirectory(
                                    Imagefoler_dir);
                            new LoadImage_dir().execute();
                            //files is the array of the paths of files selected by the Application User.
                        }
                    });

                } catch (Exception e) {
                    ToastShow("This device doesn't support this action!");
                }
            }
        });
        btnsendAllImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SendingDetail = false;
                    isSendAll=true;
                    int colorRepeat = ((ColorDrawable) repeatImport.getBackground()).getColor();
                    if (colorRepeat == Color.parseColor("#ffffff")) {
                        isRepeat = false;
                    } else {
                        isRepeat = true;
                    }

                                    if (CheckConnect()) {
                                        try {
                                            convertingProcess = new ConvertImage();
                                            convertingProcess.execute();
                                        } catch (Exception e) {

                                        }
                                    } else {
                                        Toast.makeText(getActivity(), "Check your connection!", Toast.LENGTH_SHORT).show();
                                    }

                } catch (Exception e) {

                }
            }
        });
        addImageImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonDelete(false);
                pickImages();

            }
        });
        deleteImageImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = ((ColorDrawable)deleteImageImport.getBackground()).getColor();
                if(color == Color.parseColor("#ffffff"))
                {
                    buttonDelete(true);
                    ToastShow("Deleting Mode On");

                }
                else {
                    buttonDelete(false);
                    ToastShow("Deleting Mode Off");
//                    RefreshGridView();
                }

            }
        });

        repeatImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = ((ColorDrawable)repeatImport.getBackground()).getColor();
                if(color == Color.parseColor("#ffffff"))
                {
                    repeatImport.setBackgroundColor(Color.parseColor("#bf125656"));
                    isRepeat=true;
                    ToastShow("Repeat On");
                    isStop=false;
                    isSendAll=false;
                }
                else {
                    repeatImport.setBackgroundColor(Color.parseColor("#ffffff"));
                    isRepeat=false;
                    isStop=false;
                    isSendAll=false;
                    ToastShow("Repeat Off");
                }

            }
        });
        if ((ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(),
                    Manifest.permission.CAMERA))) {

            } else {
                ActivityCompat.requestPermissions((Activity) getContext(),
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_PERMISSIONS);
            }
        }else {
//            Log.e("Else","Else");
//            fn_imagespath();
        }

    }
    private RadioGroup.OnCheckedChangeListener radioCheckedChange = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            RadioButton rb = (RadioButton) group.findViewById(checkedId);
            if (null != rb && checkedId > -1) {

                switch (checkedId) {
                    case R.id.radioSingleImport:
                        ToastShow("click to the image");
                        isSendAll=false;
                        indexImage=0;
//                        btnsendAllImport.setVisibility(View.GONE);
//                        sendOnValve=true;
                        break;
                    case R.id.radioAllImport:
//                        ToastShow("click image to send");
//                        btnsendAllImport.setVisibility(View.VISIBLE);
                        indexImage=0;
                        isSendAll=false;
//                        sendOnValve=false;
                        break;
                }
            }
        }
    };
    private void buttonDelete(boolean a){
        if(a){
            deleteImageImport.setBackgroundColor(Color.parseColor("#bf125656"));
            recyclerView.setBackgroundColor(Color.parseColor("#FFFF4444"));

        }else {
            deleteImageImport.setBackgroundColor(Color.parseColor("#ffffff"));
            recyclerView.setBackgroundColor(Color.parseColor("#ffffff"));
                   }
    }

    @Override
    public void onClicked(final int position) {
//        ToastShow("Click at: "+ position);
//        if(!isLongClick) {
        indexImageSingle=position;
        int colorDelete = ((ColorDrawable)deleteImageImport.getBackground()).getColor();
        if(colorDelete == Color.parseColor("#ffffff")) {
            if(radioSingleImport.isChecked()) {
                try {
                    isSendAll=false;
                    SendingDetail=false;
                    int colorRepeat = ((ColorDrawable) repeatImport.getBackground()).getColor();
                    if (colorRepeat == Color.parseColor("#ffffff")) {
//            repeatImport.setBackgroundColor(Color.parseColor("#bf125656"));
                        isRepeat = false;
                    } else {
//            repeatImport.setBackgroundColor(Color.parseColor("#ffffff"));
                        isRepeat = true;
                    }
//                btnRefreshAlbum.setVisibility(View.GONE);
                    //                ToastShow(FilePathStrings[position]+" "+ String.valueOf(id));
                    BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
                    bmpOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(FilePathStrings[position],
                            bmpOptions);
                    int currHeight = bmpOptions.outHeight;
                    int currWidth = bmpOptions.outWidth;

                    bmpOptions.inSampleSize = 1;
                    bmpOptions.inJustDecodeBounds = false;
                    bmp = BitmapFactory.decodeFile(FilePathStrings[position],
                            bmpOptions);
                    getSettingData();
                    if (bmp != null) {
                        imgTextImage = new WaterImage(bmp);

                        if (CheckConnect()) {
                            try {
                                convertingProcess = new ConvertImage();
                                convertingProcess.execute();
                            } catch (Exception e) {

                            }
                        } else {
                            Toast.makeText(getActivity(), "Check your connection!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        ToastShow("No Such File or directory!");
                        if (position >= listFile.length) {
                            home.notifyDataSetChanged();
//                            deleteImage_database(position - listFile.length);
//                            listImage1.remove(position);
                        }
                        RefreshGridView();
                    }

                } catch (Exception e) {

                }
            }else if(radioAllImport.isChecked()){
//                ToastShow("Send All mode");

                DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();

                final int DeviceTotalWidth = metrics.widthPixels;
                final int DeviceTotalHeight = metrics.heightPixels-100;

                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.image_perview);
//                dialog.getWindow().setLayout(DeviceTotalWidth ,DeviceTotalHeight);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                final ImageView image = (ImageView) dialog.findViewById(R.id.goProDialogImage);
                final TextView txtTitleDetailImport = (TextView) dialog.findViewById(R.id.txtTitleDetailImport);
                final TextView txtSizeDetailImport = (TextView) dialog.findViewById(R.id.txtSizeDetailImport);
                final TextView txtResolutionDetailImport = (TextView) dialog.findViewById(R.id.txtResolutionDetailImport);
                final TextView txtDateDetailImport = (TextView) dialog.findViewById(R.id.txtDateDetailImport);
                final TextView txtPathDetailImport = (TextView) dialog.findViewById(R.id.txtPathDetailImport);
                final Button btn_CloseDetailImport = (Button) dialog.findViewById(R.id.btn_CloseDetailImport);
                final Button btn_SendDetailImport = (Button) dialog.findViewById(R.id.btn_SendDetailImport);
                final BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
                progressbar_DetailImport = (ProgressBar) dialog.findViewById(R.id.progressbar_DetailImport);
                final Bitmap icon = BitmapFactory.decodeFile(FilePathStrings[position],
                        bmpOptions);
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        File file = new File(FilePathStrings[position]);
                        if(file.isFile())
                        {        bmpOptions.inJustDecodeBounds = true;

                            BitmapFactory.decodeFile(FilePathStrings[position],
                                    bmpOptions);
                            int currHeight = bmpOptions.outHeight;
                            int currWidth = bmpOptions.outWidth;

                            bmpOptions.inSampleSize = 1;
                            bmpOptions.inJustDecodeBounds = false;


//                        icon =rotateBitmapToZero(paths[i-FilePathStrings_temp.length]);


                            int targetWidth = 400;
                            int targetHeight = 400;
                            Bitmap temp=null;
                            BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
                            bmpOptions.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(FilePathStrings[position], bmpOptions);

//                        int currHeight = bmpOptions.outHeight;
//                        int currWidth = bmpOptions.outWidth;
                            int sampleSize = 1;
                            if (currHeight > targetHeight || currWidth > targetWidth) {
                                if (currWidth > currHeight)
                                    sampleSize = Math.round((float) currHeight
                                            / (float) targetHeight);
                                else
                                    sampleSize = Math.round((float) currWidth
                                            / (float) targetWidth);
                            }
                            bmpOptions.inSampleSize = sampleSize;
                            bmpOptions.inJustDecodeBounds = false;
                            temp= BitmapFactory.decodeFile(FilePathStrings[position], bmpOptions);

                            Matrix matrix = new Matrix();

                            try {
                                ExifInterface exif = new ExifInterface(FilePathStrings[position]);
                                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
//                    Matrix matrix = new Matrix();
                                switch (orientation) {
                                    case ExifInterface.ORIENTATION_ROTATE_90:
                                        matrix.postRotate(90);
                                        break;
                                    case ExifInterface.ORIENTATION_ROTATE_180:
                                        matrix.postRotate(180);
                                        break;
                                    case ExifInterface.ORIENTATION_ROTATE_270:
                                        matrix.postRotate(270);
                                        break;
                                    default:
                                        break;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {

                                temp= Bitmap.createBitmap(temp, 0, 0, temp.getWidth(), temp.getHeight(), matrix, true);
                            }catch (Exception e){

                            }
                            LinearLayout.LayoutParams layoutParams;
                            image.setImageBitmap(temp);
                            if(icon!=null) {
                                float imageWidthInPX = (float) image.getWidth();
                                float imageHeightInPX = (float) image.getHeight();
                                if ((imageWidthInPX * (float) icon.getHeight() / (float) icon.getWidth()) < DeviceTotalHeight) {

                                    layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                                            Math.round(imageWidthInPX * (float) icon.getHeight() / (float) icon.getWidth()));
                                } else {
                                    layoutParams = new LinearLayout.LayoutParams(
                                            Math.round((float) DeviceTotalWidth / 2),
                                            DeviceTotalHeight
                                    );
                                }
                                image.setLayoutParams(layoutParams);
                            }
                            float a= (float)file.length()/1024;
                            float newKB= (float) (Math.round(a*100.0)/100.0);
                            String fullPath=file.getAbsolutePath();
                            String Path=fullPath.replace("/"+file.getName(),"");
                            txtTitleDetailImport.setText("Title : "+file.getName());
                            txtSizeDetailImport.setText("Size : "+ newKB+" KB");
                            txtResolutionDetailImport.setText("Resolution : "+icon.getWidth()+"x"+icon.getHeight());
                            txtPathDetailImport.setText("Path : "+Path);
                            final String format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm aa").format(
                                    new Date(file.lastModified())
                            );
                            txtDateDetailImport.setText("Date : " + format);
//                            ToastShow("Path: "+file.getAbsolutePath() +" Title: "+file.getName()+" Size: "+newKB);
                        }


                    }
                });
                if(icon!=null) {
                    dialog.show();
                }else {
                    File file = new File(FilePathStrings[position]);
                    ToastShow("Unsupport to read: "+file.getName());
                }
                btn_CloseDetailImport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dialog.isShowing()){
                            dialog.dismiss();
                            isStopSending=true;
                            isAllowSending=false;
                        }
                    }
                });
                btn_SendDetailImport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.setCancelable(false);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        SendingDetail=true;
                        isSendAll=false;
                        int colorRepeat = ((ColorDrawable) repeatImport.getBackground()).getColor();
                        if (colorRepeat == Color.parseColor("#ffffff")) {
//            repeatImport.setBackgroundColor(Color.parseColor("#bf125656"));
                            isRepeat = false;
                        } else {
//            repeatImport.setBackgroundColor(Color.parseColor("#ffffff"));
                            isRepeat = true;
                        }
                        bmp = BitmapFactory.decodeFile(FilePathStrings[position],
                                bmpOptions);
                        getSettingData();
                        if (bmp != null) {
                            imgTextImage = new WaterImage(bmp);

                            if (CheckConnect()) {
                                try {
                                    btn_CloseDetailImport.setText("Stop");
                                    convertingProcess = new ConvertImage();
                                    convertingProcess.execute();
                                } catch (Exception e) {

                                }
                            } else {
                                Toast.makeText(getActivity(), "Check your connection!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            ToastShow("No Such File or directory!");
                            if (position >= listFile.length) {
                                home.notifyDataSetChanged();
                                deleteImage_database(position - listFile.length);
                                listImage1.remove(position);

                            }
                            RefreshGridView();
                        }

                    }
                });
            }
        }else {
            if(position >=  listFile.length)
            {
//                ToastShow("Delete file in DATATBASE "+position);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage("Do you want to delete image?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
//                                home.notifyDataSetChanged();
//                                recyclerView.removeViewAt(position);
                                deleteImage_database(position - listFile.length);
                                listImage1.remove(position);
//                                    listImage1.remove(position);
                                home.notifyItemRangeChanged(position, listImage1.size()-1);
                                home.notifyItemRemoved(position);

                            }
                        });

//                alertDialogBuilder.setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface arg0, int arg1) {_
//                            }
//                        });
                alertDialogBuilder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
            else {
//                ToastShow("Delete file in folder "+position);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                final File file_delete = new File(FilePathStrings[position]);
                ToastShow(FilePathStrings[position]);
                alertDialogBuilder.setMessage("Are you sure to delete file name: "+file_delete.getName() +" ?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
//                                home.notifyDataSetChanged();
//                                recyclerView.removeViewAt(position);
//                                deleteImage(position);
//                                try{

                                    file_delete.delete();
                                    if(file_delete.exists()){
                                        ToastShow("Cant delete");
                                    }else {
                                        ToastShow("Deleled");
                                    }
                                    listImage1.remove(position);
                                List<String> aList=new ArrayList<>();
                                for(int i =0 ;i <FilePathStrings.length ;i++ ){
                                    aList.add(FilePathStrings[i]);
                                }
                                aList.remove(position);
                                FilePathStrings=new String[aList.size()];
                                for (int i=0; i<aList.size();i++){
                                    FilePathStrings[i]=aList.get(i);
                                }

                                home.notifyItemRangeChanged(position, listImage1.size());
                                home.notifyItemRemoved(position);
//                                home.notifyItemRangeRemoved(position, listImage1.size() );
                            }
                        });

                alertDialogBuilder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
//            ToastShow("delete mode!");
        }
    }
    private void deleteImage_database(int position) {
        Cursor cursorCT = MainActivity.PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_IMAGE);
        while (cursorCT.moveToNext()) {
            if (cursorCT.getPosition() == position) {
                MainActivity.PROJECTDATABASE.deleteImage(cursorCT.getString(1));
//                new LoadImage().execute();
                home.notifyDataSetChanged();
            }
        }
        cursorCT.close();
        PROJECTDATABASE.close();
    }

    @Override
    public void onLongClicked(int position) {
        ToastShow("Long Click at: "+ FilePathStrings[position]);

        btnRefreshAlbum.setVisibility(View.VISIBLE);
//        isLongClick=true;
    }


    private class LoadImage_dir extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getListData();
            home.notifyDataSetChanged();
            Cursor cursorCT = PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_IMAGES_DIRECTORY);
            while (cursorCT.moveToNext()) {
                Imagefoler_dir =cursorCT.getString(1);
                Log.e("DIrectory: ", "doInBackground: "+ Imagefoler_dir );
            }
//            home.notifyDataSetChanged();
            btnRefreshAlbum.setEnabled(false);
        }

        @Override
        protected void onProgressUpdate(final Integer... values) {
            super.onProgressUpdate(values);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    home.notifyItemRangeInserted(values[0],listImage1.size());
            try{
//                recyclerView.getRecycledViewPool().clear();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
                home.notifyDataSetChanged();
                        home.notifyItemMoved(values[0],listImage1.size());
                        home.notifyItemInserted(values[0]);
                        home.notifyItemRangeChanged(values[0],listImage1.size());
                home.notifyDataSetChanged();
//                    }
//                }, 0);

            }catch (Exception e){

            }
//            recyclerView.getRecycledViewPool().clear();
//            home.notifyDataSetChanged();
//            home.notifyItemRangeRemoved(0, listImage1.size()-1);
//            home.notifyItemRangeInserted(0, listImage1.size());
// some insert
//            home.notifyItemRangeInserted(prevSize, listImage1.size() -prevSize);
//            home.notifyItemRangeInserted( listImage1.size()-1, listImage1.size() -prevSize);
//            home.notifyItemChanged(values[0]);
//                    home.notifyDataSetChanged();
//                }
//            }, 10);
        }

        @Override
        protected String doInBackground(String... voids) {
//            listImage1.clear();
//            ClearData();
//            getListData();

            file = new File(Imagefoler_dir);

            if (file.isDirectory()) {

                listFile = file.listFiles();

                try {
                    Arrays.sort(listFile, new Comparator<Object>() {
                        public int compare(Object o1, Object o2) {

                            if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                                return -1;
                            } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                                return +1;
                            } else {
                                return 0;
                            }
                        }
                    });
                } catch (Exception e) {

                }

//                try {
//                    if (PROJECTDATABASE.CountImage() > 0) {
//                        Cursor cursorCT = PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_IMAGE);
//                        while (cursorCT.moveToNext()) {
//                            Log.i("Data", "" + cursorCT.getString(1));
//                            File imgFile = new File(cursorCT.getString(1));
//                            if (imgFile.exists()) {
//                                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                            }
//
//                        }
//                    }
//                } catch (Exception e) {
//                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
//                }

                try {
                    int countImageInDirectory=0;
                    for (int i = 0; i < listFile.length; i++) {
                        File f =new File(listFile[i].getAbsolutePath());
                        if ((listFile[i].getAbsolutePath().endsWith(".jpg")  ||
                            listFile[i].getAbsolutePath().endsWith(".png")||
                            listFile[i].getAbsolutePath().endsWith(".gif")||
                            listFile[i].getAbsolutePath().endsWith(".bmp")||
                            listFile[i].getAbsolutePath().endsWith(".jpeg"))&&f.exists()&&f.isFile()){
                            countImageInDirectory=countImageInDirectory+1;
                        }
                        }
//                        }
                    FilePathStrings = new String[(int) (countImageInDirectory + PROJECTDATABASE.CountImage())];
                    Bitmap bmp;
                    int count=0;
                    int exist=0;
                    for (int i = 0; i < listFile.length; i++) {
                        File f =new File(listFile[i].getAbsolutePath());
                        if( (listFile[i].getAbsolutePath().endsWith(".jpg")  ||
                                listFile[i].getAbsolutePath().endsWith(".png")||
                                listFile[i].getAbsolutePath().endsWith(".gif")||
                                listFile[i].getAbsolutePath().endsWith(".bmp")||
                                listFile[i].getAbsolutePath().endsWith(".jpeg"))&&f.exists()&&f.isFile())
                        {
                            FilePathStrings[exist] = listFile[i].getAbsolutePath();
                            exist++;
                            Log.e("file index ", i + ": " + FilePathStrings[i]);
                            bmp =rotateBitmapToZero(FilePathStrings[i]);
//                        bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
                            listImage1.add(bmp);
//                            Integer[] values =new Integer[2];
//                            values[0]=i;
//                            values[1]=listImage1.size();

                            count++;
                            if(count%2==0) {
                                publishProgress(i);
                            }else if(i==listFile.length-1 ){
                                publishProgress(i);
                            }
                        }
                    }
                    count=0;
                    for (int i = 0; i < PROJECTDATABASE.CountImage(); i++) {

                        Cursor cursorCT = MainActivity.PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_IMAGE);
                        while (cursorCT.moveToNext()) {
                            if (cursorCT.getPosition() == i) {
                                File imgFile = new File(cursorCT.getString(1));
                                if((imgFile.getAbsolutePath().endsWith(".jpg")  ||
                                        imgFile.getAbsolutePath().endsWith(".png")||
                                        imgFile.getAbsolutePath().endsWith(".gif")||
                                        imgFile.getAbsolutePath().endsWith(".bmp")||
                                        imgFile.getAbsolutePath().endsWith(".jpeg"))&&imgFile.exists()&&imgFile.isFile()){
                                    FilePathStrings[exist] = cursorCT.getString(1);//imgFile.getAbsolutePath();
                                    bmp=rotateBitmapToZero(FilePathStrings[exist]);
                                    listImage1.add(bmp);
                                    count++;
                                    if(count%1==0) {
                                        publishProgress(exist);
                                    }
                                }

                            }
                        }
                        cursorCT.close();
                        PROJECTDATABASE.close();
                    }
                } catch (Exception e) {

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            home.notifyDataSetChanged();
            if (listImage1.size() == 0) {
                ToastShow("Empty!");
            } else {
//                ToastShow(String.valueOf("Added " + listImage1.size() + " images!"));
            }
                   /* for (int i = 0; i < PROJECTDATABASE.CountImage(); i++) {

                        Cursor cursorCT = MainActivity.PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_IMAGE);
                        while (cursorCT.moveToNext()) {
                            if (cursorCT.getPosition() == i) {
                                File imgFile = new File(cursorCT.getString(1));
                                if((imgFile.getAbsolutePath().endsWith(".jpg")  ||
                                        imgFile.getAbsolutePath().endsWith(".png")||
                                        imgFile.getAbsolutePath().endsWith(".gif")||
                                        imgFile.getAbsolutePath().endsWith(".bmp")||
                                        imgFile.getAbsolutePath().endsWith(".jpeg"))&&imgFile.exists()&&imgFile.isFile()){
                                    Log.e("IMG location DATABASE index: ",i+ " "+cursorCT.getString(1));
//                                    FilePathStrings[i + listFile.length] = imgFile.getAbsolutePath();
//                                    bmp=rotateBitmapToZero(FilePathStrings[i + listFile.length]);
//                                    listImage1.add(bmp);
                                }

                            }
                        }
                        cursorCT.close();
                        PROJECTDATABASE.close();
                    }*/
            String tem;
//            Cursor cursorCT = PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_IMAGES_DIRECTORY);
//            while (cursorCT.moveToNext()) {
//                tem = cursorCT.getString(1);
//                Log.e("Folder Location has: ", tem + ", Count Img in DATABASE: " + PROJECTDATABASE.CountImage());
//            }
//            PROJECTDATABASE.close();
//            Cursor cursorCT = MainActivity.PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_IMAGE);
//            for(int i= 0; i< 11;i++){
//                while (cursorCT.moveToNext()) {
//                    if (cursorCT.getPosition() == i) {
//
//                        Log.e("IMG location DATABASE index: ",i+ " "+cursorCT.getString(1));
////                        File imgFile = new File(cursorCT2.getString(1));
////                        if ((imgFile.getAbsolutePath().endsWith(".jpg") ||
////                                imgFile.getAbsolutePath().endsWith(".png") ||
////                                imgFile.getAbsolutePath().endsWith(".gif") ||
////                                imgFile.getAbsolutePath().endsWith(".bmp") ||
////                                imgFile.getAbsolutePath().endsWith(".jpeg")) && imgFile.exists() && imgFile.isFile()) {
////                            FilePathStrings[i + listFile.length] = imgFile.getAbsolutePath();
////                            bmp = rotateBitmapToZero(FilePathStrings[i + listFile.length]);
////                            listImage1.add(bmp);
////                            count++;
////                            if (count % 1 == 0) {
////                                publishProgress(i + listFile.length);
////                            }
//                        }
////                    cursorCT.close();
////                    PROJECTDATABASE.close();
////            home.notifyDataSetChanged();
//
////            dismissLoadingDialog();
//                    }
//                }
//            for (int i = 0; i < FilePathStrings.length; i++) {
//                Log.e("FilePathStrings has: ", ": Index " + i + "File: " + FilePathStrings[i]);
//            }

            btnRefreshAlbum.setEnabled(true);
            }
        }
    private Bitmap rotateBitmapToZero(String Path){
        int targetWidth = 300;
        int targetHeight =300;
        Bitmap temp=null;
        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
        bmpOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(Path, bmpOptions);

        int currHeight = bmpOptions.outHeight;
        int currWidth = bmpOptions.outWidth;
        int sampleSize = 1;
        if (currHeight > targetHeight || currWidth > targetWidth) {
            if (currWidth > currHeight)
                sampleSize = Math.round((float) currHeight
                        / (float) targetHeight);
            else
                sampleSize = Math.round((float) currWidth
                        / (float) targetWidth);
        }
        bmpOptions.inSampleSize = sampleSize;
        bmpOptions.inJustDecodeBounds = false;
        temp= BitmapFactory.decodeFile(Path, bmpOptions);

        Matrix matrix = new Matrix();

        try {
            ExifInterface exif = new ExifInterface(Path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
//                    Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            temp= Bitmap.createBitmap(temp, 0, 0, temp.getWidth(), temp.getHeight(), matrix, true);
        }catch (Exception e){

        }
        return temp;
    }

    private void RefreshGridView()
    {

        btnRefreshAlbum.setEnabled(false);
        buttonDelete(false);
        getListData();
//        recyclerView.getRecycledViewPool().clear();
//        listImage1.clear();
//        home.notifyDataSetChanged();
//        home.notifyItemInserted(0);
//        home.notifyItemRangeChanged(0,listImage1.size());
        new LoadImage_dir().execute();
//                home.notifyDataSetChanged();


    }
    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnRefreshAlbum:
                    try {
                        RefreshGridView();
                        }catch (Exception e) {

                    }
                    break;
            }
        }
    };
    private ProgressDialog convertText2ImageProgressDialog;//Converting
    private ProgressDialog sendTextProgressDialog;//Sending Dialog
    private void dismissConvertingDialog() {
        if (convertText2ImageProgressDialog != null && convertText2ImageProgressDialog.isShowing()) {
            convertText2ImageProgressDialog.dismiss();
        }
    }
    public void showConvertingDialog() {

        if (convertText2ImageProgressDialog == null) {
            convertText2ImageProgressDialog = new ProgressDialog(getActivity());
            convertText2ImageProgressDialog.setTitle("Convert Task" +
                    "" +
                    "" +
                    "");
            convertText2ImageProgressDialog.setMessage("Processing...");
            //progress_auto.setCancelable(false);
            convertText2ImageProgressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            convertText2ImageProgressDialog.setCanceledOnTouchOutside(false);
            convertText2ImageProgressDialog.setCancelable(false);
            convertText2ImageProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            convertText2ImageProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if(convertingProcess !=null &&convertingProcess.getStatus()!= AsyncTask.Status.FINISHED) {
                        convertingProcess.cancel(true);
                        isStopSending=true;
                        isAllowSending=false;
                        indexImage=0;
                        repeatedTime=0;
                    }
//                    isAutoShow=false;
                }
            });
        }
        try {
            convertText2ImageProgressDialog.show();
        }catch (Exception e)
        {

        }
    }
    private void dismissSendingDialog() {
        if ( sendTextProgressDialog!= null && sendTextProgressDialog.isShowing()) {
            sendTextProgressDialog.dismiss();
        }
    }
    public void showSendingDialog() {
        if (sendTextProgressDialog == null) {
            sendTextProgressDialog = new ProgressDialog(getActivity());
            sendTextProgressDialog.setTitle("Sending Task" +
                    "" +
                    "" +
                    "");
            sendTextProgressDialog.setMessage("Showing...");
            sendTextProgressDialog.setCanceledOnTouchOutside(false);
            sendTextProgressDialog.setCancelable(false);
            sendTextProgressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            sendTextProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            sendTextProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Stop", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isStopSending=true;
                    indexImage=0;
                    repeatedTime=0;
                }
            });
        }
        sendTextProgressDialog.show();
    }
    private class ConvertImage extends AsyncTask<Bitmap, Integer, Bitmap> {
        Bitmap bitmap;
        boolean isInvert=false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try
            {
                if(!SendingDetail){
                    if(!isSendAll||(isSendAll&&indexImage==0)){
                        dismissConvertingDialog();
                        showConvertingDialog();
                    }
                }
                if (swInvertImport.isChecked())
                {
                    isInvert=true;
                }else {
                    isInvert=false;
                }
                isAllowSending=true;
                isStopSending=false;

                if(isSendAll) {
                    if(indexImage==0){
                        getSettingData();
                    }
                    BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
                    bmpOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(FilePathStrings[indexImage],
                            bmpOptions);
                    int currHeight = bmpOptions.outHeight;
                    int currWidth = bmpOptions.outWidth;
//
                    bmpOptions.inSampleSize = 1;
                    bmpOptions.inJustDecodeBounds = false;

                        bmp = BitmapFactory.decodeFile(FilePathStrings[indexImage],
                                bmpOptions);

                    if(bmp!=null) {
                        imgTextImage = new WaterImage(bmp);
                        bitmap = imgTextImage.getImg();
                    }

                }else {
                    getSettingData();
                    bitmap =imgTextImage.getImg();
                }


            }catch (Exception e)
            {

            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(Bitmap... params) {
            Bitmap reBitmap;
            if(bitmap.getWidth() != vanNumber) {
//                ToastShow("not equal!");
                Log.e("CLICKED", "Not equal");
                reBitmap= WaterImage.ResizeImage(bitmap, vanNumber);
            }else {
                Log.e("CLICKED", "Equal");
//                ToastShow("equal!");
                reBitmap=bitmap;
            }
//            Log.e("ReBit Map", "Height , WIDTH: " +reBitmap.getHeight() + " " + reBitmap.getWidth());
//            imgTextImage = new WaterImage(reBitmap);
            Bitmap bwBitmap =reBitmap ;
            if(!isInvert){
                bwBitmap = imgTextImage.GetBitmap(reBitmap,vanNumber,thresholeNumber);
            }else {
                bwBitmap = imgTextImage.GetBitmapInvert(reBitmap,vanNumber,thresholeNumber);
            }

//            Log.e("bwBit Map", "Height , WIDTH: " +bwBitmap.getHeight() + " " + bwBitmap.getWidth()+" "+vanNumber);
            return  bwBitmap;
        }
        @Override
        protected void onPostExecute(Bitmap s) {
            bitmapToSend = s;
            dismissConvertingDialog();
//            while ()
            new SendText().execute(bitmapToSend);
//
        }
    }
    private class SendText extends AsyncTask<Bitmap, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(!SendingDetail) {
                showSendingDialog();
            }
            imgTextImage=new WaterImage(bitmapToSend);
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(!SendingDetail) {
                sendTextProgressDialog.setProgress(values[0]);
//                sendTextProgressDialog.setTitle("Sent "+String.valueOf(indexImage) +"/"+ FilePathStrings.length +" images +\n Repeated "+repeatedTime+" times");
//                File f = new File(FilePathStrings[indexImage]);
                if(!isSendAll) {
                    if (repeatedTime > 0) {
                        File f = new File(FilePathStrings[indexImageSingle]);
                        sendTextProgressDialog.setMessage("Showing image name " + f.getName() + "\n" + "Repeated " + repeatedTime + " times");
                    }
                }
//                sendTextProgressDialog.setMessage(repeatedTime);
//                sendTextProgressDialog.
            }
            else {
                progressbar_DetailImport.setProgress(values[0]);

            }
        }

        @Override
        protected Boolean doInBackground(Bitmap... voids) {
            boolean isSuccess=false;
            int times = 1;
            try{
                int byteRow;
                if(!isSendAll) {
                    byteRow = imgTextImage.getImg().getWidth() / 8;
                    int sum = 0;
                    int percent = 0;
                    if (isRepeat) {
                        Bitmap bmp = imgTextImage.getImg();
                        while (!isStopSending) {
                            serialCom = new DeviceInfo();
                            serialCom.TxCmdStart();
                            for (int i = 0; i < times; i++) {
                                sum = times * bmp.getHeight();
                                for (int k = bmp.getHeight() - 1; k >= 0; k--) {
                                    byte[] data = imgTextImage.RowToByte(k);
                                    // Log.e("DisplayImage ", "Data of row: "+ Arrays.toString(data));
//                                if (!isStopSending) {
                                    if (DisplayRow(data, milliseconds)) {
                                        isSuccess = true;
                                    }
                                    percent = ((bmp.getHeight() - k) * 100) / sum;
//                                Log.e("DisplayImage ", "Percent: " + percent);
                            if(percent==100){
                                repeatedTime++;
                            }
                                    Integer[] values =new Integer[2];
                                    values[0]=percent;
                                    values[1]=repeatedTime;
//                       mbmngfdgn             sendTextProgressDialog.setTitle("Sent "+String.valueOf(indexImage) +"/"+ FilePathStrings.length +" images +\n Repeated "+repeatedTime+" times");

                                    publishProgress(values);
//                                } else {
//
//                                    isSuccess = false;
//
                                }
                                serialCom.TxCmdStop();
                            }
                            CurtainSleep(byteRow, delayImage * 200);
                        try {
                            Thread.sleep(delayImage*10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        }

                    } else {
                        Bitmap bmp = imgTextImage.getImg();
                        serialCom = new DeviceInfo();
                        serialCom.TxCmdStart();
                        for (int i = 0; i < times; i++) {
                            sum = times * bmp.getHeight();
                            for (int k = bmp.getHeight() - 1; k >= 0; k--) {
                                byte[] data = imgTextImage.RowToByte(k);
                                // Log.e("DisplayImage ", "Data of row: "+ Arrays.toString(data));
                                if (!isStopSending) {
                                    if (DisplayRow(data, milliseconds)) {
                                        isSuccess = true;
                                    }
                                    percent = ((bmp.getHeight() - k) * 100) / sum;
//                                Log.e("DisplayImage ", "Percent: " + percent);
                                    publishProgress(percent);
                                } else {

                                    isSuccess = false;
                                }
                            }
                            serialCom.TxCmdStop();
                        }
//                    CurtainSleep(byteRow, delayImage*10);
                    }
                    CurtainSleep(byteRow, 2);
                }else if(isSendAll){
                    byteRow = imgTextImage.getImg().getWidth() / 8;
                    int sum = 0;
                    int percent = 0;
                    Bitmap bmp = imgTextImage.getImg();
                    serialCom = new DeviceInfo();
                    serialCom.TxCmdStart();
                    for (int i = 0; i < times; i++) {
                        sum = times * bmp.getHeight();
                        for (int k = bmp.getHeight() - 1; k >= 0; k--) {
                            byte[] data = imgTextImage.RowToByte(k);
                            // Log.e("DisplayImage ", "Data of row: "+ Arrays.toString(data));
                            if (!isStopSending) {
                                if (DisplayRow(data, milliseconds)) {
                                    isSuccess = true;
                                }
                                percent = ((bmp.getHeight() - k) * 100) / sum;
//                                Log.e("DisplayImage ", "Percent: " + percent);
                                publishProgress(percent);
                            } else {

                                isSuccess = false;
                            }
                        }
                        serialCom.TxCmdStop();
                    }
                    CurtainSleep(byteRow, delayImage*200);
                    try {
                        Thread.sleep(delayImage *10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e)
            {
                Log.e("Error" , "Send"+ e.toString());

            }
            return isSuccess;
        }
        @Override
        protected void onPostExecute(Boolean s) {
            if(!isSendAll) {
                dismissSendingDialog();
                repeatedTime=0;
            }
//            dismissSendingDialog_auto();
            if (s) {
                isStopSending=false;
                bitmapToSend.recycle();
                if(isSendAll) {
//                    Toast.makeText(getActivity(), "Sent "+String.valueOf(indexImage+1) +"/"+ FilePathStrings.length +" images", Toast.LENGTH_SHORT).show();
                    if(!isStopSending) {
                        indexImage++;
                        if (indexImage < FilePathStrings.length) {
                            if (CheckConnect()) {
                                try {

                                    sendTextProgressDialog.setTitle("Sent "+String.valueOf(indexImage+1) +"/"+ FilePathStrings.length );
                                    File f = new File(FilePathStrings[indexImage]);
                                    if(repeatedTime>0){
                                        sendTextProgressDialog.setMessage("Name " + f.getName() + "\nRepeated " + repeatedTime + " times");
                                    }else {
                                        sendTextProgressDialog.setMessage("Showing image name  " + f.getName());
                                    }
                                    convertingProcess = new ConvertImage();
                                    convertingProcess.execute();
                                } catch (Exception e) {

                                }
                            } else {
                                Toast.makeText(getActivity(), "Check your connection!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if(isRepeat){
                                repeatedTime++;
                                ToastShow("Repeat send Album");
                                indexImage=0;
                                convertingProcess = new ConvertImage();
                                convertingProcess.execute();
                            }else {
                                indexImage=0;
                            dismissSendingDialog();
                            }
                            Toast.makeText(getActivity(), "Sent Successful!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        indexImage=0;
                        dismissSendingDialog();
                    }
                }
            } else {

                isStopSending=false;
                bitmapToSend.recycle();
//                Toast.makeText(getActivity(), "Fail!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private boolean CurtainSleep( int bytes,int sec) throws IOException {
        byte [] temp= new byte [bytes];
        for (int i=0; i< bytes; i++) {
            if (true) {
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


//    public static class GridViewAdapter extends BaseAdapter
//    {
//        private Context                activity;
//        private String[]                filepath;
//        private static LayoutInflater   inflater    = null;
//        Bitmap                          bmp         = null;
//
//        public GridViewAdapter (Context a, String[] fpath)
//        {
//            activity = a;
//            filepath = fpath;
//            inflater = (LayoutInflater)activity
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        }
//
//        public int getCount ()
//        {
//            return filepath.length;
//        }
//
//        public Object getItem (int position)
//        {
//            return position;
//        }
//
//        public long getItemId (int position)
//        {
//            return position;
//        }
//
//        public View getView (final int position, View convertView, ViewGroup parent)
//        {
//
//            View vi = convertView;
//            if (convertView == null) {
//                vi = inflater.inflate(R.layout.gridview_item, null);
//            }
//
//            final View finalVi = vi;
//
//                    final ImageView image = (ImageView) finalVi.findViewById(R.id.image);
//                    int targetWidth = 100;
//                    int targetHeight = 100;
//                    BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
//                    bmpOptions.inJustDecodeBounds = true;
//                    BitmapFactory.decodeFile(filepath[position], bmpOptions);
//
//                    int currHeight = bmpOptions.outHeight;
//                    int currWidth = bmpOptions.outWidth;
//                    int sampleSize = 1;
//                    if (currHeight > targetHeight || currWidth > targetWidth)
//                    {
//                        if (currWidth > currHeight)
//                            sampleSize = Math.round((float)currHeight
//                                    / (float)targetHeight);
//                        else
//                            sampleSize = Math.round((float)currWidth
//                                    / (float)targetWidth);
//                    }
//                    bmpOptions.inSampleSize = sampleSize;
//                    bmpOptions.inJustDecodeBounds = false;
//                    bmp = BitmapFactory.decodeFile(filepath[position], bmpOptions);
//                    image.setImageBitmap(bmp);
//                    bmp = null;
//
//
////            bmp.recycle();
//            return vi;
//        }
//    }
    private void getListData() {
//        ClearData();
        listImage1.clear();
        home.notifyDataSetChanged();

    }
//    private void getStringImg(){
//        file = new File(Environment.getExternalStorageDirectory()
//                .getPath() + "/folder_name");
//        if (file.isDirectory()) {
//
//            listFile = file.listFiles();
//            try {
//                Arrays.sort(listFile, new Comparator<Object>() {
//                    public int compare(Object o1, Object o2) {
//
//                        if (((File) o1).lastModified() > ((File) o2).lastModified()) {
//                            return -1;
//                        } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
//                            return +1;
//                        } else {
//                            return 0;
//                        }
//                    }
//                });
//            }catch (Exception e)
//            {
//
//            }
//            FilePathStrings = new String[listFile.length];
//            for (int i = 0; i < listFile.length; i++)
//            {
//                FilePathStrings[i] = listFile[i].getAbsolutePath();
//
//            }
//        }
//    }
//    private void ClearData() {
//                for (int i = 0; i < listImage1.size() - 1; i++) {
//                    recyclerView.removeViewAt(i);
//                }
//    }
    private void getSettingData() {
        Cursor cursorCT = PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_SETTINGS);
        while (cursorCT.moveToNext()) {
            vanNumber = Integer.parseInt(cursorCT.getString(1));
            thresholeNumber = Integer.parseInt(cursorCT.getString(4));
            milliseconds = Integer.parseInt(cursorCT.getString(2));

            delayImage  = Integer.parseInt(cursorCT.getString(3));


        }
        cursorCT.close();
        PROJECTDATABASE.close();
    }

public boolean CheckConnect() {
    boolean a;
    try {
        Log.i("Send Clicked: ", "Send Cliked Listener");
        Log.i("Send Clicked: ", "Datasend.Size>0 ");
        if (mTcpClient != null && mTcpClient.isConnect() == true) {
            return true;
        } else {
            return false;
        }
    } catch (Exception e) {
        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
    }
    return false;
}
    public void ToastShow(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }
    private void pickImages(){

        //You can change many settings in builder like limit , Pick mode and colors
        try{

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
//            ImagePicker.create(AlbumDisplay).getIntent(context);

            imagePicker.create(this)
                    .returnAfterFirst(false) // set whether pick or camera action should return immediate result or not. For pick image only work on single mode
                    .folderMode(true) // folder mode (false by default)
                    .folderTitle("Album") // folder selection title
                    .imageTitle("Tap to select") // image selection title
                    .single() // single mode
//                    .di()
                    .multi() // multi mode (default mode)
                    .limit(100) // max images can be selected (99 by default)
                    .showCamera(true) // show camera or not (true by default)
                    .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
//                    .origin(listImage1) // original selected images, used in multi mode
                    .theme(R.style.ef_AppTheme) // must inherit ef_BaseTheme. please refer to sample
                    .enableLog(false) // disabling log
//                    .imageLoader(new ) // custom image loader, must be serializeable
                    .start(5000); // start image picker activity with request code

//            ImagePicker.create(this) // Activity or Fragment
//                    .start(5000);
////            startActivityForResult(cameraModule.getCameraIntent(getContext()), 5001);
//            @Override
//            if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null) {
//                ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
//            }
//            new Picker.Builder(getContext(),new MyPickListener())
//                    .setCaptureIconColor(Picker.Color.WHITE)
////                    .setCaptureIconColor(Picker.Color.BLACK).
//                    .build()
//                    .startActivity();

//            TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(getActivity())
//                    .setOnMultiImageSelectedListener(new TedBottomPicker.OnMultiImageSelectedListener() {
//                        @Override
//                        public void onImagesSelected(ArrayList<Uri> uriList) {
//                            try {
//                                ToastShow(uriList.get(0).toString());
//                            }catch (Exception e){
//
//                            }
//                            // here is selected uri list
//                        }
//                    })
//                    .setPeekHeight(1600)
//                    .showTitle(false)
//                    .setCompleteButtonText("Done")
//                    .setEmptySelectionText("No Select")
//                    .create();
//
//            bottomSheetDialogFragment.show(getFragmentManager());
//            ImagePicker.with(this)                         //  Initialize ImagePicker with activity or fragment context
//                    .setToolbarColor("#212121")         //  Toolbar color
//                    .setStatusBarColor("#000000")       //  StatusBar color (works with SDK >= 21  )
//                    .setToolbarTextColor("#FFFFFF")     //  Toolbar text color (Title and Done button)
//                    .setToolbarIconColor("#FFFFFF")     //  Toolbar icon color (Back and Camera button)
//                    .setProgressBarColor("#4CAF50")     //  ProgressBar color
//                    .setBackgroundColor("#212121")      //  Background color
//                    .setCameraOnly(false)               //  Camera mode
//                    .setMultipleMode(true)              //  Select multiple images or single image
//                    .setFolderMode(true)                //  Folder mode
//                    .setShowCamera(true)                //  Show camera button
//                    .setFolderTitle("Albums")           //  Folder title (works with FolderMode = true)
//                    .setImageTitle("Galleries")         //  Image title (works with FolderMode = false)
//                    .setDoneTitle("Done")               //  Done button title
//                    .setLimitMessage("You have reached selection limit")    // Selection limit message
//                    .setMaxSize(10)                     //  Max images can be selected
//                    .setSavePath("ImagePicker")         //  Image capture folder name
//                    .setSelectedImages(images)          //  Selected images
//                    .setKeepScreenOn(true)              //  Keep screen on when selecting images
//                    .start();                           //  Start ImagePicker

        }catch (Exception e){
            ToastShow(e.toString());
        }


    }
    private void addImage(String path) {
        try {
            MainActivity.PROJECTDATABASE.addImage(path);
//            MainActivity.PROJECTDATABASE.close();
//            Toast.makeText(getActivity(), "Save Successful!", Toast.LENGTH_SHORT).show();
//            DeviceInfo.openDialog(getActivity(), "Save Success", "Congatulation!");
        } catch (Exception e) {
            Toast.makeText(getActivity(),  e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//            DeviceInfo.openDialog(getActivity(), e.getMessage().toString(), "Failed!");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5000 && resultCode == RESULT_OK && data != null) {
//            ToastShow("Success");
//            cameraModule.getImage(context, data, new OnImageReadyListener() {
//                @Override
//                public void onImageReady(List<Image> images) {
//                    // do what you want to do with the image ...
//                    // it's either List<Image> with one item or null (still need improvement)
//                }
//            });imagePicker
//            List<Image> images = (List<Image>)
//            ToastShow(imagePicker.getImages(data).get(0).getPath().toString());
            List<com.esafirm.imagepicker.model.Image> images = imagePicker.getImages(data);
//            ToastShow("Size: "+String.valueOf(images.size()));
            String[] paths =new String[images.size()];
            for (int i=0; i<images.size();i++) {
                paths[i]=imagePicker.getImages(data).get(i).getPath().toString();

            }

            Bitmap bmp;
            String[]        FilePathStrings_temp;
            String[]        FilePathStrings_temp1;
            FilePathStrings_temp = new String[FilePathStrings.length];
            FilePathStrings_temp = Arrays.copyOf(FilePathStrings,FilePathStrings.length);
            FilePathStrings  = new String[FilePathStrings_temp.length +paths.length];
            int count_img=0;
            System.arraycopy(FilePathStrings_temp,0,FilePathStrings,0,FilePathStrings_temp.length);
            System.arraycopy(paths,0,FilePathStrings,0,paths.length);

//            FilePathStrings_fromAlbum=
            home.notifyDataSetChanged();
            for(int i=FilePathStrings_temp.length; i< FilePathStrings_temp.length+paths.length;i++) {
                File files=new File(paths[i-FilePathStrings_temp.length]);
                if(files.exists()){
                    addImage(paths[i-FilePathStrings_temp.length]);
                    FilePathStrings[i] = paths[i-FilePathStrings_temp.length];
//                    Log.e("file index ", i + ": " + FilePathStrings[i]);
                    bmp =rotateBitmapToZero(paths[i-FilePathStrings_temp.length]);
//                    home.notifyDataSetChanged();
//                        bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
                    listImage1.add(bmp);
                    home.notifyItemInserted(listImage1.size()-1);
                    home.notifyItemRangeChanged(listImage1.size()-1,listImage1.size());
                    count_img++;
                }
                else {
                    ToastShow("File: "+ files.getAbsolutePath()+ " does not exist!");
                }
            }
            recyclerView.scrollToPosition(FilePathStrings.length-1);
//            RefreshGridView();
//            home.notifyDataSetChanged();
            if(count_img==1){

                ToastShow("Added "+count_img+" image!");
            }else if(count_img>1){
                ToastShow("Added "+count_img+" images!");
            }else {

            }

//                ToastShow(imagePicker.getImages(data).get(i).getPath().toString());
            }
            else if (requestCode == 5001 && resultCode == RESULT_OK && data != null){
            cameraModule.getImage(getContext(), data, new OnImageReadyListener() {
                @Override
                public void onImageReady(List<com.esafirm.imagepicker.model.Image> list) {
                    try {
                        ToastShow("sssss");
                        ToastShow(list.get(0).getPath().toString());
                    }catch (Exception e){

                    }

                }
            });
        }
//               imagePicker.getImages(data).get(0).toString();

        }
    }

//    private class MyPickListener implements Picker.PickListener {
//
//        @Override
//        public void onPickedSuccessfully(String[] paths) {
//            Bitmap bmp;
//
//            String[]        FilePathStrings_temp;
//            String[]        FilePathStrings_temp1;
//            FilePathStrings_temp = new String[FilePathStrings.length];
//            FilePathStrings_temp = Arrays.copyOf(FilePathStrings,FilePathStrings.length);
//            FilePathStrings  = new String[FilePathStrings_temp.length +paths.length];
//            int count_img=0;
//            System.arraycopy(FilePathStrings_temp,0,FilePathStrings,0,FilePathStrings_temp.length);
//            System.arraycopy(paths,0,FilePathStrings,0,paths.length);
//
////            FilePathStrings_fromAlbum=
//            home.notifyDataSetChanged();
//            for(int i=FilePathStrings_temp.length; i< FilePathStrings_temp.length+paths.length;i++) {
//                File files=new File(paths[i-FilePathStrings_temp.length]);
//                if(files.exists()){
//                    addImage(paths[i-FilePathStrings_temp.length]);
//                    FilePathStrings[i] = paths[i-FilePathStrings_temp.length];
//                    Log.e("file index ", i + ": " + FilePathStrings[i]);
//                    bmp =rotateBitmapToZero(paths[i-FilePathStrings_temp.length]);
//                    home.notifyDataSetChanged();
////                        bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
//                    listImage1.add(bmp);
//                    count_img++;
//                }
//                else {
//                    ToastShow("File: "+ files.getAbsolutePath()+ " does not exist!");
//                }
//            }
//            recyclerView.scrollToPosition(FilePathStrings.length-1);
////            RefreshGridView();
//            home.notifyDataSetChanged();
//            if(count_img==1){
//
//                ToastShow("Added "+count_img+" image!");
//            }else if(count_img>1){
//                ToastShow("Added "+count_img+" images!");
//            }else {
//
//            }
//
//
//        }
//
//        @Override
//        public void onCancel() {
//        }
//
//    }

