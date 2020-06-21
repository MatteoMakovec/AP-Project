package it.units.project.response;

import it.units.project.request.AbstractRequest;

public abstract class AbstractResponse {
    protected String toBuild;
    protected AbstractRequest request;
    protected char separator = ';';

    public abstract String buildingResponse();

    public AbstractResponse(AbstractRequest request, String string){
        toBuild = string;
        this.request = request;
    }
}
