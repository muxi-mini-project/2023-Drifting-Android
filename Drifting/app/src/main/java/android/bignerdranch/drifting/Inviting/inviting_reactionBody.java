package android.bignerdranch.drifting.Inviting;

public class inviting_reactionBody {
    private long file_id;
    private String file_kind;
    private long friend_id;
    private long host_id;

    public long getFile_id() {
        return file_id;
    }

    public long getHost_id() {
        return host_id;
    }

    public String getFile_kind() {
        return file_kind;
    }

    public long getFriend_id() {
        return friend_id;
    }

    public inviting_reactionBody(long file_id, String file_kind, long friend_id, long host_id) {
        this.file_id = file_id;
        this.file_kind = file_kind;
        this.friend_id = friend_id;
        this.host_id = host_id;
    }
}
