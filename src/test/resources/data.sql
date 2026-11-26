INSERT INTO departement (code, nom) VALUES ('34', 'Hérault');
INSERT INTO departement (code, nom) VALUES ('13', 'Bouches-du-Rhône');
INSERT INTO departement (code, nom) VALUES ('69', 'Rhône');

INSERT INTO ville (nom, population, id_departement) VALUES ('Montpellier', 285000, (SELECT id FROM departement WHERE code = '34'));
INSERT INTO ville (nom, population, id_departement) VALUES ('Béziers', 78000, (SELECT id FROM departement WHERE code = '34'));
INSERT INTO ville (nom, population, id_departement) VALUES ('Sète', 44000, (SELECT id FROM departement WHERE code = '34'));

INSERT INTO ville (nom, population, id_departement) VALUES ('Marseille', 870000, (SELECT id FROM departement WHERE code = '13'));
INSERT INTO ville (nom, population, id_departement) VALUES ('Aix-en-Provence', 147000, (SELECT id FROM departement WHERE code = '13'));
INSERT INTO ville (nom, population, id_departement) VALUES ('Arles', 51000, (SELECT id FROM departement WHERE code = '13'));

INSERT INTO ville (nom, population, id_departement) VALUES ('Lyon', 520000, (SELECT id FROM departement WHERE code = '69'));
INSERT INTO ville (nom, population, id_departement) VALUES ('Villeurbanne', 156000, (SELECT id FROM departement WHERE code = '69'));
INSERT INTO ville (nom, population, id_departement) VALUES ('Vénissieux', 67000, (SELECT id FROM departement WHERE code = '69'));
