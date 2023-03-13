package android.bignerdranch.drifting.Book.ReturnAndReauest;

public class All_returnOf_random {
    private Long code;
    private String message;
    private DataRandom data;

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

    public DataRandom getData() {
        return data;
    }

    public void setData(DataRandom data) {
        this.data = data;
    }

    public class DataRandom{
        private Long ID;
        private String CreatedAt;
        private String UpdatedAt;
        private String DeletedAt;
        private String name;
        private String cover;
        private Long OwnerID;
        private int number;
        private int writer_number;
        private int kind;
        private String theme;

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

        public String getUpdatedAt() {
            return UpdatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            UpdatedAt = updatedAt;
        }

        public String getDeletedAt() {
            return DeletedAt;
        }

        public void setDeletedAt(String deletedAt) {
            DeletedAt = deletedAt;
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

        public Long getOwnerID() {
            return OwnerID;
        }

        public void setOwnerID(Long ownerID) {
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
    }
}
