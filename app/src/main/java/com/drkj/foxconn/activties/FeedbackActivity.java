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
import android.view.KeyEvent;
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
import com.drkj.foxconn.bean.FeedbackBean;
import com.drkj.foxconn.bean.RegionResultBean;
import com.drkj.foxconn.db.DbController;
import com.drkj.foxconn.mvp.presenter.FeedbackPresenter;
import com.drkj.foxconn.mvp.view.IFeedbackView;
import com.drkj.foxconn.util.DateUtil;
import com.drkj.foxconn.util.FileUtil;
import com.zltd.decoder.DecoderManager;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity implements ImageCaptureAdapter.OnItemClickListener, IFeedbackView, DecoderManager.IDecoderStatusListener {

    FeedbackBean feedbackBean;

    @BindView(R.id.edit_content)
    EditText content;
    @BindView(R.id.feedback_spinner_type_choose)
    Spinner typeSpinner;
    @BindView(R.id.feedback_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.feedback_tv_location)
    TextView tvLocation;
    @BindView(R.id.feedback_tv_location_code)
    TextView tvLocationCode;

    private ImageCaptureAdapter mAdapter;

    private String type = "0";

    private String feedbackId;

    private FeedbackPresenter presenter = new FeedbackPresenter();

    private List<String> typeList = new ArrayList<>();

    private ArrayAdapter<String> spinnerAdapter;

    private DecoderManager decoderManager;

    private boolean isResume = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initView();
        feedbackBean = new FeedbackBean();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResume = true;
//        decoderManager.connectDecoderSRV();
        decoderManager.addDecoderStatusListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isResume = false;
//        decoderManager.disconnectDecoderSRV();
        decoderManager.removeDecoderStatusListener(this);
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

        decoderManager = DecoderManager.getInstance();

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
        if (TextUtils.isEmpty(tvLocation.getText())) {
            Toast.makeText(this, "请扫描位置信息", Toast.LENGTH_SHORT).show();
            return;
        }

        String tempId = String.valueOf(System.currentTimeMillis());
        if (TextUtils.isEmpty(feedbackId)) {
            feedbackBean.setCreateDate(DateUtil.INSTANCE.getDate());
            feedbackBean.setContent(content.getText().toString());
            feedbackBean.setType(type);
            feedbackBean.setId(tempId);
            feedbackBean.setLocation(tvLocation.getText().toString());
            feedbackBean.setRegionName(tvLocation.getText().toString());
            feedbackBean.setRegionCode(tvLocationCode.getText().toString());
            feedbackBean.setCreateDate(DateUtil.INSTANCE.getDate());
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
            feedbackBean.setUpdateDate(DateUtil.INSTANCE.getDate());
            feedbackBean.setId(tempId);//必须重新更换id
            feedbackBean.setContent(content.getText().toString());
            feedbackBean.setRegionName(tvLocation.getText().toString());
            feedbackBean.setRegionCode(tvLocationCode.getText().toString());
            feedbackBean.setType(type);
            feedbackBean.setLocation(tvLocation.getText().toString());
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
        tvLocation.setText(bean.getRegionName());
        tvLocationCode.setText(bean.getRegionCode());

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
    public void onNfcCodeReceive(@NotNull final String location, @NotNull final String locationCode, @NonNull final String nfcCode) {
        if (!TextUtils.isEmpty(location)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvLocation.setText(location);
                    tvLocationCode.setText(locationCode);
                }
            });
            feedbackBean.setRegionCode(locationCode);
            feedbackBean.setRegionName(location);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(FeedbackActivity.this, "没有搜索到代码:" + nfcCode, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onNfcReceiveFailed() {
        Toast.makeText(this, "读取失败,请重新读取", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoNfcCode(@NotNull final String nfcCode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FeedbackActivity.this, "没有搜索到代码:" + nfcCode, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDecoderStatusChanage(int i) {

    }

    @Override
    public void onDecoderResultChanage(String s, String s1) {
        if (!TextUtils.isEmpty(s)) {
            presenter.queryFeedback(s);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(FeedbackActivity.this, "扫描失败，请重试", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BUTTON_A:
                if (isResume) {
                    decoderManager.connectDecoderSRV();
                    decoderManager.dispatchScanKeyEvent(event);
                }
                return true;
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BUTTON_A:
                if (isResume) {
                    decoderManager.disconnectDecoderSRV();
                    decoderManager.dispatchScanKeyEvent(event);
                }
                return true;
            default:
                return super.onKeyDown(keyCode, event);
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
                    Log.e("file", path);
                    mAdapter.addPic(new File(path));
                    break;
            }
        }
    }
}
