# Facial Detection & Recognition

API REST para detecção e reconhecimento facial em tempo real, desenvolvida com Java, Spring Boot e JavaCV.

O sistema permite cadastrar pessoas, capturar seus rostos via webcam, treinar um modelo de reconhecimento e identificar pessoas em tempo real.

 Tecnologias

Java 21
Spring Boot 
JavaCV
PostgreSQL
Spring Data JPA
Maven

Configuração

Crie um banco de dados no PostgreSQL:
Configure o src/main/resources/application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/facial_detection
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update

 Como rodar

git clone https://github.com/felipe-brito1/facial-detection

cd facial-detection
mvn spring-boot:run


Como usar

1. Cadastre uma pessoa:

POST /persons/
{ "name": "SeuNome" }


2. Capture as fotos pela webcam (fique na frente da câmera):

POST /persons/{id}/capture


3. Treine o modelo:

POST /recognition/train

4. Reconheça o rosto:

POST /recognition/recognize


5. Para visualizar a detecção em tempo real:

POST /webcam/start

POST /webcam/stop
