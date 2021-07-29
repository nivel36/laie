insert into USER( id, version, uid, name, surname, phoneNumber, email, locale, role, managerId, jobPosition ) values( 500, 0, 'AF500', 'Abel', 'Ferrer Jiménez','685555276', 'abel.ferrer.jimenez@gmail.com', 'es_ES', 'ADMIN', null, 'Software architect');
insert into USER( id, version, uid, name, surname, phoneNumber, email, locale, role, managerId ) values( 501, 0, 'AF501', 'Isabel', 'Vallejo Medina','685555276', 'isabel.vallejo.medina@gmail.com', 'es_ES', 'ADMIN', null);
insert into USER( id, version, uid, name, surname, phoneNumber, email, locale, role, managerId ) values( 502, 0, 'AF502', 'Juan', 'Fernández','685555276', 'jfernandez@gmail.com', 'es_ES', 'RRHH', 500);
insert into USER( id, version, uid, name, surname, phoneNumber, email, locale, role, managerId ) values( 503, 0, 'AF503', 'Pedro', 'Smith','685555276', 'psmith@gmail.com', 'es_ES', 'RRHH', 500);
insert into USER( id, version, uid, name, surname, phoneNumber, email, locale, role, managerId ) values( 504, 0, 'AF504', 'Luis', 'Carrasco','685555276', 'lcarrasco@gmail.com', 'es_ES', 'USER', 500);
insert into USER( id, version, uid, name, surname, phoneNumber, email, locale, role, managerId ) values( 505, 0, 'AF505', 'Ana', 'Casoras','685555276', 'acasoras@gmail.com', 'es_ES', 'USER', 500);
insert into USER( id, version, uid, name, surname, phoneNumber, email, locale, role, managerId ) values( 506, 0, 'AF506', 'Luis Ángel', 'Nuestra Señora del manto','685555276', 'lantrasenora@gmail.com', 'es_ES', 'USER', 500);
insert into USER( id, version, uid, name, surname, phoneNumber, email, locale, role, managerId ) values( 507, 0, 'AF507', 'Anabel', 'Villafafila','685555276', 'anabel@gmail.com', 'es_ES', 'USER', 500);

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

ALTER SEQUENCE HIBERNATE_SEQUENCE RESTART with 2000;