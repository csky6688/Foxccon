package com.drkj.foxconn.activties;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.drkj.foxconn.BaseActivity;
import com.drkj.foxconn.R;
import com.drkj.foxconn.adapter.ImageCaptureAdapter;
import com.drkj.foxconn.bean.EquipmentFaultBean;
import com.drkj.foxconn.db.DbController;
import com.drkj.foxconn.util.FileUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EquipmentFaultActivity extends BaseActivity implements ImageCaptureAdapter.OnItemClickListener {

    @BindView(R.id.edit_content)
    EditText content;
    @BindView(R.id.spinner_type_choose)
    Spinner typeSpinner;
    @BindView(R.id.equipment_fault_recycler_view)
    RecyclerView mRecyclerView;

    ImageCaptureAdapter mAdapter;

    private String type = "0";
    private EquipmentFaultBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_fault);
        ButterKnife.bind(this);
        initView();
        bean = new EquipmentFaultBean();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);

        mAdapter = new ImageCaptureAdapter(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

        List<String> data = new ArrayList<>();
        data.add("人为");
        data.add("非人为");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_spinner_type, data);
        typeSpinner.setAdapter(arrayAdapter);
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

    @OnClick(R.id.image_save_feedback)
    void saveFeedback() {
        String tempId = String.valueOf(System.currentTimeMillis());
        bean.setContent(content.getText().toString());
        bean.setType(type);
        bean.setId(tempId);

        List<EquipmentFaultBean.EquipmentFeedbackPictureListBean> pictureBeanList = new ArrayList<>();
        for (File file : mAdapter.getAllData()) {
            EquipmentFaultBean.EquipmentFeedbackPictureListBean pictureBean = new EquipmentFaultBean.EquipmentFeedbackPictureListBean();
            pictureBean.setId(tempId);
            pictureBean.setPath(file.getAbsolutePath());
            pictureBeanList.add(pictureBean);
        }
        bean.setEquipmentFeedbackPictureList(pictureBeanList);

        DbController.getInstance().saveEquipmentFault(bean);
        finish();
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
