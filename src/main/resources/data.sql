INSERT INTO USER_TABLE(user_id, email, password, rol, activo) VALUES(1, 'test@unlam.edu.ar', 'test', 'ADMIN', true);
INSERT INTO MOBILE_USER(user_id, nombre, nick_name) VALUES (1, 'Julian', 'july-megadeth');
INSERT INTO VEHICLE(id, patente, marca, modelo, color, activo, user_id) VALUES (1, 'KMM948', 'VOLKSWAGEN', 'FOX', 'Rojo', true, 1);