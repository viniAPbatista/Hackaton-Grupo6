# Sistema para gestão de tarefas.

O sistema, desafio proposto no Hackathon do programa Devs do Agi foi apresentado como um problema de gestão de tarefas com o objetivo de organizar 
objetivo de organizar e otimizar o acompanhamento das atividades dentro de uma equipe ou organização. 

Em suma, o sistema trabalha com 4 entidades relacionadas.

User - (Divididos em ADMINISTRADOR, GERENTE e FUNCIONARIO) 
task - Aonde é elaborado as tarefas e também de onde é atribuido 
Comment - Tabela que salva os comentários e é indexada as Tasks
Team - Equipe determinada pelo GERENTE 

O Administrador possui poderes para Criar usuarios Gerente e Funcionários.
O Gerente pode criar funcionários, atribuir tarefas ao mesmo, aloca-lo a uma equipe e ter acesso ao dashboard geral com todas as tarefas.
Já o Funcionário, pode apenas concluir suas tarefas, abandona-las e  nao tem acesso as tasks gerais, apenas as que são atribuidas a ele.

DESAFIO SURPRESA
Durante o Hackathon foi exigido a resolução de um problema específico:
Se alguém sai de férias, suas tarefas devem ser automaticamente atribuídas a outro funcionário e retornar para o antigo responsável quando esse retornar das férias.

## 🚀 Começando

Essas instruções permitirão que você obtenha uma cópia do projeto em operação na sua máquina local para fins de desenvolvimento e teste.

clone nosso repositório https://github.com/viniAPbatista/Hackaton-Grupo6.git no CMD de uma pasta local

### 📋 Como Rodar

Rodar na pasta local com o comando ./Gradlew build.
Acessar o site do Postman para poder fazer as requisições para teste.


### Tabela de Requisições via Postman











## 🛠️ Construído com

Mencione as ferramentas que você usou para criar seu projeto

* Java 21
* SpringBoot Framework
* Banco de Dados MySQL
* Dbeaver
* Aiven io




## ✒️ Autores

* **Davi Miguel** - https://github.com/Davi-Miguel-Rocha
* **Nelson Damico Junior** - https://github.com/neodamico
* **Osmair Coelho** - https://github.com/osmaircoelho
* **Ryan Dias** - https://github.com/ryxvdz
* **Thiago Reis** - https://github.com/Treis400
* **Vinicius Batista** https://github.com/viniAPbatista

 

