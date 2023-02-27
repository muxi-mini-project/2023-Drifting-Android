package android.bignerdranch.drifting;

import java.util.UUID;

/**
 *
 */
public class Inviting_ {
    private UUID mId;
    private String inviter;
    private int inviting_type;//0-book,1-camera,2-drawing,3-novel
    public Inviting_(String inviter, int inviting_type){
        mId = UUID.randomUUID();
        this.inviter = inviter;
        this.inviting_type = inviting_type;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public void setInviting_type(int inviting_type) {
        this.inviting_type = inviting_type;
    }

    public String getInviter() {
        return inviter;
    }

    public int getInviting_type() {
        return inviting_type;
    }

    public UUID getId() {
        return mId;
    }
}
