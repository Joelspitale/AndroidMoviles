package com.example.myapplication.modelo;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "Exhibits")
public class Exhibits implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "title")
    private String title;
    private String roomName;
    private String content;
    @ColumnInfo(name = "imagenId")
    private String imagenId;
    private String imagenDetails;


    public Exhibits(long id, String title, String roomName, String content, String imagenId, String imagenDetails) {
        this.id = id;
        this.title = title;
        this.roomName = roomName;
        this.content = content;
        this.imagenId = imagenId;
        this.imagenDetails = imagenDetails;
    }

    public Exhibits() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagenId() {
        return imagenId;
    }

    public void setImagenId(String imagenId) {
        this.imagenId = imagenId;
    }

    public String getImagenDetails() {
        return imagenDetails;
    }

    public void setImagenDetails(String imagenDetails) {
        this.imagenDetails = imagenDetails;
    }


}
