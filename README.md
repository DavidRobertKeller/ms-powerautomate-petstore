# Ms Power Automate PetStore

Thank's to Eric Vernié for the help!

The goal of this example is to demonstrate how to add microsoft extension to provide an API well formed to interact with powerautomate custom connector, and be able to publish Actions and Triggers (webhook).


This example use SpringBoot and SpringFox because only Swagger2 is allowed in MS Power Automate API for now.
Authentication is first based on an API_KEY then a OAuth2 token can be provided (optional).



# Bibliography
* https://github.com/EricVernie/CustomConnector
* https://github.com/Microsoft/PowerPlatformConnectors/tree/master/custom-connectors
* https://github.com/springfox/springfox/issues/3746
* https://docs.microsoft.com/en-us/connectors/custom-connectors/define-openapi-definition
* https://docs.microsoft.com/en-us/connectors/custom-connectors/
* https://docs.microsoft.com/en-us/connectors/custom-connectors/submit-certification
* https://aka.ms/powerpitch
* https://www.baeldung.com/spring-boot-swagger-jwt
* https://stackoverflow.com/questions/41918845/keycloak-integration-in-swagger
* https://stackoverflow.com/questions/36834020/configure-security-schemas-and-contexts-in-springfox-and-spring-mvc
* https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
* https://github.com/gregwhitaker/springboot-apikey-example

