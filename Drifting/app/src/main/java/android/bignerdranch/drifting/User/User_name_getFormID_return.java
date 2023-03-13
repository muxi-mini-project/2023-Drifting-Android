package android.bignerdranch.drifting.User;

public class User_name_getFormID_return {
    private Long code;
    private String message;
    private dataID data;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User_name_getFormID_return.dataID getData() {
        return data;
    }

    public void setData(User_name_getFormID_return.dataID data) {
        this.data = data;
    }

    public class dataID{
        private String name;
        private long studentID;
        private String avatar;
        private String sex;
        private String selfWord;

        public long getStudentID() {
            return studentID;
        }

        public void setStudentID(long studentID) {
            this.studentID = studentID;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getSelfWord() {
            return selfWord;
        }

        public void setSelfWord(String selfWord) {
            this.selfWord = selfWord;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

    }
}
