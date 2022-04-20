create table RESTAURANT_PHOTO
(
    RESTAURANT_ID NUMBER not null
        constraint RESTAURANT_ID_FK
            references RESTAURANT,
    PHOTO         BLOB   not null
)
/

