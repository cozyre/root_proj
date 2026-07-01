-- Dummy seed data

INSERT INTO users (id,username,email,password_hash,first_name,last_name,phone,bio,role,email_verified_at) VALUES
(2,'mentor_john','john@example.com','$2y$dummy','John','Tan','081111111112','Group mentor','mentor',NOW()),
(3,'coord_sarah','sarah@example.com','$2y$dummy','Sarah','Wijaya','081111111113','Group coordinator','koordinator',NOW()),
(4,'alice','alice@example.com','$2y$dummy','Alice','Santoso','081111111114','Member','user',NOW()),
(5,'bob','bob@example.com','$2y$dummy','Bob','Hartono','081111111115','Member','user',NOW()),
(6,'charlie','charlie@example.com','$2y$dummy','Charlie','Gunawan','081111111116','Member','user',NOW()),
(7,'diana','diana@example.com','$2y$dummy','Diana','Lim','081111111117','Member','user',NOW()),
(8,'edwin','edwin@example.com','$2y$dummy','Edwin','Lee','081111111118','Member','user',NOW());

INSERT INTO groups
(id,name,description,start_date,end_date,location,dresscode,meetup_time,meetup_address,mentor_id,koordinator_id,created_by,status)
VALUES
(1,'Young Adults Retreat','Weekend spiritual retreat','2026-07-10','2026-07-12','Bandung','Casual','08:00:00','Villa Lembang',2,3,1,'active'),
(2,'Bible Study Batch A','Weekly bible study','2026-08-01','2026-09-30','Jakarta','Free','19:00:00','Main Hall',2,3,1,'active');

INSERT INTO accounts (id,user_id,group_id,status_join,approved_date,approved_by,is_paid) VALUES
(1,4,1,'approved',NOW(),2,1),
(2,5,1,'approved',NOW(),2,1),
(3,6,1,'pending',NULL,NULL,0),
(4,7,1,'rejected',NULL,2,0),
(5,8,2,'approved',NOW(),2,1),
(6,4,2,'approved',NOW(),2,1);

INSERT INTO songs (id,title,author,lyrics,sort_order) VALUES
(1,'How Great Is Our God','Chris Tomlin','Sample lyrics...',1),
(2,'10,000 Reasons','Matt Redman','Sample lyrics...',2),
(3,'Goodness of God','Bethel Music','Sample lyrics...',3),
(4,'Build My Life','Pat Barrett','Sample lyrics...',4),
(5,'Way Maker','Sinach','Sample lyrics...',5);

INSERT INTO itenaries (id,group_id,itenary_date,itenary_desc) VALUES
(1,1,'2026-07-10','Arrival and Opening Session'),
(2,1,'2026-07-11','Morning Worship'),
(3,2,'2026-08-01','First Bible Study');

INSERT INTO itenary_items (id,itenary_id,start_time,end_time,type,description) VALUES
(1,1,'08:00:00','09:00:00','activity','Registration'),
(2,1,'09:00:00','09:30:00','song','Praise & Worship'),
(3,1,'09:30:00','10:30:00','devotion','Opening Devotion'),
(4,2,'07:00:00','07:30:00','song','Morning Worship'),
(5,2,'07:30:00','08:15:00','devotion','Reflection'),
(6,3,'19:00:00','20:30:00','activity','Bible Discussion');

INSERT INTO group_songs (id,group_id,song_id,itenary_id,sort_order) VALUES
(1,1,1,1,1),
(2,1,2,1,2),
(3,1,3,2,1),
(4,2,4,3,1),
(5,2,5,3,2);

INSERT INTO devotions (id,group_id,title,content,scripture_ref,devotion_date) VALUES
(1,1,'Faith in Action','Reflection on living out faith.','James 2:17','2026-07-10'),
(2,1,'Hope','Finding hope in difficult times.','Romans 15:13','2026-07-11'),
(3,2,'Walking Together','Growing together as a community.','Hebrews 10:24-25','2026-08-01');

INSERT INTO journals (id,user_id,group_id,title,content,journal_date) VALUES
(1,4,1,'First Day','I learned to trust God more.','2026-07-10'),
(2,5,1,'Grateful','Met many new friends.','2026-07-10'),
(3,4,2,'Bible Study Notes','Interesting discussion about faith.','2026-08-01');

INSERT INTO notifications (id,group_id,sender_id,title,body,type) VALUES
(1,1,2,'Welcome!','Welcome to the retreat. Please arrive on time.','announcement'),
(2,1,3,'Bring Bible','Remember to bring your Bible and notebook.','reminder'),
(3,2,2,'Weekly Study','See you this Saturday at 7 PM.','broadcast');

-- group_images intentionally omitted (image uploads ignored)
