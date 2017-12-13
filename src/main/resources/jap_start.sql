 CREATE TABLE cars
      (id BIGINT AUTO_INCREMENT PRIMARY KEY ,
      brand VARCHAR(20) NOT NULL,
	  car_year INT NOT NULL
	 );
	 
CREATE TABLE persons
     (id BIGINT AUTO_INCREMENT PRIMARY KEY ,
      contact VARCHAR(100) 
	 );

CREATE TABLE deals
	(id BIGINT AUTO_INCREMENT PRIMARY KEY,
	person_id BIGINT,
	car_id BIGINT,
	price BIGINT,
	FOREIGN KEY (person_id) REFERENCES persons (id),
	FOREIGN KEY (car_id) REFERENCES cars (id)
	);


 