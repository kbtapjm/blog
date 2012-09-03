package kr.co.blog.dao;

import java.util.List;

import kr.co.blog.domain.User;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
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
    final String MQL_GET_USER_BY_MEMBERID = "select * from user where memberid = #{memberid}";
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
     * 유저정보 중복확인
     * @param memberId
     * @return
     * @throws Exception
     */
    @Select(MQL_GET_USER_BY_MEMBERID)
    @Options(useCache=true)
    // http://loianegroner.com/2011/02/getting-started-with-ibatis-mybatis-annotations/
    public User getUserByMemberId(String memberId) throws Exception;

    /**
     * 유저등록
     * @param user
     * @return
     * @throws Exception
     */
    @Insert(MQL_CREATE_USER)
    //@Options(useGeneratedKeys = true, keyProperty="id") : 유저아이디 생성
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
