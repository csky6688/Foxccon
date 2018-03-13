package com.drkj.foxconn.activties;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.drkj.foxconn.BaseActivity;
import com.drkj.foxconn.R;
import com.drkj.foxconn.adapter.CheckAdapter;
import com.drkj.foxconn.bean.EquipmentResultBean;
import com.drkj.foxconn.bean.RegionResultBean;
import com.drkj.foxconn.db.DbController;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckActivity extends BaseActivity {

    EquipmentResultBean.DataBean bean;

    @BindView(R.id.list_equipment_attr)
    ListView attrList;
    @BindView(R.id.equipment_fault_tv_name)
    TextView equipmentNameText;
    @BindView(R.id.text_equipment_code)
    TextView equipmentCodeText;
    @BindView(R.id.equipment_falut_tv_location)
    TextView equipmentLocationText;
    @BindView(R.id.text_threshold)
    TextView equipmentThresholdText;

    private CheckAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String nfcCode = getIntent().getStringExtra("nfcCode");
        bean = DbController.getInstance().queryEquipmentByNfcCode(nfcCode);
        equipmentNameText.setText(bean.getName());
        equipmentCodeText.setText(bean.getCode());

        RegionResultBean.DataBean dataBean1 = DbController.getInstance().queryRegionInfoById(bean.getStoreyId());
        RegionResultBean.DataBean dataBean2 = DbController.getInstance().queryRegionInfoById(bean.getRoomId());
        String location = dataBean1.getName() + dataBean2.getName();

        equipmentLocationText.setText(location);

        mAdapter = new CheckAdapter(this, bean);
        mAdapter.setOnContentChangedListener(new CheckAdapter.OnContentChangedListener() {
            @Override
            public void onRangeChanged(@NotNull String hint) {
                equipmentThresholdText.setText(hint);
            }
        });
        attrList.setAdapter(mAdapter);

    }

    @OnClick(R.id.image_save_check)
    void saveCheck() {
        DbController.getInstance().updateEquipmentAttribute(mAdapter.getBean());
        for (EquipmentResultBean.DataBean.EquipmentAttributeListBean attributeListBean : mAdapter.getBean().getEquipmentAttributeList()) {
//            DbController.getInstance().updateEquipmentAttribute(attributeListBean);
            DbController.getInstance().updateEquipmentCheck(mAdapter.getBean());
            finish();
        }
    }
}
