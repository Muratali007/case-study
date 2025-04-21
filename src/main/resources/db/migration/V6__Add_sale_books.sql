create table if not exists sale_books
(
    id      bigserial primary key,
    user_id bigint references users (id),
    isbn    varchar(255) references books (isbn),
    user_book_id bigint references user_books (id),
    new    boolean not null,
    image_url varchar(255),
    price int
);
