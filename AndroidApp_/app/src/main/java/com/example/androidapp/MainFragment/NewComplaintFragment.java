package com.example.androidapp.MainFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.androidapp.Adapter.NewComplainAdapter;
import com.example.androidapp.R;
import com.example.androidapp.Utils.AppConstants;
import com.openapi.resolverApp.api.DefaultApi;
import com.openapi.resolverApp.model.ComplainOverview;

import java.util.ArrayList;
import java.util.List;

public class NewComplaintFragment extends Fragment {
    public interface ComplainCallback {
        void onSuccess(List<ComplainOverview> orderList);
        void onFailure(int errorCode, String errorMessage);
    }


    RecyclerView newComplaintRecyclerView;
    List<ComplainOverview> newComplaintList;
    SharedPreferences prefLogin;
    NewComplainAdapter newComplainAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_order, container, false);
        Log.d("myTag", "called this fragment");
        {
            newComplaintRecyclerView = view.findViewById(R.id.new_order_recyclerView);
            prefLogin = requireContext().getSharedPreferences(AppConstants.PREF_LOGIN, Context.MODE_PRIVATE);
            newComplaintList = new ArrayList<>();

        }

        getAllComplaints(new ComplainCallback() {
            @Override
            public void onSuccess(List<ComplainOverview> list) {
                newComplainAdapter = new NewComplainAdapter(list,requireContext());
                newComplaintRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
                newComplaintRecyclerView.setAdapter(newComplainAdapter);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                Toast.makeText(requireContext(),"Failed to load data",Toast.LENGTH_SHORT).show();
                Log.d("myTag", "error while fetching complaint data "+errorMessage);
            }
        });
        return view;
    }

    private void updateItems() {

    }


    public void getAllComplaints(ComplainCallback callback) {
        DefaultApi api = new DefaultApi();
        api.getComplaintsOverview(
                new Response.Listener<List<ComplainOverview>>() {
                    @Override
                    public void onResponse(List<ComplainOverview> response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            int statusCode = error.networkResponse.statusCode;
                            String data = new String(error.networkResponse.data);
                            callback.onFailure(statusCode, data.trim());
                        } catch (Exception e) {
                            callback.onFailure(0, null);
                        }
                    }
                }
        );
    }
}
