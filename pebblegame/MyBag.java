package pebblegame;

import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MyBag
{
    // instance variables - replace the example below with your own
    ArrayList<Integer> myPebbles = new ArrayList<Integer>();

    MyBag corrospondingBag;
    String bagName;

    //When constructing white bags
    public MyBag(String bagName){
        this.bagName = bagName;
    }
    
    //When constructing black bags
    public MyBag(String bagFile, String bagName) throws IOException
    {
        readFrom(bagFile);
        this.bagName = bagName;
    }
    
    //Takes filename and updates myPebbles arraylist, throws IOException if any integers in file are negative
    public void readFrom(String t) throws IOException {
        InputStream in = getClass().getResourceAsStream(t);
        Scanner scanner = new Scanner(in);
        
        //Splits the string into a list of ints without commas.
        String line;
        String[] lineVector;
        line = scanner.nextLine();
        lineVector = line.split(",");

        for (String item : lineVector){
            int temp = Integer.parseInt(item);
            if (temp < 1){
                throw new IOException("Numbers in file must be positive integers");
            }
            myPebbles.add(temp);
        }
    }

    //Sets corresponding bag.
    public void setCoBag(MyBag bag){
        corrospondingBag = bag;
    }

    //Returns an arraylist containing 10 integers which are chosen at random and removes them from the bags myPebbles arraylist.
    public ArrayList<Integer> draw10(){
        ArrayList<Integer> pebbles10 = new ArrayList<Integer>();

        for(int i=0; i<10; i++){
            Random random = new Random();
            int select = random.nextInt(myPebbles.size()-1);
            pebbles10.add(myPebbles.remove(select));
        }
        return pebbles10;
    }

    //Returns an integer chosen at random and removes it from the bags myPebbles arraylist.
    public int drawOne(){
        Random random = new Random();
        if (myPebbles.isEmpty()){
            myPebbles = corrospondingBag.emptyBag();
        }
        int select = random.nextInt(myPebbles.size());
        if (select != 0){ select--;}
        return myPebbles.remove(select);
    }

    //Returns arraylist which is equal to myPebbles and then clears myPebbles.
    public ArrayList<Integer> emptyBag() {
        ArrayList<Integer> copy = new ArrayList<Integer>(myPebbles);
        myPebbles.clear();
        return copy;
    }
    
    //Throws an exception if check is greater than the size of myPebbles.
    public void checkMin(int check) throws IOException{
        if (check > myPebbles.size()){
            throw new IOException("Number of pebbles must be 11 times the number of players!");
        }
    }
}

