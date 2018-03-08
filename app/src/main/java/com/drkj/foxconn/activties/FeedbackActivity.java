package com.drkj.foxconn.activties;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.drkj.foxconn.BaseActivity;
import com.drkj.foxconn.R;
import com.drkj.foxconn.adapter.ImageCaptureAdapter;
import com.drkj.foxconn.bean.FeedbackBean;
import com.drkj.foxconn.bean.FeedbackResultBean;
import com.drkj.foxconn.db.DbController;
import com.drkj.foxconn.util.FileUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity implements ImageCaptureAdapter.OnItemClickListener {

    FeedbackBean feedbackBean;

    @BindView(R.id.edit_content)
    EditText content;
    @BindView(R.id.spinner_type_choose)
    Spinner typeSpinner;
    @BindView(R.id.feedback_recycler_view)
    RecyclerView mRecyclerView;

    private ImageCaptureAdapter mAdapter;

    private String type = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initView();
        feedbackBean = new FeedbackBean();
    }

    private void initView() {

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);

        mAdapter = new ImageCaptureAdapter(this);
        mAdapter.setOnItemClickListener(this);


        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(manager);

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

        feedbackBean.setContent(content.getText().toString());
        feedbackBean.setType(type);
        feedbackBean.setId(tempId);

        List<FeedbackBean.LocalFeedbackPictureListBean> pictureBeanList = new ArrayList<>();
        for (File file : mAdapter.getAllData()) {
            FeedbackBean.LocalFeedbackPictureListBean pictureBean = new FeedbackBean.LocalFeedbackPictureListBean();
            pictureBean.setId(tempId);
            pictureBean.setPath(file.getAbsolutePath());
            pictureBeanList.add(pictureBean);
        }
        feedbackBean.setLocalFeedbackPictureList(pictureBeanList);

        DbController.getInstance().saveFeedback(feedbackBean);
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

//                    ImageView img = new ImageView(this);
//                    img.setLayoutParams(pic.getLayoutParams());
//                    img.setImageBitmap(bitmap);
                    String path = FileUtil.saveBitmap(bitmap);
                    Log.e("file", path);
//                    mBitmapList.add(new File(path));
                    mAdapter.addPic(new File(path));


//                    pic.setImageBitmap(bitmap);
//                    mAdapter.notifyDataSetChanged();


//                    String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
//                    String imageUrlPath = FileUtil.saveFile(this, name, bitmap);
//                    String imageBase = FileUtil.bitmapToString(bitmap);
                    //FIXME 反馈照片实体字段修改了，这里需要修改读取算法

                    //TODO /rest/cgUploadController/upload 用来上传图片并返回url，这个url放在picture里面
//                    feedbackBean.setPicture(imageBase);

//                    imagePaths.add(imageUrlPath);
//                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
