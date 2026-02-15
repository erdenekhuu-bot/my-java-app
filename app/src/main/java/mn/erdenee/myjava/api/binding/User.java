package mn.erdenee.myjava.api.binding;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("usertype")
    private String usertype;
    @SerializedName("phone")
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
