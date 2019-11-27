package classes;

public class Person {
    private int neededFlour;
    private int currentFlour;

    public Person(int neededFlour, int currentFlour){
        this.neededFlour = neededFlour;
        this.currentFlour = currentFlour;
    }

    public int getNeededFlour() {

        return neededFlour;
    }

    public int getCurrentFlour() {
        return currentFlour;
    }
}
