package android.bignerdranch.drifting.Book.ReturnAndReauest;

public class Book_Just_join_request {
    private Integer driftingNoteID;
    private Integer studentID;

    public Book_Just_join_request(Integer driftingNoteID, Integer studentID) {
        this.driftingNoteID = driftingNoteID;
        this.studentID = studentID;
    }

    public Integer getDriftingNoteID() {
        return driftingNoteID;
    }

    public void setDriftingNoteID(Integer driftingNoteID) {
        this.driftingNoteID = driftingNoteID;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }
}
