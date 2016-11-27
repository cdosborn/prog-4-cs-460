CREATE TABLE cdosborn.patient (
    patient# INTEGER NOT NULL,
    fname VARCHAR2(20) NOT NULL,
    lname VARCHAR2(20) NOT NULL,
    dob DATE NOT NULL,
    PRIMARY KEY (patient#)
);

CREATE TABLE cdosborn.appt (
    appt# INTEGER,
    patient# INTEGER,
    time DATE,
    PRIMARY KEY (appt#),
    FOREIGN KEY (patient#) REFERENCES cdosborn.patient (patient#)
);

CREATE TABLE cdosborn.service (
    service# INTEGER,
    name VARCHAR2(30),
    cost FLOAT,
    PRIMARY KEY (service#)
);

CREATE TABLE cdosborn.lab (
    lab# INTEGER,
    name VARCHAR2(30),
    PRIMARY KEY (lab#)
);

CREATE TABLE cdosborn.labservice (
    labservice# INTEGER,
    lab# INTEGER,
    service# INTEGER,
    PRIMARY KEY (labservice#),
    FOREIGN KEY (lab#) REFERENCES cdosborn.lab ( lab# ),
    FOREIGN KEY (service#) REFERENCES cdosborn.service ( service# ),
    CONSTRAINT unique_lab_service UNIQUE ( labservice#, lab#, service# )
);

CREATE TABLE cdosborn.supply (
    supply# INTEGER,
    name VARCHAR2(30),
    cost FLOAT,
    PRIMARY KEY (supply#)
);

CREATE TABLE cdosborn.servicesupply (
    servicesupply# INTEGER,
    service# INTEGER,
    supply# INTEGER,
    quantity INTEGER,
    PRIMARY KEY (servicesupply#),
    CONSTRAINT unique_service_supply UNIQUE (servicesupply#, service#, supply#)
);

CREATE TABLE cdosborn.labsupply (
    labsupply# INTEGER,
    lab# INTEGER,
    supply# INTEGER,
    quantity INTEGER,
    PRIMARY KEY (labsupply#),
    CONSTRAINT unique_lab_supply UNIQUE (labsupply#, lab#, supply#)
);

CREATE TABLE cdosborn.payment (
    payment# INTEGER,
    time DATE,
    amount FLOAT,
    PRIMARY KEY(payment#)
);

CREATE TABLE cdosborn.visit (
    visit# INTEGER,
    appt# INTEGER,
    labservice# INTEGER,
    PRIMARY KEY (visit#),
    FOREIGN KEY (appt#) REFERENCES cdosborn.appt (appt#),
    FOREIGN KEY (labservice#) REFERENCES cdosborn.labservice (labservice#),
    CONSTRAINT unique_visit UNIQUE (visit#, appt#, labservice#)
);
