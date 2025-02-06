CREATE TABLE USERS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    gender VARCHAR(50),
    address VARCHAR(255),
    phone VARCHAR(50)
);

INSERT INTO USERS (name, email, password, role, gender, address, phone) VALUES
('John Doe', 'john.doe@example.com', 'password', 'USER', 'Male', '123 Main St', '123-456-7890'),
('Jane Smith', 'jane.smith@example.com', 'password', 'ADMIN', 'Female', '456 Elm St', '987-654-3210'),
('Alice Johnson', 'alice.johnson@example.com', 'password', 'USER', 'Female', '789 Oak St', '555-123-4567'),
('Bob Brown', 'bob.brown@example.com', 'password', 'USER', 'Male', '321 Pine St', '555-987-6543'),
('Charlie Davis', 'charlie.davis@example.com', 'password', 'ADMIN', 'Male', '654 Maple St', '555-234-5678'),
('Diana Evans', 'diana.evans@example.com', 'password', 'USER', 'Female', '987 Willow St', '555-345-6789'),
('Frank Green', 'frank.green@example.com', 'password', 'USER', 'Male', '159 Cedar St', '555-456-7890'),
('Grace Hall', 'grace.hall@example.com', 'password', 'ADMIN', 'Female', '753 Birch St', '555-567-8901'),
('Henry Wright', 'henry.wright@example.com', 'password', 'USER', 'Male', '246 Spruce St', '555-678-9012'),
('Ivy King', 'ivy.king@example.com', 'password', 'USER', 'Female', '369 Elm St', '555-789-0123'),
('Jack Lee', 'jack.lee@example.com', 'password', 'ADMIN', 'Male', '852 Aspen St', '555-890-1234'),
('Karen Miller', 'karen.miller@example.com', 'password', 'USER', 'Female', '159 Cedar St', '555-901-2345'),
('Leo Nelson', 'leo.nelson@example.com', 'password', 'USER', 'Male', '753 Birch St', '555-112-2334'),
('Mia Perez', 'mia.perez@example.com', 'password', 'USER', 'Female', '246 Spruce St', '555-223-3445'),
('Nick Robinson', 'nick.robinson@example.com', 'password', 'USER', 'Male', '369 Elm St', '555-334-4556'),
('Olivia Scott', 'olivia.scott@example.com', 'password', 'ADMIN', 'Female', '852 Aspen St', '555-445-5667'),
('Paul Turner', 'paul.turner@example.com', 'password', 'USER', 'Male', '159 Cedar St', '555-556-6778'),
('Quinn Walker', 'quinn.walker@example.com', 'password', 'USER', 'Female', '753 Birch St', '555-667-7889'),
('Rachel Young', 'rachel.young@example.com', 'password', 'USER', 'Female', '246 Spruce St', '555-778-8990'),
('Sam Adams', 'sam.adams@example.com', 'password', 'ADMIN', 'Male', '369 Elm St', '555-889-9001'),
('Tina Baker', 'tina.baker@example.com', 'password', 'USER', 'Female', '852 Aspen St', '555-990-1122'),
('Uma Clark', 'uma.clark@example.com', 'password', 'USER', 'Female', '159 Cedar St', '555-101-2233'),
('Victor Diaz', 'victor.diaz@example.com', 'password', 'USER', 'Male', '753 Birch St', '555-212-3344'),
('Wendy Flores', 'wendy.flores@example.com', 'password', 'ADMIN', 'Female', '246 Spruce St', '555-323-4455'),
('Xander Gomez', 'xander.gomez@example.com', 'password', 'USER', 'Male', '369 Elm St', '555-434-5566'),
('Yara Hayes', 'yara.hayes@example.com', 'password', 'USER', 'Female', '852 Aspen St', '555-545-6677'),
('Zack Ingram', 'zack.ingram@example.com', 'password', 'ADMIN', 'Male', '159 Cedar St', '555-656-7788'),
('Amber Jordan', 'amber.jordan@example.com', 'password', 'USER', 'Female', '753 Birch St', '555-767-8899'),
('Ben Kelly', 'ben.kelly@example.com', 'password', 'USER', 'Male', '246 Spruce St', '555-878-9900'),
('Chloe Lewis', 'chloe.lewis@example.com', 'password', 'ADMIN', 'Female', '369 Elm St', '555-989-0011'),
('Daniel Martinez', 'daniel.martinez@example.com', 'password', 'USER', 'Male', '852 Aspen St', '555-090-1122'),
('Eva Nelson', 'eva.nelson@example.com', 'password', 'USER', 'Female', '159 Cedar St', '555-201-2233'),
('Finn Ortiz', 'finn.ortiz@example.com', 'password', 'USER', 'Male', '753 Birch St', '555-312-3344'),
('Grace Patel', 'grace.patel@example.com', 'password', 'ADMIN', 'Female', '246 Spruce St', '555-423-4455'),
('Henry Ramirez', 'henry.ramirez@example.com', 'password', 'USER', 'Male', '369 Elm St', '555-534-5566'),
('Ivy Simmons', 'ivy.simmons@example.com', 'password', 'USER', 'Female', '852 Aspen St', '555-645-6677'),
('Jack Thomas', 'jack.thomas@example.com', 'password', 'ADMIN', 'Male', '159 Cedar St', '555-756-7788'),
('Kimberly Underwood', 'kimberly.underwood@example.com', 'password', 'USER', 'Female', '753 Birch St', '555-867-8899'),
('Leo Vasquez', 'leo.vasquez@example.com', 'password', 'USER', 'Male', '246 Spruce St', '555-978-9900'),
('Mia White', 'mia.white@example.com', 'password', 'ADMIN', 'Female', '369 Elm St', '555-089-0011'),
('Nick Xu', 'nick.xu@example.com', 'password', 'USER', 'Male', '852 Aspen St', '555-190-1122'),
('Olivia Yang', 'olivia.yang@example.com', 'password', 'USER', 'Female', '159 Cedar St', '555-301-2233'),
('Paul Zimmer', 'paul.zimmer@example.com', 'password', 'USER', 'Male', '753 Birch St', '555-412-3344'),
('Quinn Adams', 'quinn.adams@example.com', 'password', 'ADMIN', 'Female', '246 Spruce St', '555-523-4455');
