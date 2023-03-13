package android.bignerdranch.drifting.Novel;

public class create_return {
    private Long code;//请求状态码
    private long data;//token
    private Object message;//没啥用

    public Long getCode() { return code; }

    public long getData() { return data; }

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
