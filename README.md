# cs61bl-proj3
CS61BL Project 3: Sliding Block Puzzle

This was a project for CS61BL. It was coded by a group of 3, David Yan(me), Siddharth Seth, and Quang Nguyen. The goal of this project was for us to devise an algorithm to solve a Sliding Block Puzzle using graph traversal. Because we could not find a way to accurately measure progress and implement an algorithm that would prioritize moves that would make progress, we settled with a depth-first traversal of the graph, making small optimizations along the way. The real reason our algorithm worked was because we managed to find a more optimized way to represent the board in order to cut down on the time it took to make a move. From this, we were able to pass 26 out of 28 of the hard tests. The tests we failed were those of large boards because our board representation works best with small boards with little open spaces. 

I led this group in both the devising of the algorithm and data structure and the coding. Additionally, after the project, I made a GUI to help better visualize the process. For the visualization, keep in mind that our algorithm was in no means optimized. It may seem to go in circles because it is a brute force algorithm and will go through all of the possible moves until it finds the right one. Also keep in mind that the solutions are not hard coded into the program. Everytime you click on solve, the program will run our algorithm, and then show the process once it is complete. This means that it may run slowly on other computers. The computer that I used to test on was a MacBook Pro, Processor: 2.4 GHz Intel Core i5, Memory: 4 GB 1600 MHz DDR3. 

To run the program yourself, go do the directory where you cloned from GitHub (most likely the one you are in now):

cd <directory>

Go into the src folder:

cd src

Compile and run the program:

javac Game.java
java Game