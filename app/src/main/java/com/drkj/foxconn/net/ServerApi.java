package com.drkj.foxconn.net;

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
import com.drkj.foxconn.bean.UserBean;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ganlong on 2018/1/24.
 * Modify by VeronicaRen on 2018/2/27.
 * 修改提交巡检信息的接口地址
 * 增加图片url的请求接口
 */
public interface ServerApi {

    //登录
    @FormUrlEncoded
    @POST(ConstantUrl.API_TOKEN)
    Observable<Response<ResponseBody>> getToken(@Field("username") String username, @Field("password") String password);

    //获取巡检负责人
    @GET(ConstantUrl.API_TYPE_USER)
    Observable<UserBean> getTypeUser(@Header("X-AUTH-TOKEN") String token);

    //创建巡检任务
    @Headers("Content-Type: application/json")
    @POST(ConstantUrl.API_START_TASK)
    Observable<StartTaskResultBean> createTask(@Header("X-AUTH-TOKEN") String token, @Body StartTaskBean bean);

    //获取所有区域数据
    @GET(ConstantUrl.API_GET_REGION)
    Observable<RegionResultBean> getRegion(@Header("X-AUTH-TOKEN") String token);

    //获取所有设备信息
    @GET(ConstantUrl.API_GET_EQUIPMENT)
    Observable<EquipmentResultBean> getEquipment(@Header("X-AUTH-TOKEN") String token);

    //提交巡检设备信息（完成任务、上传数据）
    @Headers({"Content-Type: application/json", "Accept:  application/json"})
    @PUT(ConstantUrl.API_END_TASK + "/{id}")
    Observable<EndTaskResultBean> endTask(@Path("id") String id, @Header("X-AUTH-TOKEN") String token, @Body EndTaskBean bean);

    //提交巡检设备信息
    @Headers({"Content-Type: application/json", "Accept:  application/json"})
    @POST(ConstantUrl.API_END_TASK)
    Observable<EndTaskResultBean> endTask(@Header("X-AUTH-TOKEN") String token, @Body EndTaskBean bean);

    //提交现场反馈
    @Headers({"Content-Type: application/json", "Accept:  application/json"})
    @POST(ConstantUrl.API_FEED_BACK)
    Observable<FeedbackResultBean> feedback(@Header("X-AUTH-TOKEN") String token, @Body FeedbackBean bean);

    //提交设备故障
    @Headers({"Content-Type: application/json", "Accept:  application/json"})
    @POST(ConstantUrl.API_EQUIPMENT_FAULT)
    Observable<EquipmentFaultResultBean> equipmentFault(@Header("X-AUTH-TOKEN") String token, @Body EquipmentFaultBean bean);

    //上传图片，获取图片的url，然后将url放在反馈bean的picture属性中
//    @Headers({"Content-Type: application/json", "Accept:  application/json"})
    @Multipart
    @POST(ConstantUrl.API_PICTURE_URL)
    Observable<PictureUrlBean> getPictureUrl(@Header("X-AUTH-TOKEN") String token, @Part MultipartBody.Part file);
}
