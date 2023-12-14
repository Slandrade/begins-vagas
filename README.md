# <h1 align="center"> Teste Begins Vagas </h1>
### <h2>Sistema de vagas de emprego</h2>

Esta é uma API Rest com objetivo de cadastrar vagas e permitir que os candidatos se inscrevam nas mesmas.<br/>

Esta API foi desenvolvida utilizando Java 21 e o framework Spring Boot.<br/>
Os testes unitários foram feitos utilizando JUnit 5 e Mockito.<br/>
Foi utilizado o Banco de Dados PostegreSQL para armazenamento dos dados.<br/>
Para trazer mais qualidade nos testes unitários, foi utilizado também o PIT Mutation Testing, para gerar cenários de mutação.<br/>

### <h2>Requisitos iniciais</h2>

* CRUD de vagas:
    - Criar, editar, excluir e listar vagas.
    - A vaga pode ser CLT, Pessoa Jurídica ou Freelancer.
* CRUD de candidatos:
    - Criar, editar, excluir e listar candidatos.
 
### <h2>Requisitos bônus</h2>

- API Rest JSON para todos os CRUDS listados acima.
  - implementado 
- Permitir deleção em massa de itens nos CRUDs.
  - implementado
- Permitir que o usuário mude o número de itens por página.
  - implementado
- Implementar autenticação de usuário na aplicação.
  - em desenvolvimento

### <h2>Executando a solução</h2>

Para executar localmente:
1. Verifique se está com o ambiente configurado para o Maven e Java 21;
2. Clone o repositório na sua máquina;
3. Execute o comando mvn clean install;
4. Na pasta _/target/_ execute o comando **java -jar vagas-0.0.1-SNAPSHOT.jar**.

Após realizar estes passos, a aplicação estará rodando e será possível testá-la localmente utilizando sua plataforma de envio de requisição para API desejada.
Uma collection com todas as requisições de teste pode ser encontrada [aqui](https://github.com/Slandrade/begins-vagas/blob/master/Begins%20Vagas.collection.json).
