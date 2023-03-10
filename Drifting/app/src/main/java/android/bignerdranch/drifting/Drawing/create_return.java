package android.bignerdranch.drifting.Drawing;

public class create_return {
    private Long code;//请求状态码
    private Object data;//token
    private Object message;//没啥用

    public Long getCode() { return code; }

    public String getData() { return "Bearer:"+data.toString(); }

    public Object getMessage() { return message; }

    @Override
    public String toString() {
        return "create_return{" +
                "code=" + code +
                ", data=" + data +
                ", message=" + message +
                '}';
    }
}
