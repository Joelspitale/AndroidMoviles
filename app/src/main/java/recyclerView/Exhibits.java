package recyclerView;


public class Exhibits {
    private String title;
    private String introduction;
    private String content;


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

}
