# Guía de Keycloak

## Conceptos Core de Keycloak

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

- **Console de Administración:**
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

## Configurando Resources & Scopes

1. **Recursos (Resources):**
   - Representan las entidades protegidas por Keycloak, como endpoints de API o servicios. Cada recurso puede tener una o más scopes que definen los niveles de acceso permitidos.

2. **Scopes:**
   - Son permisos específicos asignados a recursos. Por ejemplo, un recurso 'document' podría tener scopes como 'view', 'edit', y 'delete'.

## Configurando Policies (Client Based Policy) & Permissions

1. **Policies (Políticas):**
   - Define las reglas de acceso que determinan si una solicitud debe ser permitida o denegada. Las políticas pueden basarse en roles, grupos, atributos de usuario, y otros criterios.
   - Las client based policies se basan en las características del cliente, como sus roles o atributos.

2. **Permissions (Permisos):**
   - Combina recursos, scopes, y políticas para determinar los accesos permitidos. Un permiso define qué acciones pueden realizarse sobre un recurso dado las políticas aplicables.

## Roadmap para Aprender Keycloak

1. **Conceptos Básicos:**
   - Familiarízate con los conceptos core de Keycloak: realm, client, role, y user.
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
   - Configura multifactor authentication (MFA) y políticas de seguridad avanzadas.
   - Aprende sobre la gestión de identidades y federación de identidades.

6. **Despliegue y Escalabilidad:**
   - Configura y despliega Keycloak en entornos de producción.
   - Optimiza la configuración para escalar y gestionar grandes cantidades de usuarios y clientes.

Este roadmap te ayudará a profundizar en cada aspecto de Keycloak y a integrarlo de manera efectiva en tus aplicaciones y servicios.
