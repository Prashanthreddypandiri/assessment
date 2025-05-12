create database assesment; 

use assesment;


INSERT INTO books (id, title, author, publication_year, isbn, language, availability)
VALUES 
('123e4567-e89b-12d3-a456-426614174000', 'To Kill a Mockingbird', 'Harper Lee', 1960, '9780061120084', 'English', TRUE),
('f47ac10b-58cc-4372-a567-0e02b2c3d479', '1984', 'George Orwell', 1949, '9780451524935', 'English', TRUE),
('5c6a19e3-b8c5-477c-89a8-4d8df5d1f7d2', 'Pride and Prejudice', 'Jane Austen', 1813, '9781503290563', 'English', TRUE),
('7c3f9eae-4f4b-41a4-97e2-5a15c98de742', 'Moby-Dick', 'Herman Melville', 1851, '9781503280786', 'English', TRUE),
('4a8c48d6-9a6e-4416-bc8d-99e7a3cd5f04', 'War and Peace', 'Leo Tolstoy', 1869, '9781853260629', 'English', TRUE);

INSERT INTO users (user_id, name, email, phone_number)
VALUES ('123e4567-e89b-12d3-a456-426614174000', 'Alice', 'alice@example.com', '+123456789');

INSERT INTO reservations (reservation_id, user_id, book_id, reservation_date, due_date, status)
VALUES ('987e6543-e89b-12d3-a456-426614174111', '123e4567-e89b-12d3-a456-426614174000', '718f6027-4619-4775-bc68-d9b0a48181f4', '2025-04-01', '2025-04-15', 'reserved');



 