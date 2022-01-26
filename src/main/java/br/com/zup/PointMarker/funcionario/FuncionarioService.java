package br.com.zup.PointMarker.funcionario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

  @Autowired
    private FuncionarioRepository funcionarioRepository;

  public Iterable<Funcionario> exibirFuncionarios(){
    return funcionarioRepository.findAll();
  }

}
