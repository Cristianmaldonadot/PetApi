package com.example.petmatch.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.petmatch.model.Mascota;

@Repository
public interface IMascota extends JpaRepository<Mascota, Long> {

}
