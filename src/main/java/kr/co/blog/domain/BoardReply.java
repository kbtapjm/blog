package kr.co.blog.domain;

import java.io.Serializable;

/**
 * boardReply Domain
 * @author jmpark
 *
 */
public class BoardReply implements Serializable {
    private static final long serialVersionUID = 4999179514811531517L;
    
    String replyId;
    String replyContent;
    String createUser;
    String createDt;
    String ip;
    String boardId;
    String userId;
    
    public BoardReply() {
        super();
    }

    public BoardReply(String replyId, String replyContent, String createUser,
            String createDt, String ip, String boardId, String userId) {
        super();
        this.replyId = replyId;
        this.replyContent = replyContent;
        this.createUser = createUser;
        this.createDt = createDt;
        this.ip = ip;
        this.boardId = boardId;
        this.userId = userId;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
