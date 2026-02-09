package NHL;

import java.util.List;

public class Player {
    String name;
    String lastName;
    int jerseyNumber;

    String teamCode;
    String teamCity;
    String teamName;

    public Player(){}

    public Player(String name, String lastName, int jerseyNumber, String teamCode, String teamCity, String teamName) {
        this.name = name;
        this.lastName = lastName;
        this.jerseyNumber = jerseyNumber;
        this.teamCode = teamCode;
        this.teamCity = teamCity;
        this.teamName = teamName;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public void setLastName(String newLastName){
        this.lastName = newLastName;
    }

    public int getJerseyNumber(){
        return this.jerseyNumber;
    }

    public void setJerseyNumber(int newNumber){
        this.jerseyNumber = newNumber;
    }

    public String getTeamCity() {
        return teamCity;
    }

    public String getTeamCode(){
        return teamCode;
    }

    public String getTeamName(){
        return teamName;
    }

}
