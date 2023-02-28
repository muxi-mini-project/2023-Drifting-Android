package android.bignerdranch.drifting;

/**
 * ApifoxModel
 *登录接口返回类
 * Response
 */
public class Login_return {
    private Long code;//请求状态码
    private Object data;//token
    private Object message;//没啥用

    public Long getCode() { return code; }
    public void setCode(Long value) { this.code = value; }

    public String getData() { return "Bearer:"+data.toString(); }
    public void setData(Object value) { this.data = value; }

    public Object getMessage() { return message; }
    public void setMessage(Object value) { this.message = value; }
}