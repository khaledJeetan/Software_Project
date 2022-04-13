create table REVIEW
(
    USERNAME     VARCHAR2(40)  not null
        constraint REVIEW_USERNAME_FK
            references USER_TB,
    USER_COMMENT VARCHAR2(400) not null,
    STAR_COUNT   NUMBER        not null,
    constraint REVIEW_PK
        primary key (USERNAME, USER_COMMENT)
)
/

