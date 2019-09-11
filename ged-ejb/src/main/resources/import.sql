insert into JOB_OFFER_STATE( id, version, name, first, last, color) values (1, 0, 'job_offer_state.opened', false, false,'green');
insert into JOB_OFFER_STATE( id, version, name, first, last, color) values (2, 0, 'job_offer_state.closed', false, false, 'red');
insert into JOB_OFFER_STATE( id, version, name, first, last, color) values (3, 0, 'job_offer_state.cancelled', false, true, 'brown');
insert into JOB_OFFER_STATE( id, version, name, first, last, color) values (4, 0, 'job_offer_state.finished', false, true, 'blue');
insert into JOB_OFFER_STATE( id, version, name, first, last, color) values (5, 0, 'job_offer_state.paused', false, false, 'blueviolet');
insert into JOB_OFFER_STATE( id, version, name, first, last, color) values (6, 0, 'job_offer_state.created', true, false, 'cornflowerblue');

insert into JOB_CANDIDATURE_STATE( id, version, name, first, last, color ) values ( 10, 0, 'job_candidature_state.not_contacted', true, false, 'green');
insert into JOB_CANDIDATURE_STATE( id, version, name, first, last, color ) values ( 11, 0, 'job_candidature_state.contacted', false, false,  'blue');
insert into JOB_CANDIDATURE_STATE( id, version, name, first, last, color ) values ( 12, 0, 'job_candidature_state.submitted', false, false,  'brown');
insert into JOB_CANDIDATURE_STATE( id, version, name, first, last, color ) values ( 13, 0, 'job_candidature_state.declined', false, true,  'red');
insert into JOB_CANDIDATURE_STATE( id, version, name, first, last, color ) values ( 14, 0, 'job_candidature_state.interviewed', false, false,  'blueviolet');
insert into JOB_CANDIDATURE_STATE( id, version, name, first, last, color ) values ( 15, 0, 'job_candidature_state.offered', false, false,  'cornflowerblue');
insert into JOB_CANDIDATURE_STATE( id, version, name, first, last, color ) values ( 16, 0, 'job_candidature_state.approved', false, true,  'gray');

insert into ORIGIN( id, version, code ) values ( 20, 0, 'infojobs');
insert into ORIGIN( id, version, code ) values ( 21, 0, 'linkedin');
insert into ORIGIN( id, version, code ) values ( 22, 0, 'reference');

insert into EVENT_TYPE( id, version, name, icon ) values ( 30, 0, 'event_type.other', 'fa-pencil');
insert into EVENT_TYPE( id, version, name, icon ) values ( 31, 0, 'event_type.email', 'fa-envelope');
insert into EVENT_TYPE( id, version, name, icon ) values ( 32, 0, 'event_type.phone_call', 'fa-phone');
insert into EVENT_TYPE( id, version, name, icon ) values ( 33, 0, 'event_type.video_call', 'fa-computer');
insert into EVENT_TYPE( id, version, name, icon ) values ( 34, 0, 'event_type.message', 'fa-pencil');
insert into EVENT_TYPE( id, version, name, icon ) values ( 34, 0, 'event_type.meeting', 'fa-pencil');


insert into I18NSTRING( id, version, locale, key, text ) values( 100, 0, 'es', 'job_offer_state.opened', 'Abierta' );
insert into I18NSTRING( id, version, locale, key, text ) values( 101, 0, 'ca', 'job_offer_state.opened', 'Oberta' );
insert into I18NSTRING( id, version, locale, key, text ) values( 105, 0, 'es', 'job_offer_state.closed', 'Cerrada' );
insert into I18NSTRING( id, version, locale, key, text ) values( 106, 0, 'ca', 'job_offer_state.closed', 'Tancada' );
insert into I18NSTRING( id, version, locale, key, text ) values( 110, 0, 'es', 'job_offer_state.cancelled', 'Cancelada' );
insert into I18NSTRING( id, version, locale, key, text ) values( 111, 0, 'ca', 'job_offer_state.cancelled', 'Cancel·lada' );
insert into I18NSTRING( id, version, locale, key, text ) values( 115, 0, 'es', 'job_offer_state.finished', 'Finalizada' );
insert into I18NSTRING( id, version, locale, key, text ) values( 116, 0, 'ca', 'job_offer_state.finished', 'Finalitzada' );
insert into I18NSTRING( id, version, locale, key, text ) values( 120, 0, 'es', 'job_offer_state.paused', 'Pausada' );
insert into I18NSTRING( id, version, locale, key, text ) values( 121, 0, 'ca', 'job_offer_state.paused', 'Pausada' );
insert into I18NSTRING( id, version, locale, key, text ) values( 125, 0, 'es', 'job_offer_state.created', 'Creada' );
insert into I18NSTRING( id, version, locale, key, text ) values( 126, 0, 'ca', 'job_offer_state.created', 'Creada' );

insert into I18NSTRING( id, version, locale, key, text ) values( 200, 0, 'es', 'job_candidature_state.not_contacted', 'Sin contactar' );
insert into I18NSTRING( id, version, locale, key, text ) values( 201, 0, 'ca', 'job_candidature_state.not_contacted', 'Sense contactar' );
insert into I18NSTRING( id, version, locale, key, text ) values( 205, 0, 'es', 'job_candidature_state.contacted', 'Contactado' );
insert into I18NSTRING( id, version, locale, key, text ) values( 206, 0, 'ca', 'job_candidature_state.contacted', 'Contactat' );
insert into I18NSTRING( id, version, locale, key, text ) values( 210, 0, 'es', 'job_candidature_state.submitted', 'Enviada' );
insert into I18NSTRING( id, version, locale, key, text ) values( 211, 0, 'ca', 'job_candidature_state.submitted', 'Enviada' );
insert into I18NSTRING( id, version, locale, key, text ) values( 215, 0, 'es', 'job_candidature_state.declined', 'Rechazada' );
insert into I18NSTRING( id, version, locale, key, text ) values( 216, 0, 'ca', 'job_candidature_state.declined', 'Refusada' );
insert into I18NSTRING( id, version, locale, key, text ) values( 220, 0, 'es', 'job_candidature_state.interviewed', 'Entrevistado' );
insert into I18NSTRING( id, version, locale, key, text ) values( 221, 0, 'ca', 'job_candidature_state.interviewed', 'Entrevistado' );
insert into I18NSTRING( id, version, locale, key, text ) values( 225, 0, 'es', 'job_candidature_state.offered', 'Ofrecido' );
insert into I18NSTRING( id, version, locale, key, text ) values( 226, 0, 'ca', 'job_candidature_state.offered', 'Oferigut' );
insert into I18NSTRING( id, version, locale, key, text ) values( 230, 0, 'es', 'job_candidature_state.approved', 'Aprobado' );
insert into I18NSTRING( id, version, locale, key, text ) values( 231, 0, 'ca', 'job_candidature_state.approved', 'Aprovat' );

insert into I18NSTRING( id, version, locale, key, text ) values( 250, 0, 'es', 'event_type.other', 'Otra' );
insert into I18NSTRING( id, version, locale, key, text ) values( 251, 0, 'ca', 'event_type.other', 'Altre' );
insert into I18NSTRING( id, version, locale, key, text ) values( 255, 0, 'es', 'event_type.email', 'Correo electrónico' );
insert into I18NSTRING( id, version, locale, key, text ) values( 256, 0, 'ca', 'event_type.email', 'Correu electrónic' );
insert into I18NSTRING( id, version, locale, key, text ) values( 260, 0, 'es', 'event_type.phone_call', 'Llamada telefónica' );
insert into I18NSTRING( id, version, locale, key, text ) values( 261, 0, 'ca', 'event_type.phone_call', 'Trucada telefónica' );
insert into I18NSTRING( id, version, locale, key, text ) values( 265, 0, 'es', 'event_type.video_call', 'Videoconferencia' );
insert into I18NSTRING( id, version, locale, key, text ) values( 266, 0, 'ca', 'event_type.video_call', 'Videoconferencia' );
insert into I18NSTRING( id, version, locale, key, text ) values( 270, 0, 'es', 'event_type.message', 'Mensaje' );
insert into I18NSTRING( id, version, locale, key, text ) values( 271, 0, 'ca', 'event_type.message', 'Missatge' );
insert into I18NSTRING( id, version, locale, key, text ) values( 275, 0, 'es', 'event_type.meeting', 'Reunion' );
insert into I18NSTRING( id, version, locale, key, text ) values( 276, 0, 'ca', 'event_type.metting', 'Reunió' );

insert into I18NSTRING( id, version, locale, key, text ) values( 300, 0, 'ca', 'infojobs', 'Infojobs' );
insert into I18NSTRING( id, version, locale, key, text ) values( 301, 0, 'es', 'infojobs', 'Infojobs' );
insert into I18NSTRING( id, version, locale, key, text ) values( 305, 0, 'ca', 'linkedin', 'LinkedIn' );
insert into I18NSTRING( id, version, locale, key, text ) values( 306, 0, 'es', 'linkedin', 'LinkedIn' );
insert into I18NSTRING( id, version, locale, key, text ) values( 310, 0, 'ca', 'reference', 'Referència' );
insert into I18NSTRING( id, version, locale, key, text ) values( 311, 0, 'es', 'reference', 'Referencia' );

insert into CREDENTIAL( id, version, hashPassword, salt, created ) values( 600, 0, '16AFB50A06A958ACEC2EDA9D70283139BC7B7372E21CC83F619CCF169E6E7956', 'FFFFFF', (TO_DATE('17/12/2015', 'DD/MM/YYYY')));
insert into CREDENTIAL( id, version, hashPassword, salt, created ) values( 601, 0, '16AFB50A06A958ACEC2EDA9D70283139BC7B7372E21CC83F619CCF169E6E7956', 'FFFFFF', (TO_DATE('17/12/2015', 'DD/MM/YYYY')));
insert into CREDENTIAL( id, version, hashPassword, salt, created ) values( 602, 0, '16AFB50A06A958ACEC2EDA9D70283139BC7B7372E21CC83F619CCF169E6E7956', 'FFFFFF', (TO_DATE('17/12/2015', 'DD/MM/YYYY')));
insert into CREDENTIAL( id, version, hashPassword, salt, created ) values( 603, 0, '16AFB50A06A958ACEC2EDA9D70283139BC7B7372E21CC83F619CCF169E6E7956', 'FFFFFF', (TO_DATE('17/12/2015', 'DD/MM/YYYY')));
insert into CREDENTIAL( id, version, hashPassword, salt, created ) values( 604, 0, '16AFB50A06A958ACEC2EDA9D70283139BC7B7372E21CC83F619CCF169E6E7956', 'FFFFFF', (TO_DATE('17/12/2015', 'DD/MM/YYYY')));
insert into CREDENTIAL( id, version, hashPassword, salt, created ) values( 605, 0, '16AFB50A06A958ACEC2EDA9D70283139BC7B7372E21CC83F619CCF169E6E7956', 'FFFFFF', (TO_DATE('17/12/2015', 'DD/MM/YYYY')));
insert into CREDENTIAL( id, version, hashPassword, salt, created ) values( 606, 0, '16AFB50A06A958ACEC2EDA9D70283139BC7B7372E21CC83F619CCF169E6E7956', 'FFFFFF', (TO_DATE('17/12/2015', 'DD/MM/YYYY')));
insert into CREDENTIAL( id, version, hashPassword, salt, created ) values( 607, 0, '16AFB50A06A958ACEC2EDA9D70283139BC7B7372E21CC83F619CCF169E6E7956', 'FFFFFF', (TO_DATE('17/12/2015', 'DD/MM/YYYY')));

insert into USER( id, version, name, surname, phoneNumber, email, language, rowsPerPage, role, credentialId, managerId ) values( 500, 0, 'Abel', 'Ferrer Jiménez','685555276', 'abel.ferrer.jimenez@gmail.com', 'es', 10, 'ADMIN', 600, null);
insert into USER( id, version, name, surname, phoneNumber, email, language, rowsPerPage, role, credentialId, managerId ) values( 501, 0, 'Isabel', 'Vallejo Medina','685555276', 'isabel.vallejo.medina@gmail.com', 'es', 10, 'ADMIN', 601, null);
insert into USER( id, version, name, surname, phoneNumber, email, language, rowsPerPage, role, credentialId, managerId ) values( 502, 0, 'Juan', 'Fernández','685555276', 'jfernandez@gmail.com', 'es', 10, 'USER', 602, 500);
insert into USER( id, version, name, surname, phoneNumber, email, language, rowsPerPage, role, credentialId, managerId ) values( 503, 0, 'Pedro', 'Smith','685555276', 'psmith@gmail.com', 'es', 10, 'USER', 603, 500);
insert into USER( id, version, name, surname, phoneNumber, email, language, rowsPerPage, role, credentialId, managerId ) values( 504, 0, 'Luis', 'Carrasco','685555276', 'lcarrasco@gmail.com', 'es', 10, 'USER', 604, 500);
insert into USER( id, version, name, surname, phoneNumber, email, language, rowsPerPage, role, credentialId, managerId ) values( 505, 0, 'Ana', 'Casoras','685555276', 'acasoras@gmail.com', 'es', 10, 'USER', 605, 500);
insert into USER( id, version, name, surname, phoneNumber, email, language, rowsPerPage, role, credentialId, managerId ) values( 506, 0, 'Luis Ángel', 'Nuestra Señora del manto','685555276', 'lantrasenora@gmail.com', 'es', 10, 'USER', 606, 500);
insert into USER( id, version, name, surname, phoneNumber, email, language, rowsPerPage, role, credentialId, managerId ) values( 507, 0, 'Anabel', 'Villafafila','685555276', 'anabel@gmail.com', 'es', 10, 'USER', 607, 500);

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

insert into CANDIDATE( id, version, name, surname, phoneNumber, email, city, country, door, number, state, storey, street, zipCode, jobProfile, ownerId) values ( 510, 0, 'Abel', 'Ferrer Jiménez','685555276', 'aferrer@gmail.com', 'Barcelona', 'España','1','85', 'Barcelona', 'Principal', 'Bailén', '08809', 'Arquitecto', 500);
insert into CANDIDATE( id, version, name, surname, phoneNumber, email, city, country, door, number, state, storey, street, zipCode, jobProfile, ownerId) values ( 511, 0, 'Luís', 'González Sánchez','685555277', 'lgonzalez@gmail.com', 'Madrid', 'España','2','85', 'Madrid', '2', 'Miraflores', '80809', 'Programador JEE', 500);
insert into CANDIDATE( id, version, name, surname, phoneNumber, email, city, country, door, number, state, storey, street, zipCode, jobProfile, ownerId) values ( 512, 0, 'Juan', 'Ayuso Pérez','685555278', 'ayuson32@gmail.com', 'Valencia', 'España','2','85', 'Valencia', '1', 'Horchata', '02809', 'Analista Java', 500);
insert into CANDIDATE( id, version, name, surname, phoneNumber, email, city, country, door, number, state, storey, street, zipCode, jobProfile, ownerId) values ( 513, 0, 'Pedro', 'Gallardo Navarro','685555279', 'pedrito@gmail.com', 'Masnou', 'España','3','85', 'Barcelona', '3', 'Plaça Catalunya', '08328', 'Programador', 500);
insert into CANDIDATE( id, version, name, surname, phoneNumber, email, city, country, door, number, state, storey, street, zipCode, jobProfile, ownerId) values ( 514, 0, 'Gonzalo', 'León Cuellar','685555280', 'gonzo73@gmail.com', 'Lugo', 'España','1','85', 'Lugo', '3', 'España', '02809', 'Programador', 500);
insert into CANDIDATE( id, version, name, surname, phoneNumber, email, city, country, door, number, state, storey, street, zipCode, jobProfile, ownerId) values ( 515, 0, 'Antonio', 'García Collado','685555281', 'agc2@gmail.com', 'Pontevedra', 'España','2','85', 'Pontevedra', 'Principal', 'Luguense', '01809', 'Programador Junior', 501);

insert into CURRICULUM( id, version, candidateId) values (801, 0, 510);

insert into EDUCATION( id, version, curriculumId, degree, description,  startYear, school, stillStudying, endYear) values( 802, 0, 801, 'Licenciado en Matemáticas', 'Licenciado en Matemáticas por la universidad de Barcelona', 2000, 'Universitat de Barcelona', false, 2010);
insert into EDUCATION( id, version, curriculumId, degree, description,  startYear, school, stillStudying, endYear) values( 803, 0, 801, 'Bachillerato', null,  1988, 'IES Thalassa', false, 1992);

insert into LANGUAGE( id, version, curriculumId, name, level ) values( 804, 0, 801, 'Castellano', 'NATIVE' );
insert into LANGUAGE( id, version, curriculumId, name, level ) values( 805, 0, 801, 'Català', 'NATIVE' );
insert into LANGUAGE( id, version, curriculumId, name, level ) values( 806, 0, 801, 'English', 'HIGH' );

insert into SKILL( id, version, curriculumId, name ) values( 807, 0, 801, 'Java');
insert into SKILL( id, version, curriculumId, name ) values( 808, 0, 801, 'Spring');
insert into SKILL( id, version, curriculumId, name ) values( 809, 0, 801, 'Hibernate');
insert into SKILL( id, version, curriculumId, name ) values( 810, 0, 801, 'JSF');
insert into SKILL( id, version, curriculumId, name ) values( 811, 0, 801, 'JEE');

insert into JOBEXPERIENCE( id, version, curriculumId, companyName, description, startDate, jobPosition, stillWorking, endDate) values (812, 0, 801, 'Alten', 'Desarrollo de la herramienta para Gas Natural Fenosa con el que se dará cobertura a todas las actividades asociadas al proceso de expansión de la red y provisión de servicio en todos los ámbitos geográficos donde el grupo Gas Natural Fenosa (GNF) desarrolla sus negocios de distribución de gas natural y electricidad. Puesto de Arquitecto JEE desarrollando las siguientes tareas:', CAST('aced00057372000d6a6176612e74696d652e536572955d84ba1b2248b20c0000787077060c000007da0378' AS VARBINARY(255)), 'Arquitecto JAVA', true, null );

insert into CLIENT( id, version, name, cif, ownerId, phoneNumber, deleted) values (1000, 0, 'F.C. Barcelona', 'A0000000', 500, '935551414', false);

insert into EXPORT( id, version, exportName) values (1100, 0, 'USERS');
insert into EXPORT( id, version, exportName) values (1101, 0, 'CANDIDATES');

insert into EXPORTFIELD( id, version, exportId, fieldName, sortOrder, literalId, disabled, acquirerClass) values (1200, 0, 1100, 'NAME',      1, 'user.name',     false, 'ged.ejb.export.acquirer.impl.UserExcelAcquirer$SurnameAcquirer');
insert into EXPORTFIELD( id, version, exportId, fieldName, sortOrder, literalId, disabled, acquirerClass) values (1201, 0, 1100, 'SURNAME',   2, 'user.surname',  false, 'ged.ejb.export.acquirer.impl.UserExcelAcquirer$SurnameAcquirer');
insert into EXPORTFIELD( id, version, exportId, fieldName, sortOrder, literalId, disabled, acquirerClass) values (1202, 0, 1100, 'MAIL',      3, 'user.email',    false, 'ged.ejb.export.acquirer.impl.UserExcelAcquirer$EmailAcquirer');
insert into EXPORTFIELD( id, version, exportId, fieldName, sortOrder, literalId, disabled, acquirerClass) values (1203, 0, 1100, 'DISABLED', -1, 'user.disabled', true , 'ged.ejb.export.acquirer.impl.UserExcelAcquirer$DummyAcquirer');

insert into EXPORTDEFINITION (id, version, exportId, exportFieldId, sortOrder) values (1300, 0, 1100, 1201, 1);
insert into EXPORTDEFINITION (id, version, exportId, exportFieldId, sortOrder) values (1301, 0, 1100, 1202, 2);

ALTER SEQUENCE HIBERNATE_SEQUENCE RESTART with 2000;