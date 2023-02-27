package android.bignerdranch.drifting;

/**
 * ApifoxModel
 *登录接口返回类
 * Response
 */
public class Login_return {
    private Long code;
    private Object data;
    private Object message;

    public Long getCode() { return code; }
    public void setCode(Long value) { this.code = value; }

    public Object getData() { return data; }
    public void setData(Object value) { this.data = value; }

    public Object getMessage() { return message; }
    public void setMessage(Object value) { this.message = value; }
}