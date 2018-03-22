package com.drkj.foxconn.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.drkj.foxconn.activties.NewMainKotlinActivity;
import com.drkj.foxconn.adapter.OfflineCheckAdapter;
import com.drkj.foxconn.R;
import com.drkj.foxconn.activties.CheckActivity;
import com.drkj.foxconn.bean.EquipmentResultBean;
import com.drkj.foxconn.bean.RegionResultBean;
import com.drkj.foxconn.db.DbConstant;
import com.drkj.foxconn.db.DbController;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class OfflineCheckFragment extends Fragment implements NewMainKotlinActivity.OnNfcListener {

    @BindView(R.id.list_equipment)
    ListView listView;
    @BindView(R.id.offline_check_tv_building)
    TextView tvBuilding;
    @BindView(R.id.offline_check_tv_storey)
    TextView tvStorey;
    @BindView(R.id.offline_check_tv_room)
    TextView tvRoom;
    @BindView(R.id.offline_check_tv_equipment)
    TextView tvEquipment;

    List<EquipmentResultBean.DataBean> dataBeans;

    public OfflineCheckAdapter offlineCheckAdapter;

    private NewMainKotlinActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offline_check, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (NewMainKotlinActivity) getActivity();
        activity.setOnNfcListener(this);
    }

    @OnItemClick(R.id.list_equipment)
    void itemClick(int position) {
        Intent intent = new Intent(new Intent(getActivity(), CheckActivity.class));
        intent.putExtra("nfcCode", dataBeans.get(position).getNfcCode());
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        dataBeans = DbController.getInstance().queryAllEquipment();
        offlineCheckAdapter = new OfflineCheckAdapter(getContext(), dataBeans);
        listView.setAdapter(offlineCheckAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        dataBeans = DbController.getInstance().queryAllEquipment();
        offlineCheckAdapter = new OfflineCheckAdapter(getContext(), dataBeans);
        listView.setAdapter(offlineCheckAdapter);
        Log.e("fragment", "onResume");
    }

    private void setTab(TextView target) {
        tvBuilding.setTextColor(ContextCompat.getColor(activity, R.color.gray));
        tvStorey.setTextColor(ContextCompat.getColor(activity, R.color.gray));
        tvRoom.setTextColor(ContextCompat.getColor(activity, R.color.gray));
        tvEquipment.setTextColor(ContextCompat.getColor(activity, R.color.gray));
        target.setTextColor(ContextCompat.getColor(activity, R.color.black));
    }

    @Override
    public void onNfcReceived(@NotNull String nfcCode) {
        if (!isHidden()) {
            if (!TextUtils.isEmpty(nfcCode)) {
                if (!nfcCode.contains(DbConstant.NFC_HEAD)) {
                    nfcCode = DbConstant.NFC_HEAD + nfcCode.trim().substring(0, 6);
                }

                RegionResultBean.DataBean regionBean = DbController.getInstance().queryRegionByNfcCode(nfcCode);
                if (!TextUtils.isEmpty(regionBean.getType())) {
                    switch (regionBean.getType()) {
                        case DbConstant.TYPE_BUILDING:
                            setTab(tvBuilding);
                            setListBeans(DbController.getInstance().queryAllEquipmentByBuilding(regionBean.getId()));
                            break;
                        case DbConstant.TYPE_STOREY:
                            setTab(tvStorey);
                            setListBeans(DbController.getInstance().queryAllEquipmentByStorey(regionBean.getId()));
                            break;
                        case DbConstant.TYPE_ROOM:
                            setTab(tvRoom);
                            setListBeans(DbController.getInstance().queryAllEquipmentByRoom(regionBean.getId()));
                            break;
                        default:
                            setTab(tvEquipment);
                            List<EquipmentResultBean.DataBean> singleEquipmentList = new ArrayList<>();
                            singleEquipmentList.add(DbController.getInstance().queryEquipmentByNfcCode(nfcCode));
                            setListBeans(singleEquipmentList);
                            break;
                    }
                }

//                if ()

            } else {
                Toast.makeText(activity, "读取失败，请重新刷卡", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setListBeans(List<EquipmentResultBean.DataBean> list) {
        offlineCheckAdapter = new OfflineCheckAdapter(activity, list);
        listView.setAdapter(offlineCheckAdapter);
        offlineCheckAdapter.notifyDataSetChanged();
    }
}
