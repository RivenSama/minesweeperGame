package minesweeper;

import java.awt.*;


import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import javax.swing.plaf.DimensionUIResource;


public class Ui {
    int sizeX;
    int sizeY;
    Minesweeper back;
    static int k=0;

    public  Ui(int sizex, int sizey,Minesweeper backend) {
        sizeX=sizex;
        sizeY=sizey;
        back=backend;
        createUi();
    }

    protected void createUi() {


        JFrame win = new JFrame("Minesweeper Clone");
        JPanel game = new JPanel();
        JPanel window =new JPanel();
        JPanel labels = new JPanel();

        JLabel minesLeft = new JLabel(String.format("%d", back.nbrMines));
        JButton restart = new JButton("Return to menu");
        labels.add(minesLeft);
        labels.add(restart);
        DimensionUIResource dim = new DimensionUIResource(sizeX, sizeY);
        win.setDefaultCloseOperation(win.EXIT_ON_CLOSE);
        win.setSize(dim);

        JButton[][] squares = new JButton[back.xsize][back.ysize];

        win.add(window);

        window.setLayout(new BoxLayout(window, BoxLayout.Y_AXIS));

        game.setLayout(new GridLayout(back.xsize,back.ysize,2,2));
        game.setMaximumSize(new Dimension(back.xsize*100,back.ysize*100));



        for (int i = 0; i < squares.length; i++) {

            for (int j = 0; j < squares[i].length; j++) 
            {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(10,10));
                button.setMaximumSize(new Dimension(10,10));
                squares[i][j] = button;
                
                int coordx = i;
                int coordy = j;

                button.addMouseListener(new MouseInputAdapter(){
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        if ((e.getButton() == 3)) {
                            back.placeFlage(coordx, coordy,win,squares);
                            minesLeft.setText(String.valueOf(back.nbrMines));
                           }
                    }
                });

            
                button.addActionListener(e -> 
                {
                     
                    if(button.getText() == ""){
                        back.revealTile(coordx, coordy,win,squares);
                    }else{back.revealNeighbour(coordx, coordy,win,squares);}

                });



                game.add(button);


            }
    
        }

        restart.addActionListener(e->{

            win.dispose();
            Main.createMenuUi();

        }

        
        );

        game.setPreferredSize(dim);
        window.add(labels);
        window.add(game);
        win.setResizable(false);
        win.setVisible(true);
    

    }









    
}
