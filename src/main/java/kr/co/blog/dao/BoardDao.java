package kr.co.blog.dao;

import java.util.List;
import java.util.Map;

import kr.co.blog.dao.mapper.BoardQuery;
import kr.co.blog.domain.Board;
import kr.co.blog.domain.User;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * BoardDao
 * @author jmpark
 *
 */
@Component
public interface BoardDao {

    /**
     * 게시글 등록
     * @param board
     * @return
     * @throws Exception
     */
    @Insert("insert into board (boardid, subject, content, createuser, createdt, modifydt, count, urltype, ip, pageurl, filename, filesize, userid) " +
    		"values (#{boardId},#{subject},#{content},#{createUser},now(),now(),0,#{urlType},#{ip},#{pageUrl},#{fileName},#{fileSize},#{userId})")
    public int createBoard(Board board) throws Exception;
    
    /**
     * 게시글 상세조회
     * @param boardid
     * @return
     * @throws Exception
     */
    @Select("select * from board where boardid = #{boardId}")
    @Options(useCache=true)
    @Results(value = {
            @Result(property="boardId", column="boardid"),
            @Result(property="subject", column="subject"),
            @Result(property="content", column="content"),
            @Result(property="createUser", column="createuser"),
            @Result(property="createDt", column="createdt"),
            @Result(property="modifyDt", column="modifydt"),
            @Result(property="count", column="count"),
            @Result(property="urlType", column="urltype"),
            @Result(property="ip", column="ip"),
            @Result(property="pageUrl", column="pageurl"),
            @Result(property="fileName", column="filename"),
            @Result(property="fileSize", column="filesize"),
            @Result(property="user",  column="userid",  javaType=User.class, one=@One(select="kr.co.blog.dao.UserDao.getUserByUserId"))
    }) 
    public Board getBoardByBoardId(String boardId) throws Exception;
    
    /**
     * 게시글 조회수 증가
     * @param boardId
     * @return
     * @throws Exception
     */
    @Update("update board set  count = count + 1 where boardid=#{boardId}")
    @Options(flushCache=true)
    public int updateBoardCount(String boardId) throws Exception; 
    
    /**
     * 게시글 목록
     * @return
     * @throws Exception
     */
    @Options(useCache=true)
    @Results(value = {
            @Result(property="boardId", column="boardid"),
            @Result(property="subject", column="subject"),
            @Result(property="content", column="content"),
            @Result(property="createUser", column="createuser"),
            @Result(property="createDt", column="createdt"),
            @Result(property="modifyDt", column="modifydt"),
            @Result(property="count", column="count"),
            @Result(property="urlType", column="urltype"),
            @Result(property="ip", column="ip"),
            @Result(property="pageUrl", column="pageurl"),
            @Result(property="fileName", column="filename"),
            @Result(property="fileSize", column="filesize"),
            @Result(property="user",  column="userid",  javaType=User.class, one=@One(select="kr.co.blog.dao.UserDao.getUserByUserId"))
    }) 
    @SelectProvider(type = BoardQuery.class, method = "getBoardListQuery")
    public List<Board> getAllBoardList(Map<String, Object> params) throws Exception;
    
    /**
     * 게시글 목록 카운트
     * @param params
     * @return
     * @throws Exception
     */
    @SelectProvider(type = BoardQuery.class, method = "getBoardListCntQuery")
    public int getAllBoardListCnt(Map<String, Object> params) throws Exception;
    
    /**
     * 게시글 제목 가져오기
     * @return
     * @throws Exception
     */
    @Select("SELECT DISTINCT(subject) as subject FROM board")
    public List<String> getBoardTypeheadSubject() throws Exception;
    
    /**
     * 게시글 수정
     * @param board
     * @return
     * @throws Exception
     */
    @Update("update board set subject=#{subject}, content=#{content}, modifydt=now(), pageurl=#{pageUrl}, fileName=#{fileName}, filesize=#{fileSize} where boardid=#{boardId}")
    @Options(flushCache=true)
    public int updateBoard(Board board) throws Exception; 
    
    /**
     * 게시글 삭제
     * @param board
     * @return
     * @throws Exception
     */
    @Delete("delete from board where boardid=#{boardId}")
    @Options(flushCache=true)
    public int deleteBoard(Board board) throws Exception;
    
}
