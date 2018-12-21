import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class MenuButtons extends Board {

    //creating String RESET to be my RESET board array
    private final String reset1[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11", "12", "13", "14", " ", "15"};

    public MenuButtons(){
       //MenuButtons();
        //super();
    }
    //Setting up menu buttons

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
                        //timeClock.start();
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
