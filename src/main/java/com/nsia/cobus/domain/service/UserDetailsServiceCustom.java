package com.nsia.cobus.domain.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.models.User;
import com.nsia.cobus.domain.port.UserRepositoryPort;

@Component
@Service
public class UserDetailsServiceCustom implements UserDetailsService {

    private final UserRepositoryPort utilisateurRepository;

    public UserDetailsServiceCustom(UserRepositoryPort utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User utilisateur = utilisateurRepository.findUserByUsername(username);
            if (utilisateur == null) {
                throw new UsernameNotFoundException("Utilisateur non trouvé");
            }

            return new org.springframework.security.core.userdetails.User(utilisateur.getLogin(),
                    utilisateur.getPassword(), new ArrayList<>());
        } catch (Exception e) {
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }

    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(String roles) {
        return Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
