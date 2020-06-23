package it.units.project.response;


public class SuccessfulResponse extends AbstractResponse {
    public SuccessfulResponse(String string){
        super(string);
    }

    public String buildingResponse(){
        String returnFormat = "OK";
        return returnFormat+separator+totalTime+separator+toBuild;
    }
}
