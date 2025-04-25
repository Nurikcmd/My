-- Добавление игроков
INSERT INTO players (name, rating, country, email) VALUES
('Магнус Карлсен', 2830, 'Норвегия', 'magnus@chess.com'),
('Фабиано Каруана', 2804, 'США', 'fabiano@chess.com'),
('Дин Лижэнь', 2799, 'Китай', 'ding@chess.com'),
('Алиреза Фирузджа', 2785, 'Франция', 'alireza@chess.com'),
('Ян Непомнящий', 2771, 'Россия', 'ian@chess.com');

-- Добавление организаторов
INSERT INTO organizers (name, email, phone) VALUES
('FIDE', 'info@fide.com', '+1234567890'),
('Chess.com', 'tournaments@chess.com', '+0987654321');

-- Добавление мест проведения
INSERT INTO venues (name, address, capacity) VALUES
('Chess Palace', 'Москва, ул. Шахматная, 1', 500),
('Grand Chess Hall', 'Санкт-Петербург, пр. Шахматный, 10', 1000);

-- Добавление турниров
INSERT INTO tournaments (name, start_date, end_date, format, rounds, organizer_id, venue_id) VALUES
('Турнир претендентов 2024', '2024-04-01 10:00:00', '2024-04-15 18:00:00', 'Круговая система', 14, 1, 1),
('Гран-при 2024', '2024-05-01 10:00:00', '2024-05-15 18:00:00', 'Швейцарская система', 9, 1, 2); 