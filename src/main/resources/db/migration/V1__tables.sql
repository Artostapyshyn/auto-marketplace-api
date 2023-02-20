CREATE TABLE IF NOT EXISTS sellers (
  id serial NOT NULL,
  first_name varchar(100) NOT NULL,
  last_name varchar(100) NOT NULL,
  phone_number varchar(100) NOT NULL,
  email varchar(150) NOT NULL UNIQUE,
  password varchar(255) NOT NULL,
  role varchar(50) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS sale_advertisements (
  id serial NOT NULL,
  seller_id serial,
  vehicle_type varchar(100) NOT NULL,
  vehicle_brand varchar(100) NOT NULL,
  vehicle_model varchar(100) NOT NULL,
  body_type varchar(100) NOT NULL,
  engine_capacity varchar(100) NOT NULL,
  color varchar(100) NOT NULL,
  additional_features text,
  description text NOT NULL,
  vin_code varchar(150) UNIQUE,
  last_technical_inspection varchar(100),
  vehicle_plate_number varchar(100),
  creation_date timestamp NOT NULL,
  CONSTRAINT fk_seller_id FOREIGN KEY (seller_id) REFERENCES sellers(id),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS advertisement_images (
  id serial NOT NULL,
  sale_advertisement_id serial,
  image_name varchar(100) NOT NULL,
  image_content_type varchar(100) NOT NULL,
  image_size bigint NOT NULL,
  image_data text NOT NULL,
  CONSTRAINT fk_sale_advertisement_id FOREIGN KEY (sale_advertisement_id) REFERENCES sale_advertisements(id),
  PRIMARY KEY (id)
);