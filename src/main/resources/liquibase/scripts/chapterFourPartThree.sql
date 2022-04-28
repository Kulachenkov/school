--liquibase formatted sql

--changeset userOne:1
CREATE INDEX student_name_index ON student (name);

--changeset userOne:2
CREATE INDEX faculty_name_color_index ON faculty (color, name) ;