CREATE TABLE cdosborn.patient (
    patient# INTEGER   NOT NULL,
    fname VARCHAR2(20) NOT NULL,
    lname VARCHAR2(20) NOT NULL,
    dob DATE NOT NULL,
    PRIMARY KEY (patient#)
);

CREATE TABLE cdosborn.appt (
    appt# INTEGER NOT NULL,
    patient# INTEGER NOT NULL,
    time DATE NOT NULL,
    PRIMARY KEY (appt#),
    FOREIGN KEY (patient#) REFERENCES cdosborn.patient (patient#)
);

CREATE TABLE cdosborn.service (
    service# INTEGER NOT NULL,
    name VARCHAR2(30) NOT NULL,
    cost FLOAT NOT NULL,
    PRIMARY KEY (service#)
);

CREATE TABLE cdosborn.lab (
    lab# INTEGER NOT NULL,
    name VARCHAR2(30) NOT NULL,
    PRIMARY KEY (lab#)
);

CREATE TABLE cdosborn.labservice (
    labservice# INTEGER NOT NULL,
    lab# INTEGER NOT NULL,
    service# INTEGER NOT NULL,
    PRIMARY KEY (labservice#),
    FOREIGN KEY (lab#) REFERENCES cdosborn.lab ( lab# ),
    FOREIGN KEY (service#) REFERENCES cdosborn.service ( service# ),
    CONSTRAINT unique_lab_service UNIQUE ( lab#, service# )
);

CREATE TABLE cdosborn.supply (
    supply# INTEGER NOT NULL,
    name VARCHAR2(30) NOT NULL,
    cost FLOAT NOT NULL,
    PRIMARY KEY (supply#)
);

CREATE TABLE cdosborn.servicesupply (
    servicesupply# INTEGER NOT NULL,
    service# INTEGER NOT NULL,
    supply# INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    PRIMARY KEY (servicesupply#),
    CONSTRAINT unique_service_supply UNIQUE (service#, supply#)
);

CREATE TABLE cdosborn.labsupply (
    labsupply# INTEGER NOT NULL,
    lab# INTEGER NOT NULL,
    supply# INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    PRIMARY KEY (labsupply#),
    CONSTRAINT unique_lab_supply UNIQUE (lab#, supply#)
);

-- TODO: we need to find out which model to link this to (visit?)
CREATE TABLE cdosborn.payment (
    payment# INTEGER NOT NULL,
    time DATE NOT NULL,
    amount FLOAT NOT NULL,
    PRIMARY KEY(payment#)
);

CREATE TABLE cdosborn.visit (
    visit# INTEGER NOT NULL,
    appt# INTEGER NOT NULL,
    labservice# INTEGER NOT NULL,
    PRIMARY KEY (visit#),
    FOREIGN KEY (appt#) REFERENCES cdosborn.appt (appt#),
    FOREIGN KEY (labservice#) REFERENCES cdosborn.labservice (labservice#),
    CONSTRAINT unique_visit UNIQUE (appt#, labservice#)
);
