package kr.co.blog.user;

import java.util.List;

import kr.co.blog.common.ConnectionFactory;
import kr.co.blog.dao.UserDao;
import kr.co.blog.domain.User;

import org.apache.ibatis.session.SqlSession;

/**
 * 사용자 정보 데이터 처리
 * @author jmpark
 *
 */
public class UserBo {

    /**
     * 사용자 리스트
     * @return
     * @throws Exception
     */
    public List<User> getUsers() throws Exception{
        SqlSession session = ConnectionFactory.getSession().openSession();
        UserDao dao =session.getMapper(UserDao.class);
        List<User> users= dao.getAllUsers();
        session.close();
        return users;
    }
    
    /**
     * 사용자 정보
     * @param id
     * @return
     * @throws Exception
     */
    public User getUserById(String userId) throws Exception{
        SqlSession session = ConnectionFactory.getSession().openSession();
        UserDao dao =session.getMapper(UserDao.class);
        User user =dao.getUserById(userId);
        session.close();
        return user;
    }
    
    /**
     * 사용자 등록
     * @param user
     * @return
     * @throws Exception
     */
    public User createUser(User user) throws Exception{
        SqlSession session = ConnectionFactory.getSession().openSession();
        UserDao dao =session.getMapper(UserDao.class);
        dao.doCreateUser(user);
        session.commit();
        session.close();
        return user;
    }
    
    /**
     * 사용자 수정
     * @param user
     * @return
     * @throws Exception
     */
    public User updateUser(User user) throws Exception{
        SqlSession session = ConnectionFactory.getSession().openSession();
        UserDao dao =session.getMapper(UserDao.class);
        dao.doUpdateUser(user);
        session.commit();
        session.close();
        return user;
    }
    
    /**
     * 사용자 삭제
     * @param user
     * @return
     * @throws Exception
     */
    public int deleteUser(User user) throws Exception{
        SqlSession session = ConnectionFactory.getSession().openSession();
        UserDao dao =session.getMapper(UserDao.class);
        int cnt= dao.doDeleteUser(user);
        session.commit();
        session.close();
        return cnt;
    }
}
