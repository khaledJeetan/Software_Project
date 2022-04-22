create table ADDRESS
(
    ID       NUMBER       not null
        constraint ADDRESS_PK
            primary key,
    CITY     VARCHAR2(50) not null,
    LOCATION VARCHAR2(90) not null
)
/

