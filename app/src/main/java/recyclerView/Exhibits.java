package recyclerView;


import java.io.Serializable;
//tengo que hacerlo seriaalizable para trasportar este objeto de un fragments a otro
public class Exhibits implements Serializable {
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
    }

    /*
    public Exhibits(String title, String introduction, String content, int imagenId, int imagenDetails) {
        this.title = title;
        this.introduction = introduction;
        this.content = content;
        this.imagenId = imagenId;
        this.imagenDetails = imagenDetails;
    }

    public Exhibits(String title, String introduction, String content) {
        this.title = title;
        this.introduction = introduction;
        this.content = content;
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
