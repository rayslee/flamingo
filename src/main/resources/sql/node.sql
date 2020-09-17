delete from Node;
insert into Node (id, name) values (1910, '天津城建大学');
insert into Node (id, name, parent_id) values (1940, '图书馆', 1910);
insert into Node (id, name, parent_id) values (1980, '第一书库', 1940);
insert into Node (id, name, parent_id) values (1981, '第二书库', 1940);
insert into Node (id, name, parent_id) values (1982, '第三书库', 1940);
insert into Node (id, name, parent_id) values (1983, '第四书库', 1940);
insert into Node (id, name, parent_id) values (1984, '第五书库', 1940);
insert into Node (id, name, parent_id) values (1941, '现代教育中心', 1910);
insert into Node (id, name, parent_id) values (1950, '现代教育中心A区', 1941);
insert into Node (id, name, parent_id) values (1951, '现代教育中心B区', 1941);
insert into Node (id, name, parent_id) values (1952, '现代教育中心C区', 1941);
insert into Node (id, name, parent_id) values (1960, '现代教育中心C区1楼', 1952);
insert into Node (id, name, parent_id) values (1961, '现代教育中心C区2楼', 1952);
insert into Node (id, name, parent_id) values (1962, '现代教育中心C区3楼', 1952);
insert into Node (id, name, parent_id) values (1970, 'C301', 1962);
insert into Node (id, name, parent_id) values (1971, 'C302', 1962);
insert into Node (id, name, parent_id) values (1972, 'C303', 1962);
insert into Node (id, name, parent_id) values (1973, 'C304', 1962);
insert into Node (id, name, parent_id) values (1974, 'C305', 1962);
insert into Node (id, name, parent_id) values (1975, 'Harley.s lab', 1962);


delete from Node_Attributes;
insert into Node_Attributes (node_id, attributes_key, attributes) values (1910, '成立时间', '1978');
insert into Node_Attributes (node_id, attributes_key, attributes) values (1910, '占地面积', '61万平方米');
insert into Node_Attributes (node_id, attributes_key, attributes) values (1910, '校训', '重德重能 善学善建');