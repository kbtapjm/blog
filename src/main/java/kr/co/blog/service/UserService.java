package kr.co.blog.service;

import java.util.List;

import kr.co.blog.domain.User;

/**
 * UserService Interface
 * @author kbtapjm
 *
 */
public interface UserService {
    public int createUser(User user) throws Exception;
    public List<User> getAllUsers() throws Exception;
    public User getUserByUserId(String userId) throws Exception;
    public User getUserId(String email, String userName) throws Exception;
    public User getUserPassword(String memberId, String userName) throws Exception;
    public User getUserLoginInfo(String memberId, String password) throws Exception;
    public User getUserBymemberId(String memberId) throws Exception;
    public int updateUser(User user) throws Exception;
    public int deleteUser(User user) throws Exception;
}
