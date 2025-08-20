#  Projeto Movie List
## Objetivo: Importar um aquivo .csv extrai as informações transforma e carrega no banco de dados.

### Provendo uma API REST para consultar o intervalo máximo e mínimo entre os produtores vencedores.

### Endpoint: Obter Intervalos de Prêmios dos Produtores

**GET** `/movies/winners-interval`

Retorna os produtores com os menores e maiores intervalos entre prêmios.

**Exemplo de resposta:**

```json
{
  "min": [
    {
      "producer": "Joel Silver",
      "interval": 1,
      "previousWin": 1990,
      "followingWin": 1991
    },
    {
      "producer": "Bo Derek",
      "interval": 6,
      "previousWin": 1984,
      "followingWin": 1990
    }
  ],
  "max": [
    {
      "producer": "Matthew Vaughn",
      "interval": 13,
      "previousWin": 2002,
      "followingWin": 2015
    },
    {
      "producer": "Buzz Feitshans",
      "interval": 9,
      "previousWin": 1985,
      "followingWin": 1994
    }
  ]
}

```

## Como Executar o Projeto
O projeto foi desenvolvido na versão 22 do java: https://download.oracle.com/java/22/archive/jdk-22.0.2_windows-x64_bin.zip

Descompact o arquivo e aponte a variável de ambiete JAVA_HOME para o caminho do pacote:. Ex: C:\Java\jdk22.0.2\bin 

1. Antes de rodar o projeto certifique-se que o seu arquivo encontra-se no diretório import dentro da pasta raiz do projeto ou do executável .jar.
  ```shell

  /import/<seu_arquivo>.csv
  /movielist-0.0.1-SNAPSHOT.jar
  ```
### Layout do arquivo de importação

O arquivo deve estar no formato CSV, separado por ponto e vírgula (`;`), com o seguinte cabeçalho e colunas:

| year | title                | studios                | producers                                   | winner |
|------|----------------------|------------------------|----------------------------------------------|--------|
| 2006 | Basic Instinct 2     | MGM, C2 Pictures       | Mario Kassar, Joel B. Michaels and Andrew G. Vajna | yes    |
| 2006 | BloodRayne           | Romar Entertainment    | Uwe Boll, Dan Clarke and Wolfgang Herrold    |        |

- **producers**: Caso haja mais de um produtor, separe os nomes por vírgula (`,`) e utilize `and` antes do último nome.


2. Para rodar o projeto, execute os seguintes comandos conforme sua preferência ou ambiente:   

```bash

mvn clean package
mvn spring-boot:run
```
ou, se preferir, execute o jar gerado:

```

mvn clean package
java -jar target/movielist-0.0.1-SNAPSHOT.jar
```
3. Acesse a aplicação através do navegador no seguinte endereço:
```
http://localhost:8080/movies/winners-interval
```

4. Os dados importados complementares estão no endereço:
```
http://localhost:8080/movies
