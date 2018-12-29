****************
* CircuitTracer
* CS221-1
* 12/10/2017
* Nabil Rahman
**************** 

OVERVIEW:

 The program reads searches for the shortest paths between start and end points on a circuit board as it reads data from an input file using either a stack or queue as underlying search state storage structure and displays output to either console or to a GUI panel according to options specified via command-line arguments from the user.


INCLUDED FILES:
 
 * CircuitBoard.java - Source file.
 * CircuitTracer.java - Source file.
 * CircuitTracerGUIPanel.java - Source file.
 * InvalidFileFormatException.java - Source file.
 * OccupiedPositionException.java - Source file.
 * Storage.java - Source file.
 * Style.java - Source file
 * TraceState.java - Source file. 
 * README - Readme file


COMPILING AND RUNNING:

 To compile the program

 $ javac CircuitTracer.java
 
 To run the program with stack implementation and print output on the console
 
 $ java CircuitTracer -s -c grid.dat
 
 To run the program with stack implementation and show output in a GUI
 
 $ java CircuitTracer -s -g grid.dat
 
 To run the program with queue implementation and print output on the console
 
 $ java CircuitTracer -q -c grid.dat

 To run the program with queue implementation and show output in a GUI
 
 $ java CircuitTracer -q -g grid.dat
 

ANALYSIS:

 1. How does the choice of Storage configuration (stack vs queue) affect the order in which paths 
 are explored in the search algorithm? 

 At first, let's assume that the method we use to find adjacent cells works in this order: 
 RIGHT, LEFT, UP, DOWN. Now, we know that stack stores elements in LIFO order. So, while
 retrieving elements from stack, the point we found last will be expanded or traced first.

 However, since queue stores elements in FIFO order, queue implementation will give us routes in a
 different order than stack implementation. While retrieving elements from queue, the point
 we found first will be expanded or traced first.

 Though our algorithm is the same in both cases, the order in which paths are explored would be
 different.
 
 2. Is the total number of search states (possible paths) affected by the choice of stack or queue?

 Since our algorithm is the same for both cases, the number of search states won't be affected by 
 the choice of our underlying data structure, Though the order of finding paths would be different 
 for stack and queue, but in both cases, the program will go through all the possible paths and find 
 the shortest one.

 After writing the program, I've also tried to count the states. In both cases, it was the same
 number.
 
 3. What is the Big-Oh runtime for the search algorithm?

 Since there are no nested loops in the algorithm, the Big-Oh runtime for the given search algorithm is O(n).
 
 3. How is memory use (the maximum number of states in Storage at one time) affected by the choice 
 of underlying structure?

 Since, in both cases, we use the same algorithm, the program generates the same amount of trace states.
 So, it doesn't matter whether we use stack or queue. In both data structures, the memory used to store
 trace states would be the same. 
 
 4. Does using one of the storage structures usually find a solution faster than the other? Always?

 Well, it depends on the position of the start and the end points. Since a queue provides values in FIFO
 order, if the ending point is closer to the point that was stored first, then there's a possibility that 
 using a queue would find a solution faster. On the other hand, as a stack provides values in LIFO order,
 if the ending point is closer to the point that was stored first, then there's a possibility that stack 
 would find a solution faster. Mainly, it depends on the best and worst case of the problem.
 
 5. Does using either of the storage structures guarantee that the first solution found will be a shortest path?

 No, it doesn't. It doesn't matter what kind of storage we use because the main intention of storage is to store
 objects. Yes, in some cases, using one could give us a first solution that's correct. But, it's not guaranteed
 that it will happen all the time. Mainly, it depends on the order of cell exploration and the position of start 
 and end points. Meaning, it depends on our algorithm and the input provided.
 

DESIGN:
 
 The driver class for the program is CircuitTracer. When an input file is provided to the program, 
 it parses the file and generates an object of the CircuitBoard class. Now, while generating the board,
 the program also validates if the data is formatted in the right way.

 When it's done generating the board, it starts tracing the states by finding open states adjacent to the 
 starting point and keeps building the path until it reaches the ending point.

 The traces are stored in a Storage object. The underlying data structure used inside the storage gets 
 decided based on user's choice. 

 After the tracing is done for all the valid states, it looks for the shortest path and stores it in an ArrayList.

 Then, the output is shown on the console or in a GUI panel based on user's preference.

 The provided CircuitTracerGUIPanel has all the GUI elements inside. It uses the Style class for the customized
 dark metro design I created.

 The rest of the classes, more specifically, InvalidFileFormatException and OccupiedPositionException are the 
 expection classes. An InvalidFileFormatException gets thrown when the input file is not formatted correctly. 
 The OccupiedPositionException is thrown if the CircuitTracer tries to change the value of an occupied position.

TESTING:

 To test the program, I used the provided testprog shell script. The testprog script
 actually showed the difference between the expected result and the output provided
 by the program. The script showed that there was no difference.

 In addition to that, I also had to ensure if it generates output for all the sample
 circuit files. While running the program for the files, I had to fix some of the logics
 to make it work as expected. 

 For validation, I also had to try my program for all the invalid files as well. By having 
 a look at those invalid files, I was able to implement validation and exception handling 
 into my program properly.
 

DISCUSSION:

 To me, the project was pretty challenging to complete. I felt that the instructions
 provided in the website wasn't clear enough. However, I understand why it was given 
 that way, mainly to challege us. 

 While writing the program, it took me a while to understand how the search algorithm
 is supposed to work. To understand the logic, I had to think about it, look for other
 sources, read all the javadoc provided in the files. 

 Though the project was challenging, I was able to learn a lot of new stuff from it.
 Basically this project made me use most of the concepts we learned in the class as it
 involved implementing ADTs, analyzing the provided algorithm, debugging, exception handling, 
 understanding how searching works and so on. 


EXTRA CREDIT:

 For the extra credit, I implemented a GUI for the output generated by the program.
