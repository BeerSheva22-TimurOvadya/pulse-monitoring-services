insert into doctors values ('doctor1@gmail.com','doctor1');
insert into doctors values ('doctor2@gmail.com','doctor2');
insert into patients values (333, 'Vasya');
insert into patients values (444, 'Petya');
insert into visits (date, doctor_email, patient_id) values('2001-08-10', 'doctor1@gmail.com', 333);
insert into visits (date, doctor_email, patient_id) values('2023-10-15', 'doctor2@gmail.com', 333);
insert into visits (date, doctor_email, patient_id) values('1962-07-27', 'doctor1@gmail.com', 444);