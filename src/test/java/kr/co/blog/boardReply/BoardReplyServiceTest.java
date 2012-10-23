package kr.co.blog.boardReply;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import kr.co.blog.domain.BoardReply;
import kr.co.blog.service.BoardReplyService;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/spring/context-root.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class BoardReplyServiceTest {
    private static Logger log = Logger.getLogger(BoardReplyServiceTest.class);
    
    @Autowired BoardReplyService boardServiceReply;
    BoardReply boardReply;
    
    @Before
    public void setup() {
        boardReply = new BoardReply();
        log.debug("BoardReplyServiceTest Before");
    }
    
    @Test
    public void createBoardReplyTest() throws Exception {
        //String replyId = UUID.randomUUID().toString();
        String replyId = "5145a4b4-2e20-4298-a54f-830be06db91a";
        
        boardReply.setReplyId(replyId);
        boardReply.setCreateUser("검은몽스");
        boardReply.setIp("127.0.0.1");
        boardReply.setReplyContent("reply test");
        boardReply.setBoardId("59388bfd-d0ae-43ff-9375-2195cd5b9729");
        boardReply.setUserId("dc8b411f-fddb-4273-9b3d-8fba19fa54a0");
        
        int result = boardServiceReply.createBoardReply(boardReply);
        
        log.debug("createBoardReplyTest result : " + result);
        assertEquals(1, result);
    }
    
    @Test
    public void getBoardReplyListTest() throws Exception {
        String boardId = "59388bfd-d0ae-43ff-9375-2195cd5b9729";
        List<BoardReply> resultList = boardServiceReply.getAllBoardReplyList(boardId);
        
        log.debug("getUserListTest result : " + resultList.size());
        assertNotNull(resultList);
    }
    
    @Test
    public void getBoardReplyByReplyIdTest() throws Exception {
        BoardReply boardReply = boardServiceReply.getBoardReplyByReplyId("5145a4b4-2e20-4298-a54f-830be06db91a");

        log.debug("getBoardReplyByReplyIdTest result : " + boardReply);
        assertNotNull(boardReply);
    }
    
    @Test
    public void updateBoardReplyTest() throws Exception {
        int result = boardServiceReply.updateBoardReply("reply update", "5145a4b4-2e20-4298-a54f-830be06db91a");
        
        log.debug("updateBoardReplyTest result : " + result);
        assertEquals(1, result);
    }
    
    @Test
    public void deleteBoardReplyTest() throws Exception {
        int result = boardServiceReply.deleteBoardReply("5145a4b4-2e20-4298-a54f-830be06db91a");
        
        log.debug("deleteBoardReplyTest result : " + result);
        assertEquals(1, result);
    }
   
    @After
    public void after() {
        log.debug("BoardReplyServiceTest after");
    }
}
