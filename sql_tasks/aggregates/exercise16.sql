CREATE TABLE cd.facilities (
    facid INTEGER PRIMARY KEY,
    name VARCHAR(100),
    membercost NUMERIC,
    guestcost NUMERIC,
    initialoutlay NUMERIC,
    monthlymaintenance NUMERIC
);


INSERT INTO cd.facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);

CREATE TABLE cd.members (
    memid INTEGER PRIMARY KEY,
    surname VARCHAR(200),
    firstname VARCHAR(200),
    address VARCHAR(300),
    zipcode INTEGER,
    telephone VARCHAR(20),
    recommendedby INTEGER,
    joindate TIMESTAMP
);

INSERT INTO cd.members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');

CREATE TABLE cd.bookings (
    facid INTEGER,
    memid INTEGER,
    starttime TIMESTAMP,
    slots INTEGER,
    FOREIGN KEY (facid) REFERENCES cd.facilities(facid),
    FOREIGN KEY (memid) REFERENCES cd.members(memid)
);

INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (1, 5, 5, '2012-07-11 16:28:18', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (2, 1, 7, '2012-09-21 04:15:16', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (3, 8, 9, '2012-07-11 10:30:50', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (4, 5, 1, '2012-09-27 08:59:26', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (5, 8, 8, '2012-07-11 18:28:37', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (6, 8, 8, '2012-08-31 19:35:16', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (7, 9, 6, '2012-07-17 16:58:29', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (8, 8, 7, '2012-08-23 05:02:35', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (9, 8, 6, '2012-07-28 04:26:32', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (10, 2, 1, '2012-07-23 13:52:30', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (11, 4, 9, '2012-09-13 20:57:01', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (12, 3, 5, '2012-08-11 06:14:02', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (13, 9, 3, '2012-08-07 04:04:27', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (14, 6, 6, '2012-09-06 05:19:42', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (15, 5, 8, '2012-08-22 09:15:03', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (16, 1, 3, '2012-09-20 20:29:34', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (17, 7, 7, '2012-09-13 18:46:34', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (18, 9, 2, '2012-07-24 12:47:58', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (19, 4, 1, '2012-09-05 15:50:48', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (20, 1, 5, '2012-07-04 06:10:09', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (21, 8, 9, '2012-09-28 15:58:19', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (22, 8, 6, '2012-09-21 00:37:21', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (23, 5, 8, '2012-09-06 00:19:25', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (24, 4, 3, '2012-08-29 06:12:41', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (25, 6, 3, '2012-09-02 01:25:38', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (26, 1, 8, '2012-07-05 23:23:33', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (27, 4, 5, '2012-09-10 10:27:16', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (28, 5, 6, '2012-09-29 01:40:25', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (29, 3, 4, '2012-09-23 12:28:29', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (30, 7, 5, '2012-07-08 16:40:18', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (31, 4, 4, '2012-08-01 22:51:31', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (32, 9, 8, '2012-09-13 12:59:17', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (33, 6, 8, '2012-09-15 20:39:16', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (34, 5, 9, '2012-08-30 23:23:19', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (35, 2, 7, '2012-09-11 18:03:22', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (36, 3, 1, '2012-07-15 16:34:11', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (37, 3, 3, '2012-08-27 09:24:59', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (38, 4, 3, '2012-08-30 11:07:52', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (39, 9, 8, '2012-09-12 23:39:51', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (40, 1, 6, '2012-07-15 14:34:56', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (41, 2, 4, '2012-07-10 06:44:44', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (42, 3, 8, '2012-08-04 12:26:16', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (43, 9, 3, '2012-08-21 22:31:43', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (44, 5, 3, '2012-09-21 06:33:55', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (45, 2, 9, '2012-07-05 13:52:14', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (46, 2, 4, '2012-08-23 21:44:18', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (47, 5, 8, '2012-09-07 08:49:57', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (48, 9, 4, '2012-09-01 13:23:23', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (49, 3, 8, '2012-07-13 10:10:01', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (50, 3, 6, '2012-09-18 07:13:16', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (51, 7, 1, '2012-08-15 06:58:08', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (52, 6, 1, '2012-08-05 21:23:45', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (53, 4, 7, '2012-07-01 14:15:35', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (54, 5, 8, '2012-08-04 22:52:34', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (55, 2, 8, '2012-09-18 23:58:32', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (56, 1, 4, '2012-09-07 11:33:43', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (57, 1, 4, '2012-09-08 12:57:44', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (58, 6, 7, '2012-09-27 23:41:19', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (59, 3, 5, '2012-09-26 03:41:40', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (60, 6, 3, '2012-07-21 21:57:01', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (61, 7, 8, '2012-07-11 12:10:16', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (62, 4, 6, '2012-09-05 21:41:20', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (63, 8, 4, '2012-08-06 22:32:43', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (64, 4, 4, '2012-08-18 06:20:47', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (65, 4, 8, '2012-08-28 19:58:38', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (66, 5, 2, '2012-07-13 01:58:06', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (67, 3, 2, '2012-08-23 15:04:20', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (68, 4, 5, '2012-07-29 12:38:27', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (69, 4, 8, '2012-09-21 10:23:47', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (70, 6, 7, '2012-08-27 14:21:35', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (71, 9, 8, '2012-09-04 00:00:23', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (72, 1, 9, '2012-07-26 22:41:14', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (73, 4, 7, '2012-09-10 20:09:25', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (74, 3, 6, '2012-07-17 11:01:18', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (75, 3, 5, '2012-09-28 02:56:54', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (76, 4, 5, '2012-09-04 11:16:17', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (77, 5, 8, '2012-07-16 19:20:21', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (78, 9, 4, '2012-09-20 13:45:49', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (79, 8, 7, '2012-08-24 01:00:25', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (80, 3, 7, '2012-08-19 13:41:23', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (81, 2, 7, '2012-09-16 11:53:48', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (82, 4, 1, '2012-07-19 01:09:59', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (83, 2, 1, '2012-08-02 23:03:08', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (84, 6, 8, '2012-08-10 16:09:30', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (85, 1, 4, '2012-09-20 14:15:09', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (86, 9, 7, '2012-07-25 07:36:01', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (87, 5, 7, '2012-09-04 11:55:46', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (88, 8, 6, '2012-08-13 10:50:47', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (89, 2, 8, '2012-07-11 11:03:47', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (90, 4, 1, '2012-08-29 01:19:39', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (91, 6, 4, '2012-08-01 01:41:41', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (92, 5, 4, '2012-09-01 06:38:13', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (93, 3, 1, '2012-07-25 17:52:38', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (94, 5, 6, '2012-08-04 16:45:33', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (95, 6, 8, '2012-08-15 17:16:59', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (96, 2, 3, '2012-08-09 08:25:45', 2);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (97, 6, 5, '2012-09-15 12:33:23', 3);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (98, 4, 8, '2012-09-10 16:49:37', 1);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (99, 7, 4, '2012-09-22 19:41:32', 4);
INSERT INTO cd.bookings (bookid, facid, memid, starttime, slots) VALUES (100, 6, 9, '2012-09-02 02:51:48', 3);


select row_number() over (), firstname, surname from cd.members





  