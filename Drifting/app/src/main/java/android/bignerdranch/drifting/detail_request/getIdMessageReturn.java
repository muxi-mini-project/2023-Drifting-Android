package android.bignerdranch.drifting.detail_request;

public class getIdMessageReturn {
    private int code;
    private String message;
    private data data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public getIdMessageReturn.data getData() {
        return data;
    }

   public class data{
        private long studentID;
        private String name;
        private String sex;
        private String avatar;
        private String selfWord;

        public String getName() {
            return name;
        }

        public String getSex() {
            return sex;
        }

        public long getStudentID() {
            return studentID;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getSelfWord() {
            return selfWord;
        }

        @Override
        public String toString() {
            return "data{" +
                    "studentID=" + studentID +
                    ", name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", selfWord='" + selfWord + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "getIdMessageReturn{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
