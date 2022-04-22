create trigger ADDRESS_PK_TRIGGER
    before insert
    on ADDRESS
    for each row
begin  
   if inserting then 
      if :NEW."ID" is null then 
         select ADDRESS_PK_SEQUENCE.nextval into :NEW."ID" from dual; 
      end if; 
   end if; 
end;
/

