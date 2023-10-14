package br.com.rafaecommerce.services.exceptions;

public class EntityNotFoundException extends  RuntimeException{

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
