package android.bignerdranch.drifting.Inviting;

public class Inviting_return_of_random {
    private Long code;
    private String message;
    private data  data;

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

    public Inviting_return_of_random.data getData() {
        return data;
    }

    public void setData(Inviting_return_of_random.data data) {
        this.data = data;
    }

    public class data{

        private Long ID;  //
        private String CreatedAt;  //
        private String UpdateAt;
        private String DeleteAt;
        private String name;  //
        private String cover;  //
        private Integer OwnerID;
        private int number; //
        private int writer_number;//
        private int kind;  //
        private String theme;//


        public Long getID() {
            return ID;
        }

        public void setID(Long ID) {
            this.ID = ID;
        }

        public String getCreatedAt() {
            return CreatedAt;
        }

        public void setCreatedAt(String createdAt) {
            CreatedAt = createdAt;
        }

        public String getUpdateAt() {
            return UpdateAt;
        }

        public void setUpdateAt(String updateAt) {
            UpdateAt = updateAt;
        }

        public String getDeleteAt() {
            return DeleteAt;
        }

        public void setDeleteAt(String deleteAt) {
            DeleteAt = deleteAt;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public Integer getOwnerID() {
            return OwnerID;
        }

        public void setOwnerID(Integer ownerID) {
            OwnerID = ownerID;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getWriter_number() {
            return writer_number;
        }

        public void setWriter_number(int writer_number) {
            this.writer_number = writer_number;
        }

        public int getKind() {
            return kind;
        }

        public void setKind(int kind) {
            this.kind = kind;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String toString(){
            return  "ID:"+ID+"\nCreateAt"+CreatedAt+"\nname:"+name+"\ncover"+cover;

        }
    }

}
