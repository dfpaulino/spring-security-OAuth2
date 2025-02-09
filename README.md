# spring-security-OAuth2
use keycloak as authentication server.
This is a Resource server that read the supplied JWT. This JWT token is self contained, ie, contains all required info
to process request (roles, principal user name etc)

make use of bellow to start with keycloak

`
version: "3"
services:
  keycloak:
    image: quay.io/keycloak/keycloak:26.0.7
    command: start-dev
    environment:
      #KC_DB: postgres
      #KC_DB_URL_HOST: postgres
      #KC_DB_URL_DATABASE: keycloak
      #KC_DB_USERNAME: keycloak
      #KC_DB_PASSWORD: password
      #KC_DB_SCHEMA: public
      KEYCLOAK_ADMIN: admin
      KEYCLOAK_ADMIN_PASSWORD: admin
      KC_BOOTSTRAP_ADMIN_USERNAME: admin
      KC_BOOTSTRAP_ADMIN_PASSWORD: admin
    ports:
      - "8180:8080"
      - "9000:9000"
        #    depends_on:
        #      postgres:
        #condition: service_healthy

`
