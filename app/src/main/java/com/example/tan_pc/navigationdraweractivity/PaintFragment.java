package com.example.tan_pc.navigationdraweractivity;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaintFragment extends Fragment {
Button btnPickimage;
ImageView imageViewPaintColor;
ImageView imageViewPaintBinary;
    private static int RESULT_LOAD_IMAGE = 1;
    public PaintFragment() {
        // Required empty public constructor
    }
    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
//                case //R.id.btnPickimage:
//                    Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    getActivity().startActivityForResult(i, RESULT_LOAD_IMAGE);
//                    //imageViewHomeColor.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_paint, container, false);
       // InitializeComponent(view);
        return view;
    }
    private void InitializeComponent(View v) {
        //load into Valves
        //ReflectAndListener(v);
    }

    private void ReflectAndListener(View v) {
        //btnPickimage = (Button) v.findViewById(R.id.btnPickimage);
//        imageViewPaintColor = (ImageView) v.findViewById(R.id.imageViewHomeColor);
//        imageViewPaintBinary = (ImageView) v.findViewById(R.id.imageViewHomeBinary);
        btnPickimage.setOnClickListener(btnClickListener);
    }
}
