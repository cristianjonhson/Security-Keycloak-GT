# Security-Keycloak-GT

Proyecto de ejemplo para integrar Keycloak con una API REST construida en Spring Boot. El repositorio reúne una aplicación backend protegida con OAuth2 Resource Server, configuraciones de despliegue para levantar Keycloak con Docker y artefactos de apoyo para pruebas manuales, como una colección de Postman y un export de realm.

El foco del proyecto está en mostrar autenticación con JWT, autorización por roles y consumo de claims emitidos por Keycloak dentro de una API Java. También incluye un ejemplo CRUD simple sobre compañías para probar accesos públicos y privados.

## Tabla de Contenidos

- [Descripción General](#descripción-general)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Estructura del Repositorio](#estructura-del-repositorio)
- [Requisitos Previos](#requisitos-previos)
- [Configuración](#configuración)
- [Cómo Ejecutarlo](#cómo-ejecutarlo)
- [Uso de la Colección Postman](#uso-de-la-colección-postman)
- [Endpoints Relevantes](#endpoints-relevantes)
- [Recursos Incluidos](#recursos-incluidos)
- [Consideraciones Importantes](#consideraciones-importantes)
- [Contenido Original](#contenido-original)

## Descripción General

Este repositorio contiene principalmente los siguientes componentes:

- Una API Spring Boot en [demos/api-gestion-academica](demos/api-gestion-academica) con seguridad basada en JWT.
- Una configuración simple de Keycloak en [docker-compose.yml](docker-compose.yml).
- Una configuración más completa de Keycloak con PostgreSQL en [KC-PostgreSql/docker-compose.yml](KC-PostgreSql/docker-compose.yml).
- Un realm exportado en [Keycloak/realm-export.json](Keycloak/realm-export.json).
- Una colección Postman para pruebas manuales en [collections/CURS-000188-Keycloak.postman_collection.json](collections/CURS-000188-Keycloak.postman_collection.json).

La API expone endpoints públicos y privados y convierte roles del token JWT de Keycloak en authorities de Spring Security. El módulo también inicializa datos de ejemplo en base de datos mediante [demos/api-gestion-academica/src/main/resources/data.sql](demos/api-gestion-academica/src/main/resources/data.sql).

## Tecnologías Utilizadas

- Java 17
- Spring Boot 3.3.0
- Spring Web
- Spring Security
- Spring Security Test (pruebas)
- OAuth2 Resource Server
- Spring Data JPA
- Keycloak 25.0.0
- PostgreSQL
- Docker y Docker Compose
- Maven Wrapper
- Lombok

## Estructura del Repositorio

```text
.
├── docker-compose.yml
├── README.md
├── collections/
│   └── CURS-000188-Keycloak.postman_collection.json
├── demos/
│   └── api-gestion-academica/
│       ├── mvnw
│       ├── pom.xml
│       └── src/
│           ├── main/
│           │   ├── java/
│           │   │   └── cl/edu/galaxy/training/apps/siac/api_gestion_academica/
│           │   │       ├── configuration/
│           │   │       ├── entity/
│           │   │       ├── repository/
│           │   │       ├── restcontroller/
│           │   │       └── service/
│           │   └── resources/
│           │       ├── application.yml
│           │       ├── data.sql
│           │       └── import.sql
│           └── test/
├── KC-PostgreSql/
│   └── docker-compose.yml
└── Keycloak/
   └── realm-export.json
```

Resumen por carpeta:

- [demos/api-gestion-academica](demos/api-gestion-academica): aplicación principal Spring Boot.
- [demos/api-gestion-academica/src/main/java/cl/edu/galaxy/training/apps/siac/api_gestion_academica/configuration](demos/api-gestion-academica/src/main/java/cl/edu/galaxy/training/apps/siac/api_gestion_academica/configuration): configuración de seguridad y JWT.
- [demos/api-gestion-academica/src/main/java/cl/edu/galaxy/training/apps/siac/api_gestion_academica/restcontroller](demos/api-gestion-academica/src/main/java/cl/edu/galaxy/training/apps/siac/api_gestion_academica/restcontroller): endpoints de prueba y CRUD.
- [KC-PostgreSql](KC-PostgreSql): stack Docker para Keycloak persistido en PostgreSQL.
- [Keycloak](Keycloak): export de configuración de realm.
- [collections](collections): colección Postman para obtener tokens y probar endpoints.

## Requisitos Previos

Para ejecutar el proyecto necesitas:

- Java 17 instalado.
- Docker y Docker Compose instalados.
- Una instancia de PostgreSQL accesible desde la API.
- Acceso a Maven Wrapper o Maven local.

Versiones y parámetros observados en el repositorio:

- Java: 17 en [demos/api-gestion-academica/pom.xml](demos/api-gestion-academica/pom.xml)
- Spring Boot: 3.3.0 en [demos/api-gestion-academica/pom.xml](demos/api-gestion-academica/pom.xml)
- Keycloak: 25.0.0 en [demos/api-gestion-academica/pom.xml](demos/api-gestion-academica/pom.xml)
- Puerto de la API: 8082 en [demos/api-gestion-academica/src/main/resources/application.yml](demos/api-gestion-academica/src/main/resources/application.yml)

## Configuración

La configuración principal de la API está en [demos/api-gestion-academica/src/main/resources/application.yml](demos/api-gestion-academica/src/main/resources/application.yml).

Valores relevantes actuales:

- Puerto de la API: 8082
- Context path: /api-gestion-academica
- Base de datos PostgreSQL: jdbc:postgresql://localhost:5432/db_academica
- Usuario PostgreSQL: se define por variable de entorno `DB_USERNAME`
- Contraseña PostgreSQL: se define por variable de entorno `DB_PASSWORD`
- open-in-view: false
- JWK Set URI configurado: http://localhost:6082/realms/REALM_DEMO_V1/protocol/openid-connect/certs
- Client ID esperado por la API: api-gestion-academica

Notas de configuración:

- El dialecto de PostgreSQL no se declara explícitamente en JPA para evitar advertencias de Hibernate; se detecta automáticamente.

Recomendación práctica:

- No dejes credenciales reales en el archivo de configuración para entornos compartidos.
- Antes de arrancar la API, define `DB_USERNAME` y `DB_PASSWORD`.
- Si vas a usar otro puerto, realm o client ID, actualiza [demos/api-gestion-academica/src/main/resources/application.yml](demos/api-gestion-academica/src/main/resources/application.yml).
- Si quieres alinear la documentación operativa con la colección Postman y el realm exportado, revisa la sección de consideraciones importantes más abajo.

## Cómo Ejecutarlo

### Opción recomendada: Keycloak con PostgreSQL

Esta opción es la más cercana a la configuración que consume la API, porque expone Keycloak por el puerto 6082.

1. Define las variables sensibles requeridas y levanta Keycloak y PostgreSQL:

```bash
export KEYCLOAK_ADMIN='admin'
export KEYCLOAK_ADMIN_PASSWORD='cambia_esta_password'
export KC_DB_PASSWORD='cambia_esta_password'
export POSTGRES_PASSWORD='cambia_esta_password'
```

```bash
cd KC-PostgreSql
docker compose --profile dev up -d
```

2. Verifica el acceso a Keycloak:

- URL de acceso esperada: http://localhost:6082
- Usuario administrador: admin
- Contraseña de administrador: admin

3. Importa o recrea el realm según [Keycloak/realm-export.json](Keycloak/realm-export.json).

Nota: el compose actual no hace importación automática del realm, por lo que este paso debe completarse manualmente desde la consola de administración o ajustando el arranque del contenedor.

### Opción simple: Keycloak standalone

El archivo [docker-compose.yml](docker-compose.yml) levanta una instancia simple de Keycloak para pruebas rápidas.

```bash
docker compose --profile dev up -d
```

En esa variante, Keycloak queda expuesto en http://localhost:8080 con credenciales admin/admin. Esta opción no coincide con la URL JWK configurada por defecto en la API, por lo que requiere ajustar [demos/api-gestion-academica/src/main/resources/application.yml](demos/api-gestion-academica/src/main/resources/application.yml) si decides usarla.

### Base de datos PostgreSQL

La API espera una base de datos PostgreSQL local accesible en localhost:5432. Con la configuración actual:

- Base de datos: db_academica
- Usuario: definido por `DB_USERNAME`
- Contraseña: definida por `DB_PASSWORD`

La configuración soporta variables de entorno para contenedores o despliegues:

- DB_HOST (por defecto: localhost)
- DB_PORT (por defecto: 5432)
- DB_NAME (por defecto: db_academica)
- DB_USERNAME (obligatoria)
- DB_PASSWORD (obligatoria)

Bootstrap en un solo comando (crea rol y base de datos automáticamente):

Antes de ejecutar el bootstrap, define las contraseñas requeridas:

```bash
export PG_ADMIN_PASSWORD='cambia_esta_password'
export DB_PASSWORD='cambia_esta_password'
```

```bash
cd demos/api-gestion-academica
chmod +x scripts/bootstrap-postgres.sh
./scripts/bootstrap-postgres.sh
```

Si tu superusuario PostgreSQL requiere contraseña:

```bash
cd demos/api-gestion-academica
chmod +x scripts/bootstrap-postgres.sh
PG_ADMIN_PASSWORD='tu_password_admin' ./scripts/bootstrap-postgres.sh
```

Carga de datos semilla desde [demos/api-gestion-academica/src/main/resources/data.sql](demos/api-gestion-academica/src/main/resources/data.sql):

- El script es idempotente (`ON CONFLICT (company_id) DO NOTHING`) para evitar errores por llave primaria duplicada en ejecuciones repetidas.

Antes de ejecutar la carga, define la contraseña de la base:

```bash
export DB_PASSWORD='cambia_esta_password'
```

```bash
cd demos/api-gestion-academica
chmod +x scripts/load-data-postgres.sh
./scripts/load-data-postgres.sh
```

Comando único de bootstrap completo (base + datos):

```bash
cd demos/api-gestion-academica && chmod +x scripts/bootstrap-postgres.sh scripts/load-data-postgres.sh && ./scripts/bootstrap-postgres.sh && ./scripts/load-data-postgres.sh
```

### Ejecutar la API

Desde [demos/api-gestion-academica](demos/api-gestion-academica):

```bash
./mvnw spring-boot:run
```

Alternativamente:

```bash
./mvnw clean test
./mvnw clean package
java -jar target/api-gestion-academica-0.0.1-SNAPSHOT.jar
```

Validación rápida recomendada:

```bash
./mvnw test
```

Resultado esperado en este repositorio: BUILD SUCCESS con 4 tests (incluyendo pruebas de integración de seguridad con MockMvc).

Escaneo reproducible de CVEs:

```bash
cd demos/api-gestion-academica
chmod +x scripts/security-scan.sh
./scripts/security-scan.sh
```

Una vez iniciada, la API debería quedar accesible en:

- http://localhost:8082/api-gestion-academica

## Uso de la Colección Postman

La colección está en [collections/CURS-000188-Keycloak.postman_collection.json](collections/CURS-000188-Keycloak.postman_collection.json).

Flujo recomendado:

1. Importa la colección en Postman.
2. Revisa variables de colección y ajusta si corresponde:
- keycloak_protocol, keycloak_host, keycloak_port, realm
- client_id, username, password, scope
- api_base_url
3. Ejecuta la solicitud [Get Token (Password Grant)](collections/CURS-000188-Keycloak.postman_collection.json) dentro de la carpeta 00 - Auth.
4. La colección guarda automáticamente access_token para los endpoints protegidos.
5. Ejecuta las solicitudes de las carpetas 10 - Public API y 20 - Protected API.

Validación por roles (endpoint2 y endpoint3):

1. Antes de ejecutar endpoint2 y endpoint3, configura:
- expected_status_endpoint2
- expected_status_endpoint3
2. Usa valor 200 cuando el usuario autenticado tenga el rol requerido.
3. Usa valor 403 cuando el usuario autenticado no tenga el rol requerido.

Ejemplos prácticos:

1. Usuario con rol director_Academico y sin rol profesor:
- expected_status_endpoint2=200
- expected_status_endpoint3=403
2. Usuario con rol profesor y sin rol director_Academico:
- expected_status_endpoint2=403
- expected_status_endpoint3=200
3. Usuario con ambos roles:
- expected_status_endpoint2=200
- expected_status_endpoint3=200

Tabla rápida de validación:

| Usuario tipo | Rol esperado | Estado endpoint2 | Estado endpoint3 |
| --- | --- | --- | --- |
| Solo director | director_Academico | 200 | 403 |
| Solo profesor | profesor | 403 | 200 |
| Director y profesor | director_Academico + profesor | 200 | 200 |
| Sin roles requeridos | ninguno | 403 | 403 |

## Endpoints Relevantes

Algunos endpoints identificados en el código:

- Público: GET /api-gestion-academica/api/v1/test/libre/demo
- Listado de compañías: GET /api-gestion-academica/api/v1/companies
- Buscar compañías por nombre: GET /api-gestion-academica/api/v1/companies/by-company?company=texto
- Buscar compañías por contacto: GET /api-gestion-academica/api/v1/companies/by-contact?contact=texto
- Buscar compañías por país: GET /api-gestion-academica/api/v1/companies/by-country?country=texto
- Endpoint privado admin: GET /api-gestion-academica/api/v1/test/private/endpoint1
- Endpoint privado director: GET /api-gestion-academica/api/v1/test/private/endpoint2
- Endpoint privado profesor: GET /api-gestion-academica/api/v1/test/private/endpoint3
- Endpoint privado premium: GET /protected/premium

Autorización observada en el código:

- admin: requerido para algunos endpoints privados y para delete.
- director o admin: permitido para actualización parcial de situación.
- profesor: permitido para un endpoint de prueba específico.

Pruebas automáticas de seguridad:

- [demos/api-gestion-academica/src/test/java/cl/edu/galaxy/training/apps/siac/api_gestion_academica/SecurityIntegrationTests.java](demos/api-gestion-academica/src/test/java/cl/edu/galaxy/training/apps/siac/api_gestion_academica/SecurityIntegrationTests.java)
- Cobertura actual:
   - público sin token -> 200
   - endpoint privado sin token -> 401
   - endpoint privado con rol incorrecto -> 403

Referencia de implementación:

- Seguridad: [demos/api-gestion-academica/src/main/java/cl/edu/galaxy/training/apps/siac/api_gestion_academica/configuration/SecurityConfig.java](demos/api-gestion-academica/src/main/java/cl/edu/galaxy/training/apps/siac/api_gestion_academica/configuration/SecurityConfig.java)
- Controlador CRUD: [demos/api-gestion-academica/src/main/java/cl/edu/galaxy/training/apps/siac/api_gestion_academica/restcontroller/CompanyRestController.java](demos/api-gestion-academica/src/main/java/cl/edu/galaxy/training/apps/siac/api_gestion_academica/restcontroller/CompanyRestController.java)
- Controlador de pruebas: [demos/api-gestion-academica/src/main/java/cl/edu/galaxy/training/apps/siac/api_gestion_academica/restcontroller/TestController.java](demos/api-gestion-academica/src/main/java/cl/edu/galaxy/training/apps/siac/api_gestion_academica/restcontroller/TestController.java)

## Recursos Incluidos

- Realm exportado: [Keycloak/realm-export.json](Keycloak/realm-export.json)
- Colección Postman: [collections/CURS-000188-Keycloak.postman_collection.json](collections/CURS-000188-Keycloak.postman_collection.json)
- Datos semilla principales: [demos/api-gestion-academica/src/main/resources/data.sql](demos/api-gestion-academica/src/main/resources/data.sql)
- Script de datos alternativo incluido en el repositorio: [demos/api-gestion-academica/src/main/resources/import.sql](demos/api-gestion-academica/src/main/resources/import.sql)

Los datos semilla insertan registros iniciales en la tabla de compañías para facilitar pruebas de consulta.

## Consideraciones Importantes

La configuración por defecto del repositorio quedó alineada con esta combinación:

- Realm: REALM_DEMO_V1
- Keycloak: http://localhost:6082
- JWK Set URI de la API: http://localhost:6082/realms/REALM_DEMO_V1/protocol/openid-connect/certs
- Endpoint de token para Postman: http://localhost:6082/realms/REALM_DEMO_V1/protocol/openid-connect/token

Con esa convención:

- la API consume las claves públicas del mismo realm exportado en [Keycloak/realm-export.json](Keycloak/realm-export.json)
- la colección Postman solicita tokens contra la misma instancia de Keycloak
- el stack recomendado para pruebas sigue siendo [KC-PostgreSql/docker-compose.yml](KC-PostgreSql/docker-compose.yml), que publica Keycloak en el puerto 6082

Si decides usar otra instancia o puerto, actualiza en conjunto:

- el nombre del realm en Keycloak
- el JWK Set URI en [demos/api-gestion-academica/src/main/resources/application.yml](demos/api-gestion-academica/src/main/resources/application.yml)
- el endpoint de token en [collections/CURS-000188-Keycloak.postman_collection.json](collections/CURS-000188-Keycloak.postman_collection.json)

# Guía de Keycloak

## Conceptos Clave de Keycloak

1. **Realm (Real):**
   - Un realm en Keycloak es una unidad de gestión que agrupa usuarios, credenciales, roles y grupos. Cada realm es independiente y aísla los datos y configuraciones de seguridad de los demás. Un realm puede ser considerado como un dominio de seguridad que tiene sus propias políticas de autenticación y autorización.

2. **Client (Cliente):**
   - Un cliente en Keycloak representa una aplicación que interactúa con el servidor de Keycloak para la autenticación y autorización. Los clientes pueden ser aplicaciones web, aplicaciones móviles, servicios backend, etc. Cada cliente tiene configuraciones específicas que determinan cómo interactúa con Keycloak.

3. **Role (Rol):**
   - Los roles en Keycloak son asignaciones que definen permisos y accesos específicos dentro de un realm. Los roles pueden ser asignados a usuarios directamente o a través de grupos. Los roles pueden ser realm roles (aplicables a todo el realm) o client roles (específicos para un cliente).

4. **User (Usuario):**
   - Los usuarios en Keycloak son entidades que representan a las personas que utilizan las aplicaciones protegidas por Keycloak. Los usuarios tienen credenciales para autenticarse y pueden tener roles asignados para definir sus permisos y accesos.

## Arquitectura de Keycloak – Principales Componentes

- **Servidor Keycloak:**
  - Es el componente principal que proporciona servicios de autenticación y autorización. Gestiona realms, usuarios, clientes, roles, y políticas.

- **Base de Datos:**
  - Almacena toda la información gestionada por Keycloak, incluyendo usuarios, credenciales, configuraciones de clientes, roles, y tokens.

- **Adaptadores de Cliente:**
  - Son bibliotecas que se integran con aplicaciones para facilitar la comunicación con el servidor de Keycloak. Estos adaptadores están disponibles para diversos lenguajes y plataformas.

- **Consola de Administración:**
  - Una interfaz web que permite a los administradores gestionar realms, usuarios, clientes, roles, y políticas.

- **API REST:**
  - Keycloak proporciona una API REST completa para realizar la mayoría de las operaciones disponibles en la consola de administración.

## Creación y Configuración de Clients (Confidential)

1. **Creación de un Cliente:**
   - En la consola de administración, navega a la sección de 'Clients' y crea un nuevo cliente. Proporciona un nombre para el cliente y selecciona el tipo de acceso (por ejemplo, 'confidential' para aplicaciones backend).

2. **Configuración de un Cliente:**
   - Configura las opciones de autenticación y autorización, incluyendo la URL de redirección, el flujo de acceso (authorization code flow, implicit flow, etc.), y los roles específicos del cliente.
   - Define las credenciales del cliente (client ID y client secret) para que el cliente pueda autenticarse contra Keycloak.

## Configurando JWT Token (Standard Flow)

1. **Configuración del Flujo de Autorización:**
   - Selecciona el 'Standard Flow Enabled' en la configuración del cliente.
   - Configura las URLs de autorización y token en la aplicación cliente.

2. **Generación de JWT Tokens:**
   - Al autenticarse, el cliente recibe un JWT (JSON Web Token) que contiene información sobre el usuario autenticado y sus permisos.
   - El token incluye una firma digital que permite verificar su autenticidad y asegurar que no ha sido modificado.

## Configuración de Recursos y Scopes

1. **Recursos (Resources):**
   - Representan las entidades protegidas por Keycloak, como endpoints de API o servicios. Cada recurso puede tener una o más scopes que definen los niveles de acceso permitidos.

2. **Scopes:**
   - Son permisos específicos asignados a recursos. Por ejemplo, un recurso 'document' podría tener scopes como 'view', 'edit', y 'delete'.

## Configuración de Policies (Client Based Policy) y Permissions

1. **Policies (Políticas):**
   - Define las reglas de acceso que determinan si una solicitud debe ser permitida o denegada. Las políticas pueden basarse en roles, grupos, atributos de usuario, y otros criterios.
   - Las client based policies se basan en las características del cliente, como sus roles o atributos.

2. **Permissions (Permisos):**
   - Combina recursos, scopes, y políticas para determinar los accesos permitidos. Un permiso define qué acciones pueden realizarse sobre un recurso dado las políticas aplicables.

## Roadmap para Aprender Keycloak

1. **Conceptos Básicos:**
   - Familiarízate con los conceptos clave de Keycloak: realm, client, role y user.
   - Explora la consola de administración y crea tus primeros realms y usuarios.

2. **Configuración de Clientes:**
   - Aprende a crear y configurar clientes, tanto públicos como confidenciales.
   - Configura el flujo de autorización y autentica aplicaciones con JWT tokens.

3. **Gestión de Roles y Políticas:**
   - Define y asigna roles a usuarios y clientes.
   - Crea políticas de acceso y configura permisos detallados para recursos y scopes.

4. **Integración de Aplicaciones:**
   - Integra Keycloak con aplicaciones web y servicios backend utilizando adaptadores de cliente.
   - Configura single sign-on (SSO) y manage session.

5. **Seguridad Avanzada:**
   - Configura autenticación multifactor (MFA) y políticas de seguridad avanzadas.
   - Aprende sobre la gestión de identidades y federación de identidades.

6. **Despliegue y Escalabilidad:**
   - Configura y despliega Keycloak en entornos de producción.
   - Optimiza la configuración para escalar y gestionar grandes cantidades de usuarios y clientes.

Esta hoja de ruta te ayudará a profundizar en cada aspecto de Keycloak y a integrarlo de manera efectiva en tus aplicaciones y servicios.
