package client_server;

public class ClientHandler {
    private String username = null;
    private int idPerson;
    private String type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void printLoginOptions() {
        System.out.println("Type \"LOGIN username\" if you want to log in");
        System.out.println("Type \"EXIT\" if you want to quit the program");
    }

    public void printCompetitorOptions() {
        System.out.println("Type \"ADDSCORE username stageName score\" if you want to modify your score");
        System.out.println("Type \"STAGESCORE stageName\" if you want to see the stage ranking");
        System.out.println("Type \"TEAMSCORE teamName stageName\" if you want to see your team ranking");
        System.out.println("Type \"FINALRANK\" if you want to see your team ranking");
        System.out.println("Type \"EXIT\" if you want to quit the program");
    }

    public void printAdminOptions() {
        System.out.println("Type \"COMPETITORSCORE username stageName\" if you want to see the score of a competitor");
        System.out.println("Type \"STAGESCORE stageName\" if you want to see the stage ranking");
        System.out.println("Type \"TEAMSCORE teamName stageName\" if you want to see your team ranking");
        System.out.println("Type \"FINALRANK\" if you want to see your team ranking");
        System.out.println("Type \"ADDCOMPETITOR username fullname idTeam\" if you want to add a new person");
        System.out.println("Type \"ADDTEAM teamName\" if you want to add a new team");
        System.out.println("Type \"ADDSTAGE stageName\" if you want to add a new stage");
        System.out.println("Type \"ENROLL username teamName\" if you want to enroll a competitor in a team");
        System.out.println("Type \"EXIT\" if you want to quit the program");
    }
}
