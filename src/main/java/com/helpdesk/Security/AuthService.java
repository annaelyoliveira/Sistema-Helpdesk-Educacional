package com.helpdesk.Security;

import com.helpdesk.Model.Tecnico;
import com.helpdesk.Model.Usuario;
import com.helpdesk.Repository.TecnicoRepository;
import com.helpdesk.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Usuario usuario = usuarioRepository.buscarPorEmail(email);
            return User.builder()
                    .username(usuario.getEmail())
                    .password(usuario.getSenha())
                    .authorities(usuario.getRole())
                    .build();
        } catch (Exception e) {
            Tecnico tecnico = tecnicoRepository.buscarPorEmail(email);
            if (tecnico != null) {
                return User.builder()
                        .username(tecnico.getEmail())
                        .password(tecnico.getSenha())
                        .authorities(tecnico.getRole())
                        .build();
            }
            throw new UsernameNotFoundException("Nenhuma conta encontrada com o email: " + email);
        }
    }
}
