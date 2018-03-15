package com.drkj.foxconn.activties;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.drkj.foxconn.BaseActivity;
import com.drkj.foxconn.R;
import com.drkj.foxconn.adapter.ImageCaptureAdapter;
import com.drkj.foxconn.bean.EquipmentFaultBean;
import com.drkj.foxconn.bean.EquipmentResultBean;
import com.drkj.foxconn.db.DbController;
import com.drkj.foxconn.mvp.presenter.EquipmentFaultPresenter;
import com.drkj.foxconn.mvp.view.IEquipmentFaultView;
import com.drkj.foxconn.util.FileUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EquipmentFaultActivity extends BaseActivity implements ImageCaptureAdapter.OnItemClickListener, IEquipmentFaultView {

    @BindView(R.id.edit_content)
    EditText content;
    @BindView(R.id.spinner_type_choose)
    Spinner typeSpinner;
    @BindView(R.id.equipment_fault_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.equipment_fault_tv_location)
    TextView tvLocation;
    @BindView(R.id.equipment_fault_tv_name)
    TextView tvName;
    @BindView(R.id.equipment_fault_tv_code)
    TextView tvCode;

    private ImageCaptureAdapter mAdapter;

    private String type = "0";

    private EquipmentFaultBean equipmentFaultBean;

    private String equipmentFaultId;

    private EquipmentFaultPresenter presenter = new EquipmentFaultPresenter();

    private List<String> typeList = new ArrayList<>();

    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_fault);
        ButterKnife.bind(this);
        initView();
        equipmentFaultBean = new EquipmentFaultBean();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        presenter.queryEquipmentFault(nfcCardUtil.readPointData(intent, 0, 1));
    }

    private void initView() {
        mAdapter = new ImageCaptureAdapter(this);
        presenter.bindView(this);

        typeList.add("人为");
        typeList.add("非人为");

        if (!TextUtils.isEmpty(getIntent().getStringExtra("equipmentFaultId"))) {
            equipmentFaultId = getIntent().getStringExtra("equipmentFaultId");
            presenter.deployData(equipmentFaultId);
        } else {
            presenter.createData();
        }

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        mAdapter.setOnItemClickListener(this);

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.image_save_feedback)
    void saveFeedback() {
        String tempId = String.valueOf(System.currentTimeMillis());
        if (TextUtils.isEmpty(equipmentFaultId)) {
            equipmentFaultBean.setContent(content.getText().toString());
            equipmentFaultBean.setType(type);
            equipmentFaultBean.setId(tempId);
            equipmentFaultBean.setEquipmentName(tvName.getText().toString());
            equipmentFaultBean.setEquipmentCode(tvCode.getText().toString());
            equipmentFaultBean.setLocation(tvLocation.getText().toString());

            List<EquipmentFaultBean.EquipmentFeedbackPictureListBean> pictureBeanList = new ArrayList<>();
            for (File file : mAdapter.getAllData()) {
                EquipmentFaultBean.EquipmentFeedbackPictureListBean pictureBean = new EquipmentFaultBean.EquipmentFeedbackPictureListBean();
                pictureBean.setId(tempId);
                pictureBean.setPath(file.getAbsolutePath());
                pictureBeanList.add(pictureBean);
            }
            equipmentFaultBean.setEquipmentFeedbackPictureList(pictureBeanList);

            DbController.getInstance().saveEquipmentFault(equipmentFaultBean);
            finish();
        } else {
            equipmentFaultBean.setContent(content.getText().toString());
            equipmentFaultBean.setType(type);
            equipmentFaultBean.setId(tempId);
            equipmentFaultBean.setEquipmentCode(tvCode.getText().toString());
            equipmentFaultBean.setLocation(tvLocation.getText().toString());
            equipmentFaultBean.setEquipmentName(tvName.getText().toString());
            List<EquipmentFaultBean.EquipmentFeedbackPictureListBean> pictureBeanList = new ArrayList<>();
            for (File file : mAdapter.getAllData()) {
                EquipmentFaultBean.EquipmentFeedbackPictureListBean pictureBean = new EquipmentFaultBean.EquipmentFeedbackPictureListBean();
                pictureBean.setId(tempId);
                pictureBean.setPath(file.getAbsolutePath());
                pictureBeanList.add(pictureBean);
            }
            equipmentFaultBean.setEquipmentFeedbackPictureList(pictureBeanList);

            DbController.getInstance().saveEquipmentFault(equipmentFaultBean);
            DbController.getInstance().deleteEquipmentFaultById(equipmentFaultId);
            finish();
        }
    }

    @Override
    public void onDeployFault(@NotNull EquipmentFaultBean bean) {
        for (EquipmentFaultBean.EquipmentFeedbackPictureListBean picBean : bean.getEquipmentFeedbackPictureList()) {
            mAdapter.addPic(new File(picBean.getPath()));
        }
        equipmentFaultBean = bean;

        content.setText(bean.getContent());
        tvCode.setText(bean.getEquipmentCode());
        tvName.setText(bean.getEquipmentName());
        tvLocation.setText(bean.getLocation());

        ArrayList<File> tempList = new ArrayList<>();
        for (EquipmentFaultBean.EquipmentFeedbackPictureListBean listBean : equipmentFaultBean.getEquipmentFeedbackPictureList()) {
            tempList.add(new File(listBean.getPath()));
        }

        spinnerAdapter = new ArrayAdapter<>(this, R.layout.item_spinner_type, typeList);
        typeSpinner.setAdapter(spinnerAdapter);
        typeSpinner.setSelection(Integer.parseInt(equipmentFaultBean.getType()), true);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        type = "0";
                        break;
                    case 1:
                        type = "1";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mAdapter.setImgList(tempList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onFaultCreate() {
        spinnerAdapter = new ArrayAdapter<>(this, R.layout.item_spinner_type, typeList);
        typeSpinner.setAdapter(spinnerAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        type = "0";
                        break;
                    case 1:
                        type = "1";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onAddClick() {
        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takeIntent, 200);
    }

    @Override
    public void onReceive(@NotNull String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNfcReceiveFailed() {
        Toast.makeText(this, "读取失败,请重新刷卡", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNfcReceive(@NotNull EquipmentResultBean.DataBean bean, @NotNull String location, @NotNull String nfcCode) {
        if (bean.getName() != null) {
            tvName.setText(bean.getName());
            tvLocation.setText(location);
            tvCode.setText(bean.getCode());
        } else {
            Toast.makeText(this, "没有搜索到位置代码:" + nfcCode, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 200:
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    String path = FileUtil.saveBitmap(bitmap);
                    mAdapter.addPic(new File(path));
                    break;
            }
        }
    }
}
