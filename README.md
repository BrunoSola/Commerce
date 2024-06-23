[JAVA_BADGE]:https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[SPRING_BADGE]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white

<div style="text-align:center">
    <h1>E-Commerce 💻</h1>

[![NPM](https://img.shields.io/npm/l/react)](https://github.com/BrunoSola/Commerce/blob/main/LICENSE)

![spring][SPRING_BADGE]
![java][JAVA_BADGE]
</div>

<div style="text-align:center">
    <a href="#started">Introdução</a> • 
    <a href="#dependencias">Dependências</a> •
    <a href="#routes">API Endpoints</a> •
    <a href="#uml">Modelo conceitual</a>
</div>

<div style="text-align:center">
    <b>E-Commerce é uma aplicação Back-End construído durante a formação Java Spring, ministrado pela <a href="https://devsuperior.com.br">DevSuperior</a>.</b><br>
    <b>A aplicação consiste em um sistema de comércio eletrônico onde é possível realizar funcionalidades tais como manter um cadastro
        de produtos, pesquisar produtos para fins de catálogo e carrinho de compras, registrar e
        recuperar pedidos, consultas ao banco de dados, login e controle de acesso.
    </b>
</div>

<h2 id="started">🚀 Introdução</h2>

### Pré-requisitos

Antes de começar, certifique-se de atender aos seguintes requisitos:

1. **Java Development Kit (JDK) 21**: Certifique-se de ter o JDK 21 instalado em sua máquina. Você pode baixá-lo no site oficial da [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html).

2. **Apache Maven**: Este projeto usa Maven para gerenciamento de dependências e automação de builds. Você pode baixar o Maven no site oficial do [Maven](https://maven.apache.org/download.cgi).

### Início
Siga os passos abaixo para configurar o ambiente de desenvolvimento:

1. Clone o repositório:
   ```sh
   git clone https://github.com/BrunoSola/Commerce.git
   ```
2. Acesse o repositório:
   ```sh
   cd Commerce
   ```
3. Execute o projeto:
   ```sh
   mvn spring-boot:run
   ```

<h2 id="dependencias">📚 Dependências</h2>

* As principais dependências usadas neste projeto são:

  * Spring Boot Starter Data JPA
  * Spring Boot Starter Web
  * H2 Database
  * Spring Boot Starter Test
  * Spring Boot Starter Validation
  * Spring Boot Starter Security
  * Spring Security Test
  * Spring Security OAuth2 Authorization Server
  * Spring Boot Starter OAuth2 Resource Server

<h2 id="routes">📍 API Endpoints</h2>

* Recomenda-se o uso da ferramenta Postman para testar os endpoints.
   * Note: É possível utilizá-lo [Postman](https://www.postman.com/) via Web ou instalando-o em sua máquina. Você pode baixar o Postman no site oficial do [Postman](https://www.postman.com/downloads).
   * Note: No repositório, contém dois arquivos com a collection e environment preparados para testar os endpoints. Você so precisa importa-los.


## Endpoints

| Método                                 | Endereço       | Descrição                    | Permissão                 |
|----------------------------------------|----------------|------------------------------|---------------------------|
| <span style="color:green">GET</span>   | /categories    | Retorna todas as categorias. | TODOS                     |
| <span style="color:green">GET</span>   | /orders/{id}   | Retorna pedido por id.       | USUÁRIO DO PEDIDO/ADMIN   |
| <span style="color:green">GET</span>   | /products      | Retorna todos os produtos.   | TODOS                     |
| <span style="color:green">GET</span>   | /products/{id} | Retorna produto por id.      | TODOS                     |
| <span style="color:green">GET</span>   | /user/me       | Retorna usuário logado.      | USUÁRIO AUTENTICADO       |
| <span style="color:yellow">POST</span> | /oauth2/token  | Realiza o login.             | USUÁRIO CADASTRADO        |
| <span style="color:yellow">POST</span> | /orders        | Registrar um novo pedido.    | USUÁRIO AUTENTICADO/ADMIN |
| <span style="color:yellow">POST</span> | /products      | Registrar um novo produto.   | ADMIN                     |
| PUT                                    | /products/{id} | Atualiza um produto por id.  | ADMIN                     |
| <span style="color:red">DELETE</span>  | /products/{id} | Remove um produto por id.    | ADMIN                     |


### <span style="color:green">GET</span> /categories
**RESPONSE**
```json
[
   {
      "id": 1,
      "name": "Livros"
   },
   {
      "id": 2,
      "name": "Eletrônicos"
   },
   {
      "id": 3,
      "name": "Computadores"
   }
]
```

### <span style="color:green">GET</span> /orders/1
   * Note: Usuário precisa ser autenticado.

**RESPONSE**
```json
{
   "id": 1,
   "moment": "2022-07-25T13:00:00Z",
   "status": "PAID",
   "client": {
      "id": 1,
      "name": "Maria Brown"
   },
   "payment": {
      "id": 1,
      "moment": "2022-07-25T15:00:00Z"
   },
   "items": [
      {
         "productId": 1,
         "name": "The Lord of the Rings",
         "price": 90.5,
         "quantity": 2,
         "imgUrl": "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg",
         "subTotal": 181.0
      },
      {
         "productId": 3,
         "name": "Macbook Pro",
         "price": 1250.0,
         "quantity": 1,
         "imgUrl": "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg",
         "subTotal": 1250.0
      }
   ],
   "total": 1431.0
}
```

### <span style="color:green">GET</span> /products
**RESPONSE**
```json
{
   "totalElements": 25,
   "totalPages": 2,
   "size": 20,
   "content": [
      {
         "id": 1,
         "name": "The Lord of the Rings",
         "price": 90.5,
         "imgUrl": "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg"
      },
      {
         "id": 2,
         "name": "Smart TV",
         "price": 2190.0,
         "imgUrl": "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/2-big.jpg"
      } ...
   ]
}
```

### <span style="color:yellow">POST</span> /oauth2/token

**RESPONSE**
```json
{
   "access_token": "eyJraWQiOiIxZTMwZGNhOS1hNGVhLTQ0YzYtOThhNC1mMmQyZDY4MzhmNzgiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJteWNsaWVudGlkIiwiYXVkIjoibXljbGllbnRpZCIsIm5iZiI6MTcxOTE2NjgxNywiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwIiwiZXhwIjoxNzE5MjUzMjE3LCJpYXQiOjE3MTkxNjY4MTcsImp0aSI6IjlhNzM2ZDE3LTA5NGEtNGNiZC05YTFjLTY0MjI0MmNhM2ZkNCIsImF1dGhvcml0aWVzIjpbIlJPTEVfQ0xJRU5UIl0sInVzZXJuYW1lIjoibWFyaWFAZ21haWwuY29tIn0.ahAVOgczWSyOR3GzrLh9pptMRyru0YZgbflT3VFIyYNe-qADZK5JEds786m_RhVApS4mS8WqxEySEZJjdVF5l1VUJDJHtG8QWObGzzN--Nvghobi8pVedLAVKH5dDH38EL6OeehOamLHoGt3u595H8YRi0oX3vYEifsnpDZSLsB6J-suXu6nQOf9RI0cDRr3-STLF0xYCRYPMC4HXbDcaP0Ds-4_jjiNtl-pZB-63USbFSnK0zhNmjYmtBubaNvhA878shF_v1pvptTiy9S70yoXPENBPHNeKntxVq51BChKvlVi-08rFJaQJ-haPT9NP_F81-8gVaf5ZfVFbiJaFg",
   "token_type": "Bearer",
   "expires_in": 86400
}
```

### <span style="color:yellow">POST</span> /orders

**REQUEST**
```json
{
   "items": [
      {
         "productId": 1,
         "quantity": 2
      },
      {
         "productId": 5,
         "quantity": 1
      }
   ]
}
```

### <span style="color:yellow">POST</span> /products

**REQUEST**
```json
{
   "name": "Meu produto",
   "description": "Lorem ipsum, dolor sit amet consectetur adipisicing elit. Qui ad, adipisci illum ipsam velit et odit eaque reprehenderit ex maxime delectus dolore labore, quisquam quae tempora natus esse aliquam veniam doloremque quam minima culpa alias maiores commodi. Perferendis enim",
   "imgUrl": "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg",
   "price": 50.0,
   "categories": [
      {
         "id": 2
      },
      {
         "id": 3
      }
   ]
}
```

### PUT /products/1

**REQUEST**
```json
{
   "name": "Produto atualizado",
   "description": "Lorem ipsum, dolor sit amet consectetur adipisicing elit. Qui ad, adipisci illum ipsam velit et odit eaque reprehenderit ex maxime delectus dolore labore, quisquam quae tempora natus esse aliquam veniam doloremque quam minima culpa alias maiores commodi. Perferendis enim",
   "imgUrl": "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg",
   "price": 200.0,
   "categories": [
      {
         "id": 2
      },
      {
         "id": 3
      }
   ]
}
```

<h2 id="uml">Modelo conceitual</h2>
![Modelo conceitual](assents/commerce_UML.png)