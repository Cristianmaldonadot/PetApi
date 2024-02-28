package com.example.petmatch.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.petmatch.controller.request.CreateUserDTO;
import com.example.petmatch.interfaces.IUsuario;
import com.example.petmatch.model.ERole;
import com.example.petmatch.model.RolEntity;
import com.example.petmatch.model.Usuario;

import jakarta.validation.Valid;

@RestController
public class UsuarioController {
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private IUsuario userRepository;
	
	@PostMapping("/registrarusuario")
	public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){
		
		Set<RolEntity> roles = createUserDTO.getRoles().stream()
				.map(role -> RolEntity.builder()
						.name(ERole.valueOf(role))
						.build())
				.collect(Collectors.toSet());
		
		/*List<DireccionEntity> direcciones = createUserDTO.getDirecciones().stream()
				.map(direccion -> DireccionEntity.builder()
						.nombre(direccion)
						.build())
				.collect(Collectors.toList());*/
		
		Usuario userEntity = Usuario.builder()
				.username(createUserDTO.getUsername())
				.celular(createUserDTO.getCelular())
				.password(encoder.encode(createUserDTO.getPassword()))
				.email(createUserDTO.getEmail())
				.roles(roles)
				//.direcciones(direcciones)
				.build();
		
		userRepository.save(userEntity);
		
		return ResponseEntity.ok(userEntity);
					
	}

}
