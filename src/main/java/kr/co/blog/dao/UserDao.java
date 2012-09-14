package kr.co.blog.dao;

import java.util.List;

import kr.co.blog.domain.User;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * User Dao
 * @author jmpark
 *
 */
@Component
public interface UserDao {
    final String MQL_GET_ALL_USERS  = "select * from user";
    final String MQL_GET_USER_BY_ID = "select * from user where userid = #{userId}";
    final String MQL_GET_USER_BY_EMAIL = "select * from user where email = #{email}";
    final String MQL_GET_USER_BY_MEMBERID = "select userid, memberid, username, password, email, birthday, gender from user where memberid = #{memberid}";
    final String MQL_GET_USER_LOGIN_INFO = "select * from user where memberid = #{memberId} and password=#{password}";
    final String MQL_CREATE_USER = "insert into user (userid, memberid, username, password, email, birthday, gender) values (#{userId},#{memberId},#{userName},#{password},#{email},#{birthday},#{gender})";
    final String MQL_UPDATE_USER = "update user set userName=#{userName}, password=#{password}, email=#{email} where userid=#{userId}";
    final String MQL_DELETE_USER = "delete from user where userid=#{userId}";

    /**
     * 유저목록
     * @return
     * @throws Exception
     */
    @Select(MQL_GET_ALL_USERS)
    @Options(useCache=true)
    public List<User> getAllUsers() throws Exception;

    /**
     * 유저정보 조회
     * @param userId
     * @return
     * @throws Exception
     */
    @Select(MQL_GET_USER_BY_ID)
    @Options(useCache=true)
    public User getUserByUserId(String userId) throws Exception;
    
    /**
     * 유저정보조회(아이디찾기)
     * @param email email
     * @return
     * @throws Exception
     */
    @Select(MQL_GET_USER_BY_EMAIL)
    @Options(useCache=true)
    public User getUserByUserByEmail(String email) throws Exception;
    
    /**
     * 로그인 정보
     * @param memberId
     * @param password
     * @return
     * @throws Exception
     */
    @Select(MQL_GET_USER_LOGIN_INFO)
    @Options(useCache=true)
    public User getUserLoginInfo(@Param("memberId") String memberId, @Param("password") String password) throws Exception;
    
    /**
     * 유저정보 중복확인
     * http://www.mybatis.org/core/ko/sqlmap-xml.html 참조
     * @param memberId
     * @return 
     * @throws Exception
     */
    @Select(MQL_GET_USER_BY_MEMBERID)
    @Options(useCache=true)
    @Results(value = {
            @Result(property="userId", column="userid"),
            @Result(property="memberId", column="memberid"),
            @Result(property="userName", column="username"),
            @Result(property="password", column="password"),
            @Result(property="email", column="email"),
            @Result(property="birthday", column="birthday"),
            @Result(property="gender", column="gender")
    }) 
    public User getUserByMemberId(String memberId) throws Exception;

    /**
     * 유저등록
     * @param user
     * @return
     * @throws Exception
     */
    @Insert(MQL_CREATE_USER)
    public int createUser(User user) throws Exception;

    /**
     * 유저수정
     * @param user
     * @return
     * @throws Exception
     */
    @Update(MQL_UPDATE_USER)
    @Options(flushCache=true)
    public int updateUser(User user) throws Exception; 

    /**
     * 유저삭제
     * @param user
     * @return
     * @throws Exception
     */
    @Delete(MQL_DELETE_USER)
    @Options(flushCache=true)
    public int deleteUser(User user) throws Exception;  
}
