package kr.co.blog.service;

import java.util.List;

import kr.co.blog.domain.BoardReply;

/**
 * BoardReplyService interface
 * @author jmpark
 *
 */
public interface BoardReplyService {
    public int createBoardReply(BoardReply boardReply) throws Exception;
    public BoardReply getBoardReplyByReplyId(String replyId) throws Exception;
    public List<BoardReply> getAllBoardReplyList(String boardId) throws Exception;
    public int updateBoardReply(String replyContent, String replyId) throws Exception;
    public int deleteBoardReply(String replyId) throws Exception;
}
