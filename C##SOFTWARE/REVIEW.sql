create table REVIEW
(
    USERNAME      VARCHAR2(40)                           not null
        constraint REVIEW_USERNAME_FK
            references USER_TB,
    USER_COMMENT  VARCHAR2(400)                          not null,
    STAR_COUNT    NUMBER                                 not null,
    CREATED_AT    TIMESTAMP(6) default CURRENT_TIMESTAMP not null,
    RESTAURANT_ID NUMBER
        constraint RESTAURANT_REVIEW_ID_FK
            references RESTAURANT,
    constraint REVIEW_PK
        primary key (USERNAME, USER_COMMENT)
)
/

