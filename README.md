# springboot-swf
Example project for springboot with spring web flow - jsf 2- primefaces 6


### comandos maven
* RUN: spring-boot:run -Duser.language=es -Duser.region=CL -Dfile.encoding=UTF-8

* Limpiar BD: flyway:clean -Dplugin.flyway.url=jdbc:postgresql://localhost:5432/bd -Dplugin.flyway.user=postgres -Dplugin.flyway.password=postgres

* Ejecutar Parches BD: flyway:migrate -Dplugin.flyway.url=jdbc:postgresql://localhost:5432/bd -Dplugin.flyway.user=postgres -Dplugin.flyway.password=postgres
