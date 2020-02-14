package com.yu.hu.libnetwork2.cache;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.yu.hu.common.utils.AppUtils;

/**
 * @author Hy
 * created on 2020/02/12 11:53
 * <p>
 * 用于缓存的数据库
 * <p>
 * exportSchema 生成json文件  默认为true。文件路径可在build.gradle中配置
 **/
@Database(entities = {Cache.class}, version = 1, exportSchema = true)
public abstract class CacheDatabase extends RoomDatabase {

    private static final CacheDatabase database;

    static {

        /*
           创建一个内存数据库
           这种数据库的数据只存在于内存中，也就是进程被杀之后，数据随时丢失
           Room.inMemoryDatabaseBuilder()

           Room.databaseBuilder  //创建一个普通的数据库
        */
        database = Room.databaseBuilder(AppUtils.getApplicationByReflect(), CacheDatabase.class, "ppjoke_cache")
                .allowMainThreadQueries()  //是否允许在主线程进行查询
                //.addCallback() //数据库创建和打开后的回调
                //.setQueryExecutor()  //设置查询的线程池
                //.openHelperFactory()
                //.setJournalMode()  //room的日志模式
                //.fallbackToDestructiveMigration()  //数据库升级一场之后的回滚
                //.fallbackToDestructiveMigrationFrom()  //数据库升级异常后根据指定版本进行回滚
                //.addMigrations(CacheDatabase.sMigration)
                .build();

    }

    public abstract CacheDao cacheDao();

    public static CacheDatabase get() {
        return database;
    }

    /*
    static Migration sMigration = new Migration(1, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("alter table teacher rename to student");
            database.execSQL("alter table teacher add columu teacher_age INTEGER NOT NULL default 0");
        }
    };
    */
}
