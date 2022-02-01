# Projeto_Estrelas-Marcador_De_Pontos
<hr/>

# Cadastro de Funcionários
<hr/>

- /funcionario
- POST
- JSON

 ```json
{
  "nome":"Adeildo",
  "cpf":"563.038.670-06",
  "dataDeNascimento":"01/01/1999",
  "cargo": {
    "id":1
  }
}
```

- Resposta 201

- Saida:

```json 
{
    "nome": "Adeildo",
    "cpf": "563.038.670-06",
    "salario": 900.0,
    "dataDeNascimento": "01/01/1999",
    "cargo": {
        "id": 1,
        "nome": "Estagiario",
        "salario": 900.0
    },
    "status": "ATIVO"
}
```
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
    "nome": "Clovis Gonçalves Tevez",
    "cpf": "308.384.638-00",
    "cargo": {
      "id": 1,
      "nome": "Estagio",
      "salario": 900.0,
      "cargoHoraria": 8
    }
  },
  "diaDoTrabalho": "01/02/2022",
  "entrada": "09:00",
  "saida": "17:00"
}
```




