# AutoExam系统设计
### 表设计
```roomsql
create table admin (
    id bigserial primary key,
    name varchar(128) not null,
    description varchar(1024) not null default ''
);
create table teacher (
    id bigserial primary key,
    name varchar(128) not null,
    description varchar(1024) not null default '',
    admin_id bigint not null references admin(id)
);
create table student (
    id bigserial primary key,
    name varchar(128) not null,
    description varchar(1024) not null default ''
);
create table course (
    id bigserial primary key,
    name varchar(128) not null,
    description varchar(1024) not null default ''
);
create table chapter (
    id bigserial primary key,
    name varchar(128) not null,
    description varchar(1024) not null default '',
    course_id bigint not null references course(id)
);
create table question (
    id bigserial primary key,
    type varchar(64) not null,
    content varchar(4096) not null,
    description varchar(4096) not null default '',
    chapter_id bigint not null references chapter(id)
);
create table selection (
    id bigserial primary key,
    content varchar(1024) not null,
    is_answer boolean not null,
    question_id bigint not null references question(id)
);
```
### API设计
## 系统前端设计
## 系统小程序访问设计

```bash
mvn spring-boot:run
```

```bash
mvn clean package
java -jar target/postgres-demo-0.0.1-SNAPSHOT.jar
```