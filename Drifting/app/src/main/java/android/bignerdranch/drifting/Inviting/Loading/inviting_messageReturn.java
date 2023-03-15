package android.bignerdranch.drifting.Inviting.Loading;

import java.io.Serializable;
import java.util.ArrayList;

public class inviting_messageReturn {
    private Long code;
    private String message;
    private ArrayList<data> data;


    public static class data implements Serializable {
        private long file_id;  //
        private String createdAt; //
        private String fileKind;
        private long owner_id; //
        private String cover; //
        private int kind; //
        private String theme ; //
        private int number; //
        private int writer_number;//
        private String name; //

        public data(long file_id, String createdAt, String fileKind, long honer_id, String cover, int kind, String theme, int number) {
            this.file_id = file_id;
            this.createdAt = createdAt;
            this.fileKind = fileKind;
            this.owner_id = honer_id;
            this.cover = cover;
            this.kind = kind;
            this.theme = theme;
            this.number = number;
        }

        public data() {
        }

        public data(long file_id, String cover, int kind) {
            this.file_id = file_id;
            this.cover = cover;
            this.kind = kind;
        }

        public data(long file_id, String createdAt, String cover, int kind, String theme, int number, int writer_number, String name,long owner_id) {
            this.file_id = file_id;
            this.createdAt = createdAt; //
            this.cover = cover; //
            this.kind = kind;
            this.theme = theme; //
            this.number = number; //
            this.writer_number = writer_number;
            this.name = name;
            this.owner_id = owner_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getWriter_number() {
            return writer_number;
        }

        public void setWriter_number(Integer writer_number) {
            this.writer_number = writer_number;
        }

        public long getFile_id() {
            return file_id;
        }

        public int getKind() {
            return kind;
        }

        public int getNumber() {
            return number;
        }

        public long getHoner_id() {
            return owner_id;
        }

        public String getCover() {
            return cover;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getFileKind() {
            return fileKind;
        }

        public String getTheme() {
            return theme;
        }

        @Override
        public String toString() {
            return "data{" +
                    "file_id=" + file_id +
                    ", createdAt='" + createdAt + '\'' +
                    ", fileKind='" + fileKind + '\'' +
                    ", honer_id=" + owner_id +
                    ", cover='" + cover + '\'' +
                    ", kind=" + kind +
                    ", theme='" + theme + '\'' +
                    ", number=" + number +"name"+name+"writer_number"+writer_number+
                    '}';
        }
    }

    public ArrayList<data> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public Long getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "user_drawing_request_return{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
