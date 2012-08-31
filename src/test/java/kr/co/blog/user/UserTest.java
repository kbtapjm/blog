package kr.co.blog.user;

import java.util.UUID;

import kr.co.blog.domain.User;

public class UserTest {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        UserTest test = new UserTest();
        UserBo userBo = new UserBo(); 
        User user = new User();
        
        String userId = UUID.randomUUID().toString();
        
        // 등록
        user.setUserId(userId);
        user.setMemberId("kbtapjm");
        user.setUserName("검은몽스");
        user.setPassword("1234");
        user.setEmail("kbtapjm@gmail.com");
        user.setBirthday("19820509");
        user.setGender("M");
        
        userBo.createUser(user);
        
        // 유저목록
        System.out.println(userBo.getUsers());
        
        // 유저정보조회
        user = userBo.getUserById(userId);
        System.out.println("user : " + user.toString());
        
        // 유저정보 수정
        user.setUserName("kobe");
        user.setPassword("5678");
        user.setEmail("tapjm@naver.com");
        userBo.updateUser(user);
        
        // 유저정보 삭제
        userBo.deleteUser(user);
    }
}
