-- Clear existing data
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE reservation;
TRUNCATE TABLE restaurant_photos;
TRUNCATE TABLE restaurant_table;
TRUNCATE TABLE restaurant;
TRUNCATE TABLE user;
SET FOREIGN_KEY_CHECKS = 1;

-- Insert Users
INSERT INTO user (id, name, email, password, phone_number, role)
VALUES
    (1, 'John Doe', 'john.doe@example.com', 'password123', 555444333, 'admin'),
    (2, 'Jane Smith', 'jane.smith@example.com', 'password123', 555666222, 'user'),
    (3, 'Alice Johnson', 'alice.johnson@example.com', 'password123', 555222111, 'owner');

-- Insert Restaurants
INSERT INTO restaurant (id, name, address)
VALUES
    (1, 'The Great Steakhouse', '123 Main Street'),
    (2, 'Ocean Breeze', '456 Beach Avenue'),
    (3, 'Mountain Retreat', '789 Hilltop Road');

-- Insert Restaurant Photos
INSERT INTO restaurant_photos (restaurant_id, photo_path)
VALUES
    (1, 'steakhouse1.jpg'),
    (1, 'steakhouse2.jpg'),
    (2, 'ocean1.jpg'),
    (3, 'mountain1.jpg');

-- Insert Tables for Restaurants
INSERT INTO restaurant_table (id, name, available, restaurant_id)
VALUES
    (1, 'Table 1', true, 1),
    (2, 'Table 2', true, 1),
    (3, 'Table 1', false, 2),
    (4, 'Table 2', true, 2),
    (5, 'Table 1', true, 3);

-- Insert Reservations
INSERT INTO reservation (id, restaurant_id, user_id, table_id, reservation_time, number_of_people)
VALUES
    (1, 1, 1, 1, '2024-11-18 18:00:00', 5),
    (2, 2, 2, 3, '2024-11-18 19:00:00', 3),
    (3, 3, 3, 5, '2024-11-19 20:00:00', 2);
