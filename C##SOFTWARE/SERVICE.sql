create table SERVICE
(
    ID          NUMBER       not null
        constraint SERVICE_PK
            primary key,
    DESCRIPTION VARCHAR2(20),
    NAME        VARCHAR2(50) not null
)
/

