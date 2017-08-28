-- 14/05/2012
-- Valeur par défaut pour interdire la création des interventions les jours fériés en cycle
UPDATE lstagence set interdirePlanificationFerie = 1;

-- 11/05/2012
-- Mise en place du nouveau profile 
INSERT INTO lstprofile (idProfile, libelle) VALUES (12, 'Gestion RH' );

-- 21/06/2012
-- Mise en place des prestations type CAF
update lsttypeprestation set prestationCaf = 1;
-- Evolution factures annulées
update facture set annulee = 0 where annulee is null;

-- 03/07/2012
-- Mise en place des type d'échange par agence
INSERT INTO `lsttypeechange`
(`id`,
`libelle`,
`supprime`,
`idCodeAgence`)
VALUES('7', 'Rencontre Agence', '0', '1');

INSERT INTO `lsttypeechange`
(`id`,
`libelle`,
`supprime`,
`idCodeAgence`)
VALUES('8', 'Envoi SMS', '0', '1');

INSERT INTO `lsttypeechange`
(`id`,
`libelle`,
`supprime`,
`idCodeAgence`)
VALUES('9', 'Réception SMS', '0', '1');

update correspondance set idType = 7 where idType = 14;
update correspondance set idType = 8 where idType = 691;
update correspondance set idType = 9 where idType = 701;

delete from lsttypeechange where id in (14,691,701);

select concat(a.idCodeAgence, te.id), te.libelle, 0, a.idCodeAgence from lsttypeechange te, lstagence a; 

update correspondance set idType = concat(idAgence, idType);

delete from lsttypeechange where idCodeAgence is null;


-- 23/07/2012
insert into lstrecurrences (`id`, `libelle`, `ordre`) VALUES (15, 'Une semaine sur trois', 3);

-- 26/07/2012
INSERT INTO facturedetail
(`ajoutManuel`,
`idFacture`,
`idTva`,
`idPlanAide`,
`idTypePrestation`,
`article`)
select 0,f.idFacture,3,11,-4,0 from facture f where f.factureAvoir = 1 and ((select COUNT(idFactureDetail) from facturedetail fd where fd.idFacture = f.idFacture) = 0);

-- passage des rubriques pegase en manuel
update lsttypeprestation ltp, rubriquepegase rp 
set ltp.codeSilae = rp.code, ltp.codeNuitSilae = rp.codeNuit, ltp.codeDimancheSilae = rp.codeDimanche, ltp.codeFerieSilae = rp.codeFerie
where ltp.idCodeAgence in (select idCodeAgence from lstagence where numeroDossierSilae is null or numeroDossierSilae = '')
and ltp.idRubrique = rp.id;

update lsttypeprestation ltp
set ltp.codeSilae = '', ltp.codeNuitSilae = '', ltp.codeDimancheSilae = '', ltp.codeFerieSilae = ''
where ltp.idCodeAgence in (select idCodeAgence from lstagence where numeroDossierSilae is null or numeroDossierSilae = '')
and ltp.idRubrique is null;

-- 10/08/2012
update lstmotifsintervenant set indispoPayee = 1 where indispoPayee is null;

-- 22/08/2012
update lstagence set texteSmsNotificationEnvoiFacture='Bonjour,
 
 Veuillez consulter votre courrier électronique. Vous avez avez reçu une facture.
 Nous vous souhaitons une bonne réception.
 
 Cordialement' where texteSmsNotificationEnvoiFacture is null;
 
 update lstagence set texteSmsNotificationEnvoiPlanningIntervenant='Bonjour,
 
 Veuillez consulter votre courrier électronique. Vous avez avez reçu un planning prévisionnel.
 Nous vous souhaitons une bonne réception.
 
 Cordialement' where texteSmsNotificationEnvoiPlanningIntervenant is null; 
 
 -- 21/08/2012 Ajout de l'alerte sur la date de visite medicale
INSERT INTO `lsttypeevenement` (`id`, `libelle`) VALUES (9,'Date de visite médicale');
 
  -- 27/08/2012
update lstagence set longeurEtiquetteEnMm = 40 where longeurEtiquetteEnMm is null;
update lstagence set largeurEtiquetteEnMm = 70 where largeurEtiquetteEnMm is null;

-- 30/08/2012
update facturedetail set article = 0 where article is null;

-- 28/09/2012
update prospect set actif = 1 where actif is null;

--11/10/2012
update lsttypeprestation set forfait = 0 where forfait is null;

--16/10/2012 Insertion des valeurs de base pour AMADOM
update intervenant set idSexe = 1 where idSexe is null;

INSERT INTO `statutcandidat`
(`idStatut`,
`libStatut`)
VALUES('1', 'Demandeur D''emploi')
,('2', 'Salarié')
,('3', 'Bénéficiaire RSA SOCLE')
,('4', 'Bénéficiaire RSA NON SOCLE')
,('5', 'Stagiaire FP')
,('6', 'Jeune Mission locale')
,('7', 'Travailleur Handicapé')
,('8', 'PLIE');

INSERT INTO `ressourcecandidat`
(`id`,
`libelle`)
VALUES('1', 'ARE')
,('2', 'RSA SOCLE')
,('3', 'RSA NON SOCLE')
,('4', 'ASS')
,('5', 'AAH')
,('6', 'SANS REVENU')
,('7', 'ASP')
,('8', 'EN EMPLOI');



INSERT INTO `categoriesocioprofessionnelle`
(`id`,
`libelle`)
VALUES('1', 'ARTISAN/COMMERCANT')
,('2', 'CADRE')
,('3', 'PROFESSION INTERMEDIAIRE')
,('4', 'EMPLOYE')
,('5', 'OUVRIER')
,('6', 'RETRAITE')
,('7', 'SANS ACTIVITE PROFESSIONNELLE')
,('8', 'AUTRES');

DELETE  FROM `lstprescripteur`;

INSERT INTO `lstprescripteur` (`id`,`adresse`,`dateCreation`,`dateMaj`,`codePostal`,`ville`,`telephone`,`structure`,`organisme`,`fax`,`supprime`,`idCodeAgence`) VALUES
 (1,'',NULL,NULL,'78300','POISSY','01.39.22.59.70','ACPPAV',1,'',0,1),
 (2,'',NULL,NULL,'78260','ACHERES','01.39.72.99.06','ACR - Fripes et Chapeaux D''ACHERES',1,'',0,1),
 (3,'',NULL,NULL,'78700','CONFLANS SAINTE HONORINE','','ACR - Fripes et Chapeaux DE CONFLANS',1,'',0,1),
 (4,'12 rue des Closeaux',NULL,NULL,'78200','MANTES LA JOLIE','01.30.33.06.65','APTIMA',1,'',0,1),
 (5,'1 rue de la Demi Lune',NULL,NULL,'78130','LES MUREAUX','','ATELIER PEDAGOGIQUE INDIVIDUALISE ',1,'',0,1),
 (6,'',NULL,NULL,'78580','MAULE','01.30.90.86.25','BUREAU DE L''EMPLOI DE MAULE',1,'',0,1),
 (7,'',NULL,NULL,'78250','MEULAN','01.30.99.05.92','BUREAU DE L''EMPLOI DE MEULAN',1,'',0,1),
 (8,'',NULL,NULL,'78540','VERNOUILLET','01.39.71.56.21','BUREAU DE L''EMPLOI DE VERNOUILLET',1,'',0,1),
 (9,'',NULL,NULL,'78200','MANTES LA JOLIE','01.39.29.29.64','C3 Consultants',1,'',0,1),
 (10,'37 boulevard Devaux',NULL,NULL,'78300','POISSY','01.30.65.16.09','CAP EMPLOI 78',1,'',0,1),
 (11,'',NULL,NULL,'78130','LES MUREAUX','','CENTRE SOCIAL DE LA VIGNE BLANCHE',1,'',0,1),
 (12,'38 avenue Paul Raoult',NULL,NULL,'78130','LES MUREAUX','','CIMAP',1,'',0,1),
 (13,'',NULL,NULL,'78130','LES MUREAUX','01.34.74.25.36','CIO LES MUREAUX',1,'',0,1),
 (14,'',NULL,NULL,'78200','MANTES LA JOLIE','','CODES EMPLOI',1,'',0,1),
 (15,'',NULL,NULL,'78200','MANTES LA JOLIE','','CPF',1,'',0,1),
 (16,'8 rue Levasseur',NULL,NULL,'78130','LES MUREAUX','01.30.04.14.76','e2c78 LES MUREAUX',1,'01.30.99.31.56',0,1),
 (17,'',NULL,NULL,'78410','AUBERGENVILLE','01.30.90.36.63','ESPACE EMPLOI FORMATION AUBERGENVILLE',1,'',0,1),
 (18,'',NULL,NULL,'78400','CHATOU','01.30.15.73.13','TERRITOIRE D''ACTION SOCIALE Chatou',1,'',0,1),
 (19,'',NULL,NULL,'78370','PLAISIR','01.34.82.52.37','FLES 78',1,'',0,1),
 (20,'',NULL,NULL,'78190','TRAPPES','01.30.13.17.50','GIDEF',1,'',0,1),
 (21,'36 chemin de la côte',NULL,NULL,'78670','VILLENNES SUR SEINE','01.82.03.01.34','INRH Recrutement',1,'',0,1),
 (22,'',NULL,NULL,'78480','VERNEUIL SUR SEINE','01.34.75.56.15','LA GERBE',1,'',0,1),
 (23,'',NULL,NULL,'78200','MANTES LA JOLIE','01.30.92.53.33','LA MANDRAGORE',1,'',0,1),
 (24,'2 avenue du Château',NULL,NULL,'78540','VERNOUILLET','01.39.71.94.80','LES VERNES',1,'',0,1),
 (25,'',NULL,NULL,'78780','MAURECOURT','01.39.70.23.25','MAIRIE DE MAURECOURT',1,'',0,1),
 (26,'',NULL,NULL,'78570','CHANTELOUP LES VIGNES','01.39.74.02.74','MISSION LOCALE CHANTELOUP',1,'',0,1),
 (27,'',NULL,NULL,'78700','CONFLANS SAINTE HONORINE','01.39.72.18.88','MISSION LOCALE DE CONFLANS',1,'',0,1),
 (28,'',NULL,NULL,'78520','LIMAY','01.34.94.23.44','MISSION LOCALE DE LIMAY',1,'',0,1),
 (29,'',NULL,NULL,'78200','MANTES LA JOLIE','01.30.94.23.44','MISSION LOCALE DE MANTES LA JOLIE',1,'',0,1),
 (30,'59 place Corneille',NULL,NULL,'78300','POISSY','01.30.06.30.08','MISSION LOCALE DE POISSY',1,'',0,1),
 (31,'38 Avenue Paul RAOULT',NULL,NULL,'78130','LES MUREAUX','01.30.91.21.50','MISSION LOCALE DES MUREAUX',1,'',0,1),
 (32,'',NULL,NULL,'78200','MANTES LA JOLIE','01.30.94.23.44','MISSION LOCALE DU MANTOIS',1,'',0,1),
 (33,'',NULL,NULL,'78130','LES MUREAUX','','MJ CONSEIL en RH',1,'',0,1),
 (34,'',NULL,NULL,'78130','LES MUREAUX','01.34.92.50.63','MODE D''EMPLOI LES MUREAUX',1,'',0,1),
 (35,'',NULL,NULL,'78570','CHANTELOUP LES VIGNES','01.39.70.98.20','Multiservices Décibels',1,'',0,1),
 (36,'38 avenue Paul Raoult',NULL,NULL,'78130','LES MUREAUX','01.30.91.21.94','PLIE Amont 78',1,'',0,1),
 (37,'',NULL,NULL,'78700','CONFLANS SAINTE HONORINE','','POLE EMPLOI CONFLANS',1,'',0,1),
 (38,'',NULL,NULL,'78520','LIMAY','01.30.63.85.79','POLE EMPLOI LIMAY',1,'',0,1),
 (39,'49 rue Clos Scellier',NULL,NULL,'78200','MANTES LA JOLIE','01.30.98.79.37','POLE EMPLOI MANTES LA JOLIE',1,'',0,1),
 (40,'23 rue des 2 gares',NULL,NULL,'78711','MANTES LA VILLE','','POLE EMPLOI MANTES LA VILLE',1,'',0,1),
 (41,'14 rue Jean Claude Mary',NULL,NULL,'78300','POISSY','01.30.74.90.83','POLE EMPLOI POISSY',1,'',0,1),
 (42,'74 Boulevard Victor HUGO',NULL,NULL,'78130','LES MUREAUX','01.30.22.90.82','POLE EMPLOI LES MUREAUX',1,'',0,1),
 (43,'15 rue des Courcieux',NULL,NULL,'78570','ANDRESY','01.39.75.03.10','REC Andrésy',1,'',0,1),
 (44,'',NULL,NULL,'78955','CARRIERES SOUS POISSY','01.78.63.72.60','REC Carrières sous Poissy',1,'',0,1),
 (45,'',NULL,NULL,'78570','CHANTELOUP LES VIGNES','01.39.74.00.50','REC Chanteloup',1,'',0,1),
 (46,'',NULL,NULL,'78510','TRIEL SUR SEINE','01.39.70.20.11','REC Triel sur seine',1,'',0,1),
 (47,'1 rue du maréchal Koening',NULL,NULL,'78480','VERNEUIL SUR SEINE','01.30.06.59.59','REC Verneuil sur Seine',1,'',0,1),
 (48,'',NULL,NULL,'78200','MANTES LA JOLIE','09.63.42.28.80','RESSOURCERIE APTIPRIX',1,'',0,1),
 (49,'87-89 boulevard du Mal JUIN',NULL,NULL,'78200','MANTES LA JOLIE','01.30.63.39.90','RESSOURCES EMPLOI DE MANTES',1,'01.30.63.03.98',0,1),
 (50,'',NULL,NULL,'78300','POISSY','01.30.06.01.30','RESSOURCES EMPLOI DE POISSY',1,'',0,1),
 (51,'',NULL,NULL,'78260','ACHERES','01.39.22.66.90','SAREF ACHERES',1,'',0,1),
 (52,'',NULL,NULL,'78680','EPONE','01.30.22.07.57','SAVS',1,'',0,1),
 (53,'',NULL,NULL,'78920','ECQUEVILLY','01.34.75.01.44','SERVICE EMPLOI ECQUEVILLY',1,'',0,1),
 (54,'',NULL,NULL,'78300','POISSY','','SODIE',1,'',0,1),
 (55,'8-10 avenue de la paix',NULL,NULL,'78520','LIMAY','01.34.77.97.00','TERRITOIRE D''ACTION SOCIALE  LIMAY',1,'',0,1),
 (56,'',NULL,NULL,'78260','ACHERES','01.30.06.29.60','TERRITOIRE D''ACTION SOCIALE ACHERES',1,'',0,1),
 (57,'',NULL,NULL,'78955','CARRIERES SOUS POISSY','01.30.74.97.45','TERRITOIRE D''ACTION SOCIALE CARRIERES SOUS POISSY',1,'',0,1),
 (58,'2 mail du Coteau',NULL,NULL,'78570','CHANTELOUP LES VIGNES','','TERRITOIRE D''ACTION SOCIALE CHANTELOUP',1,'',0,1),
 (59,'',NULL,NULL,'78700','CONFLANS SAINTE HONORINE','01.39.19.77.38','TERRITOIRE D''ACTION SOCIALE CONFLANS',1,'',0,1),
 (60,'42 AVENUE PAUL RAOULT',NULL,NULL,'78130','LES MUREAUX','01.30.99.58.80','TERRITOIRE D''ACTION SOCIALE LES MUREAUX',1,'01.30.99.89.58',0,1),
 (61,'12 bis rue des Merisiers',NULL,NULL,'78200','MANTES LA JOLIE','','TERRITOIRE D''ACTION SOCIALE MANTES LA JOLIE',1,'',0,1),
 (62,'',NULL,NULL,'78711','MANTES LA VILLE','01.34.97.95.35','TERRITOIRE D''ACTION SOCIALE MANTES LA VILLE',1,'',0,1),
 (63,'',NULL,NULL,'78300','POISSY','','TERRITOIRE D''ACTION SOCIALE POISSY',1,'',0,1),
 (64,'',NULL,NULL,'78250','MEULAN','01.34.92.87.20','TERRITOIRE D''ACTION SOCIALE SEINE ET MAULDRE',1,'',0,1),
 (65,'',NULL,NULL,'78100','SAINT GERMAIN EN LAYE','','TERRITOIRE D''ACTION SOCIALE ST GERMAIN',1,'',0,1),
 (66,'',NULL,NULL,'78480','VERNEUIL SUR SEINE','','TERRITOIRE D''ACTION SOCIALE VERNEUIL',1,'',0,1),
 (67,'',NULL,NULL,'78130','LES MUREAUX','01.34.74.57.96','TREMPLIN PLUS LES MUREAUX',1,'',0,1),
 (68,'',NULL,NULL,'78250','MEULAN','01.34.92.70.27','TREMPLIN PLUS MEULAN',1,'',0,1),
 (69,'',NULL,NULL,'','','','POLE EMPLOI Trappes',2,'',0,1),
 (70,'',NULL,NULL,'','','','POLE EMPLOI Guyancourt',2,'',0,1),
 (71,'',NULL,NULL,'','','','PLIE',2,'',0,1),
 (72,'',NULL,NULL,'','','','POLE EMPLOI Rambouillet',2,'',0,1),
 (73,'',NULL,NULL,'','','','MISSION LOCALE',2,'',0,1),
 (74,'',NULL,NULL,'','','','TAS Ville Nouvelle',2,'',0,1),
 (75,'',NULL,NULL,'','','','POLE EMPLOI Versailles',2,'',0,1),
 (76,'',NULL,NULL,'','','','MEECSY',2,'',0,1),
 (77,'',NULL,NULL,'','','','SAS HOUDAN',2,'',0,1),
 (78,'',NULL,NULL,'','','','CHANTIERS YVELINES',2,'',0,1),
 (79,'',NULL,NULL,'','','','C3 CONSULTANT',2,'',0,1),
 (80,'',NULL,NULL,'','','','CAP EMPLOI',2,'',0,1),
 (81,'',NULL,NULL,'','','','AMR Consultants',2,'',0,1),
 (82,'',NULL,NULL,'','','','MDE ELANCOURT',2,'',0,1),
 (83,'',NULL,NULL,'','','','GIDEF',2,'',0,1),
 (84,'',NULL,NULL,'','','','TAS Centre Yvelines',2,'',0,1),
 (85,'',NULL,NULL,'','','','Service Emploi Communal de Magny Les Hameaux',2,'',0,1),
 (86,'',NULL,NULL,'','','','ACTYV',2,'',0,1),
 (87,'',NULL,NULL,'','','','TAS Sud Yvelines ',2,'',0,1),
 (88,'',NULL,NULL,'','','','CIDFF',2,'',0,1),
 (89,'',NULL,NULL,'','','','Association Info Soins',2,'',0,1),
 (90,'',NULL,NULL,'','','','TAS Grand Versailles',2,'',0,1),
 (91,'',NULL,NULL,'','','','POLE EMPLOI Plaisir',2,'',0,1),
 (92,'',NULL,NULL,'','','','REAGIR!',2,'',0,1),
 (93,'',NULL,NULL,'','','','ESPACE EMPLOI Maurepas',2,'',0,1),
 (94,'',NULL,NULL,'','','','Chantiers Yvelines',2,'',0,1),
 (95,'',NULL,NULL,'','','','Bureau Information Jeunesse Guyancourt',2,'',0,1),
 (96,'',NULL,NULL,'','','','Bureau Information Jeunesse Trappes',2,'',0,1),
 (97,'',NULL,NULL,'','','','APE ADECCO',2,'',0,1),
 (98,'',NULL,NULL,'','','','Yvelines Information Jeunesse',2,'',0,1),
 (99,'Quartier des 7 mares',NULL,NULL,'78990','ELANCOURT','08.25.11.11.11','AFPA Elancourt',2,'',0,1),
 (100,'ZA Trappes/Elancourt 20 avenue Roger Hennequin',NULL,NULL,'78190','TRAPPES','01.30.51.20.49','CPCV',2,'',0,1),
 (101,'19 rue Victor Hugo',NULL,NULL,'78350','JOUY EN JOSAS','01.39.46.58.67','École Jeanne BLUM',2,'',0,1),
 (102,'1 rue Edouard Branly Parc d''activités de Pissaloup',NULL,NULL,'78190','TRAPPES','01.30.68.26.80','GRETA VERSAILLES FORMATION',2,'',0,1),
 (103,'1 bis rue Louis Massotte',NULL,NULL,'78530','BUC','01.39.20.19.94','BUC RESSOURCES',2,'',0,1),
 (104,'31, Rue Edme Frémy',NULL,NULL,'78000','Versailles','01.30.83.95.61','LA CROIX ROUGE FRANCAISE',2,'',0,1),
 (105,NULL,NULL,NULL,NULL,NULL,NULL,'POLE EMPLOI MONTIGNY',2,NULL,0,1),
 (106,NULL,NULL,NULL,NULL,NULL,NULL,'ESPACE EMPLOI',2,NULL,0,1),
 (107,NULL,NULL,NULL,NULL,NULL,NULL,'TASSY Secteur1',2,NULL,0,1),
 (108,NULL,NULL,NULL,NULL,NULL,NULL,'SAS TRAPPES',2,NULL,0,1),
 (109,NULL,NULL,NULL,NULL,NULL,NULL,'SAS centre Yvelines',2,NULL,0,1),
 (110,NULL,NULL,NULL,NULL,NULL,NULL,'SAS ELANCOURT',2,NULL,0,1),
 (111,NULL,NULL,NULL,NULL,NULL,NULL,'MDE ELANCOURT',2,NULL,0,1),
 (112,NULL,NULL,NULL,NULL,NULL,NULL,'Association STARTER',1,NULL,0,1),
 (113,NULL,NULL,NULL,NULL,NULL,NULL,'CONSEIL GENERAL 78',1,NULL,0,1),
 (114,NULL,NULL,NULL,NULL,NULL,NULL,'CONSEIL GENERAL DES YVELINES',1,NULL,0,1),
 (115,NULL,NULL,NULL,NULL,NULL,NULL,'Ecole de la 2ème chance',1,NULL,0,1);
-- 12.11.2012 insertion des oragnismes AMADOM

INSERT INTO `lstprescripteur`
(`id`,
`structure`,
`adresse`,
`codePostal`,
`ville`,
`telephone`,
`fax`
,`supprime`
,`organisme`
,`idCodeAgence`)
VALUES
('99','AFPA Elancourt','Quartier des 7 mares','78990','ELANCOURT','08.25.11.11.11','',0,'2','1'),
('100','CPCV','ZA Trappes/Elancourt 20 avenue Roger Hennequin','78190','TRAPPES','01.30.51.20.49','',0,'2','1'),
('101','École Jeanne BLUM','19 rue Victor Hugo','78350','JOUY EN JOSAS','01.39.46.58.67','',0,'2','1'),
('102','GRETA VERSAILLES FORMATION','1 rue Edouard Branly Parc d''activités de Pissaloup','78190','TRAPPES','01.30.68.26.80','',0,'2','1'),
('103','BUC RESSOURCES','1 bis rue Louis Massotte','78530','BUC','01.39.20.19.94','',0,'2','1'),
('104','LA CROIX ROUGE FRANCAISE','31, Rue Edme Frémy','78000','Versailles','01.30.83.95.61','',0,'2','1');

INSERT INTO `formationcandidat`
(`id`,
`titreFormation`,
`dateCreation`,
`dateMaj`,
`supprime`,
`referentFormateur`,
`idCodeAgence`,
`idStructurePrescriptrice`)
VALUES
('1','Titre professionnel Assistant de vie aux familles','12.11.2012','12.11.2012',0,'','1','99'),
('2','Diplôme d''Etat Auxiliaire de vie sociale','12.11.2012','12.11.2012',0,'','1','100'),
('3','Diplôme d''Etat Aide médico-psychologique','12.11.2012','12.11.2012',0,'','1','100'),
('4','CAP Petite Enfance','12.11.2012','12.11.2012',0,'','1','100'),
('5','PSC1 (Prévention et secours civiques de niveau 1)','12.11.2012','12.11.2012',0,'','1','100'),
('6','Certificat de qualification professionnelle Assistant(e) de vie dépendance - Employé familial / Assistant(e) maternel(le) - Garde d''enfants FEPEM','12.11.2012','12.11.2012',0,'','1','100'),
('7','Titre professionnel Assistant de vie aux familles','12.11.2012','12.11.2012',0,'','1','101'),
('8','Diplôme d''Etat d''Auxiliaire de puériculture','12.11.2012','12.11.2012',0,'','1','101'),
('9','Diplôme d''Etat  Aide-soignant','12.11.2012','12.11.2012',0,'','1','101'),
('10','Préparation à l''accès des métiers AP/AS','12.11.2012','12.11.2012',0,'','1','101'),
('11','Certificat de qualification professionnelle Assistant(e) de vie dépendance - Employé Familial / Assistant(e) maternel(le) - Garde d''enfants FEPEM','12.11.2012','12.11.2012',0,'','1','102'),
('12','Diplôme d''Etat d''Auxiliaire de puériculture','12.11.2012','12.11.2012',0,'','1','102'),
('13','Diplôme d''Etat  Aide-soignant','12.11.2012','12.11.2012',0,'','1','102'),
('14','CAP Petite Enfance','12.11.2012','12.11.2012',0,'','1','102'),
('15','Préparation à l''accès des métiers AP/AS','12.11.2012','12.11.2012',0,'','1','102'),
('16','Diplôme d''Etat d''auxiliaire de vie sociale','12.11.2012','12.11.2012',0,'','1','103'),
('17','Diplôme d''Etat Aide médico-psychologique','12.11.2012','12.11.2012',0,'','1','103'),
('18','Diplôme d''Etat Educateur spécialisé','12.11.2012','12.11.2012',0,'','1','103');
 
-- 12.11.2012 insertion des listes pour l'écran struture AMADOM

DELETE  FROM `lstagrement`;

INSERT INTO `lstagrement` (`idAgrement`,`libAgrement`) VALUES (1,'En cours'),
(2,'Qualité'), (3,'Simple');

DELETE  FROM `lstcertification`;
INSERT INTO `lstcertification` (`idCertification`,`libCertification`) VALUES (1,'Afnor'),
 (2,'En cours'),
 (3,'QUALICERT'),
 (4,'QUALISAP');
 
DELETE  FROM `lstconventioncollective`;
 INSERT INTO `lstconventioncollective` (`idConventionCollective`,`libConventionCollective`) VALUES (1,'3217'),
 (2,'BAD'),
 (3,'CCB'),
 (4,'CCB 2011'),
 (5,'CCBAD'),
 (6,'Code du travail'),
 (7,'FEHAP 1951'),
 (8,'Mandataire : part.empl.3180 et prestataire : BAD'),
 (9,'Particulier'),
 (10,'Employeur');
 
DELETE  FROM `lstorganismeformateur`;
INSERT INTO `lstorganismeformateur` (`idOrganismeFormateur`,`libOrganismeFormateur`) VALUES (1,'AGEFOS PME'),
(2,'OPCALIA'),
(3,'UNIFORMATION');

DELETE  FROM `lstparcourssyneosstr`;
INSERT INTO `lstparcourssyneosstr` (`idParcoursSyneosStr`,`libParcoursSyneosStr`) VALUES (1,'Clé 1'),
 (2,'Clé 2'),
 (3,'Clé 3'),
 (4,'Clé 4'),
 (5,'Clé 5');
 
DELETE  FROM `lsttypestructure`;
INSERT INTO `lsttypestructure` (`idTypeStructure`,`libTypeStructure`) VALUES (1,'Association'), (2,'Entreprise'), (3,'CCAS');
 
DELETE  FROM `lsttranchesalarie`;
INSERT INTO `lsttranchesalarie` (`idTrancheSalarie`,`libTrancheSalarie`) VALUES (1,'Tranche 1 (de 0 à 10)'),
(2,'Tranche 2 (de 11 à 50)'),
(3,'Tranche 3 (de 51 à 100)'),
(4,'Tranche 4 (de 101 à 200)'),
(5,'Tranche 5 (de 201 à 300)');

DELETE  FROM `lstfederation`;
INSERT INTO `lstfederation` (`idFederation`,`libFederation`) VALUES (1,'Adhap SCES'),
 (2,'ADMR'),
 (3,'Age d’Or SCES'),
 (4,'ALDS'),
 (5,'Alliance Vie'),
 (6,'Altidom'),
 (7,'Domidom'),
 (8,'Domifacile'),
 (9,'Family Sphere'),
 (10,'FESP'),
 (11,'Junior Senior'),
 (12,'SESP'),
 (13,'Tree'),
 (14,'UNA'),
 (15,'UNA 78'),
 (16,'Vitame');
 
 
  DELETE  FROM `lstappuicimap`;
 INSERT INTO `lstappuicimap` (`idAppuiCIMAP`,`libAppuiCIMAP`) VALUES (1,'2011'),
(2,'2010'),
(3,'2009'),
(4,'2008'),
(5,'2007');

  DELETE  FROM `lstaccompafam`;
INSERT INTO `lstaccompafam` (`idAccompaFAM`,`libAccompaFAM`) VALUES (1,'2011'),
(2,'2010'),(3,'2009'),(4,'2008');

-- 18.12.2012 MAJ coté candidat suivant demande client

INSERT INTO `statutcandidat`
(`idStatut`,
`libStatut`)
VALUES('9', 'Congés parentales');

INSERT INTO `ressourcecandidat`
(`id`,
`libelle`)
VALUES('9', 'CAF');

update categoriesocioprofessionnelle set libelle = 'AUTRE PERSONNES SANS ACTIVITE PROFESSIONNELLE' where id = 7;

-- 21.12.2012 Modif lstparcourspro au lieu de lstparcoursprostr
DELETE  FROM `lstparcourspro`;
INSERT INTO `lstparcourspro` (`idParcoursPro`,`libParcoursPro`) VALUES (1,'Diagnostic'),
(2,'Vision stratégique'),
(3,'IZI Formation'),
(4,'Management de la qualité'),
(5,'Management développement et pilotage activité'),
(6,'techniques de communication'),
(7,'Renforcement communication'),
(8,'Commercial et marketing social'),
(9,'Gestion financière'),
(10,'Management animation d''équipe'),
(11,'Management accompagnement salariés'),
(12,'GPEC bilan et retours d''expérience'),
(13,'Process com '),
(14,'Formation tableau de bord de pilotage');
-- 31.01.2013 ajout des metiers ciblees 
DELETE  FROM `lstmetiercible`;
INSERT INTO `lstmetiercible` (`id`,`libMetierCible`,`supprime`) VALUES (1,'Aide Soignant',0),
 (2,'Auxiliaire de Vie Sociale',0),
 (3,'Aide Médico Psychologique',0),
 (4,'Assistant De Vie Familles',0),
 (5,'Auxiliaire de Puériculture',0),
 (6,'ATSEM',0),
 (7,'CAP Petite Enfance',0),
 (8,'A définir',0),
 (9,'Moniteur Educateur',0),
 (10,'Educateur Spécialisé',0),
 (11,'Infirmier(e)',0),
 (12,'TISF',0),
 (13,'Agent des services hospitaliers',0),
 (14,'Assistante maternelle',0),
 (15,'Puéricultrice',0),
 (16,'Assistante Sociale',0);
 
 -- 13.02.2013
-- Mise en place des nouveaus profiles 
INSERT INTO lstprofile (idProfile, libelle) VALUES 
(14, 'Agence' ),
(15, 'CVThéque' ),
(16, 'Insertion' );

UPDATE lstprofile set libelle = "Adhérents" where idProfile = 13;

-- 04.04.2013
-- Mise en place des groupements
INSERT INTO lstgroupement (id, libelleGroupement) VALUES 
(1, 'UNIDOM'),
(2, 'ALLIANS 78'),
(3, 'AMY'),
(4, 'EDOM services'),
(5, 'CCAS 78');

update structure set actif = 1 where actif is null;
