drop user 'flatmates'@'localhost';
create user 'flatmates'@'localhost' identified by 'flatmates';
grant all privileges on flatmatesdb.* to 'flatmates'@'localhost';
flush privileges;