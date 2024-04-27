package com.svalero.multasApi.handler;

import com.svalero.multasApi.domain.Multa;
import com.svalero.multasApi.service.MultaService;
import com.svalero.multasApi.util.ErrorResponse;
import com.svalero.multasApi.validator.MultaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Component
public class MultaHandler {

    @Autowired
    private MultaService multaService;

    private final Validator validator = new MultaValidator();

    public Mono<ServerResponse> getAllMultas(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(multaService.getAllMultas(), Multa.class);
    }

    public Mono<ServerResponse> getMulta(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return multaService.getMulta(id)
                .flatMap(p -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(p), Multa.class))
                .switchIfEmpty(notFound(id));
    }

    public Mono<ServerResponse> createMulta(ServerRequest serverRequest) {
        Mono<Multa> multaToSave = serverRequest.bodyToMono(Multa.class)
                .doOnNext(this::validate);

        return multaToSave.flatMap(multa ->
                ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(multaService.saveMulta(multa), Multa.class));
    }

    public Mono<ServerResponse> deleteMulta(ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");
        return multaService.getMulta(id)
                .flatMap(event -> multaService.deleteMulta(id)
                        .then(ServerResponse.noContent().build()))
                .switchIfEmpty(notFound(id));
    }

    public Mono<ServerResponse> updateMulta(ServerRequest serverRequest) {

        String multaId = serverRequest.pathVariable("id");
        Mono<Multa> multaMono = serverRequest.bodyToMono(Multa.class)
                .doOnNext(this::validate);
        return multaService.updateMulta(multaId, multaMono)
                .flatMap(updatedEvent ->
                        ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(updatedEvent))
                .switchIfEmpty(notFound(multaId));
    }

    private void validate(Multa multa){
        Errors errors = new BeanPropertyBindingResult(multa,"multa");
        validator.validate(multa, errors);
        if (errors.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    errors.getAllErrors().toString());
        }
    }

    private Mono<ServerResponse> notFound(String id) {
        return ServerResponse.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(new ErrorResponse(404, " El registro : " + id + " no se ha encontrado ")), ErrorResponse.class);
    }

}
