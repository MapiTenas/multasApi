package com.svalero.multasApi.service;

import com.svalero.multasApi.domain.Multa;
import com.svalero.multasApi.repository.MultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MultaService {
    @Autowired
    private MultaRepository multaRepository;

    public Flux<Multa> getAllMultas(){
        return multaRepository.findAll();
    }
    public Mono<Multa> getMulta(String id) {
        return multaRepository.findById(id);
    }

    public Mono<Multa> saveMulta(Multa event) {
        Multa newMulta = new Multa();
        newMulta.setMatricula(event.getMatricula());
        newMulta.setVelocidadAlcanzada(event.getVelocidadAlcanzada());
        newMulta.setFecha(event.getFecha());
        newMulta.setHora(event.getHora());
        return multaRepository.save(newMulta);
    }
}
