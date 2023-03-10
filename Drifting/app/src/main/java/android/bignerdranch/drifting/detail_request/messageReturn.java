package android.bignerdranch.drifting.detail_request;

import java.util.ArrayList;

public class messageReturn {
    private int code;
    private String message;
    private data data;
    private String zhonglei;
    private String inviter;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public messageReturn.data getData() {
        return data;
    }

    public void setZhonglei(String zhonglei) {
        this.zhonglei = zhonglei;
    }

    public String getZhonglei() {
        return zhonglei;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public static class data{
        private String Name;
        private long OwnerID;
        private ArrayList<Contacts> Contacts;

        public String getName() {
            return Name;
        }

        public long getOwnerID() {
            return OwnerID;
        }

        public ArrayList<messageReturn.data.Contacts> getContacts() {
            return Contacts;
        }

        public static class Contacts{
            private long file_id;
            private long writer_id;
            private String the_words;

            public long getFile_id() {
                return file_id;
            }

            public long getWriter_id() {
                return writer_id;
            }

            public String getThe_words() {
                return the_words;
            }

            @Override
            public String toString() {
                return "Contacts{" +
                        "file_id=" + file_id +
                        ", writer_id=" + writer_id +
                        ", the_words='" + the_words + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "data{" +
                    "Name='" + Name + '\'' +
                    ", OwnerID=" + OwnerID +
                    ", Contacts=" + Contacts +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "messageReturn{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
