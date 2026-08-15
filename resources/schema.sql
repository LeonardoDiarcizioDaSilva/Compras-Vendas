CREATE TABLE TB_CLIENT (
name CHAR(50),
id CHAR(30),
email CHAR(90),
age INT,
permission CHAR,
adress CHAR(90),
number CHAR(4),

CONSTRAINT pk_tb_client PRIMARY KEY(id),
NOT NULL name,
NOT NULL age,
CHECK(age > 16),
);
DELETE FROM TB_CLIENT;

INSERT INTO TB_CLIENT VALUES ('teste', '00@tester.com', 18, 'ADMIN', 'AV. Oiriginal Tester', '00', '00000000000');
SELECT * FROM TB_CLIENT;

CREATE TABLE CLIENT_ADRESS (
adress CHAR(90),
number CHAR(4),

CONSTRAINT fk_client_adress FOREIGN KEY(adress, number) REFERENCES TB_CLIENT(adress, number)
);

SELECT id FROM TB_CLIENT WHERE id = '123456789';