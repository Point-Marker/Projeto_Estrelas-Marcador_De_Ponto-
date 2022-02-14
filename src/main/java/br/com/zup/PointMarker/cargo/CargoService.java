package br.com.zup.PointMarker.cargo;

import br.com.zup.PointMarker.exceptions.CargoNaoCadastrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    public Cargo buscarCargo(int id) {
        Optional<Cargo> cargoOptional = cargoRepository.findById(id);

        if (cargoOptional.isEmpty()) {
            throw new CargoNaoCadastrado("Este Cargo Não Foi Cadastrado. Comunique o Admin da sua unidade/time");
        }
        return cargoOptional.get();
    }
}
