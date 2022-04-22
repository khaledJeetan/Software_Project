create table USER_TB
(
    USERNAME         VARCHAR2(40)                      not null
        constraint USER_TABLE_PK
            primary key,
    PASSWORD         VARCHAR2(50)                      not null,
    TYPE             CHAR         default 1            not null,
    CREATION_DATE    TIMESTAMP(6) default current_date not null,
    LAST_ACCESS_DATE TIMESTAMP(6),
    UPDATED_AT       TIMESTAMP(6),
    ENABLED          CHAR         default 1            not null,
    PHOTO            BLOB
)
/

