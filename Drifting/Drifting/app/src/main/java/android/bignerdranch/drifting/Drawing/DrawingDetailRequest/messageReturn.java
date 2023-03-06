package android.bignerdranch.drifting.Drawing.DrawingDetailRequest;

import java.util.ArrayList;

public class messageReturn {
    private int code;
    private String message;
    private data data;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public messageReturn.data getData() {
        return data;
    }

    private static class data{
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
            private String picture;

            public long getFile_id() {
                return file_id;
            }

            public long getWriter_id() {
                return writer_id;
            }

            public String getPicture() {
                return picture;
            }

            @Override
            public String toString() {
                return "Contacts{" +
                        "file_id=" + file_id +
                        ", writer_id=" + writer_id +
                        ", picture='" + picture + '\'' +
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
