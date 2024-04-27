package com.svalero.multasApi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("multas")

public class Multa implements Serializable {
    @Id
    private String id;
    @Field
    private String matricula;
    @Field
    private String velocidadAlcanzada;
    @Field
    private String fecha;
    @Field
    private String hora;
}
