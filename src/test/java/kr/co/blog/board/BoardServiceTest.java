package kr.co.blog.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import kr.co.blog.domain.Board;
import kr.co.blog.service.BoardService;

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
public class BoardServiceTest {
    private static Logger log = Logger.getLogger(BoardServiceTest.class);
    
    @Autowired BoardService boardService;
    Board board;

    @Before
    public void setup() {
        board = new Board();
        log.debug("BoardServiceTest Before");
    }
    
    @Test
    public void createBoardTest() throws Exception {
        board.setBoardId("5145a4b4-2e20-4298-a54f-830be06db91a");
        board.setSubject("title test");
        board.setContent("contents test");
        board.setCreateUser("검은몽스");
        board.setNoticeYn("N");
        board.setIp("127.0.0.1");
        board.setFileName("test.jpg");
        board.setFileSize(1234);
        board.setUserId("34a7bf1a-3484-4564-b93c-83ebabe0475d");
        
        int result = boardService.createBoard(board);
        
        log.debug("createBoardTest result : " + result);
        assertEquals(1, result);
    }
    
    @Test
    public void getBoardListTest() throws Exception {
        List<Board> resultList = boardService.getAllBoardList();

        log.debug("getUserListTest result : " + resultList.size());
        assertNotNull(resultList);
    }
    
    @Test
    public void getBoardByBoardIdTest() throws Exception {
        Board board = boardService.getBoardByBoardId("5145a4b4-2e20-4298-a54f-830be06db91a");
  
        log.debug("getUserByUserIdTest result : " + board);
        assertNotNull(board);
    }
    
    @Test
    public void updateBoardTest() throws Exception {
        board.setBoardId("5145a4b4-2e20-4298-a54f-830be06db91a");
        board.setSubject("update subject");
        board.setContent("update content");
        board.setCreateUser("검은몽스");
        board.setFileName("test.jpg");
        board.setFileSize(1234);
        
        int result = boardService.updateBoard(board);
        
        log.debug("updateUserTest result : " + result);
        assertEquals(1, result);
    }
    
    @Test
    public void deleteUserTest() throws Exception {
        board.setBoardId("5145a4b4-2e20-4298-a54f-830be06db91a");
        
        int result = boardService.deleteBoard(board);
        
        log.debug("deleteUserTest result : " + result);
        assertEquals(1, result);
    }
    
    @After
    public void after() {
        log.debug("BoardServiceTest after");
    }

}
