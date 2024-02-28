package com.example.petmatch.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.petmatch.model.Usuario;

@Repository
public interface IUsuario extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByUsername(String username);

}
