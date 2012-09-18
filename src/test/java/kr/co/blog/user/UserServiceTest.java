package kr.co.blog.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import kr.co.blog.common.DESedeCrypto;
import kr.co.blog.domain.User;
import kr.co.blog.service.UserService;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/spring/context-root.xml"})
public class UserServiceTest {
    private static Logger log = Logger.getLogger(UserServiceTest.class);
    
    @Autowired UserService userService;
    User user;
    
    @Before
    public void setup() {
        user = new User();
        log.debug("UserServiceTest Before");
    }
    
    @Test
    public void createUserTest() {
        // 패스워드
        byte[] array = DESedeCrypto.encrypt("aaasss");
        
        user.setUserId("5145a4b4-2e20-4298-a54f-830be06db91a");
        user.setMemberId("tapjm");
        user.setUserName("몽스");
        user.setPassword(new String(array));
        user.setBirthday("18000000");
        user.setEmail("tapjm@naver.com");
        user.setGender("F");
        
        int result = 0;
        try {
            result = userService.createUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        log.debug("createUserTest result : " + result);
        assertEquals(1, result);
    }
    
    @Test
    public void getUserListTest() {
        List<User> resultList = null;
        
        try {
            resultList = userService.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        log.debug("getUserListTest result : " + resultList.size());
        assertNotNull(resultList);
    }

    @Test
    public void getUserByUserIdTest() {
        User user = null;
        
        try {
            user = userService.getUserByUserId("5145a4b4-2e20-4298-a54f-830be06db91a");
        } catch (Exception e) {
            e.printStackTrace();
        }
  
        log.debug("getUserByUserIdTest result : " + user);
        assertNotNull(user);
    }
    
    @Test
    public void updateUserTest() {
        // 패스워드
        byte[] array = DESedeCrypto.encrypt("5145a4b4-2e20-4298-a54f-830be06db91a");
        
        user.setUserId("1111111111");
        user.setUserName("코비");
        user.setPassword(new String(array));
        user.setEmail("tapjm@daume.net");
        user.setGender("M");
        
        int result = 0;
        try {
            result = userService.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        log.debug("updateUserTest result : " + result);
        assertEquals(1, result);
    }
    
    public void deleteUserTest() {
        user.setUserId("5145a4b4-2e20-4298-a54f-830be06db91a");
        
        int result = 0;
        try {
            result = userService.deleteUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        log.debug("deleteUserTest result : " + result);
        assertEquals(1, result);
    }
    
    @After
    public void after() {
        log.debug("UserServiceTest after");
    }


}
