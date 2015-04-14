
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
    `urltype` CHAR(1) NULL DEFAULT NULL,
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

ALTER TABLE board DROP FOREIGN KEY userid;
ALTER TABLE boardreply DROP FOREIGN KEY userid;
ALTER TABLE board DROP FOREIGN KEY userid;

/*=============================================================================*/
/* ORACLE */
/*=============================================================================*/

windows

캐릿터 셋 설정
http://mansoo.tistory.com/entry/MySQL-%EC%BA%90%EB%A6%AD%ED%84%B0-%EC%85%8B-%EB%B3%80%EA%B2%BD

1) my.ini 파일에 설정 추가 (C:\Program Files\MariaDB 10.0\data)
[client]
default-character-set = utf8

[mysqld]
character-set-client-handshake = FALSE
init_connect="SET collation_connection = utf8_general_ci"
init_connect="SET NAMES utf8"
character-set-server = utf8


[mysql]
default-character-set = utf8

[mysqldump]
default-character-set = utf8


2) 저장 후 재시작(관리도구 -> 서비스 -> MySql)


3) DB 생성
CREATE DATABASE myhub;

CREATE USER kbtapjm IDENTIFIED BY '1234';

GRANT ALL PRIVILEGES ON kbtapjm.* TO 'myhub'@'%' WITH GRANT OPTION;
