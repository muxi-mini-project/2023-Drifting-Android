package android.bignerdranch.drifting.Friends;

import java.util.ArrayList;
import java.util.List;

public class FriendsBeInvitedID {
    private static FriendsBeInvitedID sFriendsBeInvitedID;
    static List<Integer> StudentsID = new ArrayList<>();

    public static FriendsBeInvitedID getFriendsBeInvitedID() {

        if (sFriendsBeInvitedID == null){
            sFriendsBeInvitedID = new FriendsBeInvitedID();
        }
        return sFriendsBeInvitedID;
    }

    public static List<Integer> getStudentsID() {
        return StudentsID;
    }

    public static void setStudentsID(List<Integer> studentsID) {
        StudentsID = studentsID;
    }
}
