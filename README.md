## Project Milk Optimization

<div align="center">
  <img src="https://github.com/yt-neto/milkapi/blob/main/docs/img/img1.png" width="250" height="250" />
  <img src="https://github.com/yt-neto/milkapi/blob/main/docs/img/img2.png" width="250" height="250" />
</div>

<h4 align="center">This a project to control milk collection in the region </h4>

<p align="center">
  [Project's badges]
</p>

<p align="center">
    <a href="#Technologies_Used">Technologies Used</a> •
    <a href="#Api_resources">Api resources</a> •
    <a href="#Folder_Architecture">Folder Architecture BackEnd</a> •
    <a href="#Running_Application">Running application</a> •
    <a href="#About_the_Author">About the Author</a> •
    <a href="https://github.com/neto-works/estocaria_ponto_net/blob/main/LICENSE">Licensing</a>
</p>

## Technologies_Used

- The following technologies were used in this project:
    - Java as a programming language.
    - springboot application.
    - And the docker containerization tool.

## Designer_basis_for_application

- [figma](https://www.figma.com/design/ooRHKTooPN2Bmo1K2cyW4P/Expo-App-Icon-%26-Splash-(Community)?node-id=0-1&t=gWTvCbz6Vk0Pkxlx-1)

## Folder_Architecture
```
api.src
  ├  └─  main.java.ofc.api
  ├                    └──────────  Config : all configs, springSecurity, Swagger ...
  ├                    └──────────  Controllers : all controllers, interfaces, Dtos ...
  ├                    └──────────  Entities  : models for aplication.
  ├                    └──────────  Repositories : Repositories, JPa base , generics ...
  ├                    └──────────  Services : all Services, generics and interfaces ...
  ├  
  └───────  main.resources   
  ├                    └──────────  aplication.properties
  ├  
  └───────  test
  ├──  docher-compose.yml
  └──  pom.xml : all dependecies
```

## Bank_Model

<img src="https://github.com/yt-neto/milkapi/blob/main/docs/diagramas/banco.png" />

## Api_resources v1

### Auth 

- open to everyone
 ```http
  POST /api/auth/login
 ```
 ```http
  POST /api/auth/register
 ```
 ```http
  POST /api/auth/refresh-token
 ```
- Authorize Policy _"QuemPuderGerenciar"(Gerente)_

 ```http
  POST /api/auth/revoke/{email}
 ```
   ```http
  POST api/auth/createRole
 ```
   ```http
  POST /api/auth/addUserToRole
 ```

### Vendedor

- Authorize Policy _"QuemPuderVender"(vendedor)_

 ```http
  PUT /api/vendedor/{id}
 ```
- Authorize Policy _"QuemPuderGerenciar"(Gerente)_

 ```http
  GET /api/vendedores/{id}
 ```
 ```http
  DELETE /api/vendedores/{id}
 ```
 ```http
  GET /api/vendedores
 ```

### Gerente

- Authorize Policy _"QuemPuderGerenciar"(Gerente)_

 ```http
  POST /api/Gerente/{id}/vendedor/{id}/Estoque/{id}
 ```
 ```http
  DELETE /api/Gerente/{id}/vendedor/{id}/Estoque/{id}
 ```

### Leiteira

- Authorize Policy _"QuemPuderVender"(vendedor) OR "QuemPuderGerenciar"(Gerente)_

 ```http
  GET /api/leiteira
 ```
 ```http
  GET /api/produtos/{id}
 ```
- Authorize Policy _"QuemPuderVender"(vendedor) OR "QuemPuderGerenciar"(Gerente)_

 ```http
  PUT /api/leiteira
 ```
 ```http
  DELETE /api/produtos/{id}
 ```
 ```http
  POST /api/Leiteira
 ```

### Categorias

- Authorize Policy _"QuemPuderGerenciar"(Gerente)_

 ```http
  POST /api/Categorias
 ```

### Produtos

- Authorize Policy _"QuemPuderVender"(vendedor) OR "QuemPuderGerenciar"(Gerente)_

 ```http
  POST /api/produtos
 ```
 ```http
  GET /api/produtos
 ```
 ```http
  GET /api/produtos/{id}
 ```
 ```http
  PUT /api/produtos/{id}
 ```
 ```http
  DELETE /api/produtos/{id}
 ```
 ```http
  POST /api/produtos/{id}/Categorias/{id}
 ```

### Recolhimento

- Authorize Policy _"QuemPuderVender"(vendedor)_
 ```http
  POST /api/recolhimentos
 ```
- Authorize Policy _"QuemPuderGerenciar"(Gerente)_
 ```http
  GET /api/recolhimentos/periodo
 ```

## Prerequisites

- SDKMAN for manager version jdk
- JDK 17
- Docker and Docke-compose plugin

## Running_Application

- create the bank:
 ```
    cd api && docker compose up
 ```

- install dependencies:
 ```
    mvn install

 ```

- to execute:
```
  java -jar target/nome_projeto-1.0-SNAPSHOT.jar
```

- http://localhost:8080/swagger-ui/index.html?urls.primaryName=public

## Test the application using postman

- open your postman desktop
- click import
- import from z_Postman_Endpoints folder

> [!IMPORTANT]
> When opening the endpoints, check the "base_url" addressing variable if it is correct.

> [!WARNING]
> When making requests, pay attention to the tokens and the app's authorization policies. Errors may also be found as it is still under development


## About_the_Author
- Clodoaldo Neto :call_me_hand:
