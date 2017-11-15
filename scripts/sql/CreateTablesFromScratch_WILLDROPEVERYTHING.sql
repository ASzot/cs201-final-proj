USE csci201l_FinalProject;

DROP TABLE User;
CREATE TABLE User (
	userID int(11) primary key not null auto_increment,
    username varchar (50) not null,
    hashed_password varchar (50) not null,
    api_access_key varchar (50),
    api_private_key varchar (50)
);	

INSERT INTO User (username, hashed_password) VALUES ('testUser1', 'testing');


DROP TABLE CurrencyInfo;
CREATE TABLE CurrencyInfo(
	currencyID int(11) primary key not null auto_increment,
    ticker varchar (10) not null,
    description varchar (500)
);




DROP TABLE UserWatchList;
CREATE TABLE UserWatchList(
	id int(11) primary key not null auto_increment,
    userID int(11) not null,
    currencyID int(11) not null,
	FOREIGN KEY fk1 (userID) REFERENCES user(userID),
    FOREIGN KEY fk2 (currencyID) REFERENCES CurrencyInfo(currencyID)
);

/*INSERT INTO UserWatchList (userID, currencyID) VALUES (1, 1); COMMENTED OUT INORDER TO ADD ALL TICKERS