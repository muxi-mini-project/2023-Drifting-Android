package android.bignerdranch.drifting.Friends.AddnewFriends;

public class AddnewFriendSend {
    private Integer adderID;
    private Integer targetID;

    public AddnewFriendSend(Integer adderID, Integer targetID) {
        this.adderID = adderID;
        this.targetID = targetID;
    }

    public AddnewFriendSend() {}

    public Integer getAdderID() {
        return adderID;
    }

    public void setAdderID(Integer adderID) {
        this.adderID = adderID;
    }

    public Integer getTargetID() {
        return targetID;
    }

    public void setTargetID(Integer targetID) {
        this.targetID = targetID;
    }
}
