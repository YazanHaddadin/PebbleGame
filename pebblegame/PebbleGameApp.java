package pebblegame;

import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.io.PrintWriter;

public class PebbleGameApp {
    static class Player extends Thread{
        public ArrayList<Integer> myHand = new ArrayList<>();
        MyBag lastBag; //Is where last pebble was taken from.
        String playerName;
        
        public Player(String playerName){
            this.playerName = playerName;
        }

        @Override
        public synchronized void run(){
            //Only done one time.
            myHand = drawPebble();
            try{
                PrintWriter writer = new PrintWriter(playerName + ".txt", "UTF-8"); // Creates a new file to write log to.

                while(!done){
                    if (is100() == true){
                        System.out.println(playerName + " Wins");
                        done = true; // Used to stop all threads.
                    } else {
                        int peb = discard();//Removes one pebble from hand.
                        int pebD = draw(); // Gets new pebble from random bag.

                        writer.println(playerName + " has discarded a " + peb + " to " + lastBag.corrospondingBag.bagName); 
                        writer.println(playerName + " hand is " + myHand);
                        
                        myHand.add(pebD); // Add pebble to myHand

                        writer.println(playerName + " has drawn a " + pebD + " from " + lastBag.bagName); 
                        writer.println(playerName + " hand is " + myHand);
                    }
                    Thread.yield();
                }
                writer.close();
            } catch(IOException e){}
        }

        // Returns an arraylist which contains 10 pebbles to be assigned to a players hand. The bag they are taken from is selected at random.
        public ArrayList<Integer> drawPebble(){
            MyBag [] bags = {X, Y, Z};
            Random random = new Random();
            int select = random.nextInt(bags.length);
            lastBag = bags[select];
            return bags[select].draw10();
        }

        // Returns boolean that is true if the sum of a players myhand is equal to 100.
        public boolean is100(){
            int sum = 0;
            for(int i=0; i<10; i++){
                sum += myHand.get(i);
            }
            return sum == 100;
        }

        // Returns an int which is the pebble being removed fromt the myHand arraylist.
        public int discard(){
            int peb = myHand.remove(0);
            lastBag.corrospondingBag.myPebbles.add(peb);
            return peb;
        }

        // Returns an int that is one pebble selected at random, from a bag that is also selected at random.
        public int draw() {
            MyBag [] bags = {X, Y, Z};
            Random random = new Random();
            int select = random.nextInt(bags.length);
            lastBag = bags[select];
            return bags[select].drawOne();
        }
    }

    static MyBag X;
    static MyBag Y;
    static MyBag Z;

    static boolean done = false;

    static MyBag A = new MyBag("A");
    static MyBag B = new MyBag("B");
    static MyBag C = new MyBag("C");
    
    public static void doWork(Scanner input) {
        System.out.println("Welcome to the PebbleGame! \n You will be asked to enter the number of players. \n and then for the location of three files in turn containing comma seperated integer values for the pebble weights. \n The integer values must be strictly positive. \n The game will then be simulated, and output written to files in this directory. \n");

        int number=0;
        boolean playerCheck=false;

        // Continues to ask for number of players until given correct input.
        while (!playerCheck){
            System.out.println("Enter the number of players: ");
            number = Integer.parseInt(input.next());
            if (number >=1){
                playerCheck = true;
            } else {
                System.out.println("Invalid player entry!");
            }
        }
        Player[] players = new Player[number];

        // Instantiates the players and stores in an array called players.
        for(int i=0 ; i<number ;i++){
            players[i] = new Player("player" + (i+1));
        }

        boolean fileCheck=false;
        int check = 11*number; // Minimum number of pebbles needed in file.

        // Continues to ask for three files until given correct input and that the given file exists.
        while (!fileCheck){
            try{
                System.out.println("Enter File 1: ");
                X = new MyBag(input.next(), "X");
                X.checkMin(check); // Checks that minimum amount of pebbles.

                System.out.println("Enter File 2: ");
                Y = new MyBag(input.next(), "Y");
                Y.checkMin(check);

                System.out.println("Enter File 3: ");
                Z = new MyBag(input.next(), "Z");
                Z.checkMin(check);

                fileCheck = true;
            }catch (IOException e){
                System.out.println(e);
            }
        }
        
        // Sets corresponding bags.
        X.setCoBag(A);
        Y.setCoBag(B);
        Z.setCoBag(C);

        A.setCoBag(X);
        B.setCoBag(Y);
        C.setCoBag(Z);

        //Starts player threads.
        for(int i=0 ; i<number ;i++){
            players[i].start();
        }

        input.close();
    }

    //Main method uses do work so that it can be tested with inputs.
    public static void main (String args[]){
        doWork(new Scanner(System.in));
    }
}
