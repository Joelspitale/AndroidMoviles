package com.example.myapplication.modelo;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
//tengo que hacerlo seriaalizable para trasportar este objeto de un fragments a otro
@Entity(tableName = "Exhibits")
public class Exhibits implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "introduction")
    private String introduction;
    private String content;
    @ColumnInfo(name = "imagenId")
    private String imagenId;
    private String imagenDetails;

    public Exhibits(long id, String title, String introduction, String content, String imagenId, String imagenDetails) {
        this.id = id;
        this.title = title;
        this.introduction = introduction;
        this.content = content;
        this.imagenId = imagenId;
        this.imagenDetails = imagenDetails;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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
    /*
    private int title;
    private int introduction;
    private int content;
    private int imagenId;
    private int imagenDetails;

    public Exhibits(int title, int introduction, int content, int imagenId, int imagenDetails) {
        this.title = title;
        this.introduction = introduction;
        this.content = content;
        this.imagenId = imagenId;
        this.imagenDetails = imagenDetails;
    }

    public Exhibits(int title, int introduction, int content) {
        this.title = title;
        this.introduction = introduction;
        this.content = content;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getIntroduction() {
        return introduction;
    }

    public void setIntroduction(int introduction) {
        this.introduction = introduction;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public int getImagenId() {
        return imagenId;
    }

    public void setImagenId(int imagenId) {
        this.imagenId = imagenId;
    }

    public int getImagenDetails() {
        return imagenDetails;
    }

    public void setImagenDetails(int imagenDetails) {
        this.imagenDetails = imagenDetails;
    }*/

}
