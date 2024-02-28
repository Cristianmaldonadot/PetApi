package com.example.petmatch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.petmatch.interfaceService.IMascotaService;
import com.example.petmatch.interfaces.IMascota;
import com.example.petmatch.model.Mascota;

@Service
public class MascotaService implements IMascotaService {
	
	@Autowired
	private IMascota dataMascota;

	@Override
	public List<Mascota> listar() {
		return dataMascota.findAll();
	}

	@Override
	public Mascota registrarMascota(Mascota mas) {
		return dataMascota.save(mas);
	}

}
