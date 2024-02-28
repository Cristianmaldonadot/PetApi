package com.example.petmatch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.petmatch.interfaceService.IUsuarioService;
import com.example.petmatch.interfaces.IUsuario;
import com.example.petmatch.model.Usuario;

@Service
public class UsuarioService implements IUsuarioService {
	
	@Autowired
	private IUsuario dataUsuario;

	@Override
	public Usuario registrarUsuario(Usuario usu) {
		return dataUsuario.save(usu);
	}

}
