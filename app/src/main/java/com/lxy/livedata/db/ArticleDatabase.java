package com.lxy.livedata.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.lxy.livedata.ui.entity.SkilEntity;

/**
 * @author a
 * @date 2018/1/25
 */

@Database(entities = {SkilEntity.class}, version = 4, exportSchema = false)
public abstract class ArticleDatabase extends RoomDatabase {

    private static ArticleDatabase sInstance;

    public static ArticleDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(), ArticleDatabase.class, "android2.db")
                    .addMigrations(MIGRATION)
                    .build();
        }

        return sInstance;
    }

    // 升级数据库表
    static final Migration MIGRATION = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //添加一列
            database.execSQL("alter table 'android2' "
                    + " add column 'test2' TEXT ");
        }
    };

    public static void onDestory() {
        sInstance = null;
    }

    public abstract SkillEntityDao getSkillDao();
}
