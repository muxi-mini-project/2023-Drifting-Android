package android.bignerdranch.drifting.Main.checkingNew;

public class info_return {
    private int code;
    private String message;
    private data data;

    public info_return.data getData() {
        return data;
    }

    public class data{
        private String Addr;
        private String Version;

        public String getAddr() {
            return Addr;
        }

        public String getVersion() {
            return Version;
        }

        @Override
        public String toString() {
            return "data{" +
                    "Addr='" + Addr + '\'' +
                    ", Version='" + Version + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "info_return{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
