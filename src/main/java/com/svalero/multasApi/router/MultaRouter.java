package com.svalero.multasApi.router;

import com.svalero.multasApi.handler.MultaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class MultaRouter {

    @Bean
    public RouterFunction<ServerResponse> productsRoute(MultaHandler multaHandler){
        return RouterFunctions
                .route(GET("/multas").and(accept(MediaType.APPLICATION_JSON)), multaHandler::getAllMultas)
                .andRoute(GET("/multa/{id}").and(accept(MediaType.APPLICATION_JSON)), multaHandler::getMulta)
                .andRoute(POST("/multas").and(accept(MediaType.APPLICATION_JSON)), multaHandler::createMulta);
                //.andRoute(DELETE("/multa/{id}").and(accept(MediaType.APPLICATION_JSON)), multaHandler::deleteMulta)
                //.andRoute(PUT("/multa/{id}").and(accept(MediaType.APPLICATION_JSON)), multaHandler::updateMulta);
    }
}