
/*사용자*/
create table user (
       userid             varchar(40) not null,
       memberid          varchar(30) not null,
       username           varchar(100) not null,
       password             varchar(12) not null,
       email                varchar(30) not null,
       birthday             char(8) null,
       gender               char(1) null,
       primary key(userid)
)
engine = innodb
default character set = latin1;
       
/* 게시판 */       
create table board (
       boardid              varchar(40) not null,
       subject              varchar(100) not null,
       content              blob(2000) not null,
       createuser           varchar(20) not null,
       createdt             datetime not null,
       modifydt             datetime null,
       count                int(10) null,
       noticeyn             char(1) null,
       ip                   varchar(15) null,
       pageurl              varchar(200) null,
       filename             varchar(200) null,
       filesize             int(7) null,
       userid             varchar(40) not null,
       primary key(boardid)
)
engine = innodb
default character set = latin1;

/* 게시판 댓글*/
create table boardreply (
       replyid              varchar(40) not null,
       replycontent         varchar(1200) not null,
       createuser           varchar(20) not null,
       createdt             datetime not null,
       ip                   varchar(15) null,
       boardid              varchar(40) not null,
       userid             varchar(40) not null,
       primary key(replyid)
)
engine = innodb
default character set = latin1;

/*foreign key*/
alter table board foreign key(userid) references user(userid) on delete cascade;

alter table boardreply foreign key(userid) references user(userid) on delete cascade;

alter table boardreply foreign key(boardid, userid) references user(board, userid) on delete cascade;

alter table board drop foreign key userid
alter table boardreply drop foreign key userid
alter table board drop foreign key boardid

