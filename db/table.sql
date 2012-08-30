
CREATE TABLE user (
       userId             VARCHAR(40) NOT NULL,
       UserMerberId          VARCHAR(30) NULL,
       userName           VARCHAR(100) NULL,
       password             VARCHAR(12) NULL,
       email                VARCHAR(30) NULL,
       birthday             CHAR(8) NULL,
       gender               CHAR(1) NULL
);


ALTER TABLE Member
       ADD PRIMARY KEY (memberId);
       
CREATE TABLE GeneralBoard (
       boardId              VARCHAR2(40) NOT NULL,
       subject              VARCHAR2(100) NOT NULL,
       content              VARCHAR2(2000) NOT NULL,
       createUser           VARCHAR2(20) NOT NULL,
       createDt             DATE NOT NULL,
       modifyDt             DATE NULL,
       count                NUMBER(10) NULL,
       noticeYn             CHAR(1) NULL,
       ip                   VARCHAR2(15) NULL,
       pageUrl              VARCHAR2(200) NULL,
       fileName             VARCHAR2(200) NULL,
       fileSize             NUMBER(7) NULL,
       memberId             VARCHAR2(40) NOT NULL
);


ALTER TABLE GeneralBoard
       ADD PRIMARY KEY (boardId, memberId);


CREATE TABLE ReplyBoard (
       replyId              VARCHAR2(40) NOT NULL,
       replyContent         VARCHAR2(1200) NOT NULL,
       createUser           VARCHAR2(20) NOT NULL,
       createDt             DATE NOT NULL,
       ip                   VARCHAR2(15) NULL,
       boardId              VARCHAR2(40) NOT NULL,
       memberId             VARCHAR2(40) NOT NULL
);


ALTER TABLE ReplyBoard
       ADD PRIMARY KEY (replyId, boardId, memberId);


ALTER TABLE GeneralBoard
       ADD FOREIGN KEY (memberId)
                             REFERENCES Member  (memberId);


ALTER TABLE ReplyBoard
       ADD FOREIGN KEY (memberId)
                             REFERENCES Member  (memberId);


ALTER TABLE ReplyBoard
       ADD FOREIGN KEY (boardId, memberId)
                             REFERENCES GeneralBoard  (boardId, 
              memberId);
