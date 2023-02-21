# GED

## Api
Swagger -> /api/

## Desarrollo
poner wildfly.h2.compatibility.mode a PostgreSQL

## Vista
Converter->Find by id
No poner @param ya que hace la busca por id y pero necesitamos todos los datos de la entidad

## JPA
Relaciones OneToOne. Lazy no funciona. Lazy es un consejo y eager es obligatorio. Cuando haces el OneToOne con nullable=false no hay manera de hacer un lazy.

## ENTIDADES
Las pantallas de cliente o candidato no pueden cargar todos los datos en una consulta porque se generan más consultas por cada entidad de candidaturas que aparezcan en las colecciones.

##TODO
* Reuniones
    * Las reuniones han de poner un campo de duración
    * Se puede poner dos reuniones el mismo día y a la misma hora
    * Falta una pantalla de vista de reuniones
    * Falta un boton de quitar reunión
    * De alguna manera se ha de saber si un email (puesto que un asistente a una reunión puede ser quien quiera) tiene horas libres o no.