package android.bignerdranch.drifting.Drawing;

public class Image_create_message {
    String cover;
    String kind;
    String name;
    Integer number;

    public Image_create_message(String cover,String kind,String name,Integer number){
        this.cover = cover;
        this.kind = kind;
        this.name = name;
        this.number = number;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
