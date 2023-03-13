package android.bignerdranch.drifting.Inviting;

public class inviting_reactionReturn {
    private int code;
    private String message;
    private String data;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "inviting_reactionReturn{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
