package android.bignerdranch.drifting.Book.ReturnAndReauest;

public class Book_Participate_request {
    private String  text;
    private Integer file_id;
    private Integer writer_id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getFile_id() {
        return file_id;
    }

    public void setFile_id(Integer file_id) {
        this.file_id = file_id;
    }

    public Integer getWriter_id() {
        return writer_id;
    }

    public void setWriter_id(Integer writer_id) {
        this.writer_id = writer_id;
    }
}
