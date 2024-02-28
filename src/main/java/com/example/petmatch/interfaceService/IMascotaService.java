package com.example.petmatch.interfaceService;

import java.util.List;

import com.example.petmatch.model.Mascota;

public interface IMascotaService {
	
	public List<Mascota> listar();
	public Mascota registrarMascota(Mascota mas);

}
