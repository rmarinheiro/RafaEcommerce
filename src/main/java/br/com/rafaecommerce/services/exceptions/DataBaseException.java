package br.com.rafaecommerce.services.exceptions;

public class DataBaseException extends  RuntimeException{

    public DataBaseException(String msg) {
        super(msg);
    }
}
