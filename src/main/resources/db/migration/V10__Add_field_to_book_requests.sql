alter table book_requests
    add column exchange_isbn varchar(255) references books (isbn),
    add column message text;
