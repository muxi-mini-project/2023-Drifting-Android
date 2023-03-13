package android.bignerdranch.drifting.Book.ReturnAndReauest;

public class Book_Participate_request {
    private String  the_words;
    private Long file_id;
    private Long writer_id;

    public String getThe_word() {
        return the_words;
    }

    public void setThe_word(String the_word) {
        this.the_words = the_word;
    }

    public Long getFile_id() {
        return file_id;
    }

    public void setFile_id(Long file_id) {
        this.file_id = file_id;
    }

    public Long getWriter_id() {
        return writer_id;
    }

    public void setWriter_id(Long writer_id) {
        this.writer_id = writer_id;
    }
}
