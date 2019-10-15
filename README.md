# MyBag Class
This class creates a bag which is represented by an ArrayList of integers using the readFrom method, which takes as an input a String which is the file name. This method throws an IOException if any of the integers in the file that has been read is less than 1.
The constructors of this class labels the bag with a string, this is used to be able to determine which bag is white and black, also to determine which bags are corresponding to each other.
The method setCoBag (MyBag bag) takes an argument bag and is used to set the corresponding white bag (bag) to the correct black bag.
The method draw10 () returns an ArrayList of integers which are 10 pebbles drawn from a black bag at random.
The method drawOne () returns an integer that is chosen from random from a black bag, it first checks if the bag is empty and if it is then the black bag is refilled from the corresponding white bag.
The method emptyBag () will return an ArrayList of the bag that has been copied and then cleared so it will later be assigned to a black bag when it is empty.
The method checkMin () will take an argument check and will throw an IOException if check is greater than the bag size, check is the number of players*11.
 






PebbleGameApp
This class creates the Player thread which is within a nested class called Player. It starts by creating the players hand as an ArrayList of integers. The constructor is used to label the player with a playerName so we can differentiate between each player. 
We declared a lastBag which is going to be the last bag in which a pebble has been taken from. This will aid in the usage of the methods below. 
The method drawPebble () returns an ArrayList of integers where using the draw10 method in the MyBag class will draw 10 pebbles randomly chosen from either 3 black bags(X, Y, and Z).
The method is100 () will return a Boolean where if the sum of all the pebbles in the players hand is equal to 100 will return true.
The method discard () will return an integer which is removed from the players hand and placed in the correct corresponding white bag.
The method draw () will return an integer which is chosen at random from the 3 black bags(X, Y, and Z) using the method drawOne in the MyBag class. 
Within the run () method which is synchronized so a Race Condition between threads would not occur, we assign the players hand to the ArrayList returned in drawPebble. Inside the while loop the method is100 is checked first to check if the players hand is equal to 100, if so the game will end by changing the Boolean done to true, then prints out the player who won, if not then the player will discard a pebble using the method discard () and then draw a pebble using the method draw (),  the outputs from draw, discard and the players hand are written to a newly created file named after the current player stating the action of the player throughout the game, this will go on until the is100 () method returns true thus changing done to true and terminating the threads.
The bags are static and declared using the MyBag class.
A Boolean done is declared as static and equals false.
Within the main method we first use a scanner for the user to input the number of players and check if the input is in correct format, if incorrect the system will print “Invalid player entry!” and re-prompt the user to enter a value, if correct then the program will continue and ask the user to input a file 3 times, like before if the file does not exist the system will re ask the user to input a correct file, once the user does then the program will execute and the game will start. Here the players are instantiated and the corresponding bags are set correctly using the setCoBag () method.
