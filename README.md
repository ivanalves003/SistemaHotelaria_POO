# Sistema Hotelaria (POO)

Sistema de gestão hoteleira desenvolvido em Java com interface gráfica (Swing), como trabalho final da disciplina de Programação Orientada a Objetos, na UFG, 2026/1.

## Pré-requisitos

- JDK 24 (ou compatível) instalado. Verifique com:
  ```bash
  java -version
  javac -version
  ```

## Como rodar o projeto

### Opção 1: Via terminal (linha de comando)

1. Compile as classes a partir da raiz do projeto:
   ```bash
   javac -d out src/*.java
   ```

2. Execute a aplicação:
   ```bash
   java -cp out TelaSistema
   ```

   Isso abrirá a interface gráfica do "Sistema Hoteleiro - Gestão".

### Opção 2: Via IntelliJ IDEA

1. Abra a pasta do projeto no IntelliJ IDEA (o projeto já possui os arquivos `.iml` e `.idea` de configuração).
2. Aguarde o IntelliJ indexar o projeto.
3. Localize a classe `TelaSistema.java` em `src/`.
4. Clique com o botão direito no arquivo e selecione **Run 'TelaSistema.main()'**.

## Estrutura do projeto

- `src/` — código-fonte Java (classes de domínio, exceções, persistência e interface gráfica).
- `out/` — arquivos `.class` compilados.
- `dados_hotel.dat` — arquivo de persistência de dados usado pelo `GerenciadorArquivo` para salvar/carregar o estado do sistema entre execuções.

## Classes principais do sistema

- `TelaSistema` — ponto de entrada da aplicação (contém o `main`) e interface gráfica.
- `DadosHotel` — armazena os dados do sistema (clientes, quartos, reservas, etc.).
- `GerenciadorArquivo` — responsável por salvar e carregar os dados em `dados_hotel.dat`.
- `Cliente`, `Dependente`, `Pessoa` — entidades de pessoas do sistema.
- `Quarto`, `TipoQuarto`, `Reserva`, `Fatura`, `MiniBar`, `Produto` — entidades relacionadas à operação do hotel.
- `HotelException` — exceção customizada do domínio.
- `GeradorDeDados` — utilitário para gerar dados de teste/exemplo.
- `TesteFuncional` — classe com testes funcionais do sistema.

## Observações

- Ao rodar `TesteFuncional` ou `GeradorDeDados`, também é possível executá-los diretamente via `java -cp out <NomeDaClasse>` para testes ou geração de dados de exemplo, sem abrir a interface gráfica.
- Os dados são persistidos em `dados_hotel.dat` na raiz do projeto, portanto executar o sistema a partir da raiz garante que os dados sejam salvos/carregados corretamente.
