CREATE TABLE Buildings(
	BuildingID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	Name VARCHAR(20) NOT NULL,
	Floors INT NOT NULL,
	Coordinates VARCHAR(250) NOT NULL
);

CREATE TABLE Rooms(
	RoomID INT PRIMARY KEY AUTO_INCREMENT NOT NULL, 
	BuildingID INT NOT NULL,
	Name VARCHAR(20) NOT NULL,
	Floor INT NOT NULL,
	Coordinates VARCHAR(250) NOT NULL,
	FOREIGN KEY(BuildingID) REFERENCES Buildings(BuildingID)
);

CREATE TABLE Bookings(
	BookingID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	RoomID INT NOT NULL,
	FromTime VARCHAR(5) NOT NULL,
	ToTime VARCHAR(5) NOT NULL,
	FOREIGN KEY(RoomID) REFERENCES Rooms(RoomID)
);

INSERT INTO Buildings values('', 'Pilestredet 35', 8, '59.919390, 10.735208');
INSERT INTO Buildings values('', 'Pilestredet 32', 4, '59.920050, 10.736118');

INSERT INTO Rooms values('', 1, 'PH170 Auditorium', 1, '59.919453, 10.735152'); 
INSERT INTO Rooms values('', 1, 'PH131 Auditorium', 1, '59.919293, 10.735110'); 
INSERT INTO Rooms values('', 1, 'PH372', 3, '59.919400, 10.735054'); 
INSERT INTO Rooms values('', 1, 'PH374', 3, '59.919444, 10.735256'); 
INSERT INTO Rooms values('', 1, 'PH355', 3, '59.919441, 10.734835'); 
INSERT INTO Rooms values('', 1, 'PH472', 4, '59.919426, 10.735154'); 
INSERT INTO Rooms values('', 1, 'PH445', 4, '59.919458, 10.735728'); 
INSERT INTO Rooms values('', 1, 'PH439', 4, '59.919232, 10.734666'); 
INSERT INTO Rooms values('', 2, 'N010_023', 1, '59.920050, 10.736118'); 
INSERT INTO Rooms values('', 2, 'N020_025', 2, '59.920050, 10.736118'); 
INSERT INTO Rooms values('', 2, 'N040_005', 4, '59.920050, 10.736118'); 


INSERT INTO Bookings values('', 1, '12:00', '13:00');
INSERT INTO Bookings values('', 2, '15:00', '16:00');
INSERT INTO Bookings values('', 3, '14:00', '15:00');
INSERT INTO Bookings values('', 1, '14:00', '15:00');
INSERT INTO Bookings values('', 9, '14:00', '15:00');

How to see all bookings belonging to Room With ID = 1
SELECT * FROM Bookings WHERE RoomID = (SELECT RoomID FROM Rooms where RoomID = 1); 

Getting all rooms in a building:
SELECT * FROM Rooms WHERE BuildingID = (SELECT BuildingID FROM Buildings where BuildingID = 1);


		
		