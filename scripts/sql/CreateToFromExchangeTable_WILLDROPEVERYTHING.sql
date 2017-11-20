USE csci201l_FinalProject;


CREATE TABLE CurrencyToFromExchange (
	ID int(11) primary key not null auto_increment,
    fromTicker varchar (10) not null,
    toTicker varchar (10) not null,
    exchangeName varchar (50) not null

);	