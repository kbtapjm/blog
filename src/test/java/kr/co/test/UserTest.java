package kr.co.test;

import java.util.List;
import java.util.UUID;

import kr.co.blog.common.ConnectionFactory;
import kr.co.blog.dao.UserDao;
import kr.co.blog.domain.User;

import org.apache.ibatis.session.SqlSession;

public class UserTest {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        UserTest test = new UserTest();
        User user = new User();
        
        user.setUserId(UUID.randomUUID().toString());
        user.setMemberId("kbtapjm");
        user.setUserName("검은몽스");
        user.setPassword("1234");
        user.setEmail("kbtapjm@gmail.com");
        user.setBirthday("19820509");
        user.setGender("M");
        
        test.createUser(user);

    }
    
    public List<User> getUsers() throws Exception{
        SqlSession session = ConnectionFactory.getSession().openSession();
            UserDao dao =session.getMapper(UserDao.class);
            List<User> users= dao.getAllUsers();
        session.close();
        return users;
    }
    public User getUserById(long id) throws Exception{
        SqlSession session = ConnectionFactory.getSession().openSession();
            UserDao dao =session.getMapper(UserDao.class);
            User user =dao.getUserById(id);
        session.close();
        return user;
    }
    public User createUser(User vo) throws Exception{
        SqlSession session = ConnectionFactory.getSession().openSession();
            UserDao dao =session.getMapper(UserDao.class);
            dao.doCreateUser(vo);
        session.commit();
        session.close();
        return vo;
    }
    public User updateUser(User vo) throws Exception{
        SqlSession session = ConnectionFactory.getSession().openSession();
            UserDao dao =session.getMapper(UserDao.class);
            dao.doUpdateUser(vo);
        session.commit();
        session.close();
        return vo;
    }
    public int deleteUser(User vo) throws Exception{
        SqlSession session = ConnectionFactory.getSession().openSession();
            UserDao dao =session.getMapper(UserDao.class);
            int cnt= dao.doDeleteUser(vo);
        session.commit();
        session.close();
        return cnt;
    }

}
