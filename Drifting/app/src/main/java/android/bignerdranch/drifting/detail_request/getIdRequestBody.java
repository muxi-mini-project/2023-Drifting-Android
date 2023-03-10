package android.bignerdranch.drifting.detail_request;

public class getIdRequestBody {
    private long studentID;

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public getIdRequestBody(long studentID) {
        this.studentID = studentID;
    }
}
