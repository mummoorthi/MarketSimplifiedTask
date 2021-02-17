package com.example.publicinfotask.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DbQueries {

    @Query("SELECT * FROM dbdata WHERE repoId IN(:id)")
    List<DbData> getAll(int id);

    @Query("SELECT * FROM dbdatapag LIMIT 10 OFFSET :count")
    List<DbDatapag> getAllRepos(int count);

    @Insert
    void insert(DbData task);

    @Insert
    void insetRepo(DbDatapag task);

    @Delete
    void delete(DbData task);

    @Update
    void update(DbData task);



}