package com.lxy.livedata.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.lxy.livedata.ui.entity.SkilEntity;

/**
 * @author a
 * @date 2018/1/25
 */

@Database(entities = {SkilEntity.class}, version = 1, exportSchema = false)
public abstract class ArticleDatabase extends RoomDatabase {

    private static ArticleDatabase sInstance;

    public static ArticleDatabase getInstance(Context context){
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),ArticleDatabase.class,"android.db").build();
        }

        return sInstance;
    }

    public static void onDestory(){
        sInstance = null;
    }

    public abstract SkillEntityDao getSkillDao();
}
