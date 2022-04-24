create trigger RESTAURANT_ID_TRIGGER
    before insert
    on RESTAURANT
    for each row
begin  
   if inserting then 
      if :NEW."ID" is null then 
         select RESTAURANT_ID_SEQUENCE.nextval into :NEW."ID" from dual; 
      end if; 
   end if; 
end;
/

