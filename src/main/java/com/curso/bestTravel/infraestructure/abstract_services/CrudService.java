package com.curso.bestTravel.infraestructure.abstract_services;

public interface CrudService <Request,Response,ID> {

    Response create(Request request);

    Response read(ID id);

    Response update(Request request,ID id);

    void delete(ID id);
}
