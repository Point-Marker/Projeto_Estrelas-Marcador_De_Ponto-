# Projeto_Estrelas-Marcador_De_Pontos
<div align="center">
 
![ezgif com-gif-maker](https://user-images.githubusercontent.com/77360662/153928572-adecd44d-d296-40e4-867c-86b5d9f7ca71.gif)

</div>
 

<hr/>

# Cadastro de Funcionários
<hr/>

- /funcionario
- POST
- JSON

 ```json
{
    "nome":"Load cobs Voks",
    "cpf":"616.653.070-64",
    "dataDeNascimento":"01/07/1979",
    "cargo": {
        "id":1
    },
    "usuario":{
    "nomeUsuario":"Load702",
    "senha":"1234"
    }
}
```

- Resposta 201

<hr/>

# Cadastro de Banco de Horas

- /bancohoras
- POST
- JSON

```json
{
  "funcionario":{
    "id":1
  },
  "entrada": "09:00",
  "saida": "17:00"
}
```

- Resposta 201
- Saida
```json
{
    "funcionario": {
        "nome": "Leoncio Das Neves",
        "cpf": "684.671.650-57",
        "cargo": {
            "id": 1,
            "nome": "Estagio",
            "salario": 900.0,
            "cargoHoraria": 8,
            "authority": "Estagio"
        }
    },
    "diaDoTrabalho": "04/02/2022",
    "entrada": "09:00",
    "saida": "17:00"
}
```

# Cadastro de Cargos

- /dashboard/cadastro/cargos
- POST
- JSON

```json
{
  "nome":"atendente",
  "salario":"700",
  "cargo_horaria":"8"
}
```

- Resposta 201
- Saida
```json
{
  "id": 3,
  "nome": "atendente",
  "salario": 700.0
}
```

# Login

- /login
- POST
- JSON

```json
{
  "nomeUsuario":"admin",
  "senha":"1234"
}
```

- Resposta 200

# Atualizar Salário

- /dashboard/salario/{id}
- PUT
- JSON

```json
{
  "salario":100
}
```

- Resposta 200
- Saida
```json
{
  "cargo": {
    "id": 2,
    "nome": "Estagio",
    "salario": 900.0,
    "cargahoraria": 8,
    "authority": "Estagio"
  },
  "status": "ATIVO",
  "salario": "1000.0"
}
```

# Atualizar Cargo

- /dashboard/cargo/{id}
- PUT
- JSON

```json
{
  "cargo":{
    "id":2
  }
}
```

- Resposta 200
- Saida
```json
{
  "cargo": {
    "id": 2,
    "nome": "Estagio",
    "salario": 900.0,
    "cargahoraria": 8,
    "authority": "Estagio"
  },
  "status": "ATIVO",
  "salario": "900.0"
}
```

# Atualizar Status

- /dashboard/status/{id}
- PUT
- JSON

```json
{
  "status":"INATIVO"
}
```

- Resposta 200
- Saida
```json
{
  "id": 2,
  "nome": "Evellyin Helenoir",
  "cargo": {
    "id": 2,
    "nome": "Estagio",
    "salario": 900.0,
    "cargahoraria": 8,
    "authority": "Estagio"
  },
  "status": "INATIVO"
}
```

# Cadastro de Cargos

- /dashboard/cadastro/cargos
- GET

- Resposta 200
- Saida
```json
[
  {
    "id": 1,
    "nome": "Rafael Alves Campos",
    "cpf": "192.870.190-61",
    "salario": 0.0,
    "dataDeNascimento": "30/11/2000",
    "cargo": {
      "id": 1,
      "nome": "gestor",
      "salario": 0.0,
      "cargahoraria": 0,
      "authority": "gestor"
    },
    "status": "ATIVO",
    "totalHorasTrabalhadas": 0,
    "horasExtras": 0,
    "bancoDeHorasDetailsDTO": []
  }
]
```

Rode o projeto e acesse o Swagger através deste link:
localhost:8080/swagger-ui/index.html#
http://localhost:8080/swagger-ui/index.html#/

Segue o arquivo com as coleções do Postman para que você teste a nossa aplicação:
[Projeto-Final_Estrelas.postman_collection.zip](https://github.com/Point-Marker/Projeto_Estrelas-Marcador_De_Ponto-/files/8063113/Projeto-Final_Estrelas.postman_collection.zip)



