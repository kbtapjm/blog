package kr.co.blog.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User Domain
 * @author jmpark
 *
 */
public class User implements Serializable {
    private static final long serialVersionUID = 6152672980439790751L;
    
    @NotNull(message = "blog.label.input.id")
    @Size(min=1) 
    private String userId;
    
    @NotNull @Size(min=1) 
    private String memberId;
    
    @NotNull @Size(min=1) 
    private String userName;
    
    @NotNull @Size(min=1) 
    private String password;
   
    @NotNull @Size(min=1) 
    private String email;
    
    @NotNull @Size(min=1) 
    private String birthday;
    
    @NotNull @Size(min=1) 
    private String gender;
    
    public User() {
        super();
    }

    public User(String userId, String memberId, String userName,
            String password, String email, String birthday, String gender) {
        super();
        this.userId = userId;
        this.memberId = memberId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
