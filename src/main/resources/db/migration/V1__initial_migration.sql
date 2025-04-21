create table if not exists authors
(
    id          bigserial primary key,
    full_name   varchar(255),
    image_url   varchar(255),
    description text
);

create table if not exists book_links
(
    id                   bigserial primary key,
    marwin_link          varchar(255),
    marwin_actual_price  int,
    marwin_current_price int,
    flip_link            varchar(255),
    flip_actual_price    int,
    flip_current_price   int,
    book24_link          varchar(255),
    book24_actual_price  int,
    book24_current_price int
);

create table if not exists books
(
    isbn            varchar(255) primary key,
    name            varchar(255)                      not null,
    author_id       bigint references authors (id)    not null,
    genre           varchar(255),
    description     text,
    year            int,
    rating          double precision,
    age             varchar(255),
    image_url       varchar(255),
    language        varchar(255),
    publisher       varchar(255),
    create_at       timestamp default now(),
    updated_at      timestamp default now(),
    book_link_id    bigint references book_links (id) not null,
    book_clicks     bigint    default 0,
    redirect_clicks bigint    default 0
);

create table if not exists social_links
(
    id             bigserial primary key not null,
    instagram_link varchar(255),
    telegram_link  varchar(255)
);

create table if not exists users
(
    id             bigserial primary key,
    username       varchar(255) unique,
    name           varchar(255),
    surname        varchar(255),
    email          varchar(255),
    phone          varchar(255),
    birth_date     date,
    location       varchar(255),
    social_link_id bigint references social_links (id)
);

create type user_book_status as enum ('READ','READING','PLANNING');

create table if not exists user_books
(
    id      bigserial primary key,
    user_id bigint references users (id),
    isbn    varchar(255) references books (isbn),
    have    boolean not null,
    status  user_book_status
);

create table if not exists given_books
(
    id               bigserial primary key,
    sender_user_id   bigint references users (id)         not null,
    receiver_user_id bigint references users (id)         not null,
    isbn             varchar(255) references books (isbn) not null
);

create table if not exists sold_books
(
    id               bigserial primary key,
    sender_user_id   bigint references users (id)         not null,
    receiver_user_id bigint references users (id)         not null,
    isbn             varchar(255) references books (isbn) not null
);

create type book_request_type as enum ('EXCHANGE','BUY');
create type book_request_status as enum ('PENDING','ACCEPTED','REJECTED');

create table if not exists book_requests
(
    id               bigserial primary key,
    sender_user_id   bigint references users (id)         not null,
    receiver_user_id bigint references users (id)         not null,
    isbn             varchar(255) references books (isbn) not null,
    type             book_request_type                    not null,
    status           book_request_status                  not null default 'PENDING',
    begin_date       timestamp                            not null default now(),
    end_date         timestamp
)
