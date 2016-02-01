use bookcatalog; 


CREATE TABLE  book 

(book_id VARCHAR(36) NOT NULL, 

book_name VARCHAR(80) NOT NULL, 

year_published INT(4), 

isbn VARCHAR(13) UNIQUE, 

publisher VARCHAR(80), 

created_date DATETIME, 

PRIMARY KEY (book_id)) 

ENGINE InnoDB CHARACTER SET utf8; 

create table  if not exists author 

(author_id VARCHAR(36) NOT NULL ,

first_name VARCHAR(50) NOT NULL,

second_name VARCHAR(50) NOT NULL,

created_date DATETIME,
PRIMARY KEY (author_id))

ENGINE InnoDB CHARACTER SET utf8;

create table  if not exists book_author 


(
book_id VARCHAR(36) NOT NULL,


author_id VARCHAR(36) NOT NULL,


PRIMARY KEY (book_id, author_id),

fOREIGN KEY (book_id) REFERENCES book (book_id)

ON DELETE RESTRICT ON UPDATE CASCADE,

fOREIGN KEY (author_id) REFERENCES author(author_id))

ENGINE InnoDB CHARACTER SET utf8;

create table  if not exists review 


(review_id VARCHAR(36) NOT NULL,

book_id VARCHAR(36) NOT NULL,

commenter_name VARCHAR(50) NOT NULL,

comment_ TEXT NOT NULL,

rating int(1),

created_date DATETIME,

PRIMARY KEY (review_id),

fOREIGN KEY (book_id) REFERENCES book(book_id))

ENGINE InnoDB CHARACTER SET utf8;



