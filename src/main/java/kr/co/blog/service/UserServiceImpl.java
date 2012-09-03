package kr.co.blog.service;

import java.util.List;

import kr.co.blog.dao.UserDao;
import kr.co.blog.domain.User;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserService implements
 * @author kbtapjm
 *
 */
@Service
public class UserServiceImpl implements UserService  {
    private static Logger log = Logger.getLogger(UserService.class);
    
    @Autowired private UserDao userDao;

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int createUser(User user) throws Exception  {
        if(log.isDebugEnabled()) {
            log.debug("UserService createUser method Call~!!!");    
        }
        
        return userDao.createUser(user);
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("UserService getAllUsers method Call~!!!");    
        }
        
        return userDao.getAllUsers();
    }

    @Override
    public User getUserByUserId(String userId) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("UserService getUserByUserId method Call~!!!");    
        }
        
        return userDao.getUserByUserId(userId);
    }

    @Override
    public User getUserBymemberId(String memberId) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("UserService getUserBymemberId method Call~!!!");    
        }
        
        return userDao.getUserByMemberId(memberId);
    }

    @Override
    public int updateUser(User user) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("UserService updateUser method Call~!!!");    
        }
        
        return userDao.updateUser(user);
    }

    @Override
    public int deleteUser(User user) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("UserService deleteUser method Call~!!!");    
        }
        
        return userDao.updateUser(user);
    }
    
    

}
