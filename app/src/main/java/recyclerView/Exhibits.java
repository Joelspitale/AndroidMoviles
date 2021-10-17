package recyclerView;


import java.io.Serializable;
//tengo que hacerlo seriaalizable para trasportar este objeto de un fragments a otro
public class Exhibits implements Serializable {
    private String title;
    private String introduction;
    private String content;
    private int imagenId;
    private int imagenDetails;

    public Exhibits(String title, String introduction, String content, int imagenId) {
        this.title = title;
        this.introduction = introduction;
        this.content = content;
        this.imagenId = imagenId;
    }

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
    }
}
