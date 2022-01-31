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
    "id_funcionario":{
      "id":1
    },
    "horaExtra": 1,
    "entrada": "12/01/2001 08:00",
    "saida": "12/01/2001 15:00"
}
```

- Resposta 201
- Saida
```json
{
"funcionario": {
"nome": "Afonso  Gonçalves Tevez",
"cpf": "527.550.148-06",
"cargo": {
"id": 2,
"nome": "Jovem Aprendiz",
"salario": 800.0
    }
  },
"entrada": "12/01/2001 08:00",
"saida": "12/01/2001 15:00"
}
```


