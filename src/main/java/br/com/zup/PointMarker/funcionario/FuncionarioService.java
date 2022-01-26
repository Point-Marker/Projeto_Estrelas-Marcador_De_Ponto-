package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.exceptions.FuncionarioNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.PointMarker.cargo.CargoRepository;
import br.com.zup.PointMarker.enums.Status;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FuncionarioService {

    private FuncionarioRepository funcionarioRepository;
    private CargoRepository cargoRepository;

    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.cargoRepository = cargoRepository;
    }

    public Funcionario salvarFuncionario(Funcionario entradafuncionario) {
        Optional<Cargo> cargoOptional = cargoRepository.findById(entradafuncionario.getCargo().getId());
        entradafuncionario.setCargo(cargoOptional.get());
        cargoOptional.orElseThrow(NoSuchElementException::new);

        entradafuncionario.setSalario(entradafuncionario.getCargo().getSalario());
        entradafuncionario.setStatus(Status.ATIVO);
        return funcionarioRepository.save(entradafuncionario);
    }
  
      public Funcionario buscarFuncionario(int id) {
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);

        if (optionalFuncionario.isEmpty()) {
            throw new FuncionarioNaoEncontradoException("Funcionário não encontrado.");
        }
        return optionalFuncionario.get();
    }

    public Funcionario atualizarCargo(int idFuncionario, Cargo idCargo) {
        Funcionario funcionario = buscarFuncionario(idFuncionario);
        funcionario.setCargo(idCargo);

        return funcionario;
    }
}
