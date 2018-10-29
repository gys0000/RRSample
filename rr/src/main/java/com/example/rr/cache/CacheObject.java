package com.example.rr.cache;

import java.util.Observable;

public class CacheObject {
    private long timestamp;
    private int period = -1;
    private Object data;

    /**
     * @param period -1 表示永不过期，大于0表示过期的时间，单位分钟
     * @param data
     */
    public CacheObject(int period, Object data) {
        this.period = period;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    /**
     * 判断有没有过期
     *
     * @return
     */
    public boolean isValid() {
        //两种判断方式，一是提前设置，二是当时间小于设置失效时间
        if (period == -1 || System.currentTimeMillis() < (timestamp + period * 60000)) {
            return true;
        }
        return false;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
