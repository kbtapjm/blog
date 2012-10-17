
/*=============================================================================*/
/* MYSQL */
/*=============================================================================*/
/*사용자*/
CREATE TABLE `user` (
    `userid` VARCHAR(40) NOT NULL,
    `memberid` VARCHAR(30) NOT NULL,
    `username` VARCHAR(100) NOT NULL,
    `password` VARCHAR(12) NOT NULL,
    `email` VARCHAR(30) NOT NULL,
    `birthday` CHAR(8) NULL DEFAULT NULL,
    `gender` CHAR(1) NULL DEFAULT NULL,
    PRIMARY KEY (`userid`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
       
/* 게시판 */       
CREATE TABLE `board` (
    `boardid` VARCHAR(40) NOT NULL,
    `subject` VARCHAR(100) NOT NULL,
    `content` LONGTEXT NOT NULL,
    `createuser` VARCHAR(20) NOT NULL,
    `createdt` DATETIME NOT NULL,
    `modifydt` DATETIME NULL DEFAULT NULL,
    `count` INT(10) NULL DEFAULT NULL,
    `noticeyn` CHAR(1) NULL DEFAULT NULL,
    `ip` VARCHAR(15) NULL DEFAULT NULL,
    `pageurl` VARCHAR(200) NULL DEFAULT NULL,
    `filename` VARCHAR(200) NULL DEFAULT NULL,
    `filesize` INT(7) NULL DEFAULT NULL,
    `userid` VARCHAR(40) NOT NULL,
    PRIMARY KEY (`boardid`),
    CONSTRAINT FOREIGN KEY (`userid`) REFERENCES user(`userid`) ON DELETE CASCADE ON UPDATE RESTRICT    
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT

/* 게시판 댓글*/
CREATE TABLE `boardreply` (
    `replyid` VARCHAR(40) NOT NULL,
    `replycontent` VARCHAR(1200) NOT NULL,
    `createuser` VARCHAR(20) NOT NULL,
    `createdt` DATETIME NOT NULL,
    `ip` VARCHAR(15) NULL DEFAULT NULL,
    `boardid` VARCHAR(40) NOT NULL,
    `userid` VARCHAR(40) NOT NULL,
    PRIMARY KEY (`replyid`),
    CONSTRAINT FOREIGN KEY (`userid`) REFERENCES `user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (`boardid`) REFERENCES `board` (`boardid`) ON DELETE CASCADE ON UPDATE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT

/*foreign key*/
alter table board foreign key(userid) references user(userid) on delete cascade;

alter table boardreply foreign key(userid) references user(userid) on delete cascade;

alter table boardreply foreign key(boardid, userid) references user(board, userid) on delete cascade;

ALTER TABLE board ADD CONSTRAINT FOREIGN KEY(userid) REFERENCES user(userid) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE board DROP FOREIGN KEY userid;
ALTER TABLE boardreply DROP FOREIGN KEY userid;
ALTER TABLE board DROP FOREIGN KEY userid;

/*=============================================================================*/
/* ORACLE */
/*=============================================================================*/
