//package SettingsSQLite;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.tan_pc.navigationdraweractivity.R;
//
//import java.util.List;
//
///**
// * Created by TAN-PC on 10/12/2016.
// */
//
//public class SettingsAdapter extends BaseAdapter {
//    private Context mContext;
//    private LayoutInflater mInflater;
//    private List<SettingsObject> mLstMenu;
//    private ViewHoler mHoler;
//    private SqliteHelper sqliteHelper;
//    public SettingsAdapter(Context context, List<SettingsObject> lstMenu, SqliteHelper sqliteHelper) {
//        this.mContext = context;
//        mInflater = (LayoutInflater) mContext
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        this.sqliteHelper=sqliteHelper;
//        this.mLstMenu = lstMenu;
//    }
//
//    @Override
//    public int getCount() {
//        return mLstMenu.size();
//    }
//
//    @Override
//    public Object getItem(int arg0) {
//        return mLstMenu.get(arg0);
//    }
//
//    @Override
//    public long getItemId(int arg0) {
//        return mLstMenu.size();
//    }
//
//
//    @Override
//    public View getView(int arg0, View view, ViewGroup arg2) {
//        if (view == null) {
//            view = mInflater.inflate(R.layout.row, null);
//            mHoler = new ViewHoler();
//            mHoler.tvId = (TextView) view.findViewById(R.id.tvId);
//            mHoler.tvName = (TextView) view.findViewById(R.id.tvName);
//            mHoler.btDelete = (Button) view.findViewById(R.id.btDelete);
//            view.setTag(mHoler);
//        } else {
//            mHoler = (ViewHoler) view.getTag();
//        }
//        final SettingsObject settingsObject= mLstMenu.get(arg0);
//        mHoler.tvId.setText(settingsObject.getId());
//        mHoler.tvName.setText(settingsObject.getName());
//        mHoler.btDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int deleteRows=sqliteHelper.deleteUser("tblUser",settingsObject.getId());
//                Toast.makeText(mContext, "Delete "+String.valueOf(deleteRows)+" row", Toast.LENGTH_SHORT).show();
//            }
//        });
//        return view;
//    }
//
//    private class ViewHoler {
//        TextView tvId,tvName;
//        Button btDelete;
//    }
//}
