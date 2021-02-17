package com.example.publicinfotask.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DbData.class,DbDatapag.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DbQueries taskDao();

}
