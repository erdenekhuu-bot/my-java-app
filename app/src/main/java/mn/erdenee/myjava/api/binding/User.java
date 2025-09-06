package mn.erdenee.myjava.api.binding;

public class User {
    private String usertype;
    private String phone;

   public User(String phone, String usertype) {
       this.phone = phone;
       this.usertype = usertype;
   }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}
