# Challenge III - MS-Notification - PB Springboot Dez 2023
> Status: Entregue
-------------------------------------------------------------------------------------------------------


O microsserviço Notification consiste em armazenar no banco de dados e receber mensagem vinda do microsserviço User.

### Colaborador

<table>
  <tr>
    <td>E-mail</td>
    <td>GitHub</td>
  </tr>
   <tr>
    <td>leandro.paupitz.pb@compasso.com.br</td>
    <td>lepaupitz</td>
 </table>

-------------------------------------------------------------------------------------------------------

### Tecnologias Utilizadas
<table>
  <tr>
    <td>Java</td>
    <td>Spring</td>
    <td>MySql</td>
  </tr>
  <tr>
    <td>17</td>
    <td>3.2</td>
    <td>8.0</td>
  </tr>
</table>

-------------------------------------------------------------------------------------------------------

### Setup
1. Clone o repositório
```
git clone https://github.com/lepaupitz/Challenge3-MS-Notification.git
```
2. Configure o banco de dados no arquivo `application.yml`

3. Execute a aplicação
```
mvn spring-boot:run
```
A aplicação deverá estar em execução e acessível em http://localhost:8081/

-------------------------------------------------------------------------------------------------------
### Aplication

1. Para o microsserviço funcionar, é necessário que envie alguma requisiçao de CREATE, UPDATE, LOGIN ou UPDATE_PASSWORD, no microsserviço User.

2. Ao fazer uma das requisições citadas a cima, o microsserviço Notification irá ser acionado e salvará o evento no banco de dados, da seguinte maneira:
  ```
 {
  "id": 1,
  "email": "teste@gmail.com",
  "event": CREATE,
  "date": 2024-03-01 18:50:23.362000
}
``` 
-------------------------------------------------------------------------------------------------------
### Dificuldades

1. O que mais eu tive dúvidas foi em como utilizar o rabitMQ, mas pós tirar uma dúvida com um colega no qual me ajudou, eu consegui fazer.

2. Não consegui utilizar o banco de dados MongDB, então utilizei o próprio MySQL.

3. Os testes eu tive algumas dificuldades e também não consegui realizar todos os testes, necessários para cobrir os 70% dos requisitos, alguns testes estão dando erro ainda.
