package com.example.tan_pc.navigationdraweractivity.Reference;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.tan_pc.navigationdraweractivity.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by leehoa on 11/25/16.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener{
    List<Bitmap> images;
    List<CheckBox> checkboxt;
    int type;
    OnClickListenerRecycler onClickListenerRecycler;
    OnClickListenerRecycler3 onClickListenerRecycler3;
    public  HomeAdapter(List<Bitmap> images,List<CheckBox> checkBoxes , int type) {
        this.images = images;
        this.checkboxt = checkBoxes;
        this.type = type;
    }
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_cell, null);
        return new ViewHolder(view);
    }

    public void setOnClickListenerRecycler(OnClickListenerRecycler onClickListenerRecycler) {
        this.onClickListenerRecycler = onClickListenerRecycler;
    }

    public void setOnClickListenerRecycle3r(OnClickListenerRecycler3 onClickListenerRecycler3) {
        this.onClickListenerRecycler3 = onClickListenerRecycler3;
    }


    @Override
    public void onBindViewHolder(final HomeAdapter.ViewHolder holder, final int position) {
        try {
            holder.setImage(images.get(position));
            holder.setCheckbox(checkboxt.get(position),false);
        }catch (Exception e){

        }
//

        if (type == 1) {
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListenerRecycler.onClicked(images.get(position));
                }
            });
        } else if (type == 2) {
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        } else if (type == 3) {
            holder.image.setOnLongClickListener(new View.OnLongClickListener() {
                @SuppressLint("LongLogTag")
                @Override
                public boolean onLongClick(View v) {
                    try {
//                        onClickListenerRecycler3.onClicked(position);
                        Log.e("onClickListenerRecycler3", "onLOngClick: " +  "at "+ getItemCount() );
                        onClickListenerRecycler3.onLongClicked(position);
//                        for (int i=0 ;i <getItemCount();i++) {
////                            holder.setCheckbox(checkboxt.get(position), false);
//                        }
//                        updateData((ArrayList<Bitmap>) images);
//                        DataChage();
                    }catch (Exception e){
//                        Log.e("onClickListenerRecycler3", "onClick: "+ e.toString() );
                    }
                    return false;
                }
            });
            holder.image.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("LongLogTag")
                @Override
                public void onClick(View view) {
                    try {
                        onClickListenerRecycler3.onClicked(position);
//                        if(!checkboxt.get(position).isChecked()) {
//                            holder.setCheckbox(checkboxt.get(position), true);
//                        }else {
//                            holder.setCheckbox(checkboxt.get(position), false);
//                        }
//                        onClickListenerRecycler3.onLongClicked(position);

                    }catch (Exception e){
                        Log.e("onClickListenerRecycler3", "onClick: "+ e.toString() );
                    }
                }


            });

        }

    }

    public void updateData(ArrayList<Bitmap> viewModels) {
        images.clear();
        images.addAll(viewModels);
        notifyDataSetChanged();
    }

    public void DataChage() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public void onClick(View v) {

    }

    @SuppressLint("LongLogTag")
    @Override
    public boolean onLongClick(View v) {
//        onBindViewHolder(v,);
         Log.e("onClickListenerRecycler3", "onLongClick: "  );
        return false;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        ImageView image;
        CheckBox cbSelect;
        public ViewHolder(View itemView) {
            super(itemView);
            try {
                image = (ImageView) itemView.findViewById(R.id.imageId);
                cbSelect =(CheckBox) itemView.findViewById(R.id.cbSelect);
                image.setLayoutParams(new LinearLayout.LayoutParams(DeviceInfo.getScreenWidth()/4-6,DeviceInfo.getScreenWidth()/4-6));
            }catch (Exception e){

            }

        }

        public void setImage(Bitmap images) {
            try {
                image.setImageBitmap(images);
            }catch (Exception e){

            }
        }
        public void setCheckbox(CheckBox cb, boolean a)
        {
            cb.setVisibility(View.VISIBLE);
            cb.setChecked(a);
            notifyDataSetChanged();
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            Log.e("LOng Click", "onLongClick: "+"Long Clicked at"+ " ");
//            cbSelect.setVisibility(View.VISIBLE);
            return true;
        }
    }
}
