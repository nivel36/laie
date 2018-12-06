insert into CONTRACTTYPE( id, version, name, description) values ( 1, 0, 'contrato_indefinido','contrato_indefinido.description');
insert into CONTRACTTYPE( id, version, name, description) values ( 2, 0, 'contrato_indefinido_fijos_discontinuos','contrato_indefinido_fijos_discontinuos.description');

insert into JOB_OFFER_STATE( id, version, name, color) values (10, 0, 'job_offer_state.opened', 'green');
insert into JOB_OFFER_STATE( id, version, name, color) values (11, 0, 'job_offer_state.closed', 'red');
insert into JOB_OFFER_STATE( id, version, name, color) values (12, 0, 'job_offer_state.cancelled', 'red');
insert into JOB_OFFER_STATE( id, version, name, color) values (13, 0, 'job_offer_state.finished', 'green');
insert into JOB_OFFER_STATE( id, version, name, color) values (14, 0, 'job_offer_state.paused', 'yellow');

insert into CONTRACTDURATION( id, version, name, description ) values ( 101, 0, 'one_year', 'one_year.description');

insert into ORIGIN( id, version, code ) values ( 110, 0, 'infojobs');
insert into ORIGIN( id, version, code ) values ( 111, 0, 'linkedin');
insert into ORIGIN( id, version, code ) values ( 112, 0, 'reference');
insert into ORIGIN( id, version, code ) values ( 113, 0, 'other');

insert into LANGUAGELEVEL( id, version, name ) values ( 120, 0, 'high');
insert into LANGUAGELEVEL( id, version, name ) values ( 121, 0, 'medium');
insert into LANGUAGELEVEL( id, version, name ) values ( 122, 0, 'low');
insert into LANGUAGELEVEL( id, version, name ) values ( 123, 0, 'native');

insert into JOB_CANDIDATURE_STATE( id, version, name, color ) values ( 130, 0, 'job_candidature_state.not_contacted', 'green');
insert into JOB_CANDIDATURE_STATE( id, version, name, color ) values ( 131, 0, 'job_candidature_state.contacted', 'green');
insert into JOB_CANDIDATURE_STATE( id, version, name, color ) values ( 132, 0, 'job_candidature_state.submitted', 'green');
insert into JOB_CANDIDATURE_STATE( id, version, name, color ) values ( 133, 0, 'job_candidature_state.declined', 'green');
insert into JOB_CANDIDATURE_STATE( id, version, name, color ) values ( 134, 0, 'job_candidature_state.interviewed', 'green');
insert into JOB_CANDIDATURE_STATE( id, version, name, color ) values ( 135, 0, 'job_candidature_state.offered', 'green');
insert into JOB_CANDIDATURE_STATE( id, version, name, color ) values ( 135, 0, 'job_candidature_state.approved', 'green');

insert into CURRICULUM( id, version) values (301, 0);

insert into EDUCATION( id, version, curriculumId, degree, description,  startYear, school, stillStudying, endYear) values( 401, 0, 301, 'Licenciado en Matemáticas', 'Licenciado en Matemáticas por la universidad de Barcelona', 2000, 'Universitat de Barcelona', false, 2010);
insert into EDUCATION( id, version, curriculumId, degree, description,  startYear, school, stillStudying, endYear) values( 402, 0, 301, 'Bachillerato', null,  1988, 'IES Thalassa', false, 1992);

insert into LANGUAGE( id, version, curriculumId, name, level ) values( 501, 0, 301, 'Castellano', 'native' );
insert into LANGUAGE( id, version, curriculumId, name, level ) values( 502, 0, 301, 'Català', 'native' );
insert into LANGUAGE( id, version, curriculumId, name, level ) values( 503, 0, 301, 'English', 'high' );

insert into SKILL( id, version, curriculumId, name ) values( 601, 0, 301, 'Java');
insert into SKILL( id, version, curriculumId, name ) values( 602, 0, 301, 'Spring');
insert into SKILL( id, version, curriculumId, name ) values( 603, 0, 301, 'Hibernate');
insert into SKILL( id, version, curriculumId, name ) values( 604, 0, 301, 'JSF');
insert into SKILL( id, version, curriculumId, name ) values( 605, 0, 301, 'JEE');

insert into JOBEXPERIENCE( id, version, curriculumId, companyName, description, startDate, jobPosition, stillWorking, endDate) values (701, 0, 301, 'Alten', 'Desarrollo de la herramienta para Gas Natural Fenosa con el que se dará cobertura a todas las actividades asociadas al proceso de expansión de la red y provisión de servicio en todos los ámbitos geográficos donde el grupo Gas Natural Fenosa (GNF) desarrolla sus negocios de distribución de gas natural y electricidad. Puesto de Arquitecto JEE desarrollando las siguientes tareas:' , CAST('aced00057372000d6a6176612e74696d652e536572955d84ba1b2248b20c0000787077060c000007da0378' AS VARBINARY(255)) , 'Arquitecto JAVA', true, null );

insert into I18NSTRING( id, version, locale, key, text ) values( 800, 0, 'es', 'all_day', 'Jornada completa' );
insert into I18NSTRING( id, version, locale, key, text ) values( 801, 0, 'ca', 'all_day', 'Jornada completa' );

insert into I18NSTRING( id, version, locale, key, text ) values( 805, 0, 'ca', 'high', 'Alt' );
insert into I18NSTRING( id, version, locale, key, text ) values( 806, 0, 'es', 'high', 'Alto' );
insert into I18NSTRING( id, version, locale, key, text ) values( 810, 0, 'ca', 'medium', 'Mitjà' );
insert into I18NSTRING( id, version, locale, key, text ) values( 811, 0, 'es', 'medium', 'Medio' );
insert into I18NSTRING( id, version, locale, key, text ) values( 815, 0, 'ca', 'low', 'Baix' );
insert into I18NSTRING( id, version, locale, key, text ) values( 816, 0, 'es', 'low', 'Bajo' );
insert into I18NSTRING( id, version, locale, key, text ) values( 820, 0, 'ca', 'native', 'Natiu' );
insert into I18NSTRING( id, version, locale, key, text ) values( 821, 0, 'es', 'native', 'Nativo' );

insert into I18NSTRING( id, version, locale, key, text ) values( 750, 0, 'ca', 'infojobs', 'Infojobs' );
insert into I18NSTRING( id, version, locale, key, text ) values( 751, 0, 'es', 'infojobs', 'Infojobs' );
insert into I18NSTRING( id, version, locale, key, text ) values( 752, 0, 'ca', 'linkedin', 'LinkedIn' );
insert into I18NSTRING( id, version, locale, key, text ) values( 753, 0, 'es', 'linkedin', 'LinkedIn' );
insert into I18NSTRING( id, version, locale, key, text ) values( 754, 0, 'ca', 'reference', 'Referència' );
insert into I18NSTRING( id, version, locale, key, text ) values( 755, 0, 'es', 'reference', 'Referencia' );
insert into I18NSTRING( id, version, locale, key, text ) values( 756, 0, 'ca', 'other', 'Altre' );
insert into I18NSTRING( id, version, locale, key, text ) values( 757, 0, 'es', 'other', 'Otro' );

insert into I18NSTRING( id, version, locale, key, text ) values( 825, 0, 'es', 'contrato_indefinido', 'Contrato indefinido' );
insert into I18NSTRING( id, version, locale, key, text ) values( 826, 0, 'ca', 'contrato_indefinido', 'Contracte indefinit' );
insert into I18NSTRING( id, version, locale, key, text ) values( 830, 0, 'es', 'contrato_indefinido.description', 'Es aquel que se concierta sin establecer límites de tiempo en la prestación de los servicios, en cuanto a la duración del contrato.' );
insert into I18NSTRING( id, version, locale, key, text ) values( 831, 0, 'ca', 'contrato_indefinido.description', 'Es aquel que se concierta sin establecer límites de tiempo en la prestación de los servicios, en cuanto a la duración del contrato.' );

insert into I18NSTRING( id, version, locale, key, text ) values( 835, 0, 'es', 'contrato_indefinido_fijos_discontinuos', 'Contrato indefinido de fijos discontinuos' );
insert into I18NSTRING( id, version, locale, key, text ) values( 836, 0, 'ca', 'contrato_indefinido_fijos_discontinuos', 'Contrato indefinido de fijos discontinuos' );
insert into I18NSTRING( id, version, locale, key, text ) values( 840, 0, 'es', 'contrato_indefinido_fijos_discontinuos.description', 'Es el que se concierta para realizar trabajos que tengan el carácter de fijos discontinuos y no se repitan en fechas ciertas, dentro del volumen normal de actividad de la empresa.A efectos de prestaciones por desempleo, también se consideran trabajadores fijos discontinuos los que desarrollen trabajos fijos y periódicos que se repiten en fechas ciertas.' );
insert into I18NSTRING( id, version, locale, key, text ) values( 841, 0, 'ca', 'contrato_indefinido_fijos_discontinuos.description', 'Es el que se concierta para realizar trabajos que tengan el carácter de fijos discontinuos y no se repitan en fechas ciertas, dentro del volumen normal de actividad de la empresa.A efectos de prestaciones por desempleo, también se consideran trabajadores fijos discontinuos los que desarrollen trabajos fijos y periódicos que se repiten en fechas ciertas.' );

insert into I18NSTRING( id, version, locale, key, text ) values( 860, 0, 'es', 'open', 'Abierta' );
insert into I18NSTRING( id, version, locale, key, text ) values( 861, 0, 'ca', 'open', 'Oberta' );
insert into I18NSTRING( id, version, locale, key, text ) values( 862, 0, 'es', 'closed', 'Cerrada' );
insert into I18NSTRING( id, version, locale, key, text ) values( 863, 0, 'ca', 'closed', 'Tancada' );

insert into ROLE( id, version, name ) values( 911, 0, 'ADMIN' );
insert into ROLE( id, version, name ) values( 912, 0, 'USER' );

insert into USER( id, version, email, language, name, rowsPerPage, surname, password, roleId, deleted, imageFileName, ownerId ) values( 901, 0, 'abel.ferrer.jimenez@gmail.com', 'es', 'Abel', 10, 'Ferrer', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', 911, false, null, 901);
insert into USER( id, version, email, language, name, rowsPerPage, surname, password, roleId, deleted, imageFileName, ownerId ) values( 902, 0, 'isabel.vallejo.medina@gmail.com', 'es', 'Isabel', 10, 'Vallejo', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', 911, false, null, 902);

insert into USERCLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 970, 0, 901, 901, 0 );
insert into USERCLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 971, 0, 902, 902, 0 );

insert into CANDIDATE( id, version, name, surname, phoneNumber, email, city, country, door, number, state, storey, street, zipCode, jobProfile, curriculumId, ownerId, deleted) values ( 201, 0, 'Abel', 'Ferrer Jiménez','685555276', 'abel.ferrer.jimenez@gmail.com', 'Barcelona', 'España','1','85', 'Barcelona', 'Principal', 'Bailén', '08809', 'Arquitecto', 301, 901, false);
insert into CANDIDATE( id, version, name, surname, phoneNumber, email, city, country, door, number, state, storey, street, zipCode, jobProfile, curriculumId, ownerId, deleted) values ( 202, 0, 'Luís', 'González Sánchez','685555277', 'lgonzalez@gmail.com', 'Madrid', 'España','2','85', 'Madrid', '2', 'Miraflores', '80809', 'Programador JEE', NULL, 901, false);
insert into CANDIDATE( id, version, name, surname, phoneNumber, email, city, country, door, number, state, storey, street, zipCode, jobProfile, curriculumId, ownerId, deleted) values ( 203, 0, 'Juan', 'Ayuso Pérez','685555278', 'ayuson32@gmail.com', 'Valencia', 'España','2','85', 'Valencia', '1', 'Horchata', '02809', 'Analista Java', NULL, 901, false);
insert into CANDIDATE( id, version, name, surname, phoneNumber, email, city, country, door, number, state, storey, street, zipCode, jobProfile, curriculumId, ownerId, deleted) values ( 204, 0, 'Pedro', 'Gallardo Navarro','685555279', 'pedrito@gmail.com', 'Masnou', 'España','3','85', 'Barcelona', '3', 'Plaça Catalunya', '08328', 'Programador', NULL, 901, false);
insert into CANDIDATE( id, version, name, surname, phoneNumber, email, city, country, door, number, state, storey, street, zipCode, jobProfile, curriculumId, ownerId, deleted) values ( 205, 0, 'Gonzalo', 'León Cuellar','685555280', 'gonzo73@gmail.com', 'Lugo', 'España','1','85', 'Lugo', '3', 'España', '02809', 'Programador', NULL, 901, false);
insert into CANDIDATE( id, version, name, surname, phoneNumber, email, city, country, door, number, state, storey, street, zipCode, jobProfile, curriculumId, ownerId, deleted) values ( 206, 0, 'Antonio', 'García Collado','685555281', 'agc2@gmail.com', 'Pontevedra', 'España','2','85', 'Pontevedra', 'Principal', 'Luguense', '01809', 'Programador Junior', NULL, 901, false);

insert into CLIENT( id, version, name, cif, ownerId, phoneNumber, deleted) values (1000, 0, 'F.C. Barcelona', 'A0000000', 901, '935551414', false);

insert into EXPORT( id, version, exportName) values (1100, 0, 'USERS');
insert into EXPORT( id, version, exportName) values (1101, 0, 'CANDIDATES');

insert into EXPORTFIELD( id, version, exportId, fieldName, sortOrder, literalId, disabled) values (1200, 0, 1100, 'NAME',      1, 'user.name',     false);
insert into EXPORTFIELD( id, version, exportId, fieldName, sortOrder, literalId, disabled) values (1201, 0, 1100, 'SURNAME',   2, 'user.surname',  false);
insert into EXPORTFIELD( id, version, exportId, fieldName, sortOrder, literalId, disabled) values (1202, 0, 1100, 'MAIL',      3, 'user.email',    false);
insert into EXPORTFIELD( id, version, exportId, fieldName, sortOrder, literalId, disabled) values (1203, 0, 1100, 'DISABLED', -1, 'user.disabled', true );

insert into EXPORTDEFINITION (id, version, exportId, exportFieldId, sortOrder) values (1300, 0, 1100, 1201, 1);

ALTER SEQUENCE HIBERNATE_SEQUENCE RESTART with 2000;