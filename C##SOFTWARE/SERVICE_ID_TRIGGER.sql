create trigger SERVICE_ID_TRIGGER
    before insert
    on SERVICE
    for each row
begin  
   if inserting then 
      if :NEW."ID" is null then 
         select SERVICE_ID_SEQUENCE.nextval into :NEW."ID" from dual; 
      end if; 
   end if; 
end;
/

