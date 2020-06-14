package it.units.project.response;

import it.units.project.request.Request;

public abstract class ResponseBuilder {
    protected String toBuild;
    protected Request request;
    protected char separator = ';';

    public abstract String buildingResponse();

    public ResponseBuilder(Request request, String string){
        toBuild = string;
        this.request = request;
    }
}
