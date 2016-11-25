CREATE TABLE mnorzagaray.patient ( 
	patient# integer,
	lname varchar2(20), 
	fname varchar2(20), 
	dob DATE,
	primary key (patient#) 
); 

CREATE TABLE mnorzagaray.appt (
	appt# integer, 
	patient# integer, 
	date DATE,
	primary key (appt#) 
); 

CREATE TABLE mnorzagaray.visit (
	appt# integer, 
	labservice# integer,
	payment# integer, 
	primary key (appt#, labservice#) 
);

CREATE TABLE mnorzagaray.service (
	service# integer, 
	name varchar2(30), 
	cost float, 
	primary key (service#)
); 

CREATE TABLE mnorzagaray.supply (
	supply# integer,
	name varchar2(30), 
	cost float, 
	primary key (supply#) 
); 

CREATE TABLE mnorzagaray.servicesupply (
	service# integer, 
	supply# integer,
	quantity integer, 
	primary key (service#, supply#)  
); 

CREATE TABLE mnorzagaray.labservice (
	lab# integer, 
	service# integer, 
	primary key (lab#, service#) 
) 

CREATE TABLE mnorzagaray.lab (
	lab# integer, 
	name varchar2(30)
	primary key (lab#) 
);

CREATE TABLE mnorzagaray.labsupply (
	lab# integer, 
	supply# integer,
	quantity integer,
	primary key (lab#, supply#)  
); 

CREATE TABLE mnorzagaray.payment (
	payment# integer, 
	date DATE, 
	amount float
	primary key(payment#)  
);  

GRANT ALL PRIVILEGES ON  mnorzagaray.patient TO cdosborn;
GRANT ALL PRIVILEGES ON  mnorzagaray.appt TO cdosborn;
GRANT ALL PRIVILEGES ON  mnorzagaray.visit TO cdosborn;
GRANT ALL PRIVILEGES ON  mnorzagaray.service TO cdosborn;
GRANT ALL PRIVILEGES ON  mnorzagaray.supply TO cdosborn;
GRANT ALL PRIVILEGES ON  mnorzagaray.servicesupply TO cdosborn;
GRANT ALL PRIVILEGES ON  mnorzagaray.labservice TO cdosborn;
GRANT ALL PRIVILEGES ON  mnorzagaray.lab TO cdosborn;
GRANT ALL PRIVILEGES ON  mnorzagaray.labsupply TO cdosborn;
GRANT ALL PRIVILEGES ON  mnorzagaray.payment TO cdosborn;

GRANT SELECT ON mnorzagaray.patient TO PUBLIC;
GRANT SELECT ON mnorzagaray.appt TO PUBLIC;
GRANT SELECT ON mnorzagaray.visit TO PUBLIC;
GRANT SELECT ON mnorzagaray.service TO PUBLIC;
GRANT SELECT ON mnorzagaray.supply TO PUBLIC;
GRANT SELECT ON mnorzagaray.servicesupply TO PUBLIC;
GRANT SELECT ON mnorzagaray.labservice TO PUBLIC;
GRANT SELECT ON mnorzagaray.lab TO PUBLIC;
GRANT SELECT ON mnorzagaray.labsupply TO PUBLIC;
GRANT SELECT ON mnorzagaray.payment TO PUBLIC;

