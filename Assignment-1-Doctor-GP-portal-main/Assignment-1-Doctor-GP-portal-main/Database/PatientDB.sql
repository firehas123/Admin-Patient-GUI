CREATE TABLE patient (
    Patientid INT AUTO_INCREMENT PRIMARY KEY,
    PatientFirstName VARCHAR(255) NOT NULL,
    PatientLastName VARCHAR(255) NOT NULL,
    DateOfBirth DATE,
    Gender ENUM('male', 'female', 'other'),
    Phone_number VARCHAR(20),
    Email VARCHAR(255) UNIQUE,
    Address TEXT,
    Doctorid int,
    FOREIGN KEY (Doctorid) REFERENCES doctors (Doctorid)
);
