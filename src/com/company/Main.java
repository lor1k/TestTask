package com.company;

import classes.Elevator;
import classes.Person;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    private final static int MAX_FLOURS = 20;
    private final static int MIN_FLOURS = 5;
    private final static int MIN_FLOUR = 1;
    private final static int MAX_PEOPLE_ON_FLOUR = 10;
    private final static int MAX_PEOPLE_IN_ELEVATOR = 5;

    private final static int AMOUNT_OF_FLOURS = MIN_FLOURS + (int) (Math.random() * MAX_FLOURS);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Elevator elevator = Elevator.getInstance();
        ArrayList<Person> people = new ArrayList<>(0);
        System.out.println(AMOUNT_OF_FLOURS);
        int amountOfPeople;
        int neededFlour;
        int leftPeople;
        for(int i = 1; i < AMOUNT_OF_FLOURS; i++){//Init people
            amountOfPeople = (int)(Math.random()*MAX_PEOPLE_ON_FLOUR);
            for(int j = 0; j < amountOfPeople; j++){
                do{
                    neededFlour = MIN_FLOUR + (int)(Math.random() * AMOUNT_OF_FLOURS);
                }while(neededFlour == i);
                people.add(new Person(neededFlour, i));
            }
        }

        while(people.size()!=0){//never zero
            //sc.next();//uncomment this to pause
            draw(elevator, people);
            if(elevator.unloadNeeded()){
               leftPeople = elevator.unload();
               for(int i = 0; i < leftPeople; i++){
                   do{
                       neededFlour = MIN_FLOUR + (int)(Math.random() * AMOUNT_OF_FLOURS);
                   }while(neededFlour == elevator.getFlour());
                   people.add(new Person(neededFlour, elevator.getFlour()));
               }
            }
            if(elevator.getDirection() == Elevator.directions.DOWN && elevator.getFlour() <= 1){
                elevator.setDirection(Elevator.directions.UP);
            }else if(elevator.getDirection() == Elevator.directions.UP && elevator.getFlour() == AMOUNT_OF_FLOURS){
                elevator.setDirection(Elevator.directions.DOWN);
            }
            for (Iterator<Person> it = people.iterator(); it.hasNext(); ) {
                if(elevator.getAmountOfPeople() == MAX_PEOPLE_IN_ELEVATOR){
                    break;
                }
                Person p = it.next();
                if(((elevator.getDirection() == Elevator.directions.UP && p.getNeededFlour() > elevator.getFlour())||
                        (elevator.getDirection() == Elevator.directions.DOWN && p.getNeededFlour() < elevator.getFlour())) &&
                        p.getCurrentFlour() == elevator.getFlour()) {
                    elevator.load(p);
                    it.remove();
                }
            }
            elevator.chooseDirection();
            elevator.nextFlour();
        }
    }

    private static void draw(Elevator elevator, ArrayList<Person> people){//draws: people in the elevator, direction ('\/' or '^'), |[number of flour]|, people on the flour
        System.out.println("-------------------------------------------------------------");
        for(int i = AMOUNT_OF_FLOURS; i >= MIN_FLOUR; i--){
            String cf = "";
            if(i == elevator.getFlour()){
                ArrayList<Person> ep = elevator.getPeople();
                for(int j = 4; j >= 0; j--){
                    try{
                        cf = cf.concat(ep.get(j).getNeededFlour() + "\t");
                    } catch (IndexOutOfBoundsException e ){
                        cf = cf.concat("\t");
                    }
                }
                if(elevator.getDirection() == Elevator.directions.UP){
                    cf = cf.concat("^");
                }else{
                    cf = cf.concat("\\/");
                }
                cf = cf.concat("\t |" + i + "|");
            }else{
                cf = cf.concat("\t\t\t\t\t\t |" + i + "|");
            }
            for (Person p :
                    people) {
                if(p.getCurrentFlour() == i){
                    cf = cf.concat("\t" + p.getNeededFlour());
                }
            }
            System.out.println(cf);
        }
    }
}
