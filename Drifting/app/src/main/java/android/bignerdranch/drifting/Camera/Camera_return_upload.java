package android.bignerdranch.drifting.Camera;

import java.util.List;

/**
 * 漂流照片网络请求返回、上传的几个类
 */
public class Camera_return_upload {
    /**
     * 获取漂流相机内容
     */
    //上传类
  static public class Camera_upload_mes_body{
        private Long id;

        public Long getid() { return id; }
        public void setid(Long value) { this.id = value; }
    }
    //返回类
   static public class Camera_return_mes_body{
        private Long code;
        private Object data;
        private Camera_return_mes_body2 message;

        public Long getCode() { return code; }
        public void setCode(Long value) { this.code = value; }

        public Object getData() { return data; }
        public void setData(Object value) { this.data = value; }

        public Camera_return_mes_body2 getMessage() { return message; }
        public void setMessage(Camera_return_mes_body2 value) { this.message = value; }
    }
   static public class Camera_return_mes_body2{
        private String Name;
        private Integer OwnerID;
        private List<String> Contacts;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public Integer getOwnerID() {
            return OwnerID;
        }

        public void setOwnerID(Integer ownerID) {
            OwnerID = ownerID;
        }

        public List<String> getContacts() {
            return Contacts;
        }

        public void setContacts(List<String> contacts) {
            Contacts = contacts;
        }
    }
    /**
     * 接受创作邀请
     */
    //上传类
   static public class Camera_accept_inviting{
        private long fileid;
        private String fileKind;
        private Long friendid;
        private Long hostid;

        public long getFileid() { return fileid; }
        public void setFileid(long value) { this.fileid = value; }

        public String getFileKind() { return fileKind; }
        public void setFileKind(String value) { this.fileKind = value; }

        public Long getFriendid() { return friendid; }
        public void setFriendid(Long value) { this.friendid = value; }

        public Long getHostid() { return hostid; }
        public void setHostid(Long value) { this.hostid = value; }
    }
    //返回类
   static public class Camera_accept_inviting_return{
        private Long code;
        private Object data;
        private Camera_accpet_inviting_return2 message;

        public Long getCode() { return code; }
        public void setCode(Long value) { this.code = value; }

        public Object getData() { return data; }
        public void setData(Object value) { this.data = value; }

        public Object getMessage() { return message; }
        public void setMessage(Camera_accpet_inviting_return2 value) { this.message = value; }
    }
   static public class Camera_accpet_inviting_return2{
        private Long Code;
        private Object Message;

        public Long getCode() {
            return Code;
        }

        public void setCode(Long code) {
            Code = code;
        }

        public Object getMessage() {
            return Message;
        }

        public void setMessage(Object message) {
            Message = message;
        }
    }

    /**
     * 创建漂流相机
     */
    //上传类
       static public class Camera_upload_create{
        private String cover;
        private Integer kind;
        private String name;
        private Integer number;
        private String theme;

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getCover() { return cover; }
        public void setCover(String value) { this.cover = value; }

        public Integer getKind() { return kind; }
        public void setKind(Integer value) { this.kind = value; }

        public String getName() { return name; }
        public void setName(String value) { this.name = value; }

        public Comparable<Integer> getNumber() { return number; }
        public void setNumber(Integer value) { this.number = value; }
    }
    //返回类
   static public class Camera_return_create{
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
    /**
     *获取用户创建的漂流相机
     */
    //返回类
   static public class Camera_return_usermessage{
       private Long code;
       private List<Camera_return_usermessage2> data;

        public Long getCode() {
            return code;
        }

        public void setCode(Long code) {
            this.code = code;
        }

        public List<Camera_return_usermessage2> getData() { return data; }
        public void setData(List<Camera_return_usermessage2> value) { this.data = value; }
    }
   static public class Camera_return_usermessage2 {
        private Long ID;
        private String name;
        private String cover;
        private Long OwnerID;
        private Long number;
        private Long kind;
        private String theme;
        private Long writer_number;

       public Long getWriter_number() {
           return writer_number;
       }

       public void setWriter_number(Long writer_number) {
           this.writer_number = writer_number;
       }

       public Long getID() {
           return ID;
       }

       public void setID(Long ID) {
           this.ID = ID;
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

       public Long getNumber() {
           return number;
       }

       public void setNumber(Long number) {
           this.number = number;
       }

       public Long getKind() {
           return kind;
       }

       public void setKind(Long kind) {
           this.kind = kind;
       }

       public String getTheme() {
           return theme;
       }

       public void setTheme(String theme) {
           this.theme = theme;
       }
   }
    /**
     *创作漂流相机
     */
    //返回类
   static public class Camera_return_make{
        private Long code;
        private Camera_return_make2 data;
        private Object message;

        public Long getCode() { return code; }
        public void setCode(Long value) { this.code = value; }

        public Object getData() { return data; }
        public void setData(Camera_return_make2 value) { this.data = value; }

        public Object getMessage() { return message; }
        public void setMessage(Object value) { this.message = value; }
    }
   static public class Camera_return_make2{
        private String Func;
        private Object Num;
        private List<String> Err;
    }

    /**
     * 获取邀请信息
     */
   static public class Camera_return_inviting{
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

    /**
     *邀请好友进行创作
     */
    //上传类
   static public class Camera_upload_inviting_friend{
        private long fileid;
        private String fileKind;
        private Long friendid;
        private Long hostid;

        public long getFileid() { return fileid; }
        public void setFileid(long value) { this.fileid = value; }

        public String getFileKind() { return fileKind; }
        public void setFileKind(String value) { this.fileKind = value; }

        public Long getFriendid() { return friendid; }
        public void setFriendid(Long value) { this.friendid = value; }

        public Long getHostid() { return hostid; }
        public void setHostid(Long value) { this.hostid = value; }
    }
    //返回类
   static public class Camera_return_inviting_friend{
        private Long code;
        private Object data;
        private Camera_return_inviting_friend2 message;

        public Long getCode() { return code; }
        public void setCode(Long value) { this.code = value; }

        public Object getData() { return data; }
        public void setData(Object value) { this.data = value; }

        public Object getMessage() { return message; }
        public void setMessage(Camera_return_inviting_friend2 value) { this.message = value; }
    }
   static public class Camera_return_inviting_friend2{
        private Long Code;
        private Object Message;

        public Long getCode() {
            return Code;
        }

        public void setCode(Long code) {
            Code = code;
        }

        public Object getMessage() {
            return Message;
        }

        public void setMessage(Object message) {
            Message = message;
        }
    }

    /**
     * 获取用户参加的漂流照片信息(不含自己创建的)
     */
    //返回类
   static public class Camera_return_join_mes{
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

    /**
     * 参加漂流相机创作（仅参加）
     */
    //上传类
   static public class Camera_upload_accept_random{
        Integer driftingPictureID;
        Integer studentID;

        public Integer getDriftingPictureID() {
            return driftingPictureID;
        }

        public void setDriftingPictureID(Integer driftingPictureID) {
            this.driftingPictureID = driftingPictureID;
        }

        public Integer getStudentID() {
            return studentID;
        }

        public void setStudentID(Integer studentID) {
            this.studentID = studentID;
        }
    }
    //返回类
   static public class Camera_return_accept_random{
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

    /**
     * 随机推荐漂流相机
     */
    //返回类
   static public class Camera_return_recommand_random{
        private Long code;
        private Object data;
        private Camera_return_recommand_random2 message;

        public Long getCode() { return code; }
        public void setCode(Long value) { this.code = value; }

        public Object getData() { return data; }
        public void setData(Object value) { this.data = value; }

        public Object getMessage() { return message; }
        public void setMessage(Camera_return_recommand_random2 value) { this.message = value; }
    }
   static public class Camera_return_recommand_random2{
        private Integer ID;
        private String CreatedAt;
        private String UpdatedAt;
        private String DeletedAt;
        private String name;
        private String contact;
        private String cover;
        private Integer OwnerID;
        private Integer number;
        private String kind;

        public Integer getID() {
            return ID;
        }

        public void setID(Integer ID) {
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

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
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

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }
    }

    /**
    * 拒绝邀请
    */
    //上传类
   static public class Camera_upload_refuse_inviting{
        private long fileid;
        private String fileKind;
        private Long friendid;
        private Long hostid;

        public long getFileid() { return fileid; }
        public void setFileid(long value) { this.fileid = value; }

        public String getFileKind() { return fileKind; }
        public void setFileKind(String value) { this.fileKind = value; }

        public Long getFriendid() { return friendid; }
        public void setFriendid(Long value) { this.friendid = value; }

        public Long getHostid() { return hostid; }
        public void setHostid(Long value) { this.hostid = value; }
    }
    //返回类
   static public class Camera_return_refuse_inviting{
        private Long code;
        private Object data;
        private Camera_return_refuse_inviting2 message;

        public Long getCode() { return code; }
        public void setCode(Long value) { this.code = value; }

        public Object getData() { return data; }
        public void setData(Object value) { this.data = value; }

        public Object getMessage() { return message; }
        public void setMessage(Camera_return_refuse_inviting2 value) { this.message = value; }
    }
   static public class Camera_return_refuse_inviting2{
        private Long Code;
        private Object Message;

        public Long getCode() {
            return Code;
        }

        public void setCode(Long code) {
            Code = code;
        }

        public Object getMessage() {
            return Message;
        }

        public void setMessage(Object message) {
            Message = message;
        }
    }
}
