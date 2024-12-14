-- Disable foreign key checks to allow truncating tables
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE menu_item_tags;
TRUNCATE TABLE restaurant_tags;
TRUNCATE TABLE reservation;
TRUNCATE TABLE restaurant_photos;
TRUNCATE TABLE restaurant_table;
TRUNCATE TABLE menu_item;
TRUNCATE TABLE restaurant;
TRUNCATE TABLE comment;
TRUNCATE TABLE user;
SET FOREIGN_KEY_CHECKS = 1;

-- Insert Users
INSERT INTO user (id, name, email, password, phone_number, role) VALUES
	(1, 'John Doe', 'john.doe@example.com', 'password123', 5554443331, 'admin'),
	(2, 'Jane Smith', 'jane.smith@example.com', 'password123', 5556662221, 'user'),
	(3, 'Alice Johnson', 'alice.johnson@example.com', 'password123', 5552221111, 'owner'),
	(4, 'Bob Wilson', 'bob.wilson@example.com', 'password123', 5553334441, 'user'),
	(5, 'Emma Brown', 'emma.brown@example.com', 'password123', 5557778881, 'owner'),
	(6, 'Michael Lee', 'michael.lee@example.com', 'password123', 5559990001, 'user');

-- Update Restaurants
INSERT INTO restaurant 
(id, name, address, phone_number, email, password, operating_hours, birthday_party, anniversary, job_meeting, proposal, additional_condition, logo_photo_path, website_link) 
VALUES
(1, 'The Great Steakhouse', '123 Main Street, Foodville', '123-456-7890', 'steakhouse@example.com', 'password123', '10:00 AM - 10:00 PM', true, true, true, false, 'Terms for The Great Steakhouse', 'steakhouse_logo.png', 'http://www.greatsteakhouse.com'),
(2, 'Ocean Breeze Seafood', '456 Beach Avenue, Coastline', '234-567-8901', 'oceanbreeze@example.com', 'password123', '11:00 AM - 11:00 PM', false, true, true, true, 'Terms for Ocean Breeze Seafood', 'oceanbreeze_logo.png', 'http://www.oceanbreezeseafood.com'),
(3, 'Mountain Retreat Cafe', '789 Hilltop Road, Mountainview', '345-678-9012', 'mountainretreat@example.com', 'password123', '8:00 AM - 8:00 PM', true, false, true, false, 'Terms for Mountain Retreat Cafe', 'mountainretreat_logo.png', 'http://www.mountainretreatcafe.com'),
(4, 'Spice Kingdom Indian Cuisine', '321 Curry Lane, Flavortown', '456-789-0123', 'spicekingdom@example.com', 'password123', '12:00 PM - 12:00 AM', true, true, false, true, 'Terms for Spice Kingdom Indian Cuisine', 'spicekingdom_logo.png', 'http://www.spicekingdom.com'),
(5, 'Green Leaf Vegetarian Bistro', '654 Veggie Street, Healthyville', '567-890-1234', 'greenleaf@example.com', 'password123', '9:00 AM - 9:00 PM', false, false, true, false, 'Terms for Green Leaf Vegetarian Bistro', 'greenleaf_logo.png', 'http://www.greenleafbistro.com'),
(6, 'Pasta Paradise', '987 Italian Way, Marinara', '678-901-2345', 'pastaparadise@example.com', 'password123', '11:00 AM - 11:00 PM', true, true, false, true, 'Terms for Pasta Paradise', 'pastaparadise_logo.png', 'http://www.pastaparadise.com'),
(7, 'Sushi Sensation', '246 Salmon Road, Japantown', '789-012-3456', 'sushisensation@example.com', 'password123', '11:30 AM - 10:30 PM', false, true, true, false, 'Terms for Sushi Sensation', 'sushisensation_logo.png', 'http://www.sushisensation.com'),
(8, 'Burger Bliss', '135 Grill Avenue, Meatsville', '890-123-4567', 'burgerbliss@example.com', 'password123', '10:00 AM - 10:00 PM', true, true, false, true, 'Terms for Burger Bliss', 'burgerbliss_logo.png', 'http://www.burgerbliss.com'),
(9, 'Mediterranean Delights', '802 Olive Circle, Hummusland', '901-234-5678', 'mediterraneandelights@example.com', 'password123', '12:00 PM - 10:00 PM', false, false, true, true, 'Terms for Mediterranean Delights', 'mediterraneandelights_logo.png', 'http://www.mediterraneandelights.com'),
(10, 'Dim Sum Palace', '579 Dumpling Street, Chinatown', '012-345-6789', 'dimsumpalace@example.com', 'password123', '10:30 AM - 9:30 PM', true, true, true, false, 'Terms for Dim Sum Palace', 'dimsumpalace_logo.png', 'http://www.dimsumpalace.com');

-- Insert Comments
INSERT INTO comment (id, user_id, restaurant_id, rating, comment, created_at) VALUES
	(1, 2, 1, 5, 'Amazing food and great service!', '2024-12-01 12:00:00'),
	(2, 4, 2, 5, 'The steak was cooked to perfection.', '2024-12-02 14:30:00'),
	(3, 6, 1, 4, 'A bit pricey but worth it.', '2024-12-03 18:45:00'),
	(4, 1, 3, 3, 'Service could be improved.', '2024-12-04 19:00:00'),
	(5, 3, 4, 4, 'Loved the ambiance!', '2024-12-05 20:30:00'),
	(6, 2, 4, 5, 'Best dining experience ever!', '2024-12-06 21:15:00'),
	(7, 5, 5, 4, 'Will visit again soon.', '2024-12-07 13:25:00'),
	(8, 4, 6, 3, 'Small portions but tasty food.', '2024-12-08 14:10:00'),
	(9, 6, 7, 4, 'Staff was very friendly.', '2024-12-09 17:45:00'),
	(10, 1, 8, 2, 'Food was a bit salty.', '2024-12-10 12:50:00'),
	(11, 3, 9, 5, 'Highly recommend this place!', '2024-12-11 16:40:00'),
	(12, 5, 10, 4, 'Great experience for our anniversary.', '2024-12-12 18:30:00'),
	(13, 2, 3, 4, 'Drinks were amazing.', '2024-12-13 19:20:00'),
	(14, 4, 7, 2, 'Dessert selection is small', '2024-12-14 20:10:00'),
	(15, 6, 4, 1, 'Worst restaurant in town!', '2024-12-15 21:00:00');

-- Insert Restaurant Tags
INSERT INTO restaurant_tags (restaurant_id, tag) VALUES
    (1, 'Steakhouse'),
    (1, 'Fine Dining'),
    (2, 'Seafood'),
    (2, 'Casual'),
    (3, 'Cafe'),
    (3, 'Breakfast'),
    (4, 'Indian'),
    (4, 'Spicy'),
    (5, 'Vegetarian'),
    (5, 'Healthy'),
    (6, 'Italian'),
    (6, 'Pasta'),
    (7, 'Japanese'),
    (7, 'Sushi'),
    (8, 'American'),
    (8, 'Burgers'),
    (9, 'Mediterranean'),
    (9, 'Healthy'),
    (10, 'Chinese'),
    (10, 'Dim Sum');

-- Insert Restaurant Photos (using placeholders)
INSERT INTO restaurant_photos (restaurant_id, photo_path) VALUES
    (1, 'steakhouse1.jpg'),
    (1, 'steakhouse2.jpg'),
    (2, 'seafood1.jpg'),
    (2, 'seafood2.jpg'),
    (3, 'cafe1.jpg'),
    (4, 'indian1.jpg'),
    (5, 'vegetarian1.jpg'),
    (6, 'pasta1.jpg'),
    (7, 'sushi1.jpg'),
    (8, 'burger1.jpg'),
    (9, 'mediterranean1.jpg'),
    (10, 'dimsum1.jpg');

-- Insert Tables for Restaurants
INSERT INTO restaurant_table (id, name, available, restaurant_id, capacity) VALUES
    (1, 'Table 1', true, 1, 2),
    (2, 'Table 2', true, 1, 4),
    (3, 'Table 3', true, 1, 6),
    (4, 'Table 1', true, 2, 6),
    (5, 'Table 2', true, 2, 6),
    (6, 'Table 3', true, 2, 4),
    (7, 'Table 1', true, 3, 4),
    (8, 'Table 2', true, 3, 4),
    (9, 'Table 1', true, 4, 4),
    (10, 'Table 2', true, 4, 4),
    (11, 'Table 1', true, 5, 4),
    (12, 'Table 2', true, 5, 4),
    (13, 'Table 1', true, 6, 4),
    (14, 'Table 2', true, 6, 2),
    (15, 'Table 1', true, 7, 2),
    (16, 'Table 2', true, 7, 2),
    (17, 'Table 1', true, 8, 2),
    (18, 'Table 2', true, 8, 2),
    (19, 'Table 1', true, 9, 2),
    (20, 'Table 2', true, 9, 2),
    (21, 'Table 1', true, 10, 2),
    (22, 'Table 2', true, 10, 2);

-- Insert Reservations
INSERT INTO reservation (id, restaurant_id, user_id, table_id, reservation_start_time, reservation_end_time, number_of_people) VALUES
    (1, 1, 1, 1, '2024-12-15 18:00:00', '2024-12-15 19:00:00', 4),
    (2, 2, 2, 4, '2024-12-16 19:30:00', '2024-12-16 20:30:00', 2),
    (3, 3, 3, 7, '2024-12-17 20:00:00', '2024-12-17 21:00:00', 3),
    (4, 4, 4, 9, '2024-12-18 19:00:00', '2024-12-18 20:00:00', 5),
    (5, 5, 5, 11, '2024-12-19 18:30:00', '2024-12-19 19:30:00', 2),
    (6, 6, 6, 13, '2024-12-20 20:30:00', '2024-12-20 21:30:00', 4);

-- Insert Menu Items
INSERT INTO menu_item (restaurant_id, name, description, price) VALUES
    -- Steakhouse
    (1, 'Prime Ribeye', 'Premium cut ribeye steak', 34.99),
    (1, 'Filet Mignon', 'Tender beef filet', 39.99),
    (1, 'Grilled Salmon', 'Fresh Atlantic salmon', 25.99),

    -- Seafood
    (2, 'Lobster Tail', 'Butter-poached lobster', 44.99),
    (2, 'Seafood Platter', 'Assorted seafood selection', 49.99),
    (2, 'Grilled Shrimp', 'Herb-marinated shrimp', 22.99),

    -- Mountain Cafe
    (3, 'Breakfast Skillet', 'Hearty breakfast with eggs and potatoes', 15.99),
    (3, 'Veggie Frittata', 'Vegetable egg casserole', 12.99),
    (3, 'Pancake Stack', 'Fluffy buttermilk pancakes', 10.99),

    -- Indian Cuisine
    (4, 'Chicken Tikka Masala', 'Classic Indian curry', 18.99),
    (4, 'Vegetable Biryani', 'Spiced vegetable rice', 16.99),
    (4, 'Lamb Vindaloo', 'Spicy lamb curry', 21.99),

    -- Vegetarian Bistro
    (5, 'Beyond Burger', 'Plant-based burger', 14.99),
    (5, 'Quinoa Salad', 'Organic quinoa mix', 12.99),
    (5, 'Vegan Pad Thai', 'Plant-based pad thai', 15.99),

    -- Pasta Place
    (6, 'Spaghetti Carbonara', 'Creamy pasta with pancetta', 16.99),
    (6, 'Lasagna', 'Classic meat lasagna', 17.99),
    (6, 'Truffle Risotto', 'Mushroom truffle risotto', 19.99),

    -- Sushi
    (7, 'Salmon Sashimi', 'Fresh salmon slices', 16.99),
    (7, 'Dragon Roll', 'Eel and avocado roll', 18.99),
    (7, 'Chirashi Bowl', 'Mixed sashimi over rice', 24.99),

    -- Burger Joint
    (8, 'Classic Cheeseburger', 'Beef patty with cheese', 12.99),
    (8, 'Bacon Burger', 'Burger with crispy bacon', 14.99),
    (8, 'Veggie Burger', 'Plant-based burger', 13.99),

    -- Mediterranean
    (9, 'Falafel Plate', 'Chickpea fritters with sides', 14.99),
    (9, 'Lamb Kebabs', 'Grilled lamb skewers', 19.99),
    (9, 'Greek Salad', 'Traditional Greek salad', 11.99),

    -- Dim Sum
    (10, 'Pork Dumplings', 'Steamed meat dumplings', 8.99),
    (10, 'Shrimp Har Gow', 'Translucent shrimp dumplings', 9.99),
    (10, 'Vegetable Spring Rolls', 'Crispy vegetable rolls', 7.99);

-- Insert Menu Item Tags
INSERT INTO menu_item_tags (menu_item_id, tag) VALUES
    -- Steakhouse tags
    (1, 'Meat'),
    (1, 'Beef'),
    (2, 'Meat'),
    (2, 'Beef'),
    (3, 'Seafood'),

    -- Seafood tags
    (4, 'Seafood'),
    (4, 'Luxury'),
    (5, 'Seafood'),
    (6, 'Seafood'),

    -- Cafe tags
    (7, 'Breakfast'),
    (8, 'Vegetarian'),
    (9, 'Breakfast'),

    -- Indian tags
    (10, 'Meat'),
    (10, 'Spicy'),
    (11, 'Vegetarian'),
    (12, 'Meat'),
    (12, 'Spicy'),

    -- Vegetarian tags
    (13, 'Vegetarian'),
    (14, 'Vegan'),
    (15, 'Vegan'),

    -- Pasta tags
    (16, 'Meat'),
    (17, 'Meat'),
    (18, 'Vegetarian'),

    -- Sushi tags
    (19, 'Seafood'),
    (20, 'Seafood'),
    (21, 'Seafood'),

    -- Burger tags
    (22, 'Meat'),
    (23, 'Meat'),
    (24, 'Vegetarian'),

    -- Mediterranean tags
    (25, 'Vegetarian'),
    (26, 'Meat'),
    (27, 'Vegetarian'),

    -- Dim Sum tags
    (28, 'Meat'),
    (29, 'Seafood'),
    (30, 'Vegetarian');