package com.example.gystr.net;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 六掌大人 on 2018/6/12.
 */

public class RankingListBean {

    /**
     * success : true
     * data : {"rank":[],"list":[{"c":547,"s":10374,"d":77,"headImg":"http://cdn.suipaohealthy.com/1526865640666.png","user_id":"7ebf0819d26a4b61aef709583c3414ed","nickname":"小柚子","@curRank := 0":0,"rank":1,"m":7295},{"c":431,"s":5319,"d":30,"headImg":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKqcZd7nXibo5IcytHGPeJJAwIG9agK63CJ3cYq27iaZSF4AexmmG32w9dxVI4O4h6oEfoLa4yh8vxQ/0","user_id":"8a156ca9a922488f9838ec3805f7955e","nickname":"断线的风筝","@curRank := 0":0,"rank":2,"m":5749},{"c":497,"s":7330,"d":45,"user_id":"eec84f14828e4ec989bf19413707dcfa","nickname":"淳安一姐","@curRank := 0":0,"rank":3,"m":5538},{"c":385,"s":7225,"d":47,"user_id":"9fd9c3983a3045099115621bcc3b3e54","@curRank := 0":0,"rank":4,"m":5131},{"c":385,"s":6037,"d":38,"user_id":"80cbc2ee51f646ba9e405c570959490b","@curRank := 0":0,"rank":5,"m":5130},{"c":340,"s":5831,"d":39,"user_id":"f21cca1300214209aa97c28d2eae6eb1","@curRank := 0":0,"rank":6,"m":4535},{"c":338,"s":7371,"d":50,"user_id":"347ede6b949d462981d0fe2ee4a9e09d","@curRank := 0":0,"rank":7,"m":4516},{"c":325,"s":7205,"d":59,"headImg":"http://cdn.suipaohealthy.com/1528153722575.png","user_id":"911ce30361df452ab353c3e1dd45ad28","nickname":"随跑科技","@curRank := 0":0,"rank":8,"m":4335},{"c":302,"s":5433,"d":30,"headImg":"https://tfs.alipayobjects.com/images/partner/T1BjJeXeXfXXXXXXXX","user_id":"3e88c6429d904f1f8209196f6ece90ad","nickname":"永乐","@curRank := 0":0,"rank":9,"m":4032},{"c":295,"s":5771,"d":35,"user_id":"0e2eb9ba984a40fe883ed28ebdbe9792","@curRank := 0":0,"rank":10,"m":3929}]}
     * code : 200
     * onlyData : false
     */

    private boolean success;
    private DataBean data;
    private int code;
    private boolean onlyData;

    public static RankingListBean objectFromData(String str) {

        return new Gson().fromJson(str, RankingListBean.class);
    }

    public static List<RankingListBean> arrayRankingListBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<RankingListBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        private List<ListBean> rank;
        private List<ListBean> list;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public List<ListBean> getRank() {
            return rank;
        }

        public void setRank(List<ListBean> rank) {
            this.rank = rank;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * c : 547
             * s : 10374
             * d : 77
             * headImg : http://cdn.suipaohealthy.com/1526865640666.png
             * user_id : 7ebf0819d26a4b61aef709583c3414ed
             * nickname : 小柚子
             * @curRank := 0 : 0
             * rank : 1
             * m : 7295
             */

            private int c;
            private int s;
            private int d;
            private String headImg;
            private String user_id;
            private String nickname;
            @SerializedName("@curRank := 0")
            private int _$CurRank0108; // FIXME check this code
            private int rank;
            private int m;

            public static ListBean objectFromData(String str) {

                return new Gson().fromJson(str, ListBean.class);
            }

            public static List<ListBean> arrayListBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<ListBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public int getC() {
                return c;
            }

            public void setC(int c) {
                this.c = c;
            }

            public int getS() {
                return s;
            }

            public void setS(int s) {
                this.s = s;
            }

            public int getD() {
                return d;
            }

            public void setD(int d) {
                this.d = d;
            }

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int get_$CurRank0108() {
                return _$CurRank0108;
            }

            public void set_$CurRank0108(int _$CurRank0108) {
                this._$CurRank0108 = _$CurRank0108;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public int getM() {
                return m;
            }

            public void setM(int m) {
                this.m = m;
            }
        }
    }
}
