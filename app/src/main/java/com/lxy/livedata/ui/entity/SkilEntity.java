package com.lxy.livedata.ui.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author a
 * @date 2018/1/25
 * 不指定表明 默认类名作为表名
 */

@Entity(tableName = "android")
public class SkilEntity {

    @PrimaryKey(autoGenerate = true)
    public int skillId;

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
        return "SkilEntity{" +
                "skillId=" + skillId +
                ", _id='" + _id + '\'' +
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
