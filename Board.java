import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;

public class Board extends JFrame implements ActionListener{

    //Declaring all new priv variables
    private int bIndex = 0;

    //creating variable to hold the blank index
    private int blankspaceindex = 0;

    //Storing position to hold the undo index(during undo func)
    private int indexundo;

    //Storing position to hold the blank index(during undo func)
    private int blankundo;

    //Setting up time clock
    private Timer timeClock;

    //initialzing delay
    private int delay = 1000;

    //declaring grid
    private GridLayout grid1;

    //Declaring two new vectors to hold ints in
    public Vector<Integer> vectorone = new Vector<Integer>();
    public Vector<Integer> vectortwo = new Vector<Integer>();
    // private Container container;

    //creating String arrray to be my display board array
    private final String array[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11", "12", "13", "14", "15", " "};
    //creating String RESET to be my RESET board array
    private final String reset1[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11", "12", "13", "14", " ", "15"};
    JFrame frame = new JFrame ();
    //Creating a button
    private JButton button1[];

    //constructor for the board
    public Board (){
        //Settting up grid x and y
        grid1 = new GridLayout( 4,4 );
        //Getting the length of the grid
        button1 = new JButton[array.length];
        //Setting the layour for the grid in Jframe
        frame.setLayout(grid1);
        //Setting size of window
        frame.setSize(800,600);
        //Creating the board
        boardCreation();
        //Creating menu buttons
        MenuButtons();
        //Creating time clock
        timeClock = new Timer(delay, new TimerHandler());
        //Setting board visibility to true
        frame.setVisible(true);

    }

    //Creating the board
    public void boardCreation(){
        //Remove all contents from board
        frame.getContentPane().removeAll();
        //Shuffle the array
        shufflearray(array);
        //Getting inversion Number
        int invernum = inversions();

        //if inversion number is odd
        while(checkinver() == false ){
            //shuffle the array and re-calculate inversion
            shufflearray(array);
            invernum = inversions();
        }
        //Print out inversion
        System.out.println("Inversion count:" + invernum);

        //Create thr board array
        create16(array);
        //et it to visible
        frame.setVisible(true);
    }

    //Creating the 16 buttons
    public void create16(String array1[]){
        //FOr loop to loop through all 16 spaces
        for(int i = 0; i < 16; i++){
            //Creating a new button from array
            button1[i] = new JButton(array1[i]);
            //Making each buttton clickable
            button1[i].addActionListener(this);
            //Getting contents and adding it to the button
            frame.getContentPane().add(button1[i]);

        }
    }

    //Shhuffling the array
    public void shufflearray(String array2[]){

        //Creating a random num
        int randNum;
        //Creating temp string
        String temp;
        //Storing random number
        Random random = new Random();

        for(int i = 0; i < array2.length; i++){

            if( array[i] != " ") {
                //Generating random num
                randNum = random.nextInt(i + 1);
                //Swapping them
                temp = array[i];
                array[i] = array[randNum];
                array[randNum] = temp;
                //End of swapping
            }
        }

    }



    //TimeHandler class implementation to implement "Sliding effect"
    private class TimerHandler implements ActionListener {

        // handle button event
        public void actionPerformed( ActionEvent event )
        {
            //undo move function
            moveundofunc();
            //If vectors are empty stop the clock
            if(vectorone.isEmpty() && vectortwo.isEmpty()) {
                timeClock.stop();
            }
        }

    } // end private inner class TimerHandler



    //Debugging purposes for vector one
    public void vectorprint1(){

        for(int i = 0;i < vectorone.size(); i++){

            System.out.println("" + vectorone.get(i));
        }
    }

    //Debugging purposes for vector two
    public void vectorprint2(){

        for(int i = 0;i < vectortwo.size(); i++){

            System.out.println("" + vectortwo.get(i));
        }
    }



    //implementing bfs function(INCOMPLETE DOESNT WORK CORRECTLY LIKE IT SHOULD
    public void BFS(){

        //Create an empty queue and an empty set
        Queue<Tuple> queue = new LinkedList<Tuple>();
        HashSet<String[]> set =  new HashSet<String[]>();

        //Let P represent the present arrangement of puzzle pieces.
        String []p = array;

        //Create the tuple (P, null)
        queue.add(new Tuple(p));

        //Add (P, null) to the end of the queue (enqueue)
        set.add(p);

        //Add P to the set (mark it as visited)

        //Declaring currrent
        Tuple current;

        //While the queue is not empty
        while(!queue.isEmpty()){
            //Current = front item in the queue; remove this
            current = queue.remove();

            //If arrangement in Current is the solved puzzle
            if(solvedCombination(current.getBoard()) ==  true){
                System.out.println("YOU WONNNNNN"+ "\n");
                break;
            }

            //For each arrangement R that can be formed by moving one piece from
            //arrangement in Current
            try {
                for (int i = 0; i < 4; i++) {
                    String[] left = findLeft(current.getBoard());
                    if ((left != null) && (!set.contains(left))) {
                        //System.out.println("Current:\n");
                        showboard(current.getBoard());

                        // System.out.println("Left:\n");
                        showboard(left);
                        Tuple newtupleleft = new Tuple(left, FindEmptybutton(left));

                        //add tuple
                        queue.add(newtupleleft);
                        set.add(left);

                    }


                    String[] right = findRight(current.getBoard());
                    if ((right != null) && (!set.contains(right))) {
                        //  System.out.println("Right:\n");
                        showboard(right);
                        Tuple newtupleRight = new Tuple(right, FindEmptybutton(right));

                        //add tuple
                        queue.add(newtupleRight);
                        set.add(right);
                    }

                    String[] top = findTop(current.getBoard());
                    if ((top != null) && (!set.contains(top))) {
                        // System.out.println("Top:\n");
                        showboard(top);
                        Tuple newtupleTop = new Tuple(top, FindEmptybutton(top));

                        //add tuple
                        queue.add(newtupleTop);
                        set.add(top);
                    }

                    String[] bottom = findBottom(current.getBoard());
                    if ((bottom != null) && (!set.contains(bottom))) {
                        // System.out.println("Bottom:\n");
                        showboard(bottom);
                        Tuple newtupleBottom = new Tuple(bottom, FindEmptybutton(bottom));

                        //add tuple
                        queue.add(newtupleBottom);
                        set.add(bottom);
                    }


                }
                //catching array out of bounds just incase
            }catch (ArrayIndexOutOfBoundsException a) {}
        }
        //If the queue is empty print no sol
        if(queue.isEmpty()){
            //No solution exists (the randomization created an unsolvable
            System.out.println("no Sol\n");
        }

    }

    //Function to check the inversion number if even or odd
    public boolean checkinver(){
        int var = inversions();

        if(( var % 2) == 0) {
            return true;
        }
        else{
            return false;
        }
    }

    //calculating inversions
    public int inversions(){
        int inver = 0;

        //two forloops looping through each of the positions
        for(int i = 0; i < array.length- 1; i++){
            for(int a = i; a < array.length - 1; a++){

                //If array at 1st is > then the rest of board then
                if(Integer.parseInt(array[i]) > Integer.parseInt(array[a]) && array[i] != " "){
                    //increment this variable
                    inver++;
                }
            }
        }

        //Subtract by one to get correct value
        inver = inver - 1;
        //System.out.println("inversion count:" + inver);

        //Return the inver
        return inver;
    }


    //Function to show the board
    public void showboard(String[] boardshow){
        for (String s: boardshow) {
            System.out.print(s + " ");
        }

        System.out.println();
    }

    //Finding empty button function
    public int FindEmptybutton(String[] b){

        //Go through the array
        for(int i = 0; i <array.length;i++) {
            //If " " found return the position
            if (b[i] == " "){
                return i;
            }
        }
        //Should never get here
        return -1;
    }

    //Find the left Side of the board
    public String[] findLeft(String[] b){
        String[] tempboard = new String[16];

        //copy elements over
        for(int i = 0; i < b.length;i++){

            tempboard[i] = b[i];
        }
        int index = FindEmptybutton(b);

        if( index != 0){
            //While index !+ 0  look around and switch/save positions
            tempboard[index] = b[index - 1];
            tempboard[index - 1] = b[index];
            return tempboard;
        }
        //shouldnt get here
        return null;

    }

    //Find the right Side of the board
    public String[] findRight(String[] b){
        String[] tempboard = new String[16];

        //copy elements over
        for(int i = 0; i < b.length;i++){

            tempboard[i] = b[i];
        }
        //Set index to empty button
        int index = FindEmptybutton(b);

        if( index != 0){
            //While index !+ 0  look around and switch/save positions
            tempboard[index] = b[index + 1];
            tempboard[index + 1] = b[index];
            return tempboard;
        }
        //shouldnt get here
        return null;
    }

    //Find the bottom Side of the board
    public String[] findBottom(String[] b){
        String[] tempboard = new String[16];

        //copy elements over
        for(int i = 0; i < b.length;i++){

            tempboard[i] = b[i];
        }
        //Set index to empty button
        int index = FindEmptybutton(b);

        if( index != 0){
            //While index !+ 0  look around and switch/save positions
            tempboard[index] = b[index + 4];
            tempboard[index + 4] = b[index];
            return tempboard;
        }
        //shouldnt get here
        return null;
    }

    //Find the top Side of the board
    public String[] findTop(String[] b){
        String[] tempboard = new String[16];

        //copy elements over
        for(int i = 0; i < b.length;i++){

            tempboard[i] = b[i];
        }
        //Set index to empty button
        int index = FindEmptybutton(b);

        if( index != 0){
            //While index !+ 0  look around and switch/save positions
            tempboard[index] = b[index - 4];
            tempboard[index  - 4] = b[index];
            return tempboard;
        }
        //shouldnt get here
        return null;
    }

    //CHecking for a solved combination
    public boolean solvedCombination(String[] b) {
        //Setting board to true
        boolean boardsolved = true;

        //looping through the array
        for (int i = 0; i < array.length; i++) {
            //System.out.println(" here\n");
            //As long as it doesnt equal blank sapce
            if(b[i] != " ") {
                //Change type from string to int to comapre array
                if (Integer.parseInt(b[i]) != i + 1) {
                    //Break condition set to false and return
                    boardsolved = false;
                    break;
                }
            }
        }
        //Return board solved
        return boardsolved;
    }


    //Function to undo the move
    public void moveundofunc() {

        int temp1 = 0;
        int temp2 = 0;
        //Getting the undo index
        indexundo = vectorone.lastElement();
        //Getting the blank index
        blankundo = vectortwo.lastElement();

        //Doing for loop to get the correct temp otherwise code messes up when removing elemnts at bottom
        for(int i = 0; i < vectorone.size();i++){
            if(vectorone.contains(indexundo)){
                temp1 = i;
            }

        }

        //Doing for loop to get the correct temp otherwise code messes up when removing elemnts at bottom
        for(int i = 0; i < vectortwo.size();i++){

            if(vectortwo.contains(blankundo))
            {
                temp2 = i;
            }

        }

        if(vectortwo.isEmpty() == false&& vectorone.isEmpty() ==  false) {

            //Creating a move backwards button setting it to get text of button of undoindex
            String moveBackwardsButton = button1[indexundo].getText();
            //button @ indexundo get set to button1 of blankundo
            button1[indexundo].setText((button1[blankundo].getText()));
            //Blank undo set to movebackwards button
            button1[blankundo].setText(moveBackwardsButton);

            //String arrray movebackwards
            String moveBackwardsarray = array[indexundo];
            //The array at index undo get set to array at blank undo
            array[indexundo] = array[blankundo];
            //array at blank undo gets set to movebackwards array
            array[blankundo] = moveBackwardsarray;

            // System.out.println("Vectorone\n");
            //vectorprint1();
            //System.out.println("Vectortwo\n");
            //vectorprint2();

            //removing each element
            vectorone.removeElementAt(temp1);

            // System.out.println("Vectorone\n");
            //vectorprint1();


            //Removing each element
            vectortwo.removeElementAt(temp2);
            //System.out.println("Vectortwo\n");
            //vectorprint2();
        }
    }

    //Function to swap buttons
    public void SwappingButtons(JButton b){

        for(int i =0; i < button1.length; i++){

            if(b.equals(button1[i])){

                bIndex = i;
            }

        }

        //Having an array for different position
        int differentposition[] = {-1,1,-4,4};

        for(int j =0; j < button1.length; j++){
            if(button1[j].getText() == " ") {

                //Seting blankspace to j
                blankspaceindex = j;
            }
        }

        //Going throught diffposition array
        for(int i = 0; i < differentposition.length; i++) {


            try {
                //If its equal to space
                if (button1[bIndex + differentposition[i]].getText() == " ") {
                    //Store array at button index at arraymove
                    String arrayMove = array[bIndex];

                    //Array at bindex set to bindex + position(-1,1,4,-4)
                    array[bIndex] = array[bIndex + differentposition[i]];

                    //Storing array at bindex+pos to arraymove
                    array[bIndex + differentposition[i]] = arrayMove;

                    //Gettting text set to button at Bindex
                    String gettingtext = button1[bIndex].getText();

                    //Array at bindex set to bindex + position(-1,1,4,-4)
                    button1[bIndex].setText(button1[bIndex + differentposition[i]].getText());

                    //button at this position set ti text of getting text
                    button1[bIndex + differentposition[i]].setText(gettingtext);

                    //Adding bindex to vector 1
                    vectorone.add(bIndex);
                    //System.out.println("Bindex:" + bIndex + "\n");
                    //ADding blankindex to vector two
                    vectortwo.add(blankspaceindex);
                    //System.out.println("blankspaceindex:" + blankspaceindex+ "\n");

                    //break
                    break;
                }
                //Cteaching out of bounds just in case
            }catch (ArrayIndexOutOfBoundsException a) {}
            // frame.getContentPane().removeAll();
            // create16(array);
            // frame.setVisible(true);

            //sETTING FRAME TO VISIBLE
            frame.setVisible(true);
        }
    }



    //overriding to create my own actionPerformed
    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton buttonclicked = (JButton)e.getSource();
        SwappingButtons(buttonclicked);
        //buttonclicked.setText("Hello");

    }

    public void MenuButtons(){
        //Creating a bar
        JMenuBar menuBar = new JMenuBar();

        //First menu EXIT, implementing action listener
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(
                new ActionListener() {  // anonymous inner class
                    // display message dialog when user selects About...
                    public void actionPerformed( ActionEvent event )
                    {
                        System.exit( 0 );
                    }
                }  // end anonymous inner class
        ); // end call to addActionListener

        //2nd menu item ABOUT implementing via action listener
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(
                new ActionListener() {  // anonymous inner class
                    // display message dialog when user selects About...
                    public void actionPerformed( ActionEvent event )
                    {
                        JOptionPane.showMessageDialog( frame,
                                "Project 2 Cs342 by Jakub Glebocki",
                                "about", JOptionPane.PLAIN_MESSAGE );
                    }
                }  // end anonymous inner class
        ); // end call to addActionListener

        //Creating HELP button via action listener
        JMenuItem help = new JMenuItem("Help!");
        help.addActionListener(
                new ActionListener() {  // anonymous inner class
                    // display message dialog when user selects About...
                    public void actionPerformed( ActionEvent event )
                    {
                        JOptionPane.showMessageDialog( frame,
                                "There are 6 button options available for this game. They are: \n" +
                                        " Exit-> terminates the game\n About->displays general information\n Mix numbers->randomizes board numbers\n" +
                                        " Undo move -> undoes last user move\n Undo all moves- undoes all moves made by player\n AutoSolve-> Solves the game for you\n" +
                                        " Reset->Resets the board",
                                "help", JOptionPane.PLAIN_MESSAGE );
                    }
                }  // end anonymous inner class
        ); // end call to addActionListener

        //Creating a mix number button via action listener
        JMenuItem MixNumbers = new JMenuItem("MixNumbers");
        MixNumbers.addActionListener(
                new ActionListener() {  // anonymous inner class
                    // display message dialog when user selects About...
                    public void actionPerformed( ActionEvent event )
                    {
                        boardCreation();
                    }
                }  // end anonymous inner class
        ); // end call to addActionListener

        //Creating UNDOPREVMOVE button via action listener
        JMenuItem UndoPrevMove = new JMenuItem("UndoPrevMove");
        UndoPrevMove.addActionListener(
                new ActionListener() {  // anonymous inner class
                    // display message dialog when user selects About...
                    public void actionPerformed( ActionEvent event )
                    {
                        try {
                            moveundofunc();

                        } catch (NoSuchElementException x) {

                        }

                        //to perform number shuffle...
                    }
                }  // end anonymous inner class
        ); // end call to addActionListener

        //Creating undoallMOVES via action listener
        JMenuItem UndoAllMoves = new JMenuItem("UndoAllMoves");
        UndoAllMoves.addActionListener(
                new ActionListener() {  // anonymous inner class
                    // display message dialog when user selects About...
                    public void actionPerformed( ActionEvent event )
                    {
                        timeClock.start();
                    }

                }  // end anonymous inner class
        ); // end call to addActionListener


        //Creating button AUTSOLVE to completely solve the board
        JMenuItem autosolve = new JMenuItem("Autosolve");
        autosolve.addActionListener(
                new ActionListener() {  // anonymous inner class
                    // display message dialog when user selects About...
                    public void actionPerformed( ActionEvent event )
                    {
                        System.out.println("BFS action listnenr");
                        BFS();
                    }
                }  // end anonymous inner class
        ); // end call to addActionListener

        //Creating menu item reset to reset the board
        JMenuItem reset = new JMenuItem("Reset");
        reset.addActionListener(
                new ActionListener() {  // anonymous inner class
                    // display message dialog when user selects About...
                    public void actionPerformed( ActionEvent event )
                    {
                        frame.getContentPane().removeAll();
                        create16(reset1);
                        frame.setVisible(true);
                    }
                }  // end anonymous inner class
        ); // end call to addActionListener


        //ADDING ALL the menu items to the menu bar
        menuBar.add(exit);
        menuBar.add(about);
        menuBar.add(help);
        menuBar.add(MixNumbers);
        menuBar.add(UndoPrevMove);
        menuBar.add(UndoAllMoves);
        menuBar.add(autosolve);
        menuBar.add(reset);
        //Setting the menu Bar
        frame.setJMenuBar(menuBar);
    }
}
