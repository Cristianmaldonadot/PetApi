package com.example.petmatch.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.petmatch.interfaces.IS3Service;
import com.example.petmatch.model.Mascota;
import com.example.petmatch.model.Usuario;
import com.example.petmatch.service.MascotaService;

@RestController
public class MascotaController {
	
	@Autowired
	private MascotaService mascotaService;
	
	@Autowired 
	private IS3Service is3Service;
	
	@GetMapping("/listarmascotas")
	public List<Mascota> listas(){
		return mascotaService.listar();
	}
	
	@PostMapping("/registrarmascota")
	public String registrarMascota(@RequestParam String nombre, @RequestParam String raza, 
			@RequestParam String color, @RequestParam int edad, 
			@RequestParam(value = "file", required = false) MultipartFile file, 
			@RequestParam double latitud, @RequestParam double longuitud, 
			@RequestParam Usuario usuario) throws IOException {
		
		String urlCompleta;
		String nombreArchivo = file.getOriginalFilename();
		urlCompleta = "http://imagenes-tienda.s3.us-east-2.amazonaws.com/" + nombreArchivo;
		is3Service.uploadFile(file);
		
		Mascota mascota = new Mascota(null, nombre, raza, color, edad, urlCompleta, latitud, longuitud, usuario);
		
		mascotaService.registrarMascota(mascota);
		
		return "Mascota Registrada";
	}
	

}
