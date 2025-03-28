package com.example.demo.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Integer Id) {
        super("O recurso requisitado com o seguinte ID não foi encontrado: " + Id);
    }
}
