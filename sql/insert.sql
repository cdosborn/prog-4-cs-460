INSERT INTO cdosborn.lab           VALUES (1, 'Oracle Implants Laboratory');
INSERT INTO cdosborn.lab           VALUES (2, 'Grant Radiography');
INSERT INTO cdosborn.lab           VALUES (3, 'Dental Prosthetics');

INSERT INTO cdosborn.patient       VALUES (1, 'Connor', 'Osborn', DATE '1993-10-13');
INSERT INTO cdosborn.patient       VALUES (2, 'Margarita', 'Norzagaray', DATE '1993-01-15');
-- The data here is for the 5th query
INSERT INTO cdosborn.patient       VALUES (3, 'Tuple', 'McRow', DATE '1876-12-15');

INSERT INTO cdosborn.supply        VALUES (1, 'X-Ray Bitewing', 5.00);
INSERT INTO cdosborn.supply        VALUES (2, 'X-Ray Occlusal Film', 5.00);
-- Do not include this supply in any services, used in 4th query
INSERT INTO cdosborn.supply        VALUES (3, 'Fluoride 1L', 8.00);

INSERT INTO cdosborn.service       VALUES (1, 'Root Canal', 30.00);
INSERT INTO cdosborn.service       VALUES (2, 'Oral Exam', 30.00);
INSERT INTO cdosborn.service       VALUES (3, 'Teeth Cleaning', 25.00);

-- #, service#, supply#, quantity
INSERT INTO cdosborn.servicesupply VALUES (1, 2, 1, 4);
INSERT INTO cdosborn.servicesupply VALUES (2, 2, 2, 1);

INSERT INTO cdosborn.labservice    VALUES (1, 1, 1);
INSERT INTO cdosborn.labservice    VALUES (2, 1, 2);
INSERT INTO cdosborn.labservice    VALUES (3, 2, 1);
INSERT INTO cdosborn.labservice    VALUES (4, 3, 2);

INSERT INTO cdosborn.procedure    VALUES (3);

-- #, patient#, date
INSERT INTO cdosborn.appt          VALUES (1, 1, SYSDATE + 1);
INSERT INTO cdosborn.appt          VALUES (2, 2, SYSDATE + 1);
INSERT INTO cdosborn.appt          VALUES (3, 1, SYSDATE - 1);
INSERT INTO cdosborn.appt          VALUES (4, 2, SYSDATE);

-- #, appt#, service#
INSERT INTO cdosborn.visit         VALUES (1, 1, 1);
INSERT INTO cdosborn.visit         VALUES (2, 2, 2);
INSERT INTO cdosborn.visit         VALUES (3, 3, 2);

-- payment#, patient#, time, amount,
INSERT INTO cdosborn.payment       VALUES (1, 2, DATE '2020-10-05', 25.00);
