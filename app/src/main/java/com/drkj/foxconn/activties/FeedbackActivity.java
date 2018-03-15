package com.drkj.foxconn.activties;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.drkj.foxconn.BaseActivity;
import com.drkj.foxconn.R;
import com.drkj.foxconn.adapter.ImageCaptureAdapter;
import com.drkj.foxconn.bean.FeedbackBean;
import com.drkj.foxconn.bean.FeedbackResultBean;
import com.drkj.foxconn.db.DbController;
import com.drkj.foxconn.mvp.presenter.FeedbackPresenter;
import com.drkj.foxconn.mvp.view.IFeedbackView;
import com.drkj.foxconn.util.FileUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity implements ImageCaptureAdapter.OnItemClickListener, IFeedbackView {

    FeedbackBean feedbackBean;

    @BindView(R.id.edit_content)
    EditText content;
    @BindView(R.id.spinner_type_choose)
    Spinner typeSpinner;
    @BindView(R.id.feedback_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.feedback_tv_location)
    TextView tvLocation;

    private ImageCaptureAdapter mAdapter;

    private String type = "0";

    private String feedbackId;

    private FeedbackPresenter presenter = new FeedbackPresenter();

    private List<String> typeList = new ArrayList<>();

    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initView();
        feedbackBean = new FeedbackBean();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        presenter.queryFeedback(nfcCardUtil.readPointData(intent, 0, 1));//默认读取0扇区1块的数据
    }

    private void initView() {
        mAdapter = new ImageCaptureAdapter(this);
        presenter.bindView(this);

        typeList.add("人为");
        typeList.add("非人为");

        if (!TextUtils.isEmpty(getIntent().getStringExtra("feedbackId"))) {
            feedbackId = getIntent().getStringExtra("feedbackId");
            presenter.deployFeedback(feedbackId);
        } else {
            spinnerAdapter = new ArrayAdapter<>(this, R.layout.item_spinner_type, typeList);
            typeSpinner.setAdapter(spinnerAdapter);
            typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i) {
                        case 0:
                            type = "0";
                            break;
                        case 1:
                            type = "1";
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            presenter.createFeedback();
        }

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        mAdapter.setOnItemClickListener(this);

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.image_save_feedback)
    void saveFeedback() {
        String tempId = String.valueOf(System.currentTimeMillis());
        if (TextUtils.isEmpty(feedbackId)) {
            feedbackBean.setContent(content.getText().toString());
            feedbackBean.setType(type);
            feedbackBean.setId(tempId);
            feedbackBean.setLoaction(tvLocation.getText().toString());
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
        } else {
            feedbackBean.setId(tempId);//必须重新更换id
            feedbackBean.setContent(content.getText().toString());
            feedbackBean.setType(type);
            feedbackBean.setLoaction(tvLocation.getText().toString());
            List<FeedbackBean.LocalFeedbackPictureListBean> pictureListBeans = new ArrayList<>();
            for (File file : mAdapter.getAllData()) {
                FeedbackBean.LocalFeedbackPictureListBean pictureBean = new FeedbackBean.LocalFeedbackPictureListBean();
                pictureBean.setId(tempId);
                pictureBean.setPath(file.getAbsolutePath());
                pictureListBeans.add(pictureBean);
            }
            feedbackBean.setLocalFeedbackPictureList(pictureListBeans);

            DbController.getInstance().saveFeedback(feedbackBean);
            DbController.getInstance().deleteFeedbackById(feedbackId);
            finish();
        }
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
    public void onDeployFeedback(@NotNull final FeedbackBean bean) {
        for (FeedbackBean.LocalFeedbackPictureListBean picBean : bean.getLocalFeedbackPictureList()) {
            mAdapter.addPic(new File(picBean.getPath()));
        }
        feedbackBean = bean;

        content.setText(bean.getContent());

        ArrayList<File> tempList = new ArrayList<>();
        for (FeedbackBean.LocalFeedbackPictureListBean listBean : feedbackBean.getLocalFeedbackPictureList()) {
            tempList.add(new File(listBean.getPath()));
        }

        //Spinner有bug，设置当前选项必须这么写
        spinnerAdapter = new ArrayAdapter<>(this, R.layout.item_spinner_type, typeList);
        typeSpinner.setAdapter(spinnerAdapter);
        typeSpinner.setSelection(Integer.parseInt(feedbackBean.getType()), true);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        type = "0";
                        break;
                    case 1:
                        type = "1";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mAdapter.setImgList(tempList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreateFeedback() {

    }

    @Override
    public void onNfcReceive(@NotNull FeedbackBean bean, @NonNull String nfcCode) {
        if (bean.getRegionName() != null) {
            tvLocation.setText(bean.getRegionName());
        } else {
            Toast.makeText(this, "没有搜索到位置代码:" + nfcCode, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNfcReceiveFailed() {
        Toast.makeText(this, "读取失败,请重新刷卡", Toast.LENGTH_SHORT).show();
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

                    //TODO /rest/cgUploadController/upload 用来上传图片并返回url，这个url放在picture里面
//                    feedbackBean.setPicture(imageBase);

//                    imagePaths.add(imageUrlPath);
//                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
