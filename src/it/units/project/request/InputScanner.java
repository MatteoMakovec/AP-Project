package it.units.project.request;

public class InputScanner {
    private String request;
    private final long initialTime;
    private String quitCommand;

    public InputScanner(String regex, String quitCommand){
        request = regex;
        this.quitCommand = quitCommand;
        initialTime = System.nanoTime();
    }

    public void inputProcessing(){
        Request reqv;
        int index = request.indexOf(";");
        if (index == -1){
            if (request == quitCommand){
                // Da capire
            }
            else{
                reqv = new StatRequest(request, initialTime);
            }
        }
        else{
            reqv = new ComputationRequest(request, initialTime);
        }
    }
}
