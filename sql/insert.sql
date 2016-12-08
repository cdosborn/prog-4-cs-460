INSERT INTO cdosborn.lab           VALUES (1, 'Oracle Implants Laboratory');
INSERT INTO cdosborn.lab           VALUES (2, 'Grant Radiography');
INSERT INTO cdosborn.lab           VALUES (3, 'Dental Prosthetics');

INSERT INTO cdosborn.patient       VALUES (1, 'Connor', 'Osborn', DATE '1993-10-13');
INSERT INTO cdosborn.patient       VALUES (2, 'Margarita', 'Norzagaray', DATE '1993-01-15');
-- The data here is for the 5th query
INSERT INTO cdosborn.patient       VALUES (3, 'Tuple', 'McRow', DATE '1876-12-15');
-- More patients 
INSERT INTO cdosborn.patient       VALUES (4, 'Madeline', 'Garay', DATE '1997-03-23'); 
INSERT INTO cdosborn.patient       VALUES (5, 'Manolo', 'Garay', DATE '1995-05-16');
INSERT INTO cdosborn.patient       VALUES (6, 'Danny', 'DeVito', DATE '1944-11-17');
INSERT INTO cdosborn.patient       VALUES (7, 'Deandra', 'Reynolds', DATE '1975-08-18');
INSERT INTO cdosborn.patient       VALUES (8, 'Charlie', 'Kelly', DATE '1976-02-09'); 
INSERT INTO cdosborn.patient       VALUES (9, 'Mary', 'Ellis', DATE '1979-05-11');
INSERT INTO cdosborn.patient       VALUES (10, 'Matthew', 'Mara', DATE '1975-12-01');
INSERT INTO cdosborn.patient       VALUES (11, 'Megan', 'Mullally', DATE '1985-11-12');
INSERT INTO cdosborn.patient       VALUES (12, 'Viola', 'Davis', DATE '1965-08-11');
INSERT INTO cdosborn.patient       VALUES (13, 'Tichina', 'Arnold', DATE '1969-06-28');
INSERT INTO cdosborn.patient       VALUES (14, 'Terry', 'Crews', DATE '1968-07-30');
INSERT INTO cdosborn.patient       VALUES (15, 'Alfonso', 'Herrera', DATE '1983-08-28');
INSERT INTO cdosborn.patient       VALUES (16, 'Bae', 'Doona', DATE '1979-10-11'); 
INSERT INTO cdosborn.patient       VALUES (17, 'Steven', 'Yeun', DATE '1983-12-21'); 
INSERT INTO cdosborn.patient       VALUES (18, 'Martha', 'Higadera', DATE '1983-08-24'); 
INSERT INTO cdosborn.patient       VALUES (19, 'Mariska', 'Hargitay', DATE '1964-01-23');
INSERT INTO cdosborn.patient       VALUES (20, 'Christopher', 'Meloni', DATE '1961-04-02'); 

INSERT INTO cdosborn.supply        VALUES (1, 'X-Ray Bitewing', 5.00);
INSERT INTO cdosborn.supply        VALUES (2, 'X-Ray Occlusal Film', 5.00);
-- Do not include this supply in any services, used in 4th query
INSERT INTO cdosborn.supply        VALUES (3, 'Fluoride 1L', 8.00);
-- More supplies 
INSERT INTO cdosborn.supply        VALUES (4, 'Gutta Percha', 5.00); 
INSERT INTO cdosborn.supply        VALUES (5, 'Amalgam', 15.00); 
INSERT INTO cdosborn.supply        VALUES (6, 'Anesthesia', 10.00); 

-- services 
INSERT INTO cdosborn.service       VALUES (1, 'Root Canal', 30.00);
INSERT INTO cdosborn.service       VALUES (2, 'Oral Exam', 30.00);
INSERT INTO cdosborn.service       VALUES (3, 'Amalgam Filling', 40.00); 
INSERT INTO cdosborn.service       VALUES (4, 'Wisdom Tooth Extraction', 35.00); 
INSERT INTO cdosborn.service       VALUES (5, 'Dental Cleaning', 20.00); 
INSERT INTO cdosborn.service       VALUES (6, 'Gum Surgery', 50.00);

-- procedure 
INSERT INTO cdosborn.procedure     VALUES (2); 
INSERT INTO cdosborn.procedure     VALUES (5);
INSERT INTO cdosborn.procedure     VALUES (6);

-- #, service#, supply#, quantity
INSERT INTO cdosborn.servicesupply VALUES (1, 1, 4, 7);
INSERT INTO cdosborn.servicesupply VALUES (2, 2, 2, 3); 
INSERT INTO cdosborn.servicesupply VALUES (3, 3, 5, 2); 
INSERT INTO cdosborn.servicesupply VALUES (4, 4, 1, 4); 
INSERT INTO cdosborn.servicesupply VALUES (5, 5, 3, 10); 
INSERT INTO cdosborn.servicesupply VALUES (6, 6, 6, 5);

-- labservice#, lab#, service#  
INSERT INTO cdosborn.labservice    VALUES (1, 1, 3);
INSERT INTO cdosborn.labservice    VALUES (2, 2, 1); 
INSERT INTO cdosborn.labservice    VALUES (3, 3, 4); 

-- labsupply#, lab#, supply#, qty
INSERT INTO cdosborn.labsupply     VALUES (1, 1, 5, 2);
INSERT INTO cdosborn.labsupply     VALUES (2, 2, 4, 7);
INSERT INTO cdosborn.labsupply     VALUES (3, 3, 1, 4); 

-- #, patient#, date
INSERT INTO cdosborn.appt          VALUES (1, 1, SYSDATE + 1);
INSERT INTO cdosborn.appt          VALUES (2, 2, SYSDATE + 1);
INSERT INTO cdosborn.appt          VALUES (3, 3, SYSDATE - 1);
INSERT INTO cdosborn.appt          VALUES (4, 4, SYSDATE);
INSERT INTO cdosborn.appt          VALUES (5, 5, SYSDATE - 30);
INSERT INTO cdosborn.appt          VALUES (6, 14, SYSDATE);
INSERT INTO cdosborn.appt          VALUES (7, 12, SYSDATE - 2);
INSERT INTO cdosborn.appt          VALUES (8, 17, SYSDATE - 22);
INSERT INTO cdosborn.appt          VALUES (9, 6, SYSDATE);
INSERT INTO cdosborn.appt          VALUES (10, 20, SYSDATE - 2);
INSERT INTO cdosborn.appt          VALUES (11, 11, SYSDATE - 22);
INSERT INTO cdosborn.appt          VALUES (12, 13, SYSDATE - 14);
INSERT INTO cdosborn.appt          VALUES (13, 12, SYSDATE - 25);
INSERT INTO cdosborn.appt          VALUES (14, 15, SYSDATE + 3);
INSERT INTO cdosborn.appt          VALUES (15, 7, SYSDATE + 3);
INSERT INTO cdosborn.appt          VALUES (16, 18, SYSDATE + 3);
INSERT INTO cdosborn.appt          VALUES (17, 19, SYSDATE + 5);
INSERT INTO cdosborn.appt          VALUES (18, 8, SYSDATE+ 2);
INSERT INTO cdosborn.appt          VALUES (19, 18, SYSDATE + 7);
INSERT INTO cdosborn.appt          VALUES (20, 9, SYSDATE + 6);
INSERT INTO cdosborn.appt          VALUES (21, 7, SYSDATE + 6 );
INSERT INTO cdosborn.appt          VALUES (22, 9, SYSDATE + 5);
INSERT INTO cdosborn.appt          VALUES (23, 10, SYSDATE + 7);
INSERT INTO cdosborn.appt          VALUES (24, 16, SYSDATE + 6);
INSERT INTO cdosborn.appt          VALUES (25, 1, SYSDATE - 6);

-- #, appt#, service#
INSERT INTO cdosborn.visit         VALUES (1, 3, 1);
INSERT INTO cdosborn.visit         VALUES (2, 4, 6); 
INSERT INTO cdosborn.visit         VALUES (3, 5, 3); 
INSERT INTO cdosborn.visit         VALUES (4, 6, 2); 
INSERT INTO cdosborn.visit         VALUES (5, 7, 4); 
INSERT INTO cdosborn.visit         VALUES (6, 8, 5); 
INSERT INTO cdosborn.visit         VALUES (7, 9, 3); 
INSERT INTO cdosborn.visit         VALUES (8, 10, 2); 
INSERT INTO cdosborn.visit         VALUES (9, 11, 4); 
INSERT INTO cdosborn.visit         VALUES (10, 12, 3); 
INSERT INTO cdosborn.visit         VALUES (11, 13, 5); 

-- payment#, patient#, time, amount,
INSERT INTO cdosborn.payment       VALUES (1, 3, SYSDATE - 1, 30.00);
INSERT INTO cdosborn.payment       VALUES (2, 5, SYSDATE - 30, 35.00);
INSERT INTO cdosborn.payment       VALUES (3, 12, SYSDATE - 2, 20.00);
INSERT INTO cdosborn.payment       VALUES (4, 17, SYSDATE - 22, 20.00);
INSERT INTO cdosborn.payment       VALUES (5, 11, SYSDATE - 22, 35.00);
INSERT INTO cdosborn.payment       VALUES (6, 13, SYSDATE - 14, 20.00);
INSERT INTO cdosborn.payment       VALUES (7, 12, SYSDATE - 25, 20.00);
