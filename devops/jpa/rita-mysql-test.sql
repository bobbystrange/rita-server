use rita;

insert into user
values (1, now(), now(), "tuke", "bar", "@", "foo", "bar", "1999-99-99", 1, "github");
insert into user
values (2, now(), now(), "foo1", "bar", "@", "foo", "bar", "1999-99-99", 1, "github"),
       (3, now(), now(), "foo2", "bar", "@", "foo", "bar", "1999-99-99", 1, "github"),
       (4, now(), now(), "foo3", "bar", "@", "foo", "bar", "1999-99-99", 1, "github");

insert into avatar
values (1, now(), now(), 1, "foo", "@#$%");

insert into post
values (1, now(), now(), 1, "name1", "title", "vs", 1, 1, "summary", "content");
insert into post
values (2, now(), now(), 1, "name_1", "title1", "vs", 1, 0, "summary1", "content"),
       (3, now(), now(), 1, "name_2", "title2", "deafult", 1, 1, "summary2", "content"),
       (4, now(), now(), 1, "name_3", "title3", "vs", 0, 1, "summary3", "content"),
       (5, now(), now(), 1, "name_4", "title4", "vs", 0, 0, "summary4", "content"),
       (6, now(), now(), 1, "name_5", "title5", "vs", 1, 0, "summary5", "content"),
       (7, now(), now(), 1, "name_6", "title6", "vs", 0, 1, "summary6", "content");

insert into tag
values (1, now(), now(), 2, "name_1", 1);
insert into tag
values (2, now(), now(), 1, "name_2", 0),
       (3, now(), now(), 1, "name_3", 0),
       (4, now(), now(), 1, "name_4", 1);

insert into post_tag
values (1, now(), now(), 1, 2),
       (2, now(), now(), 1, 3),
       (3, now(), now(), 1, 4),
       (4, now(), now(), 2, 2),
       (5, now(), now(), 3, 3),
       (6, now(), now(), 4, 4),
       (7, now(), now(), 5, 3),
       (8, now(), now(), 5, 4),
       (9, now(), now(), 6, 4);


select * from post where uid = 1;
select * from tag where name = 'name_1';

select distinct p.id, p.mtime
from post p inner join post_tag pt inner join tag t
on p.id = pt.pid and pt.tid = t.id
where p.uid = 1 and t.name = 'name_4' order by p.id desc limit 10 offset 0;

select count(distinct p.id)
from post p inner join post_tag pt inner join tag t
on p.id = pt.pid and pt.tid = t.id
where p.uid = 1 and t.name = 'name_4' order by p.id desc limit 10 offset 0


