package android.bignerdranch.drifting;

import android.content.Context;

import java.util.*;

/**
 *
 */
public class Inviting_Lab {
    private int inviting_num0;
    private int inviting_num1;
    private int inviting_num2;
    private int inviting_num3;

    public int getInviting_num0() {
        return inviting_num0;
    }

    public int getInviting_num1() {
        return inviting_num1;
    }

    public int getInviting_num2() {
        return inviting_num2;
    }

    public int getInviting_num3() {
        return inviting_num3;
    }

    private static Inviting_Lab sInvitingLab;
    private List<Inviting_> mInvitings;
    public static Inviting_Lab get(Context context) {
        if (sInvitingLab == null) {
            sInvitingLab = new Inviting_Lab(context);
        }
        return sInvitingLab;
    }

    private Inviting_Lab(Context context) {
        mInvitings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            switch (i % 4){
                case 0:{
                    inviting_num0++;
                    break;
                }
                case 1:{
                    inviting_num1++;
                    break;
                }
                case 2:{
                    inviting_num2++;
                    break;
                }
                case 3:{
                    inviting_num3++;
                    break;
                }
            }
            Inviting_ inviting = new Inviting_("A", i % 4);
            mInvitings.add(inviting);
        }
    }

    public List<Inviting_> getInvitings() {
        return mInvitings;
    }
    public Inviting_ getInviting(UUID id) {
        for (Inviting_ inviting : mInvitings){
            if(inviting.getId().equals(id)){
                return inviting;
            }
        }
        return null;
    }
}
