package kr.co.blog.service;

import java.util.List;
import java.util.Map;

import kr.co.blog.domain.Board;

/**
 * BoardService Interface
 * @author jmpark
 *
 */
public interface BoardService {
    public int createBoard(Board board) throws Exception;
    public List<Board> getAllBoardList(Map<String, Object> params) throws Exception;
    public Board getBoardByBoardId(String boardId) throws Exception;
    public int updateBoard(Board board) throws Exception;
    public int deleteBoard(Board board) throws Exception;
}
