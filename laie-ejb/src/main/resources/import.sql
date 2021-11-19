insert into JOB_CANDIDATURE_STATE( id, version, name, first, approved, declined ) values ( 10, 0, 'job_candidature_state.not_contacted', true, false, false);
insert into JOB_CANDIDATURE_STATE( id, version, name, first, approved, declined ) values ( 11, 0, 'job_candidature_state.contacted', false, false, false);
insert into JOB_CANDIDATURE_STATE( id, version, name, first, approved, declined ) values ( 12, 0, 'job_candidature_state.submitted', false, false, false);
insert into JOB_CANDIDATURE_STATE( id, version, name, first, approved, declined ) values ( 13, 0, 'job_candidature_state.declined', false, false, true);
insert into JOB_CANDIDATURE_STATE( id, version, name, first, approved, declined ) values ( 14, 0, 'job_candidature_state.interviewed', false, false, false);
insert into JOB_CANDIDATURE_STATE( id, version, name, first, approved, declined ) values ( 15, 0, 'job_candidature_state.offered', false, false, false);
insert into JOB_CANDIDATURE_STATE( id, version, name, first, approved, declined ) values ( 16, 0, 'job_candidature_state.approved', false, true, false);

insert into ORIGIN( id, version, code ) values ( 20, 0, 'origin.infojobs');
insert into ORIGIN( id, version, code ) values ( 21, 0, 'origin.linkedin');
insert into ORIGIN( id, version, code ) values ( 22, 0, 'origin.reference');
insert into ORIGIN( id, version, code ) values ( 23, 0, 'origin.other');

insert into USER( id, version, uid, name, surname, phoneNumber, email, language, rowsPerPage, role, managerId ) values( 500, 0, 'uid500', 'Abel', 'Ferrer Jiménez','685555276', 'abel.ferrer.jimenez@gmail.com', 'es', 10, 'ADMIN', null);
insert into USER( id, version, uid, name, surname, phoneNumber, email, language, rowsPerPage, role, managerId ) values( 501, 0, 'uid501', 'Isabel', 'Vallejo Medina','685555276', 'isabel.vallejo.medina@gmail.com', 'es', 10, 'ADMIN', null);
insert into USER( id, version, uid, name, surname, phoneNumber, email, language, rowsPerPage, role, managerId ) values( 502, 0, 'uid502', 'Juan', 'Fernández','685555276', 'jfernandez@gmail.com', 'es', 10, 'USER', 500);
insert into USER( id, version, uid, name, surname, phoneNumber, email, language, rowsPerPage, role, managerId ) values( 503, 0, 'uid503', 'Pedro', 'Smith','685555276', 'psmith@gmail.com', 'es', 10, 'USER', 500);
insert into USER( id, version, uid, name, surname, phoneNumber, email, language, rowsPerPage, role, managerId ) values( 504, 0, 'uid504', 'Luis', 'Carrasco','685555276', 'lcarrasco@gmail.com', 'es', 10, 'USER', 500);
insert into USER( id, version, uid, name, surname, phoneNumber, email, language, rowsPerPage, role, managerId ) values( 505, 0, 'uid505', 'Ana', 'Casoras','685555276', 'acasoras@gmail.com', 'es', 10, 'USER', 500);
insert into USER( id, version, uid, name, surname, phoneNumber, email, language, rowsPerPage, role, managerId ) values( 506, 0, 'uid506', 'Luis Ángel', 'Nuestra Señora del manto','685555276', 'lantrasenora@gmail.com', 'es', 10, 'USER', 500);
insert into USER( id, version, uid, name, surname, phoneNumber, email, language, rowsPerPage, role, managerId ) values( 507, 0, 'uid507', 'Anabel', 'Villafafila','685555276', 'anabel@gmail.com', 'es', 10, 'USER', 500);

insert into CREDENTIAL( id, version, hashPassword, salt, created, userid ) values( 600, 0, '16AFB50A06A958ACEC2EDA9D70283139BC7B7372E21CC83F619CCF169E6E7956', 'FFFFFF', (TO_DATE('17/12/2015', 'DD/MM/YYYY')), 500);
insert into CREDENTIAL( id, version, hashPassword, salt, created, userid ) values( 601, 0, '16AFB50A06A958ACEC2EDA9D70283139BC7B7372E21CC83F619CCF169E6E7956', 'FFFFFF', (TO_DATE('17/12/2015', 'DD/MM/YYYY')), 501);
insert into CREDENTIAL( id, version, hashPassword, salt, created, userid ) values( 602, 0, '16AFB50A06A958ACEC2EDA9D70283139BC7B7372E21CC83F619CCF169E6E7956', 'FFFFFF', (TO_DATE('17/12/2015', 'DD/MM/YYYY')), 502);
insert into CREDENTIAL( id, version, hashPassword, salt, created, userid ) values( 603, 0, '16AFB50A06A958ACEC2EDA9D70283139BC7B7372E21CC83F619CCF169E6E7956', 'FFFFFF', (TO_DATE('17/12/2015', 'DD/MM/YYYY')), 503);
insert into CREDENTIAL( id, version, hashPassword, salt, created, userid ) values( 604, 0, '16AFB50A06A958ACEC2EDA9D70283139BC7B7372E21CC83F619CCF169E6E7956', 'FFFFFF', (TO_DATE('17/12/2015', 'DD/MM/YYYY')), 504);
insert into CREDENTIAL( id, version, hashPassword, salt, created, userid ) values( 605, 0, '16AFB50A06A958ACEC2EDA9D70283139BC7B7372E21CC83F619CCF169E6E7956', 'FFFFFF', (TO_DATE('17/12/2015', 'DD/MM/YYYY')), 505);
insert into CREDENTIAL( id, version, hashPassword, salt, created, userid ) values( 606, 0, '16AFB50A06A958ACEC2EDA9D70283139BC7B7372E21CC83F619CCF169E6E7956', 'FFFFFF', (TO_DATE('17/12/2015', 'DD/MM/YYYY')), 506);
insert into CREDENTIAL( id, version, hashPassword, salt, created, userid ) values( 607, 0, '16AFB50A06A958ACEC2EDA9D70283139BC7B7372E21CC83F619CCF169E6E7956', 'FFFFFF', (TO_DATE('17/12/2015', 'DD/MM/YYYY')), 507);

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

insert into CANDIDATE( id, uid, version, name, surname, phoneNumber, email, city, country, door, number, region, storey, street, zipCode, jobProfile, ownerId) values ( 510, 'uid510', 0, 'Abel', 'Ferrer Jiménez','685555276', 'aferrer@gmail.com', 'Barcelona', 'España','1','85', 'Barcelona', 'Principal', 'Bailén', '08809', 'Arquitecto', 500);
insert into CANDIDATE( id, uid, version, name, surname, phoneNumber, email, city, country, door, number, region, storey, street, zipCode, jobProfile, ownerId) values ( 511, 'uid511', 0, 'Luís', 'González Sánchez','685555277', 'lgonzalez@gmail.com', 'Madrid', 'España','2','85', 'Madrid', '2', 'Miraflores', '80809', 'Programador JEE', 500);
insert into CANDIDATE( id, uid, version, name, surname, phoneNumber, email, city, country, door, number, region, storey, street, zipCode, jobProfile, ownerId) values ( 512, 'uid512', 0, 'Juan', 'Ayuso Pérez','685555278', 'ayuson32@gmail.com', 'Valencia', 'España','2','85', 'Valencia', '1', 'Horchata', '02809', 'Analista Java', 500);
insert into CANDIDATE( id, uid, version, name, surname, phoneNumber, email, city, country, door, number, region, storey, street, zipCode, jobProfile, ownerId) values ( 513, 'uid513', 0, 'Pedro', 'Gallardo Navarro','685555279', 'pedrito@gmail.com', 'Masnou', 'España','3','85', 'Barcelona', '3', 'Plaça Catalunya', '08328', 'Programador', 500);
insert into CANDIDATE( id, uid, version, name, surname, phoneNumber, email, city, country, door, number, region, storey, street, zipCode, jobProfile, ownerId) values ( 514, 'uid514', 0, 'Gonzalo', 'León Cuellar','685555280', 'gonzo73@gmail.com', 'Lugo', 'España','1','85', 'Lugo', '3', 'España', '02809', 'Programador', 500);
insert into CANDIDATE( id, uid, version, name, surname, phoneNumber, email, city, country, door, number, region, storey, street, zipCode, jobProfile, ownerId) values ( 515, 'uid515', 0, 'Antonio', 'García Collado','685555281', 'agc2@gmail.com', 'Pontevedra', 'España','2','85', 'Pontevedra', 'Principal', 'Luguense', '01809', 'Programador Junior', 501);

insert into CURRICULUM( id, uid, version, candidate_id) values (801, 'uid801', 0, 510);

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

insert into JOBEXPERIENCE( id, version, curriculum_id, companyName, description, startDate, jobPosition, stillWorking, endDate) values (812, 0, 801, 'Alten', 'Desarrollo de la herramienta para Gas Natural Fenosa con el que se dará cobertura a todas las actividades asociadas al proceso de expansión de la red y provisión de servicio en todos los ámbitos geográficos donde el grupo Gas Natural Fenosa (GNF) desarrolla sus negocios de distribución de gas natural y electricidad. Puesto de Arquitecto JEE desarrollando las siguientes tareas:', CAST('aced00057372000d6a6176612e74696d652e536572955d84ba1b2248b20c0000787077060c000007da0378' AS VARBINARY(255)), 'Arquitecto JAVA', true, null );

insert into CLIENT( id, uid, version, name, cif, ownerId, phoneNumber, deleted) values (1000, 'uid1000', 0, 'F.C. Barcelona', 'A0000000', 500, '935551414', false);

insert into EXPORT( id, version, exportName) values (1100, 0, 'USERS');
insert into EXPORT( id, version, exportName) values (1101, 0, 'CANDIDATES');

insert into EXPORTFIELD( id, version, exportId, fieldName, sortOrder, literalId, disabled, acquirerClass) values (1200, 0, 1100, 'NAME',      1, 'user.name',     false, 'es.nivel36.laie.ejb.export.acquirer.impl.UserExcelAcquirer$SurnameAcquirer');
insert into EXPORTFIELD( id, version, exportId, fieldName, sortOrder, literalId, disabled, acquirerClass) values (1201, 0, 1100, 'SURNAME',   2, 'user.surname',  false, 'es.nivel36.laie.ejb.export.acquirer.impl.UserExcelAcquirer$SurnameAcquirer');
insert into EXPORTFIELD( id, version, exportId, fieldName, sortOrder, literalId, disabled, acquirerClass) values (1202, 0, 1100, 'MAIL',      3, 'user.email',    false, 'es.nivel36.laie.ejb.export.acquirer.impl.UserExcelAcquirer$EmailAcquirer');
insert into EXPORTFIELD( id, version, exportId, fieldName, sortOrder, literalId, disabled, acquirerClass) values (1203, 0, 1100, 'DISABLED', -1, 'user.disabled', true , 'es.nivel36.laie.ejb.export.acquirer.impl.UserExcelAcquirer$DummyAcquirer');

insert into EXPORTDEFINITION (id, version, exportId, exportFieldId, sortOrder) values (1300, 0, 1100, 1201, 1);
insert into EXPORTDEFINITION (id, version, exportId, exportFieldId, sortOrder) values (1301, 0, 1100, 1202, 2);

insert into DOCUMENT_TEMPLATE (id, uid, version, language, name, title, text) values (1500, '1500', 0, 'es', 'gdpr', 'Regulación general de protección de datos', '<p>Barcelona a %date%</p><br/><p>Inserta aquí el documento de GDPR de la empresa en castellano</p><p> </p><p> </p><p> </p><p align="center"><b>Firma</b></p>');
insert into DOCUMENT_TEMPLATE (id, uid, version, language, name, title, text) values (1501, '1501', 0, 'ca', 'gdpr', 'Regulació general de protecció de dades', '<p>Barcelona a %date%</p><br/><p>Insereix aquí el documento de GDPR de la empresa en català</p><p> </p><p> </p><p> </p><p align="center"><b>Signatura</b></p>');

insert into CURRICULUM_TEMPLATE (id, version, title, css, description, screenshoot) values (1510, 0, 'cv_template.basic', 'body{font-family:sans-serif}h1{font-size:1.6em;margin-bottom:0}.data{border-top:1px solid grey;padding:1em 0 1em 0}.contact .label{font-weight:700;width:180px;display:inline-block;margin-bottom:.3em}.education,.jobExperience{padding-bottom:2em}.education .from-to-date,.jobExperience .from-to-date{float:left;width:180px;margin-top:-1.5em}.education .degree,.jobExperience .jobPosition{margin-left:180px;font-weight:700;font-size:1.2em;padding-bottom:.2em}.education .school,.jobExperience .companyName{margin-left:180px;font-size:1.1em;padding-bottom:.2em}.education .description,.jobExperience .description{margin-left:180px}.language .name{width:180px;margin-bottom:.3em;display:inline-block}.skill{display:inline-block;margin:0 1em .2em 0}.user-image{float:right; margin-top: -3em; border-left:20px solid white; z-index:10}.user-image .picture{object-fit: cover;image-orientation: from-image;width: 98px;border:1px solid black;}', 'cv_template.basic.description', null);
insert into CURRICULUM_TEMPLATE (id, version, title, css, description, screenshoot) values (1511, 0, 'cv_template.basic_anon', 'body{font-family:sans-serif}h1{font-size:1.6em;margin-bottom:0}.data{border-top:1px solid grey;padding:1em 0 1em 0}.contact .label{font-weight:700;width:180px;display:inline-block;margin-bottom:.3em}.education,.jobExperience{padding-bottom:2em}.education .from-to-date,.jobExperience .from-to-date{float:left;width:180px;margin-top:-1.5em}.education .degree,.jobExperience .jobPosition{margin-left:180px;font-weight:700;font-size:1.2em;padding-bottom:.2em}.education .school,.jobExperience .companyName{margin-left:180px;font-size:1.1em;padding-bottom:.2em}.education .description,.jobExperience .description{margin-left:180px}.language .name{width:180px;margin-bottom:.3em;display:inline-block}.skill{display:inline-block;margin:0 1em .2em 0}.user-image{float:right; margin-top: -3em; border-left:20px solid white; z-index:10}.user-image .picture{object-fit: cover;image-orientation: from-image;width: 98px;border:1px solid black;}.contact {display: none;}' , 'cv_template.basic_anon.description', null);
insert into CURRICULUM_TEMPLATE (id, version, title, css, description, screenshoot) values (1512, 0, 'cv_template.corp', 'body{font-family:sans-serif;margin:0}h1{font-size:1.6em;margin-bottom:2em}.name-profile{height:80px;background-color:#131313;padding:3em}.name-profile .name .label,.name-profile .profile .label{display:none}.name-profile .name{font-size:2em;color:#fff}.name-profile .profile{font-size:1.5em;color:#ff2}.contact{width:210px;background-color:#cfc7c7;float:left;height:100%;padding:2em}.contact h1{display:none}.contact .label{display:none}.contact .name{font-size:2em;font-weight:700}.educations,.jobExperiences,.languages,.skills{margin:20px 50px 0 300px}.education,.jobExperience,.langauge,.skill{padding-bottom:2em}.education .from-to-date,.jobExperience .from-to-date{float:left;width:180px;margin-top:-1.5em}.education .degree,.jobExperience .jobPosition{margin-left:180px;font-weight:700;font-size:1.2em;padding-bottom:.2em}.education .school,.jobExperience .companyName{margin-left:180px;font-size:1.1em;padding-bottom:.2em}.education .description,.jobExperience .description{margin-left:180px}.language .name{width:180px;margin-bottom:.3em;display:inline-block}.skill{display:inline-block;margin:0 1em .2em 0}.user-image{float:right;margin-top:-3em;border-left:20px solid #fff;z-index:10}.user-image .picture{object-fit:cover;image-orientation:from-image;width:98px;border:1px solid #000}', 'cv_template.corp.description', null);
insert into CURRICULUM_TEMPLATE (id, version, title, css, description, screenshoot) values (1513, 0, 'cv_template.corp_anon', '@page{margin:0pt;} body{font-family:sans-serif}h1{font-size:1.6em;margin-bottom:0}.data{border-top:1px solid grey;padding:1em 0 1em 0}.contact .label{font-weight:700;width:180px;display:inline-block;margin-bottom:.3em}.education,.jobExperience{padding-bottom:2em}.education .from-to-date,.jobExperience .from-to-date{float:left;width:180px;margin-top:-1.5em}.education .degree,.jobExperience .jobPosition{margin-left:180px;font-weight:700;font-size:1.2em;padding-bottom:.2em}.education .school,.jobExperience .companyName{margin-left:180px;font-size:1.1em;padding-bottom:.2em}.education .description,.jobExperience .description{margin-left:180px}.language .name{width:180px;margin-bottom:.3em;display:inline-block}.skill{display:inline-block;margin:0 1em .2em 0}.user-image{float:right; margin-top: -3em; border-left:20px solid white; z-index:10}.user-image .picture{object-fit: cover;image-orientation: from-image;width: 98px;border:1px solid black;}.contact {display: none;}' , 'cv_template.corp_anon.description', null);

ALTER SEQUENCE HIBERNATE_SEQUENCE RESTART with 2000;