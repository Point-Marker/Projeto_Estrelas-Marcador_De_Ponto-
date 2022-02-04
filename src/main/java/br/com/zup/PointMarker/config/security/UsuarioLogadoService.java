package br.com.zup.PointMarker.config.security;

import br.com.zup.PointMarker.usuario.Usuario;
import br.com.zup.PointMarker.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UsuarioLogadoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String nomeUsuario) throws UsernameNotFoundException {

        Optional<Usuario> usuarioOptional = usuarioRepository.findByNomeUsuario(nomeUsuario);
        usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Nome de Usuario ou Senha estão Incorretos"));
        if (usuarioOptional.get().getNomeUsuario().equals("admin")) {

            return new UsuarioLogado(usuarioOptional.get().getId(),
                    usuarioOptional.get().getNomeUsuario(),
                    usuarioOptional.get().getSenha(), Arrays.asList("ADMIN"));
        }

        return new UsuarioLogado(usuarioOptional.get().getId(),
                usuarioOptional.get().getNomeUsuario(),
                usuarioOptional.get().getSenha(), Arrays.asList("USER"));

    }
}
