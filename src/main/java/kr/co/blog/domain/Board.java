package kr.co.blog.domain;

/**
 * board Domain
 */
import java.io.Serializable;

public class Board implements Serializable {
    private static final long serialVersionUID = 7748985247815744810L;
    
    private String boardId;
    private String subject;
    private String content;
    private String createUser;
    private String createDt;
    private String modifyDt;
    private int count;
    private String urlType;
    private String ip;
    private String pageUrl;
    private String fileName;
    private int fileSize;
    private String userId;
    private User user;
    
    public Board() {
        super();
    }

    public Board(String boardId, String subject, String content,
            String createUser, String createDt, String modifyDt, int count,
            String urlType, String ip, String pageUrl, String fileName,
            int fileSize, String userId) {
        super();
        this.boardId = boardId;
        this.subject = subject;
        this.content = content;
        this.createUser = createUser;
        this.createDt = createDt;
        this.modifyDt = modifyDt;
        this.count = count;
        this.urlType = urlType;
        this.ip = ip;
        this.pageUrl = pageUrl;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.userId = userId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
