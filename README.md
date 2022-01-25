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


