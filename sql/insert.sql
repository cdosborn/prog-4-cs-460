INSERT INTO cdosborn.lab           VALUES (1, 'Oracle Implants Laboratory');
INSERT INTO cdosborn.lab           VALUES (2, 'Grant Radiography');

INSERT INTO cdosborn.patient       VALUES (1, 'Connor', 'Osborn', DATE '1993-10-13');
INSERT INTO cdosborn.patient       VALUES (2, 'Margarita', 'Norzagaray', DATE '1993-01-15');

INSERT INTO cdosborn.supply        VALUES (1, 'X-Ray Bitewing', 5.00);
INSERT INTO cdosborn.supply        VALUES (2, 'X-Ray Occlusal Film', 5.00);

INSERT INTO cdosborn.service       VALUES (1, 'Root Canal', 30.00);
INSERT INTO cdosborn.service       VALUES (2, 'Oral Exam', 30.00);

-- #, service#, supply#, quantity
INSERT INTO cdosborn.servicesupply VALUES (1, 2, 1, 4);
INSERT INTO cdosborn.servicesupply VALUES (2, 2, 2, 1);

INSERT INTO cdosborn.appt          VALUES (1, 1, SYSDATE);
INSERT INTO cdosborn.labservice    VALUES (1, 1, 1);
INSERT INTO cdosborn.visit         VALUES (1, 1, 1);
