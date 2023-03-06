package android.bignerdranch.drifting.Inviting.Loading;

import java.util.ArrayList;

public class inviting_messageReturn {
    private Integer code;
    private String message;
    private ArrayList<data> data;


    public static class data{
        private long file_id;
        private String createdAt;
        private String fileKind;
        private long honer_id;
        private String cover;
        private int kind;
        private String theme;
        private int number;

        public data(long file_id, String createdAt, String fileKind, long honer_id, String cover, int kind, String theme, int number) {
            this.file_id = file_id;
            this.createdAt = createdAt;
            this.fileKind = fileKind;
            this.honer_id = honer_id;
            this.cover = cover;
            this.kind = kind;
            this.theme = theme;
            this.number = number;
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
            return honer_id;
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
                    ", honer_id=" + honer_id +
                    ", cover='" + cover + '\'' +
                    ", kind=" + kind +
                    ", theme='" + theme + '\'' +
                    ", number=" + number +
                    '}';
        }
    }

    public ArrayList<data> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
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
