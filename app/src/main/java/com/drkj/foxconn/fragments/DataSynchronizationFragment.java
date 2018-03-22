package com.drkj.foxconn.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.drkj.foxconn.App;
import com.drkj.foxconn.R;
import com.drkj.foxconn.activties.NewMainKotlinActivity;
import com.drkj.foxconn.bean.EndTaskBean;
import com.drkj.foxconn.bean.EndTaskResultBean;
import com.drkj.foxconn.bean.EquipmentFaultBean;
import com.drkj.foxconn.bean.EquipmentFaultResultBean;
import com.drkj.foxconn.bean.EquipmentResultBean;
import com.drkj.foxconn.bean.FeedbackBean;
import com.drkj.foxconn.bean.FeedbackResultBean;
import com.drkj.foxconn.bean.PictureUrlBean;
import com.drkj.foxconn.bean.RegionResultBean;
import com.drkj.foxconn.bean.StartTaskBean;
import com.drkj.foxconn.bean.StartTaskResultBean;
import com.drkj.foxconn.db.DbController;
import com.drkj.foxconn.net.NetClient;
import com.drkj.foxconn.util.SpUtil;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by ganlong on 2018/1/16.
 * <p>
 * Modify by VeronicaRen on 2018/3/1.
 * 修改了反馈上传机制
 */
public class DataSynchronizationFragment extends Fragment {

    private final int UPDATE_FEEDBACK = 1;

    private final int UPDATE_EQUIPMENT_FAULT = 2;

    private List<PictureUrlBean> pictureUrlBeanList = new ArrayList<>();

    private Handler mHandler;

    private NewMainKotlinActivity activity;

    private boolean once = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_synchronization, null);
        ButterKnife.bind(this, view);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE_FEEDBACK:
                        final FeedbackBean tempFeedbackBean = (FeedbackBean) msg.obj;
                        Logger.t("feedback").e(new Gson().toJson(tempFeedbackBean));
                        NetClient.getInstance().getApi().feedback(SpUtil.getString(getContext(), "token"), tempFeedbackBean)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<FeedbackResultBean>() {
                                    @Override
                                    public void accept(FeedbackResultBean feedbackResultBean) throws Exception {
                                        if (TextUtils.equals(feedbackResultBean.getRespCode(), "0")) {
                                            Log.e("update", new Gson().toJson(tempFeedbackBean));
                                            DbController.getInstance().deleteFeedback(tempFeedbackBean);

                                            Log.e("update", "现场反馈(有图)上传完成");
                                        }
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        throwable.printStackTrace();
                                        Log.e("update", "现场反馈(有图)上传失败:" + throwable.getMessage());
                                    }
                                });
                        break;
                    case UPDATE_EQUIPMENT_FAULT:
                        final EquipmentFaultBean tempEquipmentFaultBean = (EquipmentFaultBean) msg.obj;
                        Logger.t("feedback").e(new Gson().toJson(tempEquipmentFaultBean));
                        NetClient.getInstance().getApi().equipmentFault(SpUtil.getString(getContext(), "token"), tempEquipmentFaultBean)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<EquipmentFaultResultBean>() {
                                    @Override
                                    public void accept(EquipmentFaultResultBean equipmentFaultResultBean) throws Exception {
                                        if (TextUtils.equals(equipmentFaultResultBean.getRespCode(), "0")) {
                                            Log.e("update", new Gson().toJson(tempEquipmentFaultBean));
                                            DbController.getInstance().deleteEquipmentFault(tempEquipmentFaultBean);
                                            Log.e("update", "设备故障(有图)上传完成");
                                        }
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Log.e("update", "设备反馈(有图)上传失败:" + throwable.getMessage());
                                    }
                                });
                        break;
                }
            }
        };
        activity = (NewMainKotlinActivity) getActivity();
        return view;
    }

    @OnClick(R.id.image_download_data)
    void downloadData() {
        if (!TextUtils.isEmpty(SpUtil.getString(activity, SpUtil.TASK_ID))) {
            Toast.makeText(activity, "巡检未结束，不能下载数据", Toast.LENGTH_SHORT).show();
            return;
        }

        DbController.getInstance().deleteAllData();//删除后重新下载

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_data_synchronization_hint);
        dialog.setCancelable(false);
        dialog.show();

        NetClient.getInstance().getApi().getRegion(SpUtil.getString(getContext(), "token"))
                .subscribeOn(Schedulers.newThread())
                .flatMap(new Function<RegionResultBean, ObservableSource<EquipmentResultBean>>() {
                    @Override
                    public ObservableSource<EquipmentResultBean> apply(RegionResultBean regionResultBean) throws Exception {
                        Logger.t("region").e(new Gson().toJson(regionResultBean));
                        DbController.getInstance().saveRegionInfo(regionResultBean);
                        return NetClient.getInstance().getApi().getEquipment(SpUtil.getString(getContext(), "token"));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EquipmentResultBean>() {
                    @Override
                    public void accept(EquipmentResultBean bean) throws Exception {
                        DbController.getInstance().saveEquipment(bean, SpUtil.getString(activity, SpUtil.TASK_TYPE));//
                        dialog.findViewById(R.id.avi).setVisibility(View.GONE);
                        dialog.findViewById(R.id.dialog_btn_confirm).setVisibility(View.VISIBLE);
                        TextView message = dialog.findViewById(R.id.dialog_message);
                        message.setText("数据同步完成!");
                        if (activity.getFragmentList().get(activity.getFRAGMENT_OFFLINE_CHECK()) != null) {
                            activity.getFragmentList().get(activity.getFRAGMENT_OFFLINE_CHECK()).onResume();
                        }
                        dialog.findViewById(R.id.dialog_btn_confirm).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        dialog.findViewById(R.id.avi).setVisibility(View.GONE);
                        dialog.findViewById(R.id.dialog_btn_confirm).setVisibility(View.VISIBLE);
                        TextView message = dialog.findViewById(R.id.dialog_message);
                        Log.e("sync", throwable.getMessage());
                        message.setText("数据同步失败!");
                        dialog.findViewById(R.id.dialog_btn_confirm).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
    }

    @OnClick(R.id.image_upload_data)
    void uploadData() {

        if (TextUtils.isEmpty(SpUtil.getString(getContext(), "taskId"))) {
            Toast.makeText(getContext(), "未开始巡检", Toast.LENGTH_SHORT).show();
            return;
        }
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_data_synchronization_hint);
        dialog.setCancelable(false);
        dialog.show();

        List<EquipmentFaultBean> faultBeans = DbController.getInstance().queryAllEquipmentFault();
        List<FeedbackBean> feedbackBeans = DbController.getInstance().queryAllFeedback();

        updateFault(faultBeans);
        updateFeedback(feedbackBeans);

        List<EndTaskBean> endTaskBeanList = DbController.getInstance().queryAllEndTask(SpUtil.getString(getContext(), SpUtil.TASK_ID));
        if (endTaskBeanList.size() == 0) {
            Toast.makeText(activity, "巡检未开始", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }

        once = true;//成功以后只重新绘制一次
        for (final EndTaskBean endTaskBean : endTaskBeanList) {
            endTaskBean.setEquipmentType(SpUtil.getString(activity, SpUtil.TASK_TYPE));
            Logger.d(new Gson().toJson(endTaskBean));
            NetClient.getInstance().getApi().endTask(SpUtil.getString(getContext(), "token"), endTaskBean)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<EndTaskResultBean>() {
                        @Override
                        public void accept(EndTaskResultBean endTaskResultBean) throws Exception {
                            if (endTaskResultBean != null && TextUtils.equals(endTaskResultBean.getRespCode(), "0")) {
                                dialog.findViewById(R.id.avi).setVisibility(View.GONE);
                                dialog.findViewById(R.id.dialog_btn_confirm).setVisibility(View.VISIBLE);
                                TextView message = dialog.findViewById(R.id.dialog_message);
                                message.setText("数据同步完成!");
                                SpUtil.putString(activity, "taskId", "");
                                DbController.getInstance().deleteAllCheckEquipment();
//                                if (DbController.getInstance().queryNotCheckCount() <= 0) {//全部巡检结束时，为巡检的数量为0
//                                    SpUtil.putString(activity, SpUtil.TASK_ID, "");//清除taskId
//                                    DbController.getInstance().deleteEquipmentCheck();
//                                }

                                if (once) {
                                    once = false;
                                    activity.getFragmentList().get(activity.getFRAGMENT_OFFLINE_CHECK()).onResume();
                                    activity.hideTab();
                                    activity.switchFragment(activity.getFragmentList().get(activity.getFRAGMENT_OFFLINE_CHECK()));
                                }

                                dialog.findViewById(R.id.dialog_btn_confirm).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                            } else {
                                Logger.t("update").e(new Gson().toJson(endTaskResultBean));
                                dialog.findViewById(R.id.avi).setVisibility(View.GONE);
                                dialog.findViewById(R.id.dialog_btn_confirm).setVisibility(View.VISIBLE);
                                TextView message = dialog.findViewById(R.id.dialog_message);

                                if (!TextUtils.isEmpty(endTaskResultBean.getMessage())) {
                                    message.setText("上传失败\n" + endTaskResultBean.getMessage());
                                } else {
                                    message.setText("上传失败！");
                                }

                                dialog.findViewById(R.id.dialog_btn_confirm).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.dismiss();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                            dialog.findViewById(R.id.avi).setVisibility(View.GONE);
                            dialog.findViewById(R.id.dialog_btn_confirm).setVisibility(View.VISIBLE);
                            TextView message = dialog.findViewById(R.id.dialog_message);
                            Log.e("sync", throwable.getMessage());
                            message.setText("数据同步失败!");
                            dialog.findViewById(R.id.dialog_btn_confirm).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
        }

        //        EndTaskBean endTaskBean = DbController.getInstance().queryAllAttribute();
//        endTaskBean.setTaskId(SpUtil.getString(getContext(), SpUtil.TASK_ID));
//        endTaskBean.setCreateDate(DateUtil.INSTANCE.getDate());
//        endTaskBean.setCreateName(SpUtil.getString(activity, SpUtil.REAL_NAME));
//        Log.e("task", new Gson().toJson(endTaskBean));
//        Logger.d(new Gson().toJson(endTaskBean));

//        NetClient.getInstance().getApi().endTask(SpUtil.getString(getContext(), "token"), endTaskBean)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<EndTaskResultBean>() {
//                    @Override
//                    public void accept(EndTaskResultBean endTaskResultBean) throws Exception {
//                        if (endTaskResultBean != null && TextUtils.equals(endTaskResultBean.getRespCode(), "0")) {
//                            dialog.findViewById(R.id.avi).setVisibility(View.GONE);
//                            dialog.findViewById(R.id.button_confirm).setVisibility(View.VISIBLE);
//                            TextView message = dialog.findViewById(R.id.text_message);
//                            message.setText("数据同步完成!");
//                            SpUtil.putString(activity, "taskId", null);
//                            DbController.getInstance().deleteEquipmentCheck();
//                            activity.getFragmentList().get(activity.getFRAGMENT_OFFLINE_CHECK()).onResume();
//                            dialog.findViewById(R.id.button_confirm).setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.dismiss();
//                                }
//                            });
//                        } else {
//                            dialog.dismiss();
//                        }
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        throwable.printStackTrace();
//                        dialog.findViewById(R.id.avi).setVisibility(View.GONE);
//                        dialog.findViewById(R.id.button_confirm).setVisibility(View.VISIBLE);
//                        TextView message = dialog.findViewById(R.id.text_message);
//                        Log.e("sync", throwable.getMessage());
//                        message.setText("数据同步失败!");
//                        dialog.findViewById(R.id.button_confirm).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });
//                    }
//                });
//        dialog.dismiss();
        activity.getFragmentList().get(activity.getFRAGMENT_FEEDBACK()).onResume();
        activity.getFragmentList().get(activity.getFRAGMENT_FAULT()).onResume();
    }

    @OnClick(R.id.image_start_check)
    void startTask() {
        if (DbController.getInstance().queryAllEquipment() == null || DbController.getInstance().queryAllEquipment().size() == 0) {
            Toast.makeText(activity, "请下载数据", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(SpUtil.getString(activity, SpUtil.TASK_ID))) {
            Toast.makeText(activity, "巡检任务未完成，不能创建新任务", Toast.LENGTH_SHORT).show();
            return;
        }

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_data_synchronization_hint);
        dialog.setCancelable(false);
        dialog.show();

        final StartTaskBean startTaskBean = new StartTaskBean();
        startTaskBean.setUserName(SpUtil.getString(activity, SpUtil.REAL_NAME));
        startTaskBean.setUserId(SpUtil.getString(activity, SpUtil.USER_ID));
        NetClient.getInstance().getApi().createTask(SpUtil.getString(getActivity(), "token"), startTaskBean)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<StartTaskResultBean>() {
                    @Override
                    public void accept(StartTaskResultBean startTaskResultBean) throws Exception {
                        dialog.dismiss();
                        if (TextUtils.equals("0", startTaskResultBean.getRespCode())) {
                            Toast.makeText(App.getInstance(), "创建任务成功", Toast.LENGTH_SHORT).show();
                            DbController.getInstance().deleteEquipmentCheck();//创建任务成功的时候，取消所有巡检状态
                            SpUtil.putString(getContext(), "taskId", startTaskResultBean.getData().getId());
                            Log.e("task", new Gson().toJson(startTaskResultBean));
                            activity.showTab();
                        } else {
                            Logger.t("task").e(new Gson().toJson(startTaskBean));
                            Toast.makeText(App.getInstance(), "创建任务失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dialog.dismiss();
                        throwable.printStackTrace();
                        Logger.t("task").e(throwable.getMessage());
                        Toast.makeText(App.getInstance(), "创建任务失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateFault(List<EquipmentFaultBean> faultBeans) {
        for (final EquipmentFaultBean faultBean : faultBeans) {
            faultBean.setLocation(null);
            faultBean.setCreateDate(null);
            if (faultBean.getEquipmentFeedbackPictureList().size() <= 0) {
                NetClient.getInstance().getApi().equipmentFault(SpUtil.getString(getContext(), "token"), faultBean)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<EquipmentFaultResultBean>() {
                            @Override
                            public void accept(EquipmentFaultResultBean equipmentFaultResultBean) throws Exception {
                                if (TextUtils.equals(equipmentFaultResultBean.getRespCode(), "0")) {
                                    Log.e("update", new Gson().toJson(faultBean));
                                    DbController.getInstance().deleteEquipmentFault(faultBean);
                                    Log.e("update", "设备故障上传完成");
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e("update", "设备反馈上传失败:" + throwable.getMessage());
                                Log.e("update", new Gson().toJson(faultBean));
                            }
                        });
            } else {
                for (int i = 0; i < faultBean.getEquipmentFeedbackPictureList().size(); i++) {
                    pictureUrlBeanList.clear();
                    final EquipmentFaultBean.EquipmentFeedbackPictureListBean pictureBean = faultBean.getEquipmentFeedbackPictureList().get(i);
                    File file = new File(pictureBean.getPath());
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("uploadFile", file.getName(), requestFile);
                    NetClient.getInstance().getApi().getPictureUrl(SpUtil.getString(getContext(), "token"), body)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<PictureUrlBean>() {
                                @Override
                                public void accept(PictureUrlBean pictureUrlBean) throws Exception {
                                    pictureBean.setPicture(pictureUrlBean.getAttributes().getUrl());
                                    pictureBean.setPath(null);//必须删除，这不是服务器要得字段
                                    pictureUrlBeanList.add(pictureUrlBean);
                                    if (pictureUrlBeanList.size() >= faultBean.getEquipmentFeedbackPictureList().size()) {
                                        faultBean.setLocation(null);
                                        Message message = new Message();
                                        message.obj = faultBean;
                                        message.what = UPDATE_EQUIPMENT_FAULT;
                                        mHandler.sendMessage(message);
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Log.e("update", "设备故障上传图片:" + throwable.getMessage());
                                }
                            });
                }
            }
        }
    }

    private void updateFeedback(List<FeedbackBean> feedbackBeans) {
        for (final FeedbackBean bean : feedbackBeans) {
            bean.setLocation(null);
            bean.setCreateDate(null);
            if (bean.getLocalFeedbackPictureList().size() <= 0) {
                NetClient.getInstance().getApi().feedback(SpUtil.getString(getContext(), "token"), bean)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<FeedbackResultBean>() {
                            @Override
                            public void accept(FeedbackResultBean feedbackResultBean) throws Exception {
                                if (TextUtils.equals(feedbackResultBean.getRespCode(), "0")) {
                                    Log.e("update", new Gson().toJson(bean));
                                    DbController.getInstance().deleteFeedback(bean);
                                    Log.e("update", "现场反馈（无图）上传完成");
                                    Log.e("update", new Gson().toJson(feedbackResultBean));
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                throwable.printStackTrace();
                                Log.e("update", "现场反馈（无图）上传失败:" + throwable.getMessage());
                            }
                        });
            } else {
                for (int i = 0; i < bean.getLocalFeedbackPictureList().size(); i++) {
                    pictureUrlBeanList.clear();
                    final FeedbackBean.LocalFeedbackPictureListBean pictureBean = bean.getLocalFeedbackPictureList().get(i);
                    File file = new File(pictureBean.getPath());
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("uploadFile", file.getName(), requestFile);
                    NetClient.getInstance().getApi().getPictureUrl(SpUtil.getString(getContext(), "token"), body)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<PictureUrlBean>() {
                                @Override
                                public void accept(PictureUrlBean pictureUrlBean) throws Exception {
                                    pictureBean.setPicture(pictureUrlBean.getAttributes().getUrl());
                                    pictureBean.setPath(null);//必须删除，这不是服务器要得字段
                                    pictureUrlBeanList.add(pictureUrlBean);
                                    if (pictureUrlBeanList.size() >= bean.getLocalFeedbackPictureList().size()) {
                                        bean.setLocation(null);
                                        Message message = new Message();
                                        message.obj = bean;
                                        message.what = UPDATE_FEEDBACK;
                                        mHandler.sendMessage(message);
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Log.e("update", "现场反馈上传图片错误:" + throwable.getMessage());
                                }
                            });
                }
            }
        }
    }
}
