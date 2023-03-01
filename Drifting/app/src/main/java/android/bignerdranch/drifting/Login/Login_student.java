package android.bignerdranch.drifting.Login;
/**
 * 登录所用信息
 * 作为Body自定义类传入
 */
public class Login_student {
    Integer studentID;
    String passWord;

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public String getPassWord() {
        return passWord;
    }
}
