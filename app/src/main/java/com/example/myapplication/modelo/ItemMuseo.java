package com.example.myapplication.modelo;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import java.util.List;

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
    private String itemYoutube;
    private String itemTags;
    private String itemAudioLink;
    private double itemLat;
    private double itemLong;
    @Ignore
    private List<ItemGallery> itemGallery;
    @Ignore
    private List <ItemExternalLink> itemExternalLinks;

    public ItemMuseo(long id, String itemTitle, String itemIntro, String itemMainContent, String itemMainPicture,
                     String imagenDetails, String itemYoutube, String itemTags, String itemAudioLink, double itemLat, double itemLong) {
        this.id = id;
        this.itemTitle = itemTitle;
        this.itemIntro = itemIntro;
        this.itemMainContent = itemMainContent;
        this.itemMainPicture = itemMainPicture;
        this.imagenDetails = imagenDetails;
        this.itemYoutube = itemYoutube;
        this.itemTags = itemTags;
        this.itemAudioLink = itemAudioLink;
        this.itemLat = itemLat;
        this.itemLong = itemLong;
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

    public String getItemYoutube() {
        return itemYoutube;
    }

    public void setItemYoutube(String itemYoutube) {
        this.itemYoutube = itemYoutube;
    }

    public String getItemTags() {
        return itemTags;
    }

    public void setItemTags(String itemTags) {
        this.itemTags = itemTags;
    }

    public String getItemAudioLink() {
        return itemAudioLink;
    }

    public void setItemAudioLink(String itemAudioLink) {
        this.itemAudioLink = itemAudioLink;
    }

    public double getItemLat() {
        return itemLat;
    }

    public void setItemLat(double itemLat) {
        this.itemLat = itemLat;
    }

    public double getItemLong() {
        return itemLong;
    }

    public void setItemLong(double itemLong) {
        this.itemLong = itemLong;
    }

    public List<ItemGallery> getItemGallery() {
        return itemGallery;
    }

    public void setItemGallery(List<ItemGallery> itemGallery) {
        this.itemGallery = itemGallery;
    }

    public List<ItemExternalLink> getItemExternalLinks() {
        return itemExternalLinks;
    }

    public void setItemExternalLinks(List<ItemExternalLink> itemExternalLinks) {
        this.itemExternalLinks = itemExternalLinks;
    }
}
