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
import com.drkj.foxconn.db.DbController;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class OfflineCheckFragment extends Fragment implements NewMainKotlinActivity.OnNfcListener {

    @BindView(R.id.list_equipment)
    ListView listView;
    List<EquipmentResultBean.DataBean> dataBeans;

//    MyAdapter adapter;

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
        //FIXME 暂时将数据刷新移动到onResume方法中
//        dataBeans = DbController.getInstance().queryAllEquipment();
//        adapter = new MyAdapter(dataBeans);
//        listView.setAdapter(adapter);
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

    public void updateData() {
        dataBeans = DbController.getInstance().queryAllEquipment();
        offlineCheckAdapter = new OfflineCheckAdapter(getContext(), dataBeans);
        listView.setAdapter(offlineCheckAdapter);
    }

    @Override
    public void onNfcReceived(@NotNull String nfcCode) {
        if (!isHidden()) {
            if (!TextUtils.isEmpty(nfcCode)) {
                nfcCode = "XJ" + nfcCode.trim().substring(0, 6);



                Toast.makeText(activity, "收到:" + nfcCode, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "读取失败，请重新刷卡", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
