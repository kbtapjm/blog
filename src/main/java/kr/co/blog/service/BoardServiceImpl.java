package kr.co.blog.service;

import java.util.List;
import java.util.Map;

import kr.co.blog.common.FileUtil;
import kr.co.blog.dao.BoardDao;
import kr.co.blog.dao.BoardReplyDao;
import kr.co.blog.domain.Board;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * BoardService implements
 * @author kbtapjm
 *
 */
@Service
public class BoardServiceImpl implements BoardService {
    private static Logger log = Logger.getLogger(BoardService.class);

    @Autowired BoardDao boardDao;
    @Autowired BoardReplyDao boardReplyDao;
    
    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int createBoard(Board board) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardService createBoard method Call~!!!");    
        }
        
        return boardDao.createBoard(board);
    }

    @Override
    public List<Board> getAllBoardList(Map<String, Object> params) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardService getAllBoardList method Call~!!!");    
        }
        
        return boardDao.getAllBoardList(params);
    }

    @Override
    public int getAllBoardListCnt(Map<String, Object> params) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardService getAllBoardListCnt method Call~!!!");    
        }
        
        return boardDao.getAllBoardListCnt(params);
    }

    @Override
    public List<String> getBoardTypeheadSubject() throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardService getAllBoardListCnt method Call~!!!");    
        }
        
        return boardDao.getBoardTypeheadSubject();
    }

    @Override
    public Board getBoardByBoardId(String boardId) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardService getBoardByBoardId method Call~!!!");    
        }
        
        return boardDao.getBoardByBoardId(boardId);
    }

    @Override
    public int updateBoardCount(String boardId) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardService updateBoardCount method Call~!!!");    
        }
        
        return boardDao.updateBoardCount(boardId);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int updateBoard(Board board) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardService updateBoard method Call~!!!");    
        }
        
        return boardDao.updateBoard(board);
    }

    @Override
    public int deleteBoard(Board board) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardService deleteBoard method Call~!!!");    
        }
        
        return boardDao.deleteBoard(board);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int multiDeleteBoard(String[] boardIds) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardService multiDeleteBoard method Call~!!!");    
        }
        
        int result = 0;
        
        // 삭제처리
        for(String boardId : boardIds) {
            // 삭제할 파일 조회
            Board board = boardDao.getBoardByBoardId(boardId);
          
            // 댓글 삭제
            boardReplyDao.deleteReplyByBoardId(board.getBoardId());
          
            // 게시글 삭제
            result = boardDao.deleteBoard(board);
            
            // 첨부파일 삭제
            if(board.getFileName().length() > 0) {
                FileUtil.fileDelete(board.getFileName());    
            }
        }
        
        return result;
    }  
}
