ALTER TABLE authors
    ADD COLUMN total_books BIGINT DEFAULT 0;

CREATE OR REPLACE PROCEDURE update_total_books()
    LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE authors a
    SET total_books = (
        SELECT COUNT(*)
        FROM books b
        WHERE b.author_id = a.id
    );
END;
$$;

CALL update_total_books();
