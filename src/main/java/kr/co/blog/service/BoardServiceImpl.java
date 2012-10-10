package kr.co.blog.service;

import java.util.List;
import java.util.Map;

import kr.co.blog.dao.BoardDao;
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
    public Board getBoardByBoardId(String boardId) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardService getBoardByBoardId method Call~!!!");    
        }
        
        return boardDao.getBoardByBoardId(boardId);
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
    
}
