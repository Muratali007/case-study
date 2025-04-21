CREATE SEQUENCE book_compilation_sequence START 1 INCREMENT 1;

CREATE TABLE IF NOT EXISTS book_compilations (
    id          BIGINT PRIMARY KEY DEFAULT NEXTVAL('book_compilation_sequence'),
    name        VARCHAR(255),
    description TEXT,
    image_url   VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS book_compilation_books (
                                                      id                   BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                                      book_compilation_id   BIGINT REFERENCES book_compilations (id),
    book_isbn             VARCHAR(255) REFERENCES books (isbn)
    );
