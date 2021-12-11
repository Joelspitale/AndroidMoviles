package com.example.myapplication.modelo;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "ItemMuseo")
public class ItemMuseo implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "title")
    private String itemTitle;
    private String roomName;
    private String itemIntro;
    private String itemMainContent;
    @ColumnInfo(name = "imagenId")
    private String itemMainPicture;
    private String imagenDetails;


    public ItemMuseo(long id, String itemTitle, String itemIntro, String itemMainContent, String itemMainPicture, String imagenDetails) {
        this.id = id;
        this.itemTitle = itemTitle;
        this.itemIntro = itemIntro;
        this.itemMainContent = itemMainContent;
        this.itemMainPicture = itemMainPicture;
        this.imagenDetails = imagenDetails;
    }

    public ItemMuseo() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemIntro() {
        return itemIntro;
    }

    public void setItemIntro(String itemIntro) {
        this.itemIntro = itemIntro;
    }

    public String getItemMainContent() {
        return itemMainContent;
    }

    public void setItemMainContent(String itemMainContent) {
        this.itemMainContent = itemMainContent;
    }

    public String getItemMainPicture() {
        return itemMainPicture;
    }

    public void setItemMainPicture(String itemMainPicture) {
        this.itemMainPicture = itemMainPicture;
    }

    public String getImagenDetails() {
        return imagenDetails;
    }

    public void setImagenDetails(String imagenDetails) {
        this.imagenDetails = imagenDetails;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
