package com.drkj.foxconn.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.drkj.foxconn.R;
import com.drkj.foxconn.activties.EquipmentFaultActivity;
import com.drkj.foxconn.adapter.EquipmentFaultAdapter;
import com.drkj.foxconn.adapter.FeedbackAdapter;
import com.drkj.foxconn.bean.EquipmentFaultBean;
import com.drkj.foxconn.db.DbController;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

public class EquipmentFaultFragment extends Fragment {
    @BindView(R.id.listview_fault)
    ListView listView;

    EquipmentFaultAdapter mAdapter;

    List<EquipmentFaultBean> equipmentFaultBeanList;

    private AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipment_fault, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.image_create_feedback)
    void createFeedback() {
        startActivity(new Intent(getContext(), EquipmentFaultActivity.class));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        equipmentFaultBeanList = DbController.getInstance().queryAllEquipmentFault();
        mAdapter = new EquipmentFaultAdapter(getActivity(), equipmentFaultBeanList);


//        MyAdapter adapter = new MyAdapter();
//        listView.setAdapter(adapter);
    }

    @OnItemClick(R.id.listview_fault)
    void itemClick(int position) {
        Intent intent = new Intent(getActivity(), EquipmentFaultActivity.class);
        intent.putExtra("equipmentFaultId", equipmentFaultBeanList.get(position).getId());
        startActivity(intent);
    }

    @OnItemLongClick(R.id.listview_fault)
    boolean itemLongClick(final int position) {
        dialog = new AlertDialog.Builder(getActivity())
                .setMessage(String.format(getResources().getString(R.string.really_delete_number_data), (position + 1)))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DbController.getInstance().deleteEquipmentFault(equipmentFaultBeanList.get(position));
                        equipmentFaultBeanList = DbController.getInstance().queryAllEquipmentFault();
                        mAdapter = new EquipmentFaultAdapter(getActivity(), equipmentFaultBeanList);
                        listView.setAdapter(mAdapter);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        dialog.show();
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        equipmentFaultBeanList = DbController.getInstance().queryAllEquipmentFault();
        mAdapter = new EquipmentFaultAdapter(getActivity(), equipmentFaultBeanList);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        equipmentFaultBeanList = DbController.getInstance().queryAllEquipmentFault();
        mAdapter = new EquipmentFaultAdapter(getActivity(), equipmentFaultBeanList);
        listView.setAdapter(mAdapter);
    }
}
