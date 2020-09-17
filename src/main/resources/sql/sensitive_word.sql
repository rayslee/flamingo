create table if not exists Sensitive_Word (
	word varchar(25) primary key
);

delete from Sensitive_Word;
insert into Sensitive_Word values ('Caribou');
insert into Sensitive_Word values ('Condor');
insert into Sensitive_Word values ('Flamingo');
insert into Sensitive_Word values ('Giraffe');
insert into Sensitive_Word values ('普京');
insert into Sensitive_Word values ('特朗普');