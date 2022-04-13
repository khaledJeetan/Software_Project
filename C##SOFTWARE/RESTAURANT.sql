create table RESTAURANT
(
    ID                 NUMBER        not null
        constraint RESTAURANT_PK
            primary key,
    NAME               VARCHAR2(50)  not null,
    ADDRESS            VARCHAR2(100) not null,
    HAS_DELIVERY       CHAR,
    PHONE              NUMBER        not null,
    IS_HEALTH_APPROVED CHAR default 0
)
/

