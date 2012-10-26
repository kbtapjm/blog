package kr.co.blog.service;

import java.util.List;

import kr.co.blog.dao.BoardReplyDao;
import kr.co.blog.domain.BoardReply;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BoardReplyService implements
 * @author jmpark
 *
 */
@Service
public class BoardReplyServiceImpl implements BoardReplyService {
    private static Logger log = Logger.getLogger(BoardReplyService.class);
    
    @Autowired BoardReplyDao boardReplyDao;

    @Override
    public int createBoardReply(BoardReply boardReply) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardReplyServiceImpl createBoardReply method Call~!!!");    
        }
        
        return boardReplyDao.createBoardReply(boardReply);
    }

    @Override
    public BoardReply getBoardReplyByReplyId(String replyId) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardReplyServiceImpl getBoardReplyByReplyId method Call~!!!");    
        }
        
        return boardReplyDao.getBoardReplyByReplyId(replyId);
    }

    @Override
    public List<BoardReply> getAllBoardReplyList(String boardId) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardReplyServiceImpl getAllBoardReplyList method Call~!!!");    
        }
        
        return boardReplyDao.getAllBoardReplyList(boardId);
    }

    @Override
    public int updateBoardReply(String replyContent, String replyId) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardReplyServiceImpl updateBoardReply method Call~!!!");    
        }
        
        return boardReplyDao.updateBoardReply(replyContent, replyId);
    }

    @Override
    public int deleteBoardReply(String replyId) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardReplyServiceImpl deleteBoardReply method Call~!!!");    
        }
        
        return boardReplyDao.deleteBoardReply(replyId);
    }
}
