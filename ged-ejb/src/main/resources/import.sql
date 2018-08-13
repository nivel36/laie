insert into CONTRACTTYPE( id, version, name, description) values ( 1, 0, 'contrato_indefinido','contrato_indefinido.description');
insert into CONTRACTTYPE( id, version, name, description) values ( 2, 0, 'contrato_indefinido_fijos_discontinuos','contrato_indefinido_fijos_discontinuos.description');

insert into CONTRACTDURATION( id, version, name, description ) values ( 101, 0, 'one_year', 'one_year.description');

insert into SKILLLEVEL( id, version, name, description ) values ( 110, 0, 'high', 'high.description');
insert into SKILLLEVEL( id, version, name, description ) values ( 111, 0, 'medium', 'medium.description');
insert into SKILLLEVEL( id, version, name, description ) values ( 112, 0, 'low', 'low.description');

insert into LANGUAGELEVEL( id, version, name, description ) values ( 120, 0, 'high', 'high.description');
insert into LANGUAGELEVEL( id, version, name, description ) values ( 121, 0, 'medium', 'medium.description');
insert into LANGUAGELEVEL( id, version, name, description ) values ( 122, 0, 'low', 'low.description');
insert into LANGUAGELEVEL( id, version, name, description ) values ( 123, 0, 'native', 'native.description');

insert into JOBCANDIDATURESTATE( id, version, name, description ) values ( 130, 0, 'open', 'open.description');
insert into JOBCANDIDATURESTATE( id, version, name, description ) values ( 131, 0, 'closed', 'closed.description');

insert into CURRICULUM( id, version, deleted ) values (301, 0,  false);

insert into EDUCATION( id, version, curriculumId, degree, description,  fromDate, school, stillStudying, toDate, deleted) values( 401, 0, 301, 'Licenciado en Matemáticas', 'Licenciado en Matemáticas por la universidad de Barcelona',  DATE '2000-10-01', 'Universitat de Barcelona', false, DATE '2012-12-01', false);
insert into EDUCATION( id, version, curriculumId, degree, description,  fromDate, school, stillStudying, toDate, deleted) values( 402, 0, 301, 'Bachillerato', null,  DATE '1988-10-01', 'IES Thalassa', false, DATE '1996-06-01', false);

insert into LANGUAGE( id, version, curriculumId, languageName, read, speak, write, deleted ) values( 501, 0, 301, 'Castellano', 'native', 'native', 'native', false );
insert into LANGUAGE( id, version, curriculumId, languageName, read, speak, write, deleted ) values( 502, 0, 301, 'Català', 'native', 'native', 'native', false );
insert into LANGUAGE( id, version, curriculumId, languageName, read, speak, write, deleted ) values( 503, 0, 301, 'English', 'high', 'low', 'medium', false );

insert into SKILL( id, version, curriculumId, name, level, deleted ) values( 601, 0, 301, 'Java', 'high', false);
insert into SKILL( id, version, curriculumId, name, level, deleted ) values( 602, 0, 301, 'Spring', 'high', false);
insert into SKILL( id, version, curriculumId, name, level, deleted ) values( 603, 0, 301, 'Hibernate', 'high', false);
insert into SKILL( id, version, curriculumId, name, level, deleted ) values( 604, 0, 301, 'JSF', 'high', false);
insert into SKILL( id, version, curriculumId, name, level, deleted ) values( 605, 0, 301, 'JEE', 'high', false);
insert into SKILL( id, version, curriculumId, name, level, deleted ) values( 606, 0, 301, 'C', 'high', false);
insert into SKILL( id, version, curriculumId, name, level, deleted ) values( 607, 0, 301, 'C++', 'high', false);
insert into SKILL( id, version, curriculumId, name, level, deleted ) values( 608, 0, 301, 'HTML5', 'high', false);
insert into SKILL( id, version, curriculumId, name, level, deleted ) values( 609, 0, 301, 'Javascript', 'high', false);

insert into JOBEXPERIENCE( id, version, curriculumId, companyName, description, fromDate, jobPosition, stillWorking, toDate) values (701, 0, 301, 'Alten', 'Desarrollo de la herramienta para Gas Natural Fenosa con el que se dará cobertura a todas las actividades asociadas al proceso de expansión de la red y provisión de servicio en todos los ámbitos geográficos donde el grupo Gas Natural Fenosa (GNF) desarrolla sus negocios de distribución de gas natural y electricidad. Puesto de Arquitecto JEE desarrollando las siguientes tareas:' , DATE '2010-11-04', 'Arquitecto JAVA', true, null);

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

insert into I18NSTRING( id, version, locale, key, text ) values( 825, 0, 'es', 'contrato_indefinido', 'Contrato indefinido' );
insert into I18NSTRING( id, version, locale, key, text ) values( 826, 0, 'ca', 'contrato_indefinido', 'Contracte indefinit' );
insert into I18NSTRING( id, version, locale, key, text ) values( 830, 0, 'es', 'contrato_indefinido.description', 'Es aquel que se concierta sin establecer límites de tiempo en la prestación de los servicios, en cuanto a la duración del contrato.' );
insert into I18NSTRING( id, version, locale, key, text ) values( 831, 0, 'ca', 'contrato_indefinido.description', 'Es aquel que se concierta sin establecer límites de tiempo en la prestación de los servicios, en cuanto a la duración del contrato.' );

insert into I18NSTRING( id, version, locale, key, text ) values( 835, 0, 'es', 'contrato_indefinido_fijos_discontinuos', 'Contrato indefinido de fijos discontinuos' );
insert into I18NSTRING( id, version, locale, key, text ) values( 836, 0, 'ca', 'contrato_indefinido_fijos_discontinuos', 'Contrato indefinido de fijos discontinuos' );
insert into I18NSTRING( id, version, locale, key, text ) values( 840, 0, 'es', 'contrato_indefinido_fijos_discontinuos.description', 'Es el que se concierta para realizar trabajos que tengan el carácter de fijos discontinuos y no se repitan en fechas ciertas, dentro del volumen normal de actividad de la empresa.A efectos de prestaciones por desempleo, también se consideran trabajadores fijos discontinuos los que desarrollen trabajos fijos y periódicos que se repiten en fechas ciertas.' );
insert into I18NSTRING( id, version, locale, key, text ) values( 841, 0, 'ca', 'contrato_indefinido_fijos_discontinuos.description', 'Es el que se concierta para realizar trabajos que tengan el carácter de fijos discontinuos y no se repitan en fechas ciertas, dentro del volumen normal de actividad de la empresa.A efectos de prestaciones por desempleo, también se consideran trabajadores fijos discontinuos los que desarrollen trabajos fijos y periódicos que se repiten en fechas ciertas.' );

insert into I18NSTRING( id, version, locale, key, text ) values( 851, 0, 'es', 'curriculum', 'Curriculum vitae' );
insert into I18NSTRING( id, version, locale, key, text ) values( 852, 0, 'ca', 'curriculum', 'Curriculum vitae' );
insert into I18NSTRING( id, version, locale, key, text ) values( 853, 0, 'es', 'lopd', 'LOPD' );
insert into I18NSTRING( id, version, locale, key, text ) values( 854, 0, 'ca', 'lopd', 'LOPD' );
insert into I18NSTRING( id, version, locale, key, text ) values( 855, 0, 'es', 'edited_curriculum', 'Curriculum editado' );
insert into I18NSTRING( id, version, locale, key, text ) values( 856, 0, 'ca', 'edited_curriculum', 'Curriculum editat' );
insert into I18NSTRING( id, version, locale, key, text ) values( 857, 0, 'es', 'other', 'Otro' );
insert into I18NSTRING( id, version, locale, key, text ) values( 858, 0, 'ca', 'other', 'Altre' );

insert into I18NSTRING( id, version, locale, key, text ) values( 860, 0, 'es', 'open', 'Abierta' );
insert into I18NSTRING( id, version, locale, key, text ) values( 861, 0, 'ca', 'open', 'Oberta' );
insert into I18NSTRING( id, version, locale, key, text ) values( 864, 0, 'es', 'closed', 'Cerrada' );
insert into I18NSTRING( id, version, locale, key, text ) values( 865, 0, 'ca', 'closed', 'Tancada' );

insert into ROLE( id, version, name, parentRoleId ) values( 911, 0, 'ADMIN', null );
insert into ROLE( id, version, name, parentRoleId ) values( 913, 0, 'RECRUITER_ADMIN', 911 );
insert into ROLE( id, version, name, parentRoleId ) values( 912, 0, 'RECRUITER', 913 );
insert into ROLE( id, version, name, parentRoleId ) values( 915, 0, 'TECHNIC_ADMIN', 911 );
insert into ROLE( id, version, name, parentRoleId ) values( 914, 0, 'TECHNIC', 915 );
insert into ROLE( id, version, name, parentRoleId ) values( 916, 0, 'PROJECT_MANAGER', 911 );

insert into ROLECLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 925, 0, 911, 911, 0 );
insert into ROLECLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 926, 0, 911, 912, 2 );
insert into ROLECLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 927, 0, 911, 913, 1 );
insert into ROLECLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 928, 0, 911, 914, 2 );
insert into ROLECLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 929, 0, 911, 915, 1 );
insert into ROLECLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 930, 0, 912, 912, 0 );
insert into ROLECLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 931, 0, 913, 913, 0 );
insert into ROLECLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 932, 0, 913, 912, 1 );
insert into ROLECLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 933, 0, 914, 914, 0 );
insert into ROLECLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 934, 0, 915, 915, 0 );
insert into ROLECLOSURE( id, version, antecessor_id, descendant_id, pathLength ) values( 935, 0, 915, 914, 1 );

insert into USER( id, version, email, language, name, rowsPerPage, surname, password, roleId, deleted, imageFileName ) values( 901, 0, 'abel.ferrer.jimenez@gmail.com', 'es', 'Abel', 25, 'Ferrer', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', 911, false, null);
insert into USER( id, version, email, language, name, rowsPerPage, surname, password, roleId, deleted, imageFileName ) values( 902, 0, 'isabel.vallejo.medina@gmail.com', 'es', 'Isabel', 25, 'Vallejo', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', 911, false, null);

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