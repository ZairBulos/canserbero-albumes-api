<div align="center">
  <img 
    src="https://i.ibb.co/XYcJrmk/Canserbero.jpg"
    alt="imagen-de-canserbero"
    title="Tirone José González Orama, Canserbero"
  />

# Cansebero Álbumes API

API RESTful para consultar los álbumes y canciones del rapero, compositor y activista venezolano Tirone José González Orama, conocido artísticamente como Canserbero.

[![OpenAPI](https://img.shields.io/badge/OpenAPI%20-%20Swagger%20-%20green?color=green)](https://canserbero-albumes-api.onrender.com/swagger-ui/index.html)
[![Version](https://img.shields.io/badge/Version%20-%201.0.0%20-%20blue?color=blue)](https://github.com/ZairBulos/canserbero-albumes-api/releases)
[![License](https://img.shields.io/badge/License%20-%20BSD--3--Clause%20license%20-%20yellow?color=yellow)](https://opensource.org/license/bsd-3-clause)
</div>

## Índice

- [Introducción](#introducción)
- [Recursos Disponibles](#recursos-disponibles)
    - [Álbumes](#álbumes)
    - [Canciones](#canciones)
- [Colecciones de API](#colecciones-de-api)
- [Licencia](#licencia)

## Introducción

El URL base para todas las solicitudes es: `https://canserbero-albumes-api.onrender.com/api/`

## Recursos Disponibles

### Álbumes

#### Obtener todos los álbumes

````http request
GET /api/albumes
````

#### Obtener todos los álbumes con canciones

````http request
GET /api/albumes/canciones
````

#### Obtener álbum por id

````http request
GET /api/albumes/:id
````

#### Obtener álbum con canciones por id

````http request
GET /api/albumes/canciones/:id
````

### Canciones

#### Obtener todos las canciones

````http request
GET /api/canciones
````

#### Obtener canciones por álbum

````http request
GET /api/canciones/:albumId
````

#### Buscar canciones por nombre

````http request
GET /api/canciones/buscarPorNombre?nombre=:nombre
````

## Colecciones de API

- [Colección de Postman](https://www.postman.com/crimson-space-910033/workspace/cansebero-albumes-api)

## Licencia

Este proyecto está bajo la [licencia BSD-3-Clause](/LICENSE.md)