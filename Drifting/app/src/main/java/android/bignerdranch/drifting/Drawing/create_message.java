package android.bignerdranch.drifting.Drawing;

public class create_message {
    String cover;
    int kind;
    String name;
    int number;
    String theme;

    public create_message(String cover, int kind, String name, int number, String theme) {
        this.cover = cover;
        this.kind = kind;
        this.name = name;
        this.number = number;
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

    public String getCover() {
        return cover;
    }

    public int getNumber() {
        return number;
    }

    public int getKind() {
        return kind;
    }

    public String getName() {
        return name;
    }
}
