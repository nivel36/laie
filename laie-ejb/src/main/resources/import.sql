insert into JOB_CANDIDATURE_STATE( id, version, name, first, approved, declined ) values ( 10, 0, 'not_contacted', true, false, false);
insert into JOB_CANDIDATURE_STATE( id, version, name, first, approved, declined ) values ( 11, 0, 'contacted', false, false, false);
insert into JOB_CANDIDATURE_STATE( id, version, name, first, approved, declined ) values ( 12, 0, 'submitted', false, false, false);
insert into JOB_CANDIDATURE_STATE( id, version, name, first, approved, declined ) values ( 13, 0, 'declined', false, false, true);
insert into JOB_CANDIDATURE_STATE( id, version, name, first, approved, declined ) values ( 14, 0, 'interviewed', false, false, false);
insert into JOB_CANDIDATURE_STATE( id, version, name, first, approved, declined ) values ( 15, 0, 'offered', false, false, false);
insert into JOB_CANDIDATURE_STATE( id, version, name, first, approved, declined ) values ( 16, 0, 'approved', false, true, false);

insert into JOB_CANDIDATURE_STATE_REL( PARENT_ID, JOB_CANDIDATURE_ID ) values ( 10, 13);
insert into JOB_CANDIDATURE_STATE_REL( PARENT_ID, JOB_CANDIDATURE_ID ) values ( 10, 11);
insert into JOB_CANDIDATURE_STATE_REL( PARENT_ID, JOB_CANDIDATURE_ID ) values ( 11, 12);
insert into JOB_CANDIDATURE_STATE_REL( PARENT_ID, JOB_CANDIDATURE_ID ) values ( 11, 13);
insert into JOB_CANDIDATURE_STATE_REL( PARENT_ID, JOB_CANDIDATURE_ID ) values ( 11, 14);
insert into JOB_CANDIDATURE_STATE_REL( PARENT_ID, JOB_CANDIDATURE_ID ) values ( 11, 15);
insert into JOB_CANDIDATURE_STATE_REL( PARENT_ID, JOB_CANDIDATURE_ID ) values ( 11, 16);
insert into JOB_CANDIDATURE_STATE_REL( PARENT_ID, JOB_CANDIDATURE_ID ) values ( 12, 13);
insert into JOB_CANDIDATURE_STATE_REL( PARENT_ID, JOB_CANDIDATURE_ID ) values ( 12, 14);
insert into JOB_CANDIDATURE_STATE_REL( PARENT_ID, JOB_CANDIDATURE_ID ) values ( 12, 15);
insert into JOB_CANDIDATURE_STATE_REL( PARENT_ID, JOB_CANDIDATURE_ID ) values ( 12, 16);
insert into JOB_CANDIDATURE_STATE_REL( PARENT_ID, JOB_CANDIDATURE_ID ) values ( 14, 13);
insert into JOB_CANDIDATURE_STATE_REL( PARENT_ID, JOB_CANDIDATURE_ID ) values ( 14, 15);
insert into JOB_CANDIDATURE_STATE_REL( PARENT_ID, JOB_CANDIDATURE_ID ) values ( 14, 16);
insert into JOB_CANDIDATURE_STATE_REL( PARENT_ID, JOB_CANDIDATURE_ID ) values ( 15, 13);
insert into JOB_CANDIDATURE_STATE_REL( PARENT_ID, JOB_CANDIDATURE_ID ) values ( 15, 16);

insert into ORIGIN( id, version, code ) values ( 20, 0, 'origin.infojobs');
insert into ORIGIN( id, version, code ) values ( 21, 0, 'origin.linkedin');
insert into ORIGIN( id, version, code ) values ( 22, 0, 'origin.reference');
insert into ORIGIN( id, version, code ) values ( 23, 0, 'origin.other');

insert into USER( id, version, name, surname, role, phoneNumber, email, language, rowsPerPage, managerId ) values( 500, 0, 'Abel', 'Ferrer Jiménez', 'ADMIN', '685555276', 'abel.ferrer.jimenez@gmail.com', 'es', 10, null);
insert into USER( id, version, name, surname, role, phoneNumber, email, language, rowsPerPage, managerId ) values( 501, 0, 'Isabel', 'Vallejo Medina', 'ADMIN','685555276', 'isabel.vallejo.medina@gmail.com', 'es', 10, null);
insert into USER( id, version, name, surname, role, phoneNumber, email, language, rowsPerPage, managerId ) values( 502, 0, 'Juan', 'Fernández', 'USER','685555276', 'jfernandez@gmail.com', 'es', 10, 500);
insert into USER( id, version, name, surname, role, phoneNumber, email, language, rowsPerPage, managerId ) values( 503, 0, 'Pedro', 'Smith', 'USER','685555276', 'psmith@gmail.com', 'es', 10, 500);
insert into USER( id, version, name, surname, role, phoneNumber, email, language, rowsPerPage, managerId ) values( 504, 0, 'Luis', 'Carrasco', 'USER','685555276', 'lcarrasco@gmail.com', 'es', 10, 500);
insert into USER( id, version, name, surname, role, phoneNumber, email, language, rowsPerPage, managerId ) values( 505, 0, 'Ana', 'Casoras', 'USER','685555276', 'acasoras@gmail.com', 'es', 10, 500);
insert into USER( id, version, name, surname, role, phoneNumber, email, language, rowsPerPage, managerId ) values( 506, 0, 'Luis Ángel', 'Nuestra Señora del manto', 'USER','685555276', 'lantrasenora@gmail.com', 'es', 10, 500);
insert into USER( id, version, name, surname, role, phoneNumber, email, language, rowsPerPage, managerId ) values( 507, 0, 'Anabel', 'Villafafila', 'USER','685555276', 'anabel@gmail.com', 'es', 10, 500);

insert into USERCLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 700, 0, 500, 500, 0 );
insert into USERCLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 701, 0, 501, 501, 0 );
insert into USERCLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 702, 0, 502, 502, 0 );
insert into USERCLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 703, 0, 503, 503, 0 );
insert into USERCLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 704, 0, 504, 504, 0 );
insert into USERCLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 705, 0, 505, 505, 0 );
insert into USERCLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 706, 0, 506, 506, 0 );
insert into USERCLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 707, 0, 507, 507, 0 );
insert into USERCLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 708, 0, 500, 502, 1 );
insert into USERCLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 709, 0, 500, 503, 1 );
insert into USERCLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 710, 0, 500, 504, 1 );
insert into USERCLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 711, 0, 500, 505, 1 );
insert into USERCLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 712, 0, 500, 506, 1 );
insert into USERCLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 713, 0, 500, 507, 1 );

insert into CANDIDATE( id, version, name, surname, PHONE_NUMBER, email, city, country, door, number, region, storey, street, zipCode, JOB_PROFILE, OWNER_ID) values ( 510, 0, 'Abel', 'Ferrer Jiménez','685555276', 'aferrer@gmail.com', 'Barcelona', 'España','1','85', 'Barcelona', 'Principal', 'Bailén', '08809', 'Arquitecto', 500);
insert into CANDIDATE( id, version, name, surname, PHONE_NUMBER, email, city, country, door, number, region, storey, street, zipCode, JOB_PROFILE, OWNER_ID) values ( 511, 0, 'Luís', 'González Sánchez','685555277', 'lgonzalez@gmail.com', 'Madrid', 'España','2','85', 'Madrid', '2', 'Miraflores', '80809', 'Programador JEE', 500);
insert into CANDIDATE( id, version, name, surname, PHONE_NUMBER, email, city, country, door, number, region, storey, street, zipCode, JOB_PROFILE, OWNER_ID) values ( 512, 0, 'Juan', 'Ayuso Pérez','685555278', 'ayuson32@gmail.com', 'Valencia', 'España','2','85', 'Valencia', '1', 'Horchata', '02809', 'Analista Java', 500);
insert into CANDIDATE( id, version, name, surname, PHONE_NUMBER, email, city, country, door, number, region, storey, street, zipCode, JOB_PROFILE, OWNER_ID) values ( 513, 0, 'Pedro', 'Gallardo Navarro','685555279', 'pedrito@gmail.com', 'Masnou', 'España','3','85', 'Barcelona', '3', 'Plaça Catalunya', '08328', 'Programador', 500);
insert into CANDIDATE( id, version, name, surname, PHONE_NUMBER, email, city, country, door, number, region, storey, street, zipCode, JOB_PROFILE, OWNER_ID) values ( 514, 0, 'Gonzalo', 'León Cuellar','685555280', 'gonzo73@gmail.com', 'Lugo', 'España','1','85', 'Lugo', '3', 'España', '02809', 'Programador', 500);
insert into CANDIDATE( id, version, name, surname, PHONE_NUMBER, email, city, country, door, number, region, storey, street, zipCode, JOB_PROFILE, OWNER_ID) values ( 515, 0, 'Antonio', 'García Collado','685555281', 'agc2@gmail.com', 'Pontevedra', 'España','2','85', 'Pontevedra', 'Principal', 'Luguense', '01809', 'Programador Junior', 501);

insert into CURRICULUM( id, version, candidate_id) values (801, 0, 510);

insert into EDUCATION( id, version, curriculum_id, degree, description,  startYear, school, stillStudying, endYear) values( 802, 0, 801, 'Licenciado en Matemáticas', 'Licenciado en Matemáticas por la universidad de Barcelona', 2000, 'Universitat de Barcelona', false, 2010);
insert into EDUCATION( id, version, curriculum_id, degree, description,  startYear, school, stillStudying, endYear) values( 803, 0, 801, 'Bachillerato', null,  1988, 'IES Thalassa', false, 1992);

insert into LANGUAGE( id, version, curriculum_id, name, level ) values( 804, 0, 801, 'Castellano', 'NATIVE' );
insert into LANGUAGE( id, version, curriculum_id, name, level ) values( 805, 0, 801, 'Català', 'NATIVE' );
insert into LANGUAGE( id, version, curriculum_id, name, level ) values( 806, 0, 801, 'English', 'HIGH' );

insert into SKILL( id, version, curriculum_id, name ) values( 807, 0, 801, 'Java');
insert into SKILL( id, version, curriculum_id, name ) values( 808, 0, 801, 'Spring');
insert into SKILL( id, version, curriculum_id, name ) values( 809, 0, 801, 'Hibernate');
insert into SKILL( id, version, curriculum_id, name ) values( 810, 0, 801, 'JSF');
insert into SKILL( id, version, curriculum_id, name ) values( 811, 0, 801, 'JEE');

insert into JOBEXPERIENCE( id, version, curriculum_id, companyName, description, startYear, startMonth, jobPosition, stillWorking, endYear, endMonth) values (812, 0, 801, 'Alten', 'Desarrollo de la herramienta para Gas Natural Fenosa con el que se dará cobertura a todas las actividades asociadas al proceso de expansión de la red y provisión de servicio en todos los ámbitos geográficos donde el grupo Gas Natural Fenosa (GNF) desarrolla sus negocios de distribución de gas natural y electricidad. Puesto de Arquitecto JEE desarrollando las siguientes tareas:', 2002, 12, 'Arquitecto JAVA', true, null, null );

insert into CLIENT( id, version, name, cif, ownerId, phoneNumber, city, region, deleted) values (1000, 0, 'Nivel 36', 'A0000000', 500, '935551414', 'Barcelona', 'Barcelona', false);

insert into EXPORT( id, version, exportName) values (1100, 0, 'USERS');
insert into EXPORT( id, version, exportName) values (1101, 0, 'CANDIDATES');

insert into EXPORTFIELD( id, version, exportId, fieldName, sortOrder, literalId, disabled, acquirerClass) values (1200, 0, 1100, 'NAME',      1, 'user.name',     false, 'es.nivel36.laie.ejb.export.acquirer.impl.UserExcelAcquirer$SurnameAcquirer');
insert into EXPORTFIELD( id, version, exportId, fieldName, sortOrder, literalId, disabled, acquirerClass) values (1201, 0, 1100, 'SURNAME',   2, 'user.surname',  false, 'es.nivel36.laie.ejb.export.acquirer.impl.UserExcelAcquirer$SurnameAcquirer');
insert into EXPORTFIELD( id, version, exportId, fieldName, sortOrder, literalId, disabled, acquirerClass) values (1202, 0, 1100, 'MAIL',      3, 'user.email',    false, 'es.nivel36.laie.ejb.export.acquirer.impl.UserExcelAcquirer$EmailAcquirer');
insert into EXPORTFIELD( id, version, exportId, fieldName, sortOrder, literalId, disabled, acquirerClass) values (1203, 0, 1100, 'DISABLED', -1, 'user.disabled', true , 'es.nivel36.laie.ejb.export.acquirer.impl.UserExcelAcquirer$DummyAcquirer');

insert into EXPORTDEFINITION (id, version, exportId, exportFieldId, sortOrder) values (1300, 0, 1100, 1201, 1);
insert into EXPORTDEFINITION (id, version, exportId, exportFieldId, sortOrder) values (1301, 0, 1100, 1202, 2);

insert into DOCUMENT_TEMPLATE (id, version, language, name, title, text) values (1500, 0, 'es', 'gdpr', 'Regulación general de protección de datos', '<p>Barcelona a %date%</p><br/><p>Inserta aquí el documento de GDPR de la empresa en castellano</p><p> </p><p> </p><p> </p><p align="center"><b>Firma</b></p>');
insert into DOCUMENT_TEMPLATE (id, version, language, name, title, text) values (1501, 0, 'ca', 'gdpr', 'Regulació general de protecció de dades', '<p>Barcelona a %date%</p><br/><p>Insereix aquí el documento de GDPR de la empresa en català</p><p> </p><p> </p><p> </p><p align="center"><b>Signatura</b></p>');

insert into CURRICULUM_TEMPLATE (id, version, title, css, description, screenshoot) values (1510, 0, 'cv_template.basic', 'body{font-family:sans-serif}h1{font-size:1.6em;margin-bottom:0}.data{border-top:1px solid grey;padding:1em 0 1em 0}.contact .label{font-weight:700;width:180px;display:inline-block;margin-bottom:.3em}.education,.jobExperience{padding-bottom:2em}.education .from-to-date,.jobExperience .from-to-date{float:left;width:180px;margin-top:-1.5em}.education .degree,.jobExperience .jobPosition{margin-left:180px;font-weight:700;font-size:1.2em;padding-bottom:.2em}.education .school,.jobExperience .companyName{margin-left:180px;font-size:1.1em;padding-bottom:.2em}.education .description,.jobExperience .description{margin-left:180px}.language .name{width:180px;margin-bottom:.3em;display:inline-block}.skill{display:inline-block;margin:0 1em .2em 0}.user-image{float:right; margin-top: -3em; border-left:20px solid white; z-index:10}.user-image .picture{object-fit: cover;image-orientation: from-image;width: 98px;border:1px solid black;}', 'cv_template.basic.description', null);
insert into CURRICULUM_TEMPLATE (id, version, title, css, description, screenshoot) values (1511, 0, 'cv_template.basic_anon', 'body{font-family:sans-serif}h1{font-size:1.6em;margin-bottom:0}.data{border-top:1px solid grey;padding:1em 0 1em 0}.contact .label{font-weight:700;width:180px;display:inline-block;margin-bottom:.3em}.education,.jobExperience{padding-bottom:2em}.education .from-to-date,.jobExperience .from-to-date{float:left;width:180px;margin-top:-1.5em}.education .degree,.jobExperience .jobPosition{margin-left:180px;font-weight:700;font-size:1.2em;padding-bottom:.2em}.education .school,.jobExperience .companyName{margin-left:180px;font-size:1.1em;padding-bottom:.2em}.education .description,.jobExperience .description{margin-left:180px}.language .name{width:180px;margin-bottom:.3em;display:inline-block}.skill{display:inline-block;margin:0 1em .2em 0}.user-image{float:right; margin-top: -3em; border-left:20px solid white; z-index:10}.user-image .picture{object-fit: cover;image-orientation: from-image;width: 98px;border:1px solid black;}.contact {display: none;}' , 'cv_template.basic_anon.description', null);
insert into CURRICULUM_TEMPLATE (id, version, title, css, description, screenshoot) values (1512, 0, 'cv_template.corp', 'body{font-family:sans-serif;margin:0}h1{font-size:1.6em;margin-bottom:2em}.name-profile{height:80px;background-color:#131313;padding:3em}.name-profile .name .label,.name-profile .profile .label{display:none}.name-profile .name{font-size:2em;color:#fff}.name-profile .profile{font-size:1.5em;color:#ff2}.contact{width:210px;background-color:#cfc7c7;float:left;height:100%;padding:2em}.contact h1{display:none}.contact .label{display:none}.contact .name{font-size:2em;font-weight:700}.educations,.jobExperiences,.languages,.skills{margin:20px 50px 0 300px}.education,.jobExperience,.langauge,.skill{padding-bottom:2em}.education .from-to-date,.jobExperience .from-to-date{float:left;width:180px;margin-top:-1.5em}.education .degree,.jobExperience .jobPosition{margin-left:180px;font-weight:700;font-size:1.2em;padding-bottom:.2em}.education .school,.jobExperience .companyName{margin-left:180px;font-size:1.1em;padding-bottom:.2em}.education .description,.jobExperience .description{margin-left:180px}.language .name{width:180px;margin-bottom:.3em;display:inline-block}.skill{display:inline-block;margin:0 1em .2em 0}.user-image{float:right;margin-top:-3em;border-left:20px solid #fff;z-index:10}.user-image .picture{object-fit:cover;image-orientation:from-image;width:98px;border:1px solid #000}', 'cv_template.corp.description', null);
insert into CURRICULUM_TEMPLATE (id, version, title, css, description, screenshoot) values (1513, 0, 'cv_template.corp_anon', '@page{margin:0pt;} body{font-family:sans-serif}h1{font-size:1.6em;margin-bottom:0}.data{border-top:1px solid grey;padding:1em 0 1em 0}.contact .label{font-weight:700;width:180px;display:inline-block;margin-bottom:.3em}.education,.jobExperience{padding-bottom:2em}.education .from-to-date,.jobExperience .from-to-date{float:left;width:180px;margin-top:-1.5em}.education .degree,.jobExperience .jobPosition{margin-left:180px;font-weight:700;font-size:1.2em;padding-bottom:.2em}.education .school,.jobExperience .companyName{margin-left:180px;font-size:1.1em;padding-bottom:.2em}.education .description,.jobExperience .description{margin-left:180px}.language .name{width:180px;margin-bottom:.3em;display:inline-block}.skill{display:inline-block;margin:0 1em .2em 0}.user-image{float:right; margin-top: -3em; border-left:20px solid white; z-index:10}.user-image .picture{object-fit: cover;image-orientation: from-image;width: 98px;border:1px solid black;}.contact {display: none;}' , 'cv_template.corp_anon.description', null);

insert into JOB_OFFER(id, version, state, city, region, clientId, closeDate, description, maxSalary, minSalary, openDate, ownerId, places, published, title) values(1600, 0, 'CREATED', 'Barcelona', 'Barcelona', '1000', (TO_DATE('17/12/2025', 'DD/MM/YYYY')), '<p>Se necesita un arquitecto de software</p>', 85000, 50000, (TO_DATE('17/12/2022', 'DD/MM/YYYY')), 500, 1, false, 'Arquitecto de software'); 

insert into JOB_OFFER_EVENT(id, version, date, jobOfferId, state, type, userId) values (1700, 1, (TO_DATE('17/12/2021', 'DD/MM/YYYY')), 1600, 'CREATED', 'MANUAL_EVENT', 500);

ALTER SEQUENCE HIBERNATE_SEQUENCE RESTART with 2000;