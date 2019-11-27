package classes;

import java.util.ArrayList;
import java.util.Iterator;

public class Elevator {
    private int flour = 1;
    private ArrayList<Person> people = new ArrayList<>(0);
    private directions direction = directions.UP;

    private static Elevator instance;
    private Elevator(){

    }
    public static Elevator getInstance(){
        if(instance == null){
            instance = new Elevator();
        }
        return instance;
    }

    public int getFlour() {
        return flour;
    }
    public int getAmountOfPeople(){
        return this.people.size();
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public boolean unloadNeeded() {
        if(this.people.size() == 0)
        return false;
        for (Person p :
                this.people) {
            if(p.getNeededFlour() == this.flour)
                return true;
        }
        return false;
    }

    public int unload() {
        int count = 0;
        for (Iterator<Person> it = this.people.iterator(); it.hasNext(); ) {
            Person p = it.next();
            if (p.getNeededFlour() == this.flour){
                it.remove();
                count++;
            }
        }
        return count;
    }

    public void load(Person p) {
        this.people.add(p);
    }

    public directions getDirection() {
        return direction;
    }

    public void chooseDirection() {
        if(this.people.size() != 0){
            if(this.people.get(0).getNeededFlour() - this.flour > 0){//needs up (all people actually need same direction)
                this.direction = directions.UP;
            } else{//never 0
                this.direction = directions.DOWN;
            }
        }
    }
    public void nextFlour(){
        if(this.direction == directions.UP){
            flour++;
        }else{
            flour--;
        }
    }

    public void setDirection(directions direction) {
        this.direction = direction;
    }

    public enum directions{
        UP,
        DOWN
    }


}
