create database university_management_DB;
use university_management_DB;
create table login(
	username varchar(30),
    password varchar(30)
);
select * from login;
insert into login value("Admin","admin@123");

create table teacher(
	name varchar(40),
    fatherName varchar(40),
    empId varchar(40),
    dob varchar(40),
    address varchar(40),
    phone varchar(40),
    email varchar(40),
    class_X varchar(40),
    class_XII varchar(40),
    aadhar varchar(40),
    education varchar(40),
    department varchar(40)
);
select * from teacher;

create table student(
	name varchar(40),
    fatherName varchar(40),
    rollNo varchar(40),
    dob varchar(40),
    address varchar(40),
    phone varchar(40),
    email varchar(40),
    class_X varchar(40),
    class_XII varchar(40),
    aadhar varchar(40),
    course varchar(40),
    branch varchar(40)
);

select * from student;

create table studentLeave(
	rollNo varchar(20),
    date varchar(20),
    time varchar(20)
);

select * from studentLeave;

create table teacherLeave(
	empId varchar(20),
    date varchar(20),
    time varchar(20)
);

select * from teacherLeave;

create table subject(
	rollNo varchar(20),
    semester varchar(20),
    sub1 varchar(50),
    sub2 varchar(50),
    sub3 varchar(50),
    sub4 varchar(50),
    sub5 varchar(50)
);

select * from subject;

create table marks(
	rollNo varchar(20),
    semester varchar(20),
    mark1 varchar(50),
    mark2 varchar(50),
    mark3 varchar(50),
    mark4 varchar(50),
    mark5 varchar(50)
);

select * from marks;

CREATE TABLE fee(
    course VARCHAR(20), 
    `Semester 1` VARCHAR(20), 
    `Semester 2` VARCHAR(20), 
    `Semester 3` VARCHAR(20), 
    `Semester 4` VARCHAR(20), 
    `Semester 5` VARCHAR(20), 
    `Semester 6` VARCHAR(20), 
    `Semester 7` VARCHAR(20), 
    `Semester 8` VARCHAR(20)
);

select * from fee;

insert into fee values("BTech", "49000", "43000","43000","43000","43000","43000","43000","43000");
insert into fee values("Bsc", "44000", "35000","35000","35000","35000","35000","","");
insert into fee values("BCA", "39000", "34000","34000","34000","34000","34000","","");
insert into fee values("MTech", "70000", "60000","60000","60000","","","","");
insert into fee values("MSc", "44000", "45000","45000","45000","","","","");
insert into fee values("MCA", "66000", "42000","42000","49000","","","","");
insert into fee values("Bcom", "32000", "20000","20000","20000","20000","20000","","");
insert into fee values("Mcom", "46000", "30000","30000","30000","","","","");

create table feecollege(
	rollNo varchar(20),
    course varchar(20),
    branch varchar(20),
    semester varchar(20),
    total varchar(20)
);
select * from feecollege;
