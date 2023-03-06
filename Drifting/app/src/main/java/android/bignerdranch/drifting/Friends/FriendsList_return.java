package android.bignerdranch.drifting.Friends;

import java.util.List;

public class FriendsList_return {

   private Integer code;
   private String message;
   private List<Friends_Net> data;
   //带上N防止和前面的混淆


   public Integer getCode() {
      return code;
   }

   public void setCode(Integer code) {
      this.code = code;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public List<Friends_Net> getDataN() {
      return data;
   }

   public void setDataN(List<Friends_Net> data) {
      this.data = data;
   }

   public class Friends_Net{
      private String name;
      private String sex;
      private String avatar;
      private String selfWord;
      private Integer studentID;

      public Integer getStudentID() {
         return studentID;
      }

      public void setStudentID(Integer studentID) {
         this.studentID = studentID;
      }

      private Friends_Net mFriends_net;

      public Friends_Net(Friends_Net friends_net) {
         mFriends_net = friends_net;
      }

      public String getNameN() {
         return name;
      }

      public void setNameN(String name) {
         this.name = name;
      }

      public String getSex() {
         return sex;
      }

      public void setSex(String sex) {
         this.sex = sex;
      }

      public String getAvatar() {
         return avatar;
      }

      public void setAvatar(String avatar) {
         this.avatar = avatar;
      }

      public String getSelfWord() {
         return selfWord;
      }

      public void setSelfWord(String selfWord) {
         this.selfWord = selfWord;
      }
   }
}
