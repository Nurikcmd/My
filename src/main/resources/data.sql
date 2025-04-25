-- Добавление игроков
INSERT INTO players (name, rating, email, country) VALUES
('Магнус Карлсен', 2830, 'magnus@chess.com', 'Норвегия'),
('Фабиано Каруана', 2804, 'fabiano@chess.com', 'США'),
('Дин Лижэнь', 2799, 'ding@chess.com', 'Китай'),
('Алиреза Фирузджа', 2785, 'alireza@chess.com', 'Франция'),
('Ян Непомнящий', 2771, 'ian@chess.com', 'Россия');

-- Добавление организаторов
INSERT INTO organizers (name, email, phone) VALUES
('FIDE', 'info@fide.com', '+1234567890'),
('Chess.com', 'tournaments@chess.com', '+0987654321');

-- Добавление мест проведения
INSERT INTO venues (name, address, capacity, contact_phone) VALUES
('Chess Palace', 'Москва, ул. Шахматная, 1', 500, '+7 (999) 123-45-67'),
('Grand Chess Hall', 'Санкт-Петербург, пр. Шахматный, 10', 1000, '+7 (999) 765-43-21');

-- Добавление турниров
INSERT INTO tournaments (name, start_date, end_date, format, rounds, organizer_id, venue_id) VALUES
('Турнир претендентов 2024', '2024-04-01', '2024-04-15', 'ROUND_ROBIN', 14, 1, 1),
('Гран-при 2024', '2024-05-01', '2024-05-15', 'SWISS', 9, 1, 2);

-- Добавление регистраций на турниры
INSERT INTO tournament_registrations (player_id, tournament_id, registration_date, status, payment_status) VALUES
(1, 1, '2023-12-01 10:00:00', 'APPROVED', 'PAID'),
(2, 1, '2023-12-02 11:00:00', 'APPROVED', 'PAID'),
(3, 1, '2023-12-03 12:00:00', 'PENDING', 'PENDING'),
(4, 1, '2023-12-04 13:00:00', 'APPROVED', 'PAID'),
(5, 1, '2023-12-05 14:00:00', 'REJECTED', 'REFUNDED'),
(1, 2, '2023-12-10 10:00:00', 'APPROVED', 'PAID'),
(2, 2, '2023-12-11 11:00:00', 'PENDING', 'PENDING'),
(3, 2, '2023-12-12 12:00:00', 'APPROVED', 'PAID'),
(4, 2, '2023-12-13 13:00:00', 'CANCELLED', 'REFUNDED'); 