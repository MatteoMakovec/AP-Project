package it.units.project.response;


public class ErrorResponse extends AbstractResponse {
    public ErrorResponse(String string){
        super(string);
    }

    public String buildingResponse(){
        String returnFormat = "ERR";

        return returnFormat+separator+toBuild;
    }
}
