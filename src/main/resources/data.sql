INSERT INTO PATIENT (first_name, last_name)
VALUES ('Krystian', 'Kostow'),
       ('Adrian', 'Malecki'),
       ('Kacper', 'Rogozinski'),
       ('Krystian', 'Krzynówek'),
       ('Mateusz', 'Bieniek'),
       ('Krzysztof', 'Wór');

INSERT INTO DOCTOR (first_name, last_name)
VALUES ('Jan', 'Milas'),
       ('Jan', 'Dobrzyniecki'),
       ('Piotr', 'Milaszewski'),
       ('Pola', 'Dobro'),
       ('Janusz', 'Kot'),
       ('Ewa', 'Mila');

INSERT INTO APPOINTMENT(doctor_id, patient_id, appointment_reason, term, appointment_time)
VALUES (1, 1, 'TESTS', ('2024-05-02 12:00'), 15),
       (6, 6, 'PREVENTION', ('2024-12-02 10:00'), 20),
       (5, 3, 'PREVENTION', ('2024-02-01 10:50'), 20),
       (1, 5, 'PREVENTION', ('2024-05-02 10:00'), 20),
       (2, 2, 'ILLNESS', ('2024-06-02 12:00'), 30),
       (3, 1, 'TESTS', ('2024-05-12 12:30'), 15),
       (5, 5, 'PREVENTION', ('2024-05-01 10:50'), 20),
       (4, 1, 'TESTS', ('2024-02-22 12:20'), 15),
       (2, 5, 'PREVENTION', ('2024-05-11 11:50'), 20),
       (3, 2, 'ILLNESS', ('2024-06-02 17:00'), 30),
       (1, 2, 'TESTS', ('2024-12-01 19:30'), 15),
       (6, 5, 'PREVENTION', ('2024-05-28 15:50'), 20),
       (4, 6, 'TESTS', ('2024-02-22 11:20'), 15),
       (1, 6, 'TESTS', ('2024-02-22 16:20'), 15),
       (4, 1, 'TESTS', ('2024-02-22 13:20'), 15),
       (4, 2, 'PREVENTION', ('2024-02-22 19:00'), 20);

