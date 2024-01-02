DELETE FROM teacher;

INSERT INTO TEACHER (id, degree, name, surname, teacher_source_id)
VALUES (1, 'Master degree', 'Wendy', 'Green', null);
INSERT INTO TEACHER (id, degree, name, surname, teacher_source_id)
VALUES (2, 'Master degree', 'Max', 'Newton', null);
INSERT INTO TEACHER (id, degree, name, surname, teacher_source_id)
VALUES (3, 'Engineer degree', 'Bill', 'Blake', null);
INSERT INTO TEACHER (id, degree, name, surname, teacher_source_id)
VALUES (4, 'Engineer degree', 'Bud', 'Paul', null);
INSERT INTO TEACHER (id, degree, name, surname, teacher_source_id)
VALUES (5, 'Engineer degree', 'Cecil', 'Hanson', null);

DELETE FROM STUDENT;

INSERT INTO STUDENT (id, email, name, surname, status)
VALUES (1, 'Bush@wp.pl', 'Shane', 'Bush', 1);
INSERT INTO STUDENT (id, email, name, surname, status)
VALUES (2, 'Cress@wp.pl', 'Sheila', 'Cress', 1);
INSERT INTO STUDENT (id, email, name, surname, status)
VALUES (3, 'Fuller@wp.pl', 'Veronica', 'Fuller', 1);
INSERT INTO STUDENT (id, email, name, surname, status)
VALUES (4, 'Clarissa@wp.pl', 'Stanley', 'Clarissa', 1);
INSERT INTO STUDENT (id, email, name, surname, status)
VALUES (5, 'Brown@wp.pl', 'Michael', 'Brown', 1);

DELETE FROM COURSE;

INSERT INTO COURSE (id, description, end_date, name, participant_limit, participant_number, start_date, status, value)
VALUES (1, 'Course for beginners', CAST('2022-11-11 08:00:00' AS TIMESTAMP), 'Java course', 100, 0, CAST('2022-10-11 08:00:00' AS TIMESTAMP), 1, null);

INSERT INTO COURSE (id, description, end_date, name, participant_limit, participant_number, start_date, status, value)
VALUES (2, 'Advanced Course', CAST('2022-10-11 08:00:00' AS TIMESTAMP), 'Java course', 100, 0, CAST('2022-09-11 08:00:00' AS TIMESTAMP), 1, null);

INSERT INTO COURSE (id, description, end_date, name, participant_limit, participant_number, start_date, status, value)
VALUES (3, 'Course for beginners', CAST('2022-11-21 08:00:00' AS TIMESTAMP), 'Kotlin course', 100, 0, CAST('2022-11-11 08:00:00' AS TIMESTAMP), 1, null);

INSERT INTO COURSE (id, description, end_date, name, participant_limit, participant_number, start_date, status, value)
VALUES (4, 'Course for beginners', CAST('2022-03-11 08:00:00' AS TIMESTAMP), 'Python course', 100, 0, CAST('2022-01-11 08:00:00' AS TIMESTAMP), 1, null);

INSERT INTO COURSE (id, description, end_date, name, participant_limit, participant_number, start_date, status, value)
VALUES (5, 'Advanced Course', CAST('2022-05-11 08:00:00' AS TIMESTAMP), 'Kotlin course', 100, 0, CAST('2022-03-11 08:00:00' AS TIMESTAMP), 1, null);