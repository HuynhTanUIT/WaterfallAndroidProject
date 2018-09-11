package com.example.tan_pc.navigationdraweractivity.EmployeeFragment;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.tan_pc.navigationdraweractivity.Activity.LoginActivity;
import com.example.tan_pc.navigationdraweractivity.R;

import java.util.ArrayList;
import java.util.List;

import ClientSocket.GetDataService;
import ClientSocket.RetrofitClientInstance;
import ClientSocket.account;
import adapter.CustomAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EmployeeTrackingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EmployeeTrackingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeTrackingFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;

    ArrayList<account> accountsArray;
    ArrayAdapter<account> arrayAdapter1;
    public EmployeeTrackingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmployeeTrackingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmployeeTrackingFragment newInstance(String param1, String param2) {
        EmployeeTrackingFragment fragment = new EmployeeTrackingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_employee_tracking, container, false);

        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        // Set Adapter mặc định cho Activity
//        ArrayAdapter<account> arrayAdapter =
//                new ArrayAdapter<account>(this,
//                        android.R.layout.simple_list_item_1,
//                        android.R.id.text1,
//                        new ArrayList<account>());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            accountsArray = new ArrayList<account>();
            arrayAdapter1 =new ArrayAdapter<account>(getContext(), R.layout.user_row, accountsArray);
        }
//        setListAdapter(arrayAdapter1);

        // Khởi tạo Retrofit để gán API ENDPOINT (Domain URL) cho Retrofit 2.0
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://odyssey-one-mock-server.herokuapp.com")
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Khởi tạo các cuộc gọi cho Retrofit 2.0
        GetDataService service = retrofit.create(GetDataService.class);

        Call<List<account>> call = service.loadUser("user");
        // Cuộc gọi bất đồng bọ (chạy dưới background)\

        call.enqueue(new Callback<List<account>>() {
            @Override
            public void onResponse(Call<List<account>> call, Response<List<account>> response) {
                progressDoalog.dismiss();
                generateDataList(response.body());
            }

            private void generateDataList(List<account> body) {
                recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewListEmployee);
//                adapter = new CustomAdapter(this,body);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
//                recyclerView.setAdapter(arrayAdapter1);
            }

            @Override
            public void onFailure(Call<List<account>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


//        @Override
//        public void onResponse(Call<List<account>> call, Response<List<account>> response) {
//            progressDoalog.dismiss();
////            generateDataList(response.body());
//        }
//
//        @Override
//        public void onFailure(Call<List<account>> call, Throwable t) {
//            progressDoalog.dismiss();
//            Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
//        }
//        private void generateDataList(List<account> body) {
//            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewListEmployee);
//            adapter = new CustomAdapter(this,body);
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setAdapter(adapter);
//        }
        /*
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<List<account>> call = service.getAllUser();
        call.enqueue(new Callback<List<account>>() {
            @Override
            public void onResponse(Call<List<account>> call, Response<List<account>> response) {
                progressDoalog.dismiss();
                generateDataList(response.body());
            }

            private void generateDataList(List<account> body) {
                recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewListEmployee);
                adapter = new CustomAdapter(this,body);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<account>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
        */
      return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
