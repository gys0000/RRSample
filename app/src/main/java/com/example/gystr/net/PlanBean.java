package com.example.gystr.net;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.gystr.BR;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 计划主页面的全部计划列表和已参加计划
 */
public class PlanBean extends BaseObservable{

    /**
     * success : true
     * data : [{"id":"1001","planName":"计划一","planCover":"http://cdn.suipaohealthy.com/c46c72b49004414abead91b5fbe11fec.png","planImg":"http://cdn.suipaohealthy.com/430f7a9f589b43b68e5987183ff7c706.png","planSummary":"1","joinNum":56,"clicks":17,"planCoverVideo":"1","status":1,"remark":"1","sort":1,"joinStatus":2},{"id":"1002","planName":"计划二","planCover":"http://cdn.suipaohealthy.com/8f19a6b2402a4ea99cd05d18a8381d35.png","planImg":"http://cdn.suipaohealthy.com/5debb297ccb54cc3a67db2a9e9c7ec86.jpg","planSummary":"2","joinNum":38,"clicks":12,"planCoverVideo":"2","status":1,"remark":"1","sort":2,"joinStatus":2},{"id":"1003","planName":"计划三","planCover":"http://cdn.suipaohealthy.com/ec8dfd82edea4e419386c5cdcef6ff2c.png","planImg":"http://cdn.suipaohealthy.com/f175ef393d0c4eb19887c255e98edb39.png","planSummary":"3","joinNum":6848,"clicks":6849,"planCoverVideo":"3","status":1,"remark":"1","sort":3,"joinStatus":2}]
     * code : 200
     * onlyData : false
     */

    private boolean success;
    private int code;
    private boolean onlyData;
    private List<DataBean> data;

//    public static PlanBean objectFromData(String str) {
//
//        return new Gson().fromJson(str, PlanBean.class);
//    }
//
//    public static List<PlanBean> arrayBaseMoreBeanFromData(String str) {
//
//        Type listType = new TypeToken<ArrayList<PlanBean>>() {
//        }.getType();
//
//        return new Gson().fromJson(str, listType);
//    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isOnlyData() {
        return onlyData;
    }

    public void setOnlyData(boolean onlyData) {
        this.onlyData = onlyData;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean extends BaseObservable{
        /**
         * id : 1001
         * planName : 计划一
         * planCover : http://cdn.suipaohealthy.com/c46c72b49004414abead91b5fbe11fec.png
         * planImg : http://cdn.suipaohealthy.com/430f7a9f589b43b68e5987183ff7c706.png
         * planSummary : 1
         * joinNum : 56
         * clicks : 17
         * planCoverVideo : 1
         * status : 1
         * remark : 1
         * sort : 1
         * joinStatus : 2
         */

        private String id;//计划ID
        private String planName;//计划名称
        private String planCover;//计划封面图片
        private String planImg;//内容图片
        private String planSummary;//计划简介
        private int joinNum;//参加人数
        private int clicks;//点击数
        private String planCoverVideo;//计划封面视频
        private int status;//状态
        private String remark;//备注
        private int sort;//排序
        private int joinStatus;//1.未参加  2.已参加
        private int userPlanStatus;//用户完成总计划状态（0.未完成/再次参与  1.当前  2.已完成）
        private int currentNum;//当前完成天数
        private int totalNum;//总体计划数
        private String create_date;
        private String create_by;
        private String update_date;
        private String update_by;
        private String del_flag;

//        public static DataBean objectFromData(String str) {
//
//            return new Gson().fromJson(str, DataBean.class);
//        }
//
//        public static List<DataBean> arrayBaseMoreBeanFromData(String str) {
//
//            Type listType = new TypeToken<ArrayList<DataBean>>() {
//            }.getType();
//
//            return new Gson().fromJson(str, listType);
//        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Bindable
        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
            notifyPropertyChanged(BR.planName);
        }

        public String getPlanCover() {
            return planCover;
        }

        public void setPlanCover(String planCover) {
            this.planCover = planCover;
        }

        public String getPlanImg() {
            return planImg;
        }

        public void setPlanImg(String planImg) {
            this.planImg = planImg;
        }

        public String getPlanSummary() {
            return planSummary;
        }

        public void setPlanSummary(String planSummary) {
            this.planSummary = planSummary;
        }

        public int getJoinNum() {
            return joinNum;
        }

        public void setJoinNum(int joinNum) {
            this.joinNum = joinNum;
        }

        public int getClicks() {
            return clicks;
        }

        public void setClicks(int clicks) {
            this.clicks = clicks;
        }

        public String getPlanCoverVideo() {
            return planCoverVideo;
        }

        public void setPlanCoverVideo(String planCoverVideo) {
            this.planCoverVideo = planCoverVideo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getJoinStatus() {
            return joinStatus;
        }

        public void setJoinStatus(int joinStatus) {
            this.joinStatus = joinStatus;
        }

        public int getUserPlanStatus() {
            return userPlanStatus;
        }

        public void setUserPlanStatus(int userPlanStatus) {
            this.userPlanStatus = userPlanStatus;
        }

        public int getCurrentNum() {
            return currentNum;
        }

        public void setCurrentNum(int currentNum) {
            this.currentNum = currentNum;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getCreate_by() {
            return create_by;
        }

        public void setCreate_by(String create_by) {
            this.create_by = create_by;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }

        public String getUpdate_by() {
            return update_by;
        }

        public void setUpdate_by(String update_by) {
            this.update_by = update_by;
        }

        public String getDel_flag() {
            return del_flag;
        }

        public void setDel_flag(String del_flag) {
            this.del_flag = del_flag;
        }
    }
}
