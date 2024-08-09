insert into JOB_OFFER_PROCESS( ID, VERSION, NAME ) values ( 1, 0, 'Proceso simple con entrevista');

ALTER SEQUENCE JOB_OFFER_PROCESS_SEQ RESTART WITH 2;

insert into JOB_CANDIDATURE_STATE( ID, VERSION, NAME, FIRST, APPROVED, DECLINED, COLOR, BACKGROUND_COLOR, JOB_OFFER_PROCESS_ID ) values ( 1, 0, 'No contactado', true, false, false, '#FFFFFF', '#0288D1', 1);
insert into JOB_CANDIDATURE_STATE( ID, VERSION, NAME, FIRST, APPROVED, DECLINED, COLOR, BACKGROUND_COLOR, JOB_OFFER_PROCESS_ID ) values ( 2, 0, 'Contactado', false, false, false, '#FFFFFF', '#0288D1', 1);
insert into JOB_CANDIDATURE_STATE( ID, VERSION, NAME, FIRST, APPROVED, DECLINED, COLOR, BACKGROUND_COLOR, JOB_OFFER_PROCESS_ID ) values ( 3, 0, 'Entrevistado', false, false, false, '#FFFFFF', '#0288D1', 1);
insert into JOB_CANDIDATURE_STATE( ID, VERSION, NAME, FIRST, APPROVED, DECLINED, COLOR, BACKGROUND_COLOR, JOB_OFFER_PROCESS_ID ) values ( 4, 0, 'Oferta', false, false, false, '#FFFFFF', '#0288D1', 1);
insert into JOB_CANDIDATURE_STATE( ID, VERSION, NAME, FIRST, APPROVED, DECLINED, COLOR, BACKGROUND_COLOR, JOB_OFFER_PROCESS_ID ) values ( 5, 0, 'Contratado', false, true, false, '#FFFFFF', '#0288D1', 1);
insert into JOB_CANDIDATURE_STATE( ID, VERSION, NAME, FIRST, APPROVED, DECLINED, COLOR, BACKGROUND_COLOR, JOB_OFFER_PROCESS_ID ) values ( 6, 0, 'Rechazado', false, false, true, '#FFFFFF', '#0288D1', 1);

ALTER SEQUENCE JOB_CANDIDATURE_STATE_SEQ RESTART WITH 7;

insert into TRANSITION( ID, VERSION, EVENT, ORIGIN_STATE_ID, DESTINATION_STATE_ID ) values ( 1, 0, 'Contactar', 1, 2 );
insert into TRANSITION( ID, VERSION, EVENT, ORIGIN_STATE_ID, DESTINATION_STATE_ID ) values ( 3, 0, 'Seleccionar para entrevista', 2, 3 );
insert into TRANSITION( ID, VERSION, EVENT, ORIGIN_STATE_ID, DESTINATION_STATE_ID ) values ( 4, 0, 'Hacer oferta', 3, 4 );
insert into TRANSITION( ID, VERSION, EVENT, ORIGIN_STATE_ID, DESTINATION_STATE_ID ) values ( 5, 0, 'Aceptar oferta', 4, 5 );
insert into TRANSITION( ID, VERSION, EVENT, ORIGIN_STATE_ID, DESTINATION_STATE_ID ) values ( 6, 0, 'Rechazar', 1, 6 );
insert into TRANSITION( ID, VERSION, EVENT, ORIGIN_STATE_ID, DESTINATION_STATE_ID ) values ( 7, 0, 'Rechazar', 2, 6 );
insert into TRANSITION( ID, VERSION, EVENT, ORIGIN_STATE_ID, DESTINATION_STATE_ID ) values ( 8, 0, 'Rechazar', 3, 6 );
insert into TRANSITION( ID, VERSION, EVENT, ORIGIN_STATE_ID, DESTINATION_STATE_ID ) values ( 9, 0, 'Rechazar', 4, 6 );

ALTER SEQUENCE TRANSITION_SEQ RESTART WITH 10;

insert into ORIGIN( ID, VERSION, CODE ) values ( 1, 0, 'origin.infojobs');
insert into ORIGIN( ID, VERSION, CODE ) values ( 2, 0, 'origin.linkedin');
insert into ORIGIN( ID, VERSION, CODE ) values ( 3, 0, 'origin.reference');
insert into ORIGIN( ID, VERSION, CODE ) values ( 4, 0, 'origin.other');
ALTER SEQUENCE JOB_CANDIDATURE_STATE_SEQ RESTART WITH 5;

insert into PERSON( ID, VERSION, NAME, SURNAME, ROLE, PHONE_NUMBER, EMAIL, LANGUAGE, ROWS_PER_PAGE, MANAGER_ID ) values( 1, 0, 'Abel', 'Ferrer Jiménez', 'ADMIN', '685555276', 'abel.ferrer.jimenez@gmail.com', 'es', 10, null);
insert into PERSON( ID, VERSION, NAME, SURNAME, ROLE, PHONE_NUMBER, EMAIL, LANGUAGE, ROWS_PER_PAGE, MANAGER_ID ) values( 2, 0, 'Isabel', 'Vallejo Medina', 'ADMIN','685555276', 'isabel.vallejo.medina@gmail.com', 'es', 10, null);
insert into PERSON( ID, VERSION, NAME, SURNAME, ROLE, PHONE_NUMBER, EMAIL, LANGUAGE, ROWS_PER_PAGE, MANAGER_ID ) values( 3, 0, 'Juan', 'Fernández', 'USER','685555276', 'jfernandez@gmail.com', 'es', 10, 1);
insert into PERSON( ID, VERSION, NAME, SURNAME, ROLE, PHONE_NUMBER, EMAIL, LANGUAGE, ROWS_PER_PAGE, MANAGER_ID ) values( 4, 0, 'Pedro', 'Smith', 'USER','685555276', 'psmith@gmail.com', 'es', 10, 1);
insert into PERSON( ID, VERSION, NAME, SURNAME, ROLE, PHONE_NUMBER, EMAIL, LANGUAGE, ROWS_PER_PAGE, MANAGER_ID ) values( 5, 0, 'Luis', 'Carrasco', 'USER','685555276', 'lcarrasco@gmail.com', 'es', 10, 1);
insert into PERSON( ID, VERSION, NAME, SURNAME, ROLE, PHONE_NUMBER, EMAIL, LANGUAGE, ROWS_PER_PAGE, MANAGER_ID ) values( 6, 0, 'Ana', 'Casoras', 'USER','685555276', 'acasoras@gmail.com', 'es', 10, 1);
insert into PERSON( ID, VERSION, NAME, SURNAME, ROLE, PHONE_NUMBER, EMAIL, LANGUAGE, ROWS_PER_PAGE, MANAGER_ID ) values( 7, 0, 'Luis Ángel', 'Nuestra Señora del manto', 'USER','685555276', 'lantrasenora@gmail.com', 'es', 10, 1);
insert into PERSON( ID, VERSION, NAME, SURNAME, ROLE, PHONE_NUMBER, EMAIL, LANGUAGE, ROWS_PER_PAGE, MANAGER_ID ) values( 8, 0, 'Anabel', 'Villafafila', 'USER','685555276', 'anabel@gmail.com', 'es', 10, 1);
ALTER SEQUENCE PERSON_SEQ RESTART WITH 9;

insert into PERSON_CLOSURE( ID, VERSION, ANTECESSOR_ID, DESCENDANT_ID, PATH_LENGTH ) values( 1, 0, 1, 1, 0 );
insert into PERSON_CLOSURE( ID, VERSION, ANTECESSOR_ID, DESCENDANT_ID, PATH_LENGTH ) values( 2, 0, 2, 2, 0 );
insert into PERSON_CLOSURE( ID, VERSION, ANTECESSOR_ID, DESCENDANT_ID, PATH_LENGTH ) values( 3, 0, 3, 3, 0 );
insert into PERSON_CLOSURE( ID, VERSION, ANTECESSOR_ID, DESCENDANT_ID, PATH_LENGTH ) values( 4, 0, 4, 4, 0 );
insert into PERSON_CLOSURE( ID, VERSION, ANTECESSOR_ID, DESCENDANT_ID, PATH_LENGTH ) values( 5, 0, 5, 5, 0 );
insert into PERSON_CLOSURE( ID, VERSION, ANTECESSOR_ID, DESCENDANT_ID, PATH_LENGTH ) values( 6, 0, 6, 6, 0 );
insert into PERSON_CLOSURE( ID, VERSION, ANTECESSOR_ID, DESCENDANT_ID, PATH_LENGTH ) values( 7, 0, 7, 7, 0 );
insert into PERSON_CLOSURE( ID, VERSION, ANTECESSOR_ID, DESCENDANT_ID, PATH_LENGTH ) values( 8, 0, 8, 8, 0 );
insert into PERSON_CLOSURE( ID, VERSION, ANTECESSOR_ID, DESCENDANT_ID, PATH_LENGTH ) values( 9, 0, 1, 3, 1 );
insert into PERSON_CLOSURE( ID, VERSION, ANTECESSOR_ID, DESCENDANT_ID, PATH_LENGTH ) values( 10, 0, 1, 4, 1 );
insert into PERSON_CLOSURE( ID, VERSION, ANTECESSOR_ID, DESCENDANT_ID, PATH_LENGTH ) values( 11, 0, 1, 5, 1 );
insert into PERSON_CLOSURE( ID, VERSION, ANTECESSOR_ID, DESCENDANT_ID, PATH_LENGTH ) values( 12, 0, 1, 6, 1 );
insert into PERSON_CLOSURE( ID, VERSION, ANTECESSOR_ID, DESCENDANT_ID, PATH_LENGTH ) values( 13, 0, 1, 7, 1 );
insert into PERSON_CLOSURE( ID, VERSION, ANTECESSOR_ID, DESCENDANT_ID, PATH_LENGTH ) values( 14, 0, 1, 8, 1 );
ALTER SEQUENCE PERSON_CLOSURE_SEQ RESTART WITH 9;

insert into CANDIDATE( ID, VERSION, NAME, SURNAME, PHONE_NUMBER, EMAIL, CITY, COUNTRY, DOOR, NUMBER, REGION, STOREY, STREET, ZIP_CODE, JOB_PROFILE, OWNER_ID) values ( 1, 0, 'Abel', 'Ferrer Jiménez','685555276', 'aferrer@gmail.com', 'Barcelona', 'España','1','85', 'Barcelona', 'Principal', 'Bailén', '08809', 'Arquitecto', 1);
insert into CANDIDATE( ID, VERSION, NAME, SURNAME, PHONE_NUMBER, EMAIL, CITY, COUNTRY, DOOR, NUMBER, REGION, STOREY, STREET, ZIP_CODE, JOB_PROFILE, OWNER_ID) values ( 2, 0, 'Luís', 'González Sánchez','685555277', 'lgonzalez@gmail.com', 'MadrID', 'España','2','85', 'MadrID', '2', 'Miraflores', '80809', 'Programador JEE', 1);
insert into CANDIDATE( ID, VERSION, NAME, SURNAME, PHONE_NUMBER, EMAIL, CITY, COUNTRY, DOOR, NUMBER, REGION, STOREY, STREET, ZIP_CODE, JOB_PROFILE, OWNER_ID) values ( 3, 0, 'Juan', 'Ayuso Pérez','685555278', 'ayuson32@gmail.com', 'Valencia', 'España','2','85', 'Valencia', '1', 'Horchata', '02809', 'Analista Java', 1);
insert into CANDIDATE( ID, VERSION, NAME, SURNAME, PHONE_NUMBER, EMAIL, CITY, COUNTRY, DOOR, NUMBER, REGION, STOREY, STREET, ZIP_CODE, JOB_PROFILE, OWNER_ID) values ( 4, 0, 'Pedro', 'Gallardo Navarro','685555279', 'pedrito@gmail.com', 'Masnou', 'España','3','85', 'Barcelona', '3', 'Plaça Catalunya', '08328', 'Programador', 1);
insert into CANDIDATE( ID, VERSION, NAME, SURNAME, PHONE_NUMBER, EMAIL, CITY, COUNTRY, DOOR, NUMBER, REGION, STOREY, STREET, ZIP_CODE, JOB_PROFILE, OWNER_ID) values ( 5, 0, 'Gonzalo', 'León Cuellar','685555280', 'gonzo73@gmail.com', 'Lugo', 'España','1','85', 'Lugo', '3', 'España', '02809', 'Programador', 1);
insert into CANDIDATE( ID, VERSION, NAME, SURNAME, PHONE_NUMBER, EMAIL, CITY, COUNTRY, DOOR, NUMBER, REGION, STOREY, STREET, ZIP_CODE, JOB_PROFILE, OWNER_ID) values ( 6, 0, 'Antonio', 'García Collado','685555281', 'agc2@gmail.com', 'Pontevedra', 'España','2','85', 'Pontevedra', 'Principal', 'Luguense', '01809', 'Programador Junior', 2);
ALTER SEQUENCE CANDIDATE_SEQ RESTART WITH 7;

insert into CURRICULUM( CANDIDATE_ID, VERSION) values (1, 0);

insert into EDUCATION( ID, VERSION, CURRICULUM_ID, DEGREE, DESCRIPTION,  START_YEAR, SCHOOL, STILL_STUDYING, END_YEAR) values( 1, 0, 1, 'Licenciado en Matemáticas', 'Licenciado en Matemáticas por la universIDad de Barcelona', 2000, 'Universitat de Barcelona', false, 2010);
insert into EDUCATION( ID, VERSION, CURRICULUM_ID, DEGREE, DESCRIPTION,  START_YEAR, SCHOOL, STILL_STUDYING, END_YEAR) values( 2, 0, 1, 'Bachillerato', null,  1988, 'IES Thalassa', false, 1992);
ALTER SEQUENCE EDUCATION_SEQ RESTART WITH 3;

insert into LANGUAGE( ID, VERSION, CURRICULUM_ID, NAME, LEVEL ) values( 1, 0, 1, 'Castellano', 'NATIVE' );
insert into LANGUAGE( ID, VERSION, CURRICULUM_ID, NAME, LEVEL ) values( 2, 0, 1, 'Català', 'NATIVE' );
insert into LANGUAGE( ID, VERSION, CURRICULUM_ID, NAME, LEVEL ) values( 3, 0, 1, 'English', 'HIGH' );
ALTER SEQUENCE LANGUAGE_SEQ RESTART WITH 4;

insert into SKILL( ID, VERSION, CURRICULUM_ID, NAME ) values( 1, 0, 1, 'Java');
insert into SKILL( ID, VERSION, CURRICULUM_ID, NAME ) values( 2, 0, 1, 'Spring');
insert into SKILL( ID, VERSION, CURRICULUM_ID, NAME ) values( 3, 0, 1, 'Hibernate');
insert into SKILL( ID, VERSION, CURRICULUM_ID, NAME ) values( 4, 0, 1, 'JSF');
insert into SKILL( ID, VERSION, CURRICULUM_ID, NAME ) values( 5, 0, 1, 'JEE');
ALTER SEQUENCE SKILL_SEQ RESTART WITH 6;

insert into JOB_EXPERIENCE( ID, VERSION, CURRICULUM_ID, COMPANY_NAME, DESCRIPTION, START_YEAR, START_MONTH, JOB_POSITION, STILL_WORKING, END_YEAR, END_MONTH) values (1, 0, 1, 'Alten', 'Desarrollo de la herramienta para Gas Natural Fenosa con el que se dará cobertura a todas las activIDades asociadas al proceso de expansión de la red y provisión de servicio en todos los ámbitos geográficos donde el grupo Gas Natural Fenosa (GNF) desarrolla sus negocios de distribución de gas natural y electricIDad. Puesto de Arquitecto JEE desarrollando las siguientes tareas:', 2002, 12, 'Arquitecto JAVA', true, null, null );
ALTER SEQUENCE JOB_EXPERIENCE_SEQ RESTART WITH 2;

insert into CLIENT( ID, VERSION, NAME, CIF, OWNER_ID, PHONE_NUMBER, CITY, REGION, DELETED) values (1, 0, 'Nivel 36', 'A0000000', 1, '935551414', 'Barcelona', 'Barcelona', false);
ALTER SEQUENCE CLIENT_SEQ RESTART WITH 2;

insert into CONTACT( ID, VERSION, NAME, SURNAME, EMAIL, PHONE_NUMBER,CLIENT_ID) values (1, 0, 'Luis', 'Carrasco', 'lcarrasco@nivel36.es', '935551414', 1);
ALTER SEQUENCE CONTACT_SEQ RESTART WITH 2;

insert into DOCUMENT_TEMPLATE (ID, VERSION, LANGUAGE, NAME, TITLE, TEXT) values (1, 0, 'es', 'gdpr', 'Regulación general de protección de datos', '<p>Barcelona a %date%</p><br/><p>Inserta aquí el documento de GDPR de la empresa en castellano</p><p> </p><p> </p><p> </p><p align="center"><b>Firma</b></p>');
insert into DOCUMENT_TEMPLATE (ID, VERSION, LANGUAGE, NAME, TITLE, TEXT) values (2, 0, 'ca', 'gdpr', 'Regulació general de protecció de dades', '<p>Barcelona a %date%</p><br/><p>Insereix aquí el documento de GDPR de la empresa en català</p><p> </p><p> </p><p> </p><p align="center"><b>Signatura</b></p>');
ALTER SEQUENCE DOCUMENT_TEMPLATE_SEQ RESTART WITH 3;

insert into CURRICULUM_TEMPLATE (ID, VERSION, TITLE, CSS, DESCRIPTION, SCREENSHOOT) values (1, 0, 'cv_template.basic', 'body{font-family:sans-serif}h1{font-size:1.6em;margin-bottom:0}.data{border-top:1px solid grey;padding:1em 0 1em 0}.contact .label{font-weight:700;width:180px;display:inline-block;margin-bottom:.3em}.education,.jobExperience{padding-bottom:2em}.education .from-to-date,.jobExperience .from-to-date{float:left;width:180px;margin-top:-1.5em}.education .degree,.jobExperience .jobPosition{margin-left:180px;font-weight:700;font-size:1.2em;padding-bottom:.2em}.education .school,.jobExperience .companyName{margin-left:180px;font-size:1.1em;padding-bottom:.2em}.education .description,.jobExperience .description{margin-left:180px}.language .name{width:180px;margin-bottom:.3em;display:inline-block}.skill{display:inline-block;margin:0 1em .2em 0}.user-image{float:right; margin-top: -3em; border-left:20px solid white; z-index:10}.user-image .picture{object-fit: cover;image-orientation: from-image;width: 98px;border:1px solid black;}', 'cv_template.basic.description', null);
insert into CURRICULUM_TEMPLATE (ID, VERSION, TITLE, CSS, DESCRIPTION, SCREENSHOOT) values (2, 0, 'cv_template.basic_anon', 'body{font-family:sans-serif}h1{font-size:1.6em;margin-bottom:0}.data{border-top:1px solid grey;padding:1em 0 1em 0}.contact .label{font-weight:700;width:180px;display:inline-block;margin-bottom:.3em}.education,.jobExperience{padding-bottom:2em}.education .from-to-date,.jobExperience .from-to-date{float:left;width:180px;margin-top:-1.5em}.education .degree,.jobExperience .jobPosition{margin-left:180px;font-weight:700;font-size:1.2em;padding-bottom:.2em}.education .school,.jobExperience .companyName{margin-left:180px;font-size:1.1em;padding-bottom:.2em}.education .description,.jobExperience .description{margin-left:180px}.language .name{width:180px;margin-bottom:.3em;display:inline-block}.skill{display:inline-block;margin:0 1em .2em 0}.user-image{float:right; margin-top: -3em; border-left:20px solid white; z-index:10}.user-image .picture{object-fit: cover;image-orientation: from-image;width: 98px;border:1px solid black;}.contact {display: none;}' , 'cv_template.basic_anon.description', null);
insert into CURRICULUM_TEMPLATE (ID, VERSION, TITLE, CSS, DESCRIPTION, SCREENSHOOT) values (3, 0, 'cv_template.corp', 'body{font-family:sans-serif;margin:0}h1{font-size:1.6em;margin-bottom:2em}.name-profile{height:80px;background-color:#131313;padding:3em}.name-profile .name .label,.name-profile .profile .label{display:none}.name-profile .name{font-size:2em;color:#fff}.name-profile .profile{font-size:1.5em;color:#ff2}.contact{width:210px;background-color:#cfc7c7;float:left;height:100%;padding:2em}.contact h1{display:none}.contact .label{display:none}.contact .name{font-size:2em;font-weight:700}.educations,.jobExperiences,.languages,.skills{margin:20px 50px 0 300px}.education,.jobExperience,.langauge,.skill{padding-bottom:2em}.education .from-to-date,.jobExperience .from-to-date{float:left;width:180px;margin-top:-1.5em}.education .degree,.jobExperience .jobPosition{margin-left:180px;font-weight:700;font-size:1.2em;padding-bottom:.2em}.education .school,.jobExperience .companyName{margin-left:180px;font-size:1.1em;padding-bottom:.2em}.education .description,.jobExperience .description{margin-left:180px}.language .name{width:180px;margin-bottom:.3em;display:inline-block}.skill{display:inline-block;margin:0 1em .2em 0}.user-image{float:right;margin-top:-3em;border-left:20px solid #fff;z-index:10}.user-image .picture{object-fit:cover;image-orientation:from-image;width:98px;border:1px solid #000}', 'cv_template.corp.description', null);
insert into CURRICULUM_TEMPLATE (ID, VERSION, TITLE, CSS, DESCRIPTION, SCREENSHOOT) values (4, 0, 'cv_template.corp_anon', '@page{margin:0pt;} body{font-family:sans-serif}h1{font-size:1.6em;margin-bottom:0}.data{border-top:1px solid grey;padding:1em 0 1em 0}.contact .label{font-weight:700;width:180px;display:inline-block;margin-bottom:.3em}.education,.jobExperience{padding-bottom:2em}.education .from-to-date,.jobExperience .from-to-date{float:left;width:180px;margin-top:-1.5em}.education .degree,.jobExperience .jobPosition{margin-left:180px;font-weight:700;font-size:1.2em;padding-bottom:.2em}.education .school,.jobExperience .companyName{margin-left:180px;font-size:1.1em;padding-bottom:.2em}.education .description,.jobExperience .description{margin-left:180px}.language .name{width:180px;margin-bottom:.3em;display:inline-block}.skill{display:inline-block;margin:0 1em .2em 0}.user-image{float:right; margin-top: -3em; border-left:20px solid white; z-index:10}.user-image .picture{object-fit: cover;image-orientation: from-image;width: 98px;border:1px solid black;}.contact {display: none;}' , 'cv_template.corp_anon.description', null);
ALTER SEQUENCE CURRICULUM_TEMPLATE_SEQ RESTART WITH 5;

insert into JOB_OFFER(ID, VERSION, STATE, CITY, REGION, CLIENT_ID, CLOSE_DATE, DESCRIPTION, MAX_SALARY, MIN_SALARY, OPEN_DATE, OWNER_ID, PLACES, PUBLISHED, TITLE, JOB_OFFER_PROCESS_ID) values(1, 0, 'OPENED', 'Barcelona', 'Barcelona', 1, (TO_DATE('17/12/2025', 'DD/MM/YYYY')), '<p>Se necesita un arquitecto de software</p>', 85000, 50000, (TO_DATE('17/12/2021', 'DD/MM/YYYY')), 1, 1, false, 'Arquitecto de software', 1); 
insert into JOB_OFFER(ID, VERSION, STATE, CITY, REGION, CLIENT_ID, CLOSE_DATE, DESCRIPTION, MAX_SALARY, MIN_SALARY, OPEN_DATE, OWNER_ID, PLACES, PUBLISHED, TITLE, JOB_OFFER_PROCESS_ID) values(2, 0, 'CREATED', 'Barcelona', 'Barcelona', 1, (TO_DATE('17/12/2025', 'DD/MM/YYYY')), '<p>Se necesita un arquitecto java</p>', 85000, 50000, (TO_DATE('17/02/2022', 'DD/MM/YYYY')), 1, 1, false, 'Arquitecto java', 1);
ALTER SEQUENCE JOB_OFFER_SEQ RESTART WITH 3;

insert into JOB_OFFER_EVENT(ID, VERSION, DATE, JOB_OFFER_ID, STATE, TYPE, USER_ID) values (1, 1, (TO_TIMESTAMP('17/12/2021 20:12:24', 'DD/MM/YYYY HH24:MI:SS')), 2, 'CREATED', 'MANUAL_EVENT', 1);
insert into JOB_OFFER_EVENT(ID, VERSION, DATE, JOB_OFFER_ID, STATE, TYPE, USER_ID) values (2, 1, (TO_TIMESTAMP('17/12/2021 20:22:55', 'DD/MM/YYYY HH24:MI:SS')), 2, 'OPENED', 'MANUAL_EVENT', 1);
insert into JOB_OFFER_EVENT(ID, VERSION, DATE, JOB_OFFER_ID, STATE, TYPE, USER_ID) values (3, 1, (TO_TIMESTAMP('17/12/2021 20:05:12', 'DD/MM/YYYY HH24:MI:SS')), 1, 'CREATED', 'MANUAL_EVENT', 1);
ALTER SEQUENCE JOB_OFFER_EVENT_SEQ RESTART WITH 4;

insert into JOB_CANDIDATURE(ID, VERSION, CANDIDATE_ID, JOB_OFFER_ID, JOB_CANDIDATURE_STATE_ID) values (1, 1, 1, 1, 2);
ALTER SEQUENCE JOB_CANDIDATURE_SEQ RESTART WITH 2;

insert into JOB_CANDIDATURE_EVENT(ID, VERSION, DATE, JOB_CANDIDATURE_ID, NOTES, JOB_CANDIDATURE_STATE_ID, TYPE, USER_ID) values (1, 1, (TO_TIMESTAMP('17/12/2021 20:12:54', 'DD/MM/YYYY  HH24:MI:SS')), 1, 'El candidato parece realmente interesado en el trabajo', 2, 'PHONE_CALL', 1);
ALTER SEQUENCE JOB_CANDIDATURE_EVENT_SEQ RESTART WITH 3;

insert into ACTION(ID, VERSION, DATE, ENTITY_ID, ENTITY_NAME, ENTITY_TITLE, TYPE, USER_ID) values (1, 1, (TO_TIMESTAMP('17/12/2023 20:02:02', 'DD/MM/YYYY HH24:MI:SS')), 1, 'CLIENT', 'Nivel 36', 'CREATE', 1)
insert into ACTION(ID, VERSION, DATE, ENTITY_ID, ENTITY_NAME, ENTITY_TITLE, TYPE, USER_ID) values (2, 1, (TO_TIMESTAMP('17/12/2023 20:04:13', 'DD/MM/YYYY HH24:MI:SS')), 1, 'CANDIDATE', 'Abel Ferrer', 'CREATE', 1)
insert into ACTION(ID, VERSION, DATE, ENTITY_ID, ENTITY_NAME, ENTITY_TITLE, TYPE, USER_ID) values (3, 1, (TO_TIMESTAMP('19/12/2023 20:05:12', 'DD/MM/YYYY HH24:MI:SS')), 1, 'JOB_OFFER', 'Arquitecto de Software', 'CREATE', 1)
insert into ACTION(ID, VERSION, DATE, ENTITY_ID, ENTITY_NAME, ENTITY_TITLE, TYPE, USER_ID) values (4, 1, (TO_TIMESTAMP('31/12/2023 20:12:24', 'DD/MM/YYYY HH24:MI:SS')), 2, 'JOB_OFFER', 'Arquitecto Java', 'CREATE', 1)
insert into ACTION(ID, VERSION, DATE, ENTITY_ID, ENTITY_NAME, ENTITY_TITLE, TYPE, USER_ID) values (5, 1, (TO_TIMESTAMP('05/01/2024 20:22:55', 'DD/MM/YYYY HH24:MI:SS')), 2, 'JOB_OFFER', 'Arquitecto Java', 'UPDATE', 1)
ALTER SEQUENCE ACTION_SEQ RESTART WITH 6;

insert into MEETING(ID, VERSION, DATE_PLANNED, DURATION, MEETING_TYPE, OWNER_ID, DESCRIPTION, TITLE) values (1, 1, (TO_TIMESTAMP('27/02/2024 08:30:00', 'DD/MM/YYYY HH24:MI:SS')), 5400, 'PHONE', 1, 'Hay que valorar si el candidato es adecuado para el puesto de arquitecto', 'Reunión para candidatura')
insert into MEETING(ID, VERSION, DATE_PLANNED, DURATION, MEETING_TYPE, OWNER_ID, DESCRIPTION, TITLE) values (2, 1, (TO_TIMESTAMP('01/03/2024 12:30:00', 'DD/MM/YYYY HH24:MI:SS')), 5400, 'PHONE', 1, 'El responsable técnico ha de valorar los conocimientos de Java e Hibernate del candidato', 'Reunión para evaluación')
insert into MEETING(ID, VERSION, DATE_PLANNED, DURATION, MEETING_TYPE, OWNER_ID, DESCRIPTION, TITLE) values (3, 1, (TO_TIMESTAMP('27/02/2024 12:30:00', 'DD/MM/YYYY HH24:MI:SS')), 5400, 'IN_PERSON', 1, 'Reunión interna para valorar el candidato', 'Reunión interna')
ALTER SEQUENCE MEETING_SEQ RESTART WITH 4;

insert into MEETING_EMAILS(MEETING_ID, EMAIL) values (1, 'abel.ferrer.jimenez@gmail.com');
insert into MEETING_EMAILS(MEETING_ID, EMAIL) values (1, 'aferrer@gmail.com');

insert into MEETING_EMAILS(MEETING_ID, EMAIL) values (2, 'abel.ferrer.jimenez@gmail.com');
insert into MEETING_EMAILS(MEETING_ID, EMAIL) values (2, 'aferrer@gmail.com');

insert into MEETING_EMAILS(MEETING_ID, EMAIL) values (3, 'abel.ferrer.jimenez@gmail.com');
insert into MEETING_EMAILS(MEETING_ID, EMAIL) values (3, 'isabel.vallejo.medina@gmail.com');

