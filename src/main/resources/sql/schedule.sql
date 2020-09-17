delete from Hebdomad;
insert into Hebdomad (id, name, depot_id, finish, start) values (3000, 'Database Management By Harley', 1975, '2019-06-09 20:50:00', '2019-03-09 18:15:00');

delete from Hebdomad_Weeks;
insert into Hebdomad_Weeks (hebdomad_id, weeks) values (3000, 0);
insert into Hebdomad_Weeks (hebdomad_id, weeks) values (3000, 1);

insert into Node_Modules (node_id, modules) values (1975, 'SCHEDULE');