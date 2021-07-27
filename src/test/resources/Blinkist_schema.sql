create table blinkist_user (
    user_id uuid,
    subscription_id uuid,
    name varchar(50),
    email varchar(50) UNIQUE,
    password varchar(100),
    username varchar(30) UNIQUE,
    phone NUMERIC UNIQUE,
    is_active boolean,
    creation_date TIMESTAMP,
    is_admin boolean,
    PRIMARY KEY(user_id)
);

create table genre(
    genre_id UUID,
    category_name varchar(30),
    description varchar(100),
    PRIMARY KEY(genre_id)

);

create table languages(
    language_id UUID,
    language_name varchar(20),
    language_code varchar(5),
    is_active boolean,
    PRIMARY KEY(language_id)
);


create table book(
    book_id UUID,
    book_title varchar(50),
    creation_date DATE,
    author varchar(50),
    cover_image BYTEA,
    is_active boolean,
    language_id UUID,
    genre_id UUID,
    number_of_reads INTEGER,
    PRIMARY KEY(book_id),
    FOREIGN KEY(language_id)
        REFERENCES languages(language_id),
    FOREIGN KEY(genre_id)
        REFERENCES genre(genre_id)
);



\

create table library(
    book_id UUID,
    subscription_id UUID,
    date_added DATE,
    is_completed boolean,
    PRIMARY KEY(subscription_id, book_id),
    FOREIGN KEY(subscription_id )
        REFERENCES subscription(subscription_id ),
    FOREIGN KEY(book_id)
        REFERENCES book(book_id)
);

create table subscription(
    subscription_id UUID,
    is_finished boolean,
creation_date DATE
finish_date DATE
    description varchar(100),
    PRIMARY KEY(genre_id)

);
