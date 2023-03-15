package android.bignerdranch.drifting.Camera;

import java.util.ArrayList;
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
        private Integer id;

        public Integer getid() { return id; }
        public void setid(Integer value) { this.id = value; }
    }
    //返回类
   static public class Camera_return_mes_body{
        private Long code;
        private Object message;
        private Camera_return_mes_body2 data;

        public Long getCode() { return code; }
        public void setCode(Long value) { this.code = value; }

        public Camera_return_mes_body2 getData() { return data; }
        public void setData(Camera_return_mes_body2 value) { this.data = value; }

        public Object getMessage() { return message; }
        public void setMessage(Object value) { this.message = value; }
    }
   static public class Camera_return_mes_body2{
        private String Name;
        private Long OwnerID;
        private ArrayList<Camera_return_mes_body3> Contacts;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public Long getOwnerID() {
            return OwnerID;
        }

        public void setOwnerID(Long ownerID) {
            OwnerID = ownerID;
        }

        public ArrayList<Camera_return_mes_body3> getContacts() {
            return Contacts;
        }

        public void setContacts(ArrayList<Camera_return_mes_body3> contacts) {
            Contacts = contacts;
        }
    }
    static public class Camera_return_mes_body3{
      private Long file_id;
      private Long writer_id;
      private String the_words;

        public Long getFile_id() {
            return file_id;
        }

        public void setFile_id(Long file_id) {
            this.file_id = file_id;
        }

        public Long getWriter_id() {
            return writer_id;
        }

        public void setWriter_id(Long writer_id) {
            this.writer_id = writer_id;
        }

        public String getThe_words() {
            return the_words;
        }

        public void setThe_words(String the_words) {
            this.the_words = the_words;
        }
    }
    /**
     * 接受创作邀请
     */
    //上传类
   static public class Camera_accept_inviting{
        private long fileid;
        private String fileKind;

        public long getFileid() { return fileid; }
        public void setFileid(long value) { this.fileid = value; }

        public String getFileKind() { return fileKind; }
        public void setFileKind(String value) { this.fileKind = value; }
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
        private Long kind;
        private String name;
        private Long number;
        private String theme;

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getCover() { return cover; }
        public void setCover(String value) { this.cover = value; }
        public Long getKind() { return kind; }
        public void setKind(Long value) { this.kind = value; }

        public String getName() { return name; }
        public void setName(String value) { this.name = value; }

        public Comparable<Long> getNumber() { return number; }
        public void setNumber(Long value) { this.number = value; }
    }
    //返回类
   static public class Camera_return_create{
        private Long code;
        private Long data;
        private Object message;

        public Long getCode() { return code; }
        public void setCode(Long value) { this.code = value; }

        public Long getData() { return data; }
        public void setData(Long value) { this.data = value; }

        public Object getMessage() { return message; }
        public void setMessage(Object value) { this.message = value; }
    }
    /**
     *获取漂流项目
     */
    //返回类
   static public class Items_return_usermessage {
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
        private String CreatedAt;

       public String getCreatedAt() {
           return CreatedAt;
       }

       public void setCreatedAt(String createdAt) {
           CreatedAt = createdAt;
       }

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
        private Long file_id;
        private String file_Kind;
        private Long friend_id;
        private Long host_id;

        public Long getFile_id() {
            return file_id;
        }

        public void setFile_id(Long file_id) {
            this.file_id = file_id;
        }

        public String getFile_Kind() {
            return file_Kind;
        }

        public void setFile_Kind(String file_Kind) {
            this.file_Kind = file_Kind;
        }

        public Long getFriend_id() {
            return friend_id;
        }

        public void setFriend_id(Long friend_id) {
            this.friend_id = friend_id;
        }

        public Long getHost_id() {
            return host_id;
        }

        public void setHost_id(Long host_id) {
            this.host_id = host_id;
        }
    }
    //返回类
   static public class Camera_return_inviting_friend{
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
        Long driftingPictureID;
        Long studentID;

        public Long getDriftingPictureID() {
            return driftingPictureID;
        }

        public void setDriftingPictureID(Long driftingPictureID) {
            this.driftingPictureID = driftingPictureID;
        }

        public Long getStudentID() {
            return studentID;
        }

        public void setStudentID(Long studentID) {
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

        public long getFileid() { return fileid; }
        public void setFileid(long value) { this.fileid = value; }

        public String getFileKind() { return fileKind; }
        public void setFileKind(String value) { this.fileKind = value; }

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

    /**
     * 删除项目
     */
    //上传
    static public class Delete_upload{
       private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
    //返回
    static public class Delete_return{
        private Long code;
        private Object data;
        private Object message;

        public Long getCode() {
            return code;
        }

        public void setCode(Long code) {
            this.code = code;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Object getMessage() {
            return message;
        }

        public void setMessage(Object message) {
            this.message = message;
        }
    }
}
