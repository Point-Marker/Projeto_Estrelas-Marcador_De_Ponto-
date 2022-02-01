package br.com.zup.PointMarker.config.security;

import br.com.zup.PointMarker.cargo.Cargo;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UsuarioLogado implements UserDetails {
    private int id;
    private String nomeUsuario;
    private String senha;
    private List<Cargo> cargoList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return cargoList;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return nomeUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
