package android.bignerdranch.drifting;

public class friendsInvitingItem {
    private String name;
    private long id;
    private boolean ifChosen;

    public friendsInvitingItem(String name, long id) {
        this.name = name;
        this.id = id;
        this.ifChosen = false;
    }

    public boolean getIfChosen(){
        return ifChosen;
    }

    public void setIfChosen(boolean ifChosen) {
        this.ifChosen = ifChosen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }
}
