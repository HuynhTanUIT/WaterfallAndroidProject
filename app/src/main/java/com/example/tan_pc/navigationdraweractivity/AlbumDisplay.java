package com.example.tan_pc.navigationdraweractivity;

import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

import adapter.AdapterGrid;
import adapter.GridAdapterHome;
import adapter.ImageSingelHome;

import static SettingsSQLite.SqliteHelper.TABLE_BINARY_192;
import static com.example.tan_pc.navigationdraweractivity.MainActivity.PROJECTDATABASE;
import static com.example.tan_pc.navigationdraweractivity.R.id.btnConvertHome;

//import adapter.GridAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumDisplay extends Fragment {
    // Context context;
    ArrayList <ImageSingelHome>imageArray;
    ArrayAdapter<ImageSingelHome> adapter;
    GridView gridviewAlbum;
    Button btnRefreshAlbum;
    //  ArrayList<File> list;
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

    //khi xoay man hinh thi khong bi giu nguyen layout
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        BackupComponen();
        ViewGroup rootView = (ViewGroup) getView();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View newview = inflater.inflate(R.layout.fragment_import, rootView, false);
        rootView.removeAllViews();

        rootView.addView(newview);
        //Restore Values
        InitializeComponent(newview);
        RecoverValuesComponent();
    }

    private void InitializeComponent(View v){
        ReflectAndListener(v);


        LoadImageToGridView();
    }
    private void ReflectAndListener(View v) {
        gridviewAlbum =(GridView)v.findViewById(R.id.gridviewAlbum);
         btnRefreshAlbum=(Button)v.findViewById(R.id.btnRefreshAlbum);
        btnRefreshAlbum.setOnClickListener(btnClickListener);

        imageArray=new ArrayList<ImageSingelHome>();

    }

    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnRefreshAlbum:
                    LoadImageToGridView();
                    break;
            }
        }
    };

    private void BackupComponen(){

    }
    private void RecoverValuesComponent(){

    }
    public void LoadImageToGridView(){
        imageArray.clear();
        Cursor image=PROJECTDATABASE.GetData("SELECT * FROM "+TABLE_BINARY_192);
        while (image.moveToNext()){
            imageArray.add(new ImageSingelHome(
                    image.getString(1),
                    image.getLong(2),
                    image.getLong(3),
                    image.getBlob(4)
            ));
        }
        GridAdapterHome apdaterHome= new GridAdapterHome(getContext(),R.layout.row,imageArray);
       gridviewAlbum.setAdapter(apdaterHome);
        //gridviewHome.setAdapter(null);
    }


    //   ArrayList<File> imageReader(File root)
//   {
//        ArrayList<File> a=new ArrayList<>();
//       File[] files=root.listFiles();
//       for(int i=0;i<files.length;i++){
//           if(files[i].isDirectory()){
//                a.addAll(imageReader(files[i]));
//           }
//           else
//           {
//               if(files[i].getName().endsWith(".jpg")){
//                   a.add(files[i]);
//               }
//           }
//       }
//
//       return a;
//   }
//    public class GridAdapter extends BaseAdapter {
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return list.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            convertView =LayoutInflater.from(context).inflate(R.layout.layout_adapter_gridview,parent,false);
//            ImageView iv=(ImageView) convertView.findViewById(R.id.img_grid);
//            iv.setImageURI(Uri.parse(getItem(position).toString()));
//            //convertView = LayoutInflater.from(context).inflate(res, parent, false);
//            return convertView;
//        }
//    }
//    private void AddImage() {
//        //arrayimg = new ArrayList<Integer>();
//        arrayimg.clear();
//        arrayimg.add(R.drawable.a1);//0
//        arrayimg.add(R.drawable.a2);//1
//        arrayimg.add(R.drawable.a3);//2
//        arrayimg.add(R.drawable.a4);//0
//        arrayimg.add(R.drawable.a5);//1
//        arrayimg.add(R.drawable.a6);//
//        arrayimg.add(R.drawable.a7);//1
//        arrayimg.add(R.drawable.a8);//2
//        arrayimg.add(R.drawable.a9);//1
//        arrayimg.add(R.drawable.a10);
//        arrayimg.add(R.drawable.a11);
//        arrayimg.add(R.drawable.a12);
//        arrayimg.add(R.drawable.a13);
//        arrayimg.add(R.drawable.a14);//
//        arrayimg.add(R.drawable.a15);//1
//        arrayimg.add(R.drawable.a16);//2
//        arrayimg.add(R.drawable.a17);//2
//        arrayimg.add(R.drawable.a18);//2
//        arrayimg.add(R.drawable.a19);//2
//        arrayimg.add(R.drawable.a20);//2
//        arrayimg.add(R.drawable.a21);//2
//        arrayimg.add(R.drawable.a22);//2
//        arrayimg.add(R.drawable.a23);//2
//        arrayimg.add(R.drawable.a24);//2
//        arrayimg.add(R.drawable.a25);//2
//        arrayimg.add(R.drawable.a29);//2
//        arrayimg.add(R.drawable.a30);

//    }
}
