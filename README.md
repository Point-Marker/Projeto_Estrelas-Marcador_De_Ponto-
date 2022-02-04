# Projeto_Estrelas-Marcador_De_Pontos
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




