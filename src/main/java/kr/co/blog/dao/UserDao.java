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
    String MQL_GET_ALL_USERS  = "select * from user";
    String MQL_GET_USER_BY_ID = "select * from user where userid = #{userId}";
    String MQL_CREATE_USER    = "insert into user (userId, memberId, userName, password, email, birthday, gender) values (#{userId},#{memberId},#{userName},#{password},#{email},#{birthday},#{gender})";
    String MQL_UPDATE_USER    = "update user set userName=#{userName}, password=#{password}, email=#{email} where userid=#{userId}";
    String MQL_DELETE_USER    = "delete from user where userid=#{userId}";

    @Select(MQL_GET_ALL_USERS)
    @Options(useCache=true)
    public List<User> getAllUsers() throws Exception;

    @Select(MQL_GET_USER_BY_ID)
    @Options(useCache=true)
    public User getUserById(String userId) throws Exception;

    @Insert(MQL_CREATE_USER)
    //@Options(useGeneratedKeys = true, keyProperty="id") : 유저아이디 생성
    public int doCreateUser(User vo) throws Exception;

    @Update(MQL_UPDATE_USER)
    @Options(flushCache=true)
    public int doUpdateUser(User vo) throws Exception; 

    @Delete(MQL_DELETE_USER)
    @Options(flushCache=true)
    public int doDeleteUser(User vo) throws Exception;  

}
