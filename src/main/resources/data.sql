INSERT INTO Product (name, description, price, currency)
VALUES ('Laptop', 'Powerful laptop with SSD storage', 1200.0, 'USD');

INSERT INTO Product (name, price, currency)
VALUES ('Smartphone', 800.0, 'USD');

INSERT INTO Product (name, description, price, currency)
VALUES ('Headphones', 'Wireless noise-canceling headphones', 250.0, 'PLN');

INSERT INTO Product (name, price, currency)
VALUES ('PC', 700.0, 'PLN');

INSERT INTO Promo_Code (code, expiration_date, discount, currency, allowed_usages, is_second_type)
VALUES ('qqIxQ', '2024-12-31', 20.99, 'USD', 5, false);

INSERT INTO Promo_Code (code, expiration_date, discount, currency, allowed_usages, is_second_type)
VALUES ('YlQgxVGVIEbmqVHNSpsX9KQn', '2024-06-30', 30.00, 'PLN', 10, true);

INSERT INTO Promo_Code (code, expiration_date, discount, currency, allowed_usages, is_second_type)
VALUES ('wFjA4Ok2', '2024-10-31', 50.50, 'USD', 3, false);

INSERT INTO Purchase (date_of_purchase, product_id, regular_price)
VALUES ('2024-05-08', 1, 1200.00);

INSERT INTO Purchase (date_of_purchase, product_id, regular_price, discount)
VALUES ('2024-05-08', 1, 1200.00, 20.99);

INSERT INTO Purchase (date_of_purchase, product_id, regular_price, discount)
VALUES ('2024-05-08', 2, 800.00, 30.00);

INSERT INTO Purchase (date_of_purchase, product_id, regular_price)
VALUES ('2024-05-08', 3, 250.00);

INSERT INTO Purchase (date_of_purchase, product_id, regular_price, discount)
VALUES ('2024-05-08', 4, 700.00, 50.50);