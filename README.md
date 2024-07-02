# Foro Hub

> **Disclaimer:** Con fines de aprendizaje, toda la implementación ha sido realizada en inglés.

Este repositorio contiene el reto final de backend del programa Oracle ONE la cual consiste en una API de tópicos (o preguntas).

## Pre Requisitos

El proyecto fue desarrollado con JAVA 17.

Así mismo, el proyecto asume que tienes un servidor de base de datos disponible, en este caso se usa MarisaDB, pero con las modificaciones necesarias se puede adaptar a cualquier otro proveedor.

Para el desarrollo y ejecución del proyecto, es necesario crear un archivo llamado `secrets.properties` dentro de la ruta /src/main/resources/ (al mismo nivel que el archivo `application.yml`) con los siguientes valores:

```
DATABASE_HOST=<IP del servidor>
DATABASE_NAME=<Nombre de la base de datos>
DATABASE_USERNAME=<Usuario de la base de datos>
DATABASE_PASSWORD=<Contraseña del usuario>
JWT_SECRET_KEY=<Llave secreta para generar los tokens JWT>
JWT_ISSUER=<Nombre del emisor de los tokens JWT>
JWT_EXPIRATION_TIME=<Duración de la vigencia de un token en milisegundos>
```

## Rutas

> **Nota**: Los campos marcados como protegidos requieren de un token JWT válido el cuál puede se obtenido a través de la ruta /auth/login proporcionando credenciales de usuario válidas.

Las rutas implementadas son las siguientes:

|  Verbo   | Endopoint    |       Protegido       | Descripción                                                                          |
| :------: | ------------ | :-------------------: | ------------------------------------------------------------------------------------ |
|  `POST`  | /auth/signup | :white_large_square: | Crear un nuevo usuario                                                               |
|  `POST`  | /auth/login  | :white_large_square: | Generar un token para un usuario existente                                           |
|  `GET`   | /users       |  :white_check_mark:   | Obtener todos los usuarios existentes                                                |
|  `GET`   | /users/{id}  |  :white_check_mark:   | Obtener un usuario por ID                                                            |
|  `GET`   | /users/me    |  :white_check_mark:   | Obtener la información del usuario actualmente identificado (a través del token JWT) |
|  `POST`  | /topics      |  :white_check_mark:   | Crear un nuevo tópico                                                                |
|  `GET`   | /topics      |  :white_check_mark:   | Obtener todos los tópicos                                                            |
|  `GET`   | /topics/{id} |  :white_check_mark:   | Obtener un tópico por ID                                                             |
| `DELETE` | /topics/{id} |  :white_check_mark:   | Eliminar un tópico                                                                   |
|  `PUT`   | /topics/{id} |  :white_check_mark:   | Actualizar un tópico                                                                 |

## Errores

La API es capaz de manejar los errores y generar un mensaje de utilidad para el usuario final.

## Entidades

Para la creación de las entidades se usó `ULID` como ID en lugar de un número del tipo `long`. Esto con fines de aprendizaje y para indagar más sobre las anotación `@Prepersist`.

> **Nota:** Las entidades marcadas con * están mapeadas pero no se hace uso de ellas actualmente.

Las entidades disponibles son la siguentes:

- `BaseEntity`: Entidad abstracta que contiene los campos en común para todas las entidades (id, fecha de creción, fecha de actualización, elminado y estatus), así como los métodos para desactivar o activar una entidad a través del campo `deleted`.
  - `User`: Entidad que extiende de `BaseEntity` y que agrega las propiedades nombre completo, email, y contraseña, necesarias para un usuario.
  - `Topic`: Entidad que extiende de `BaseEntity` y que agrega las propiedades necesarias para un tópico como lo son título, mensaje, autor, y curso.
  - `Course`*: Entidad que extiende de `BaseEntity` y que agrega las propiedades necesarias para un curso como lo son nombre, y categoría.
  - `Category`*: Entidad que extiende de `BaseEntity` y que agrega las propiedades necesarias para una categoría como lo son nombre.

Al hacer uso de una entidad base, se facilita la creación de nuevas entidades que tendrán propiedades en común.

## TODO

Las siguientes actividades están pendientes por realizar en un futuro:

- [ ] Agregar documentación a través de springdoc.
- [ ] Implementar rutas y lógica relacionada a `Course`.
- [ ] Implementar rutas y lógica relacionada a `Category`.
- [ ] Permitir la actualización de usuarios.
- [ ] Agregar Unit Tests.