package br.com.zup.PointMarker.config.security;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioLogadoService implements UserDetailsService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;


    @Override
    public UserDetails loadUserByUsername(String nomeUsuario) throws UsernameNotFoundException {
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByNomeUsuario(nomeUsuario);

        funcionarioOptional.orElseThrow(() -> new UsernameNotFoundException("Nome de Usuario ou Senha estão Incorretos"));

        List<Cargo> cargosDoFuncionario = new ArrayList<>();
        cargosDoFuncionario.add(funcionarioOptional.get().getCargo());

        Funcionario funcionario = funcionarioOptional.get();

        return new UsuarioLogado(funcionario.getId(), funcionario.getNomeUsuario(), funcionario.getSenha()
                , cargosDoFuncionario);
    }

}
