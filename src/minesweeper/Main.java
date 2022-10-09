package minesweeper;
import javax.swing.*;
import java.awt.*; 

public class Main {
    public static void main(String[] args) {
   
        createMenuUi();
    }


    protected static void createMenuUi() {

        JFrame win = new JFrame("Minesweeper Clone");
        JPanel menu = new JPanel();
        JPanel titleContainer = new JPanel();
        JPanel BoxContainer = new JPanel();
        JLabel title = new JLabel("MINESWEEPER CLONE");
        JLabel instruct1 = new JLabel("Press RIGHT MOUSE button to put/remove a flag on a mine");
        JLabel instruct2 = new JLabel("If number of flag correspond to a tile, press the tile to reveal area ");
        JLabel instruct3 = new JLabel("You win when all the tiles are revealed");
        JButton newGame = new JButton("New Game");


        title.setFont(new Font("Serif", Font.PLAIN, 24));
        BoxContainer.setLayout(new BoxLayout(BoxContainer, 1));
        titleContainer.setLayout(new BoxLayout(titleContainer, 0));

        String[] optionsToChoose = {"8x8 : 10 mines", "10x10 : 20 mines", "12x12 : 30 mines", "16x16 : 40 mines"};

        JComboBox<String> jComboBox = new JComboBox<>(optionsToChoose);
        win.setDefaultCloseOperation(win.EXIT_ON_CLOSE);
        
        newGame.addActionListener(e->{

                int mines = 10;
                int x = 8;
                switch (jComboBox.getItemAt(jComboBox.getSelectedIndex())) {
                    case "8x8 : 10 mines":
                        x=8;
                        mines=10;
                        break;
                
                    case "10x10 : 20 mines":
                        x=10;
                        mines=20;
                        break;
                    case "12x12 : 30 mines":
                        x=12;
                        mines=30;
                        break;
                    case "16x16 : 40 mines":
                        x=16;
                        mines=40;
                        break;
                }

                Minesweeper test = new Minesweeper(mines, x,x);
                test.placeMines();
                test.placeNumbers();
                new Ui(800,800,test);
                win.dispose();
       
            
        });
        
        win.add(BoxContainer);
        titleContainer.add(title);
        BoxContainer.add(titleContainer);
        BoxContainer.add(menu);
        menu.add(newGame);
        menu.add(jComboBox);
        menu.add(instruct1);
        menu.add(instruct2);
        menu.add(instruct3);
        win.setSize(500,300);
        win.setResizable(false);
        
        win.setVisible(true);
        
    }

}


