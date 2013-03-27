# --- First database schema

# --- !Ups

CREATE SEQUENCE user_id_seq;
CREATE TABLE user(
    email   varchar(255) not null primary key,
    name    varchar(255) not null,
    password varchar(255) not null,
    admin   boolean default false not null
);


CREATE SEQUENCE project_seq start with 1000;
CREATE TABLE PROJECT(
    id          long NOT NULL DEFAULT nextval('project_seq_start') PRIMARY KEY,
    name        varchar(255) not null,
    content     varchar(512) not null,
    owner       varchar(255) null,
    foreign key(owner) references user(email) on delete cascade
);


create sequence campaign_seq start with 1000;
create table campaign(
    id              long not null default nextval('campaign_seq') primary key,
    project_id      long not null,
    active          boolean,
    due_date        timestamp,
    foreign key(project_id) references project(id) on delete cascade
);

create table contributions(
    email           varchar(255) not null,
    campaign        long not null
);


# ---!Downs

drop table if exists user;
drop sequence if exists user_id_seq;
drop sequence if exists project_seq;
drop table if exists project;
drop sequence if exists campaign_seq;
drop table if exists campaign;
drop table if exists contributions;