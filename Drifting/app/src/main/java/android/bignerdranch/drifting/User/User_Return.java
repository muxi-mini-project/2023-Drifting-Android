package android.bignerdranch.drifting.User;

/**
 * 通过token请求用户信息所得到的数据
 */
public class User_return {
        private String avatar;
        private String name;
        private String selfWord;
        private String sex;

        public String getAvatar() { return avatar; }
        public void setAvatar(String value) { this.avatar = value; }

        public String getName() { return name; }
        public void setName(String value) { this.name = value; }

        public String getSelfWord() { return selfWord; }
        public void setSelfWord(String value) { this.selfWord = value; }

        public String getSex() { return sex; }
        public void setSex(String value) { this.sex = value; }
}
