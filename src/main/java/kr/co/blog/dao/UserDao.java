package kr.co.blog.dao;

import java.util.List;

import kr.co.blog.domain.User;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * User Dao
 * @author jmpark
 *
 */
public interface UserDao {
    String MQL_GET_ALL_USERS  = "select * from users";
    String MQL_GET_USER_BY_ID = "select * from users where id = #{userId}";
    String MQL_CREATE_USER    = "insert into users (userId, memberId, userName, password, email, birthday, gender) values (#{userId},#{memberId},#{userName},#{password},#{email},#{birthday},#{gender})";
    String MQL_UPDATE_USER    = "update users set fullName=#{userName}, email=#{email}, address=#{birthday}, mobile=#{gender} where id=#{userId}";
    String MQL_DELETE_USER    = "delete from users where id=#{userId}";

    @Select(MQL_GET_ALL_USERS)
    public List<User> getAllUsers() throws Exception;

    @Select(MQL_GET_USER_BY_ID)
    public User getUserById(long id) throws Exception;

    @Insert(MQL_CREATE_USER)
    //@Options(useGeneratedKeys = true, keyProperty="id") : 유저아이디 생성
    public int doCreateUser(User vo) throws Exception;

    @Update(MQL_UPDATE_USER)
    public int doUpdateUser(User vo) throws Exception; 

    @Delete(MQL_DELETE_USER)
    public int doDeleteUser(User vo) throws Exception;  

}
