create table RESTAURANT_SERVICE
(
    RESTAURANT_ID NUMBER not null
        constraint RESTAURANTS_ID_FK
            references RESTAURANT,
    SERVICE_ID    NUMBER not null
        constraint SERVICE_ID_FK
            references SERVICE,
    PRICE         NUMBER not null,
    constraint RESTAURANTS_SERVICE_PK
        primary key (RESTAURANT_ID, SERVICE_ID)
)
/

