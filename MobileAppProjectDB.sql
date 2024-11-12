CREATE SCHEMA IF NOT EXISTS MobileApplicationProject;
USE MobileApplicationProject;

CREATE TABLE IF NOT EXISTS Users (
    userID INT(6) AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(45) UNIQUE,
    email VARCHAR(45),
    passwords VARCHAR(50),
    userrole ENUM('client', 'vendor', 'admin') NOT NULL DEFAULT 'client'
);

CREATE TABLE IF NOT EXISTS Cafeteria_Location (
    locationID INT(6) AUTO_INCREMENT PRIMARY KEY,
    location_name VARCHAR(45) UNIQUE,
    address VARCHAR(100),
    opening_hours TIME,  
    closing_hours TIME   
);

CREATE TABLE IF NOT EXISTS Vendors (
    vendorID INT(6) AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(45) UNIQUE,
    descriptions VARCHAR(255),
    locationID INT(6),  
    phone_number VARCHAR(15),
    CONSTRAINT fk_location FOREIGN KEY (locationID) REFERENCES Cafeteria_Location(locationID) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Menu_Items (
    itemID INT(3) AUTO_INCREMENT PRIMARY KEY,
    vendorID INT(6),
    item_name VARCHAR(45),
    descriptions VARCHAR(255),
    price DECIMAL(7, 2),
    availability BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_vendor FOREIGN KEY (vendorID) REFERENCES Vendors(vendorID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Orders (
    orderID INT(6) AUTO_INCREMENT PRIMARY KEY,
    userID INT(6),
    vendorID INT(6),
    order_status ENUM('pending', 'confirmed', 'completed', 'cancelled') NOT NULL DEFAULT 'pending',
    quantity INT(3),
    total_price DECIMAL(7, 2),
    order_date DATETIME,
    pickup_time TIME,            
    CONSTRAINT fk_user FOREIGN KEY (userID) REFERENCES Users(userID) ON DELETE CASCADE,
    CONSTRAINT fk_vendor_order FOREIGN KEY (vendorID) REFERENCES Vendors(vendorID) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Payments (
    transactionID INT(6) AUTO_INCREMENT PRIMARY KEY,
    orderID INT(6),
    payment_status ENUM('paid', 'unpaid', 'refunded') NOT NULL DEFAULT 'unpaid',
    payment_method ENUM('Mpesa', 'cash', 'card') NOT NULL DEFAULT 'Mpesa',
    transaction_date DATE,
    transaction_time TIME,
    CONSTRAINT fk_order FOREIGN KEY (orderID) REFERENCES Orders(orderID) ON DELETE CASCADE
);
