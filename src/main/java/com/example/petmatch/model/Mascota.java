package com.example.petmatch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mascota")
public class Mascota {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idmascota;
	private String nombre;
	private String raza;
	private String color;
	private int edad;
	private String imagen;
	private double latitud;
	private double longuitud;
	@ManyToOne
	@JoinColumn(name = "idusuario")
	private Usuario usuario;

}
