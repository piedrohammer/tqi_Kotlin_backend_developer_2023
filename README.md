<h1> [TQI] Bootcamp Kotlin BE Developer :mortar_board:</h1>
<h3> tqi_Kotlin_backend_developer_2023 </h3>

<h3>Após a conclusão do [TQI] Bootcamp Kotlin BE Developer a TQI propôs um desafio!<h3>
<h3>:scroll: Desafio </h3>
<p>Uma mercearia do Bairro Bom Descanso chamada JuMarket necessita de uma solução para venda de auto-atendimento, para tanto necessitamos desenvolver as seguintes funcionalidades:</p>

<h3>:clipboard:Cadastro de Categorias, um cadastro categorias de produtos apenas contento o nome da categoria:</h3>
 
  - ID de Cadastro;
  - NomeCategoria;

<h3>🏷️Cadastro de Produtos, um cadastro contendo os produtos do mercado, este cadastro poderá conter os seguintes campos:</h3>

  - ID de Produto;
  - Nome do Produto;
  - Unidade de Medida;
  - Preço Unitário;

<h3>🛒Carrinho, o Carrinho será a funcionalidade na qual o usuário selecionou os produtos que deseja adquirir, neste caso conter os seguintes dados:</h3>

  - ID de Carrinho;
  - Produtos;
  - Quantidade de Itens;
  - Preço da Venda;

<h3>:credit_card:Finalização da Venda, ao finalizar a venda deverá ser informado o valor total e a forma de pagamento escolhida, as opções são: Cartão de Credito/Débito, Dinheiro e Pix</h3>

  - Preço Total;
  - Forma de Pagamento;

<h3>:warning::rotating_light:Restrições: </h3>
<p>A implementação deve utilizar a linguagem banckend: Kotlin com o Springboot, O banco de dados utilizado tem que subir via Docker e utilizar um gerênciador de migration (Flyway, Liquibase ou outro). E testar pelo menos a camada de serviços com Junit.</p>

<h3>⚙️ Configuração do Projeto </h3>

 - Intellij

 - Postman

 - Spring Initializr:
   - Project Gradle Kotlin
   - Language Kotlin
   - Springboot 3.1.1
   - Packaging Jar
   - Java 17

 - Dependências:
   - Spring Web
   - Spring Data JPA
   - H2 DataBase
   - MySQL Driver 
   - Flyway Migration
   - Spring Boot DevTools
   - Validation
   - JUnit
  
   ![image](https://github.com/piedrohammer/tqi_Kotlin_backend_developer_2023/assets/89158456/d0b26e58-67a8-4638-ab28-825112d29012)

     
 - Docker:
   - Está configurado no arquivo docker-compose.yml
   - Usar o comando docker-compose up para subir pro docker
  
     
   ![image](https://github.com/piedrohammer/tqi_Kotlin_backend_developer_2023/assets/89158456/5b195ca5-b94f-4b3f-88c7-df8fe8cc73e5)
   
  
<h4>:rotating_light:Lembrando que toda a configuração de dependências está no arquivo build.gradle.kts</h4>
<h4>:rotating_light:E o package do Postman está no arquivo Ju Market.postman_collection.json</h4>
     
