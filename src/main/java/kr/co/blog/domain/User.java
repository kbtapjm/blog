package kr.co.blog.domain;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 6152672980439790751L;
    
    private String userId;
    private String memberId;
    private String userName;
    private String password;
    private String email;
    private String birthday;
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
    
    public void userToString() {
        System.out.println("userId : " + this.userId);
        System.out.println("memberId : " + this.memberId);
        System.out.println("password : " + this.password);
        System.out.println("userName : " + this.userName);
        System.out.println("email : " + this.email);
        System.out.println("birthday : " + this.birthday);
        System.out.println("gender : " + this.gender);
    }
}
