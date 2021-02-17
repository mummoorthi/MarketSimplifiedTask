package com.example.publicinfotask.database;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class DbData implements Serializable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "repoId")
    private Integer repoId;

    @ColumnInfo(name = "repoComment")
    private String repoComment;


    public Integer getRepoId() {
        return repoId;
    }

    public void setRepoId(Integer repoId) {
        this.repoId = repoId;
    }

    public String getRepoComment() {
        return repoComment;
    }

    public void setRepoComment(String repoComment) {
        this.repoComment = repoComment;
    }
}
