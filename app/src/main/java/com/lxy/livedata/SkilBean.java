package com.lxy.livedata;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author a
 * @date 2018/1/4
 * @Entity注解 将其标识为数据库的一个表
 */

@Entity
public class SkilBean implements Serializable{

    public boolean error;
    public List<ResultsBean> results;

    public static class ResultsBean {
        /**
         * _id : 5a07b7fe421aa90fe7253624
         * createdAt : 2017-11-12T10:54:54.391Z
         * desc : 应用模块化和懒加载在 Instagram 中的实现
         * publishedAt : 2018-01-04T13:45:57.211Z
         * source : chrome
         * type : Android
         * url : https://github.com/Instagram/ig-lazy-module-loader
         * used : true
         * who : vincgao
         */

        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "_id='" + _id + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", desc='" + desc + '\'' +
                    ", publishedAt='" + publishedAt + '\'' +
                    ", source='" + source + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", used=" + used +
                    ", who='" + who + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SkilBean{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
