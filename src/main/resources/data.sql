INSERT INTO USER (USER_ID, USERNAME, LAST_FM_USERNAME, CITY) VALUES
(1,'test1', '', 'Warszawa'),
(2,'test2', '', 'Warszawa'),
(3,'test3', '', 'Warszawa');

INSERT INTO STORED_EVENT(ID, CURRENCY, DATETBA, DATETBD, IMAGE_URL, LOCAL_DATE, LOCAL_TIME, NAME, PRICE_MAX, PRICE_MIN,SALE_END_DATE, SALE_START_DATE, TICKETMASTER_ID, TIMETBA, URL, VENUE_ADDRESS, VENUE_CITY, VENUE_NAME) VALUES
(1, 'PLN', FALSE, FALSE , 'https://s1.ticketm.net/dam/a/21f/5ea9a6d7-cf3b-4e49-85a7-4a05f4f1121f_847541_TABLET_LANDSCAPE_16_9.jpg', '2019-08-21', '16:30:00', 'Metallica', 479.0, 229.0, '2019-08-21T18:00:00Z', '2018-09-28T08:00:00Z', 'Z598xZQpZa7Ak',FALSE, 'https://www.ticketmaster.pl/event/metallica-bilety/8345', 'Al. Ks. J. Poniatowskiego 1', 'Warszawa', 'PGE Narodowy'),
(2, 'PLN', FALSE, FALSE , 'https://s1.ticketm.net/dam/a/21f/5ea9a6d7-cf3b-4e49-85a7-4a05f4f1121f_847541_TABLET_LANDSCAPE_16_9.jpg', '2019-08-21', '16:30:00', 'Metallica', 9290.0, 850.0, '2019-08-21T18:00:00Z', '2018-09-28T08:00:00Z', 'Z598xZQpZa1d1',FALSE, 'https://www.ticketmaster.pl/event/metallica-bilety/8929', 'Al. Ks. J. Poniatowskiego 1', 'Warszawa', 'PGE Narodowy');

INSERT INTO USER_EVENT(KEYWORD, STATUS, TYPE, STORED_EVENT_ID, USER_USER_ID) VALUES
('metallica', 'LIKED', 'MUSIC', 1, 1),
('narodowy', 'LIKED', 'OTHER', 2, 1);