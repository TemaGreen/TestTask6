CREATE TABLE city(
	name varchar(40),
	latitude decimal(24,16),
	longitude decimal(24,16),
	PRIMARY KEY(name)
); 

CREATE TABLE distance(
	fromcity varchar(40),
	tocity varchar(40),
	distance decimal(16,2),
	CONSTRAINT FK_fromcity FOREIGN KEY(fromcity)
		REFERENCES city(name) ON DELETE CASCADE,
	CONSTRAINT FK_tocity FOREIGN KEY(tocity)
		REFERENCES city(name) ON DELETE CASCADE,
	PRIMARY KEY(fromcity, tocity)
);