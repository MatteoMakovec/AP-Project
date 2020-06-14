package it.units.project.response;

import it.units.project.request.Request;

public class SuccessfullResponse extends ResponseBuilder{
    public SuccessfullResponse(Request request, String string){
        super(request, string);
    }

    public String buildingResponse(){
        String returnFormat = "OK";

        return returnFormat+separator+request.closeRequest(true)+separator+toBuild;
    }
}
