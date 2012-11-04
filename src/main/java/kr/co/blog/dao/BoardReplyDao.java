package kr.co.blog.dao;

import java.util.List;

import kr.co.blog.domain.BoardReply;

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
 * boardReply Dao
 * @author jmpark
 *
 */
@Component
public interface BoardReplyDao {
    
    /**
     * 게시글 댓글 등록
     * @param boardReply
     * @return
     * @throws Exception
     */
    @Insert("insert into boardreply (replyid, replycontent, createuser, createdt, ip, boardid, userid) " +
            "values (#{replyId},#{replyContent},#{createUser},now(),#{ip},#{boardId},#{userId})")
    public int createBoardReply(BoardReply boardReply) throws Exception;
    
    /**
     * 게시글 댓글 조회
     * @return
     * @throws Exception
     */
    @Select("select * from boardreply where replyid=#{replyId}")
    @Options(useCache=true)
    @Results(value = {
            @Result(property="replyId", column="replyid"),
            @Result(property="replyContent", column="replycontent"),
            @Result(property="createUser", column="createuser"),
            @Result(property="createDt", column="createdt"),
            @Result(property="ip", column="ip"),
            @Result(property="boardId", column="boardid"),
            @Result(property="userId", column="userid")
    }) 
    public BoardReply getBoardReplyByReplyId(@Param("replyId") String replyId) throws Exception;
    
    /**
     * 게시글 댓글 목록
     * @return
     * @throws Exception
     */
    @Select("select * from boardreply where boardid=#{boardId} ORDER BY createdt ASC")
    @Options(useCache=true)
    @Results(value = {
            @Result(property="replyId", column="replyid"),
            @Result(property="replyContent", column="replycontent"),
            @Result(property="createUser", column="createuser"),
            @Result(property="createDt", column="createdt"),
            @Result(property="ip", column="ip"),
            @Result(property="boardId", column="boardid"),
            @Result(property="userId", column="userid")
    }) 
    public List<BoardReply> getAllBoardReplyList(@Param("boardId") String boardId) throws Exception;
    
    /**
     * 게시글 댓글 카운트
     * @param boardId
     * @return
     * @throws Exception
     */
    @Select("select count(*) as count  from boardreply where boardid=#{boardId}")
    @Options(useCache=true)
    public int getAllBoardReplyListCnt(@Param("boardId") String boardId) throws Exception;
    
    /**
     * 게시글 댓글 수정
     * @param board
     * @return
     * @throws Exception
     */
    @Update("update boardreply set replycontent=#{replyContent} where replyid=#{replyId}")
    @Options(flushCache=true)
    public int updateBoardReply(@Param("replyContent") String replyContent, @Param("replyId") String replyId) throws Exception; 
    
    /**
     * 게시글 댓글 삭제
     * @param boardReply
     * @return
     * @throws Exception
     */
    @Delete("delete from boardreply where replyid=#{replyId}")
    @Options(flushCache=true)
    public int deleteBoardReply(@Param("replyId") String replyId) throws Exception;

}
