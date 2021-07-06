
create table lab04_parse (
	id NUMBER PRIMARY KEY ,
	data_hora VARCHAR(50)
);

create sequence seq_lab04_parse INCREMENT BY 1 NOCACHE;

CREATE OR REPLACE PROCEDURE mock_lab04_parse IS
    cont int :=0;
    BEGIN
        LOOP
    
            INSERT INTO lab04_parse (id, data_hora) values (seq_lab04_parse.NEXTVAL, TO_CHAR(current_timestamp, 'DD/MM/YYYY HH24:MI:SS') );
            cont := cont+1;
            COMMIT;
            
            EXIT WHEN cont = 100001;
        END LOOP;
END mock_lab04_parse;

SET TIMING ON
EXEC mock_lab04_parse;


select count(*) from lab04_parse ; 