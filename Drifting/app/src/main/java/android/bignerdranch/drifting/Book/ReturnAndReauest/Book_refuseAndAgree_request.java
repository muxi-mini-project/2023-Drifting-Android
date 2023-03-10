package android.bignerdranch.drifting.Book.ReturnAndReauest;

public class Book_refuseAndAgree_request {

        private long file_id;
        private String file_kind;
        private Long friend_id;
        private Long host_id;

        public Book_refuseAndAgree_request(long file_id, String file_kind, Long friend_id, Long host_id) {
                this.file_id = file_id;
                this.file_kind = file_kind;
                this.friend_id = friend_id;
                this.host_id = host_id;
        }

        public Book_refuseAndAgree_request(long file_id, String file_kind, Long host_id) {
                this.file_id = file_id;
                this.file_kind = file_kind;
                this.host_id = host_id;
        }

        public long getFile_id() { return file_id; }
        public void setFile_id(long value) { this.file_id = value; }

        public String getFileKind() { return file_kind; }
        public void setFileKind(String value) { this.file_kind = value; }

        public Long getFriend_id() { return friend_id; }
        public void setFriend_id(Long value) { this.friend_id = value; }

        public Long getHost_id() { return host_id; }
        public void setHost_id(Long value) { this.host_id = value; }

}
