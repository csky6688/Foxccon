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
import com.drkj.foxconn.util.NfcCardUtil;
import com.orhanobut.logger.Logger;

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
    @BindView(R.id.offline_check_tv_no_data)
    TextView tvNoData;

    List<EquipmentResultBean.DataBean> dataBeans = new ArrayList<>();

    public OfflineCheckAdapter offlineCheckAdapter;

    private NewMainKotlinActivity activity;

    private NfcCardUtil nfcCardUtil;

    private String scanNfcCode;

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
        nfcCardUtil = new NfcCardUtil(activity);
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
        Log.d("fragment", "onStart");
        if (!TextUtils.isEmpty(scanNfcCode)) {
            displayBeansByNfcCode(scanNfcCode, false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("fragment", "onResume");

        String tempNfcCode = nfcCardUtil.formatNfcCode(nfcCardUtil.readPointData(activity.getIntent(), 0, 1));

        if (!TextUtils.isEmpty(tempNfcCode)) {
            scanNfcCode = tempNfcCode;
        } else {
            dataBeans.clear();
        }
        displayBeansByNfcCode(scanNfcCode, false);
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
        scanNfcCode = nfcCode;
        displayBeansByNfcCode(nfcCode, true);
    }

    private void displayBeansByNfcCode(String nfcCode, boolean isQrCode) {
        if (!TextUtils.isEmpty(nfcCode)) {
            RegionResultBean.DataBean regionBean = DbController.getInstance().queryRegionByNfcCode(nfcCode);
            if (!TextUtils.isEmpty(regionBean.getType()) && !TextUtils.isEmpty(regionBean.getName())) {
                switch (regionBean.getType()) {
                    case DbConstant.TYPE_BUILDING:
                        setTab(tvBuilding);
                        dataBeans = DbController.getInstance().queryAllEquipmentByBuilding(regionBean.getId());
                        break;
                    case DbConstant.TYPE_STOREY:
                        setTab(tvStorey);
                        dataBeans = DbController.getInstance().queryAllEquipmentByStorey(regionBean.getId());
                        break;
                    case DbConstant.TYPE_ROOM:
                        setTab(tvRoom);
                        dataBeans = DbController.getInstance().queryAllEquipmentByRoom(regionBean.getId());
                        break;
                }
                setListBeans(dataBeans);
            }

            if (!TextUtils.isEmpty(nfcCode) && TextUtils.isEmpty(regionBean.getName())) {
                setTab(tvEquipment);
                List<EquipmentResultBean.DataBean> singleEquipmentList = new ArrayList<>();
                EquipmentResultBean.DataBean bean = DbController.getInstance().queryEquipmentByNfcCode(nfcCode);
                if (!TextUtils.isEmpty(bean.getName())) {
                    singleEquipmentList.add(bean);
                    dataBeans = singleEquipmentList;
                    setListBeans(singleEquipmentList);
                } else {
                    dataBeans.clear();
                    setListBeans(dataBeans);
                }
            }
        } else if (isQrCode) {
            Toast.makeText(activity, getResources().getString(R.string.scan_failed), Toast.LENGTH_SHORT).show();
        } else {
            dataBeans.clear();
            setListBeans(dataBeans);
        }
    }

    private void setListBeans(List<EquipmentResultBean.DataBean> list) {
        offlineCheckAdapter = new OfflineCheckAdapter(activity, list);
        listView.setAdapter(offlineCheckAdapter);
        if (list.size() <= 0) {
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.GONE);
        }
    }

    public void resetFragment() {
        scanNfcCode = null;
    }

    @Override
    public void onFragmentResult(@NotNull String result) {
        Logger.d(result);
    }
}
