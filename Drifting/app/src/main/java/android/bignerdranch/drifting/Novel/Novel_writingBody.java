package android.bignerdranch.drifting.Novel;

public class Novel_writingBody {
    private long file_id;
    private String the_words;
    private long writer_id;

    public Novel_writingBody(long file_id, String the_words, long writer_id) {
        this.file_id = file_id;
        this.the_words = the_words;
        this.writer_id = writer_id;
    }
}
