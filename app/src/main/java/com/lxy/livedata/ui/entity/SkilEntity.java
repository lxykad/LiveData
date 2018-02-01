package com.lxy.livedata.ui.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author a
 * @date 2018/1/25
 * 不指定表明 默认类名作为表名
 */

@Entity(tableName = "android2")
public class SkilEntity {

    @PrimaryKey
    @NonNull
    public String _id;
    public String createdAt;
    public String desc;
    public String publishedAt;
    public String source;
    public String type;
    public String url;
    public boolean used;
    public String who;

    public String test;
    public String test2;
}
