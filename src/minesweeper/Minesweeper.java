package minesweeper;

import java.util.Arrays;
import java.util.Random;


import java.awt.*;
import javax.swing.*;

public class Minesweeper {


    int[][] board;
    int[][] boardShown;
    int  nbrMines;
    int size;
    int xsize;
    int ysize;
    int nbrSquares;
    


    public Minesweeper(int nbr, int sizex, int sizey ){
        nbrMines=nbr;
        board = new int[sizex][sizey];
        boardShown=new int[sizex][sizey];
        size = sizex*sizey;
        xsize=sizex;
        ysize=sizey;
        nbrSquares= (sizex*sizey)-nbrMines;

    }

    
    protected void placeMines() {

        int minesNumber = nbrMines;
        Random rd = new Random();

        while (minesNumber > 0) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if ((rd.nextInt(size+1) <= nbrMines) & (minesNumber > 0)) {
                        board[i][j] = -1;
                        minesNumber--;
                    }   
                }
            }
        } 
        
    }


    protected int checkNeighbour(int i,int j, int target, int[][] location) {

        int nbrTarget = 0;
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y<2 ; y++){
                if ((i+x >= 0)&(j+y >= 0)&(i+x <= xsize-1)&(j+y <= ysize-1)) {
                    if(location[i+x][j+y] == target){
                        nbrTarget++;
                    }
                }
            }
        }
        return nbrTarget;                     
    }



    protected void placeNumbers(){


        for (int[] board : boardShown) {
            Arrays.fill(board, 9);
        }

        
        for (int i = 0; i < xsize; i++) {
            for (int j = 0; j < ysize; j++) {
                if (board[i][j] != -1) {
                    board[i][j] = checkNeighbour(i,j,-1,board);

                }
            }
        }

    }



    protected void removeButton( JFrame win, JButton[][] sq, int x,int y) {
        //win.remove(sq[x][y]);
        sq[x][y].setText(String.format("%d", boardShown[x][y]));
        sq[x][y].setBackground(Color.gray);
        win.repaint();
        
    }

    protected void buttonToFlag(JFrame win, JButton[][] sq, int x,int y) {
        ImageIcon flag = new ImageIcon("src/minesweeper/flag.png");
        sq[x][y].setIcon(flag);
        sq[x][y].setBackground(Color.gray);
        win.repaint();
    }


    protected void revealArea(int x, int y,JFrame win, JButton[][] sq){

       wonCheck(win);
        
        for (int i = -1; i < 2; i++) 
        {
            for (int j = -1; j < 2; j++) 
            {
                if ((x+i >= 0)&(y+j <= ysize-1)&(y+j >= 0)&(x+i <= xsize-1)) 
                {
                    if((boardShown[x+i][y+j] == 9)&(board[x+i][y+j] == 0))
                    {
                    boardShown[x+i][y+j] = board[x+i][y+j];
                    removeButton(win,sq,x+i,y+j);
                    nbrSquares--;
                    revealArea(x+i, y+j,win,sq);
                    }
                    else if((boardShown[x+i][y+j] == 9)&(board[x+i][y+j] != -1))
                    {
                        nbrSquares--;
                        boardShown[x+i][y+j] = board[x+i][y+j];
                        removeButton(win,sq,x+i,y+j);
                    }
                }
            }
        }

    }

    protected void revealArround(int x, int y,JFrame win, JButton[][] sq) {

        wonCheck(win);

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((x+i >= 0)&(y+j <= ysize-1)&(y+j >= 0)&(x+i <= xsize-1)) {
                    if ( (board[x+i][y+j] == -1)&&(boardShown[x+i][y+j] != 66)){
                        loose(win);
                    }else if((boardShown[x+i][y+j] == 9)&(board[x+i][y+j] != 0)){
                        nbrSquares--;

                        boardShown[x+i][y+j] = board[x+i][y+j] ;
                        removeButton(win,sq,x+i,y+j);
                    }else{revealArea(x+i, y+j,win,sq);}
                    
                }
            }
        }
            
    }
        


    protected void revealTile(int x, int y,JFrame win, JButton[][] sq){

        if (boardShown[x][y] != 66) {

            if (board[x][y] == -1) {

                loose(win);
            }
            else{
                revealArea(x, y,win,sq);

            }

            wonCheck(win);
                
            }


    }
        
    protected void revealNeighbour(int x, int y,JFrame win, JButton[][] sq){

        int nbr = board[x][y];
        if (nbr == checkNeighbour(x,y,66,boardShown) ){
            revealArround(x, y,win,sq);
        }

        wonCheck(win);


    }

    protected void placeFlage(int x, int y,JFrame win, JButton[][] sq){
        if( boardShown[x][y] == 9){
            boardShown[x][y] = 66;
            nbrMines--;
            buttonToFlag(win,sq,x,y);
        }
        else if( boardShown[x][y] == 66){
            boardShown[x][y] = 9;
            nbrMines++;
            removeFlag(win,sq,x,y);
     
        }


    }

    protected void removeFlag(JFrame win, JButton[][] sq,int x, int y){
            sq[x][y].setIcon(null);
            sq[x][y].setBackground(null);
            win.repaint();
        }
    

    protected void wonCheck(JFrame win) {
        if(nbrSquares <= 0){
            String[] responses = {"Return to Menu","QUIT"};
            if (JOptionPane.showOptionDialog(win,
            "YOU WON", 
            "YOU WON", 
            JOptionPane.YES_NO_OPTION, 
            0, 
            new ImageIcon("smiling.png"),
            responses, 
            responses[0]) == JOptionPane.YES_OPTION) {
                nbrSquares = 3333;
                win.dispose();
                Main.createMenuUi();

            } else {
                nbrSquares = 3333;
                win.dispose();
            }

        }

    }

    protected void loose(JFrame win) {

        String[] responses = {"Return to Menu","QUIT"};
        if (JOptionPane.showOptionDialog(win,
        "YOU LOST", 
        "YOU LOST", 
        JOptionPane.YES_NO_OPTION, 
        0, 
        null,
        responses, 
        responses[0]) == JOptionPane.YES_OPTION) {
            win.dispose();
            Main.createMenuUi();
        } else {
            win.dispose();
        }
        boardShown = board;


        
    }






}
