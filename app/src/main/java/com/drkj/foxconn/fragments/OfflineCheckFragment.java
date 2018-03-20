package com.drkj.foxconn.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.drkj.foxconn.activties.NewMainKotlinActivity;
import com.drkj.foxconn.adapter.OfflineCheckAdapter;
import com.drkj.foxconn.R;
import com.drkj.foxconn.activties.CheckActivity;
import com.drkj.foxconn.bean.EquipmentResultBean;
import com.drkj.foxconn.bean.RegionResultBean;
import com.drkj.foxconn.db.DbController;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class OfflineCheckFragment extends Fragment implements NewMainKotlinActivity.OnNfcListener {

    @BindView(R.id.list_equipment)
    ListView listView;
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

    @Override
    public void onNfcReceived(@NotNull String nfcCode) {
        if (!isHidden()) {
            if (!TextUtils.isEmpty(nfcCode)) {
                nfcCode = "XJ" + nfcCode.trim().substring(0, 6);
                EquipmentResultBean.DataBean equipmentBean = DbController.getInstance().queryEquipmentByNfcCode(nfcCode);
                RegionResultBean.DataBean regionBean = DbController.getInstance().queryRegionByNfcCode(nfcCode);

                if (equipmentBean != null) {
                    Intent intent = new Intent(activity, CheckActivity.class);
                    intent.putExtra("equipment", (Serializable) equipmentBean);
                    startActivity(intent);
                } else if (regionBean != null) {
                    if (regionBean.getType().equals("1")) {//楼栋

                    } else if (regionBean.getType().equals("2")) {//楼层

                    } else if (regionBean.getType().equals("3")) {//机房

                    } else {
                        Toast.makeText(activity, "没有:" + nfcCode + "的信息", Toast.LENGTH_SHORT).show();
                    }
                }
                Toast.makeText(activity, "收到:" + nfcCode, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "读取失败，请重新刷卡", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
