package com.example.publicinfotask.database;

import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.example.publicinfotask.model.Example;

import java.io.Serializable;

import de.hdodenhof.circleimageview.CircleImageView;

@Entity
public class DbDatapag implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "roomId")
    private int roomId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "fullname")
    private String fullname;
    @ColumnInfo(name = "login")
    private String login;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "avatarurl")
    private String avatarurl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @BindingAdapter("android:load")
    public static void setItems(CircleImageView view, DbDatapag data) {
        Glide.with(view.getContext()).load(data.getAvatarurl()).into(view);
    }
}
