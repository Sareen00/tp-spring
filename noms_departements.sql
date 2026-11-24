-- Ajout du nom des départements (mise à jour par code)
SET SQL_SAFE_UPDATES = 0;

UPDATE departement d
JOIN (
    SELECT '01' AS code, 'Ain' AS nom UNION ALL
    SELECT '02', 'Aisne' UNION ALL
    SELECT '03', 'Allier' UNION ALL
    SELECT '04', 'Alpes-de-Haute-Provence' UNION ALL
    SELECT '05', 'Hautes-Alpes' UNION ALL
    SELECT '06', 'Alpes-Maritimes' UNION ALL
    SELECT '07', 'Ardèche' UNION ALL
    SELECT '08', 'Ardennes' UNION ALL
    SELECT '09', 'Ariège' UNION ALL
    SELECT '10', 'Aube' UNION ALL
    SELECT '11', 'Aude' UNION ALL
    SELECT '12', 'Aveyron' UNION ALL
    SELECT '13', 'Bouches-du-Rhône' UNION ALL
    SELECT '14', 'Calvados' UNION ALL
    SELECT '15', 'Cantal' UNION ALL
    SELECT '16', 'Charente' UNION ALL
    SELECT '17', 'Charente-Maritime' UNION ALL
    SELECT '18', 'Cher' UNION ALL
    SELECT '19', 'Corrèze' UNION ALL
    SELECT '2A', 'Corse-du-Sud' UNION ALL
    SELECT '2B', 'Haute-Corse' UNION ALL
    SELECT '21', 'Côte-d''Or' UNION ALL
    SELECT '22', 'Côtes-d''Armor' UNION ALL
    SELECT '23', 'Creuse' UNION ALL
    SELECT '24', 'Dordogne' UNION ALL
    SELECT '25', 'Doubs' UNION ALL
    SELECT '26', 'Drôme' UNION ALL
    SELECT '27', 'Eure' UNION ALL
    SELECT '28', 'Eure-et-Loir' UNION ALL
    SELECT '29', 'Finistère' UNION ALL
    SELECT '30', 'Gard' UNION ALL
    SELECT '31', 'Haute-Garonne' UNION ALL
    SELECT '32', 'Gers' UNION ALL
    SELECT '33', 'Gironde' UNION ALL
    SELECT '34', 'Hérault' UNION ALL
    SELECT '35', 'Ille-et-Vilaine' UNION ALL
    SELECT '36', 'Indre' UNION ALL
    SELECT '37', 'Indre-et-Loire' UNION ALL
    SELECT '38', 'Isère' UNION ALL
    SELECT '39', 'Jura' UNION ALL
    SELECT '40', 'Landes' UNION ALL
    SELECT '41', 'Loir-et-Cher' UNION ALL
    SELECT '42', 'Loire' UNION ALL
    SELECT '43', 'Haute-Loire' UNION ALL
    SELECT '44', 'Loire-Atlantique' UNION ALL
    SELECT '45', 'Loiret' UNION ALL
    SELECT '46', 'Lot' UNION ALL
    SELECT '47', 'Lot-et-Garonne' UNION ALL
    SELECT '48', 'Lozère' UNION ALL
    SELECT '49', 'Maine-et-Loire' UNION ALL
    SELECT '50', 'Manche' UNION ALL
    SELECT '51', 'Marne' UNION ALL
    SELECT '52', 'Haute-Marne' UNION ALL
    SELECT '53', 'Mayenne' UNION ALL
    SELECT '54', 'Meurthe-et-Moselle' UNION ALL
    SELECT '55', 'Meuse' UNION ALL
    SELECT '56', 'Morbihan' UNION ALL
    SELECT '57', 'Moselle' UNION ALL
    SELECT '58', 'Nièvre' UNION ALL
    SELECT '59', 'Nord' UNION ALL
    SELECT '60', 'Oise' UNION ALL
    SELECT '61', 'Orne' UNION ALL
    SELECT '62', 'Pas-de-Calais' UNION ALL
    SELECT '63', 'Puy-de-Dôme' UNION ALL
    SELECT '64', 'Pyrénées-Atlantiques' UNION ALL
    SELECT '65', 'Hautes-Pyrénées' UNION ALL
    SELECT '66', 'Pyrénées-Orientales' UNION ALL
    SELECT '67', 'Bas-Rhin' UNION ALL
    SELECT '68', 'Haut-Rhin' UNION ALL
    SELECT '69', 'Rhône' UNION ALL
    SELECT '70', 'Haute-Saône' UNION ALL
    SELECT '71', 'Saône-et-Loire' UNION ALL
    SELECT '72', 'Sarthe' UNION ALL
    SELECT '73', 'Savoie' UNION ALL
    SELECT '74', 'Haute-Savoie' UNION ALL
    SELECT '75', 'Paris' UNION ALL
    SELECT '76', 'Seine-Maritime' UNION ALL
    SELECT '77', 'Seine-et-Marne' UNION ALL
    SELECT '78', 'Yvelines' UNION ALL
    SELECT '79', 'Deux-Sèvres' UNION ALL
    SELECT '80', 'Somme' UNION ALL
    SELECT '81', 'Tarn' UNION ALL
    SELECT '82', 'Tarn-et-Garonne' UNION ALL
    SELECT '83', 'Var' UNION ALL
    SELECT '84', 'Vaucluse' UNION ALL
    SELECT '85', 'Vendée' UNION ALL
    SELECT '86', 'Vienne' UNION ALL
    SELECT '87', 'Haute-Vienne' UNION ALL
    SELECT '88', 'Vosges' UNION ALL
    SELECT '89', 'Yonne' UNION ALL
    SELECT '90', 'Territoire de Belfort' UNION ALL
    SELECT '91', 'Essonne' UNION ALL
    SELECT '92', 'Hauts-de-Seine' UNION ALL
    SELECT '93', 'Seine-Saint-Denis' UNION ALL
    SELECT '94', 'Val-de-Marne' UNION ALL
    SELECT '95', 'Val-d''Oise' UNION ALL
    SELECT '971', 'Guadeloupe' UNION ALL
    SELECT '972', 'Martinique' UNION ALL
    SELECT '973', 'Guyane' UNION ALL
    SELECT '974', 'La Réunion' UNION ALL
    SELECT '976', 'Mayotte'
) n ON n.code = d.code
SET d.nom = n.nom;

SET SQL_SAFE_UPDATES = 1;

-- Vérification : départements restés sans nom
SELECT * FROM departement WHERE nom IS NULL;
