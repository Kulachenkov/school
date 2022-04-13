select * from student;

select * from student
where age > 8 AND age < 10;

select name from student;

select * from student
where name like '%o%';

select * from student
where age<student.id;

select age,name from student
order by age;
