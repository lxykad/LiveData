package com.lxy.livedata;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.lxy.livedata.ui.entity.SkilEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author a
 * @date 2018/1/4
 * @Entity注解 将其标识为数据库的一个表
 */

public class SkilBean implements Serializable {

    public boolean error;
    public List<SkilEntity> results;
}
