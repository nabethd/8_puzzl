import java.util.Scanner;

import static java.lang.System.exit;

public class Board{
    private int board[][];
    private int loc[];
    private int cost;
    private int g_n;




    Board() {
        board =new int[][] {{1, 2, 3},
                            {4, 5, 6},
                            {7, 8, 0}};

        loc = new int[]{2, 2};

    }

    Board(int n) {
        if (n==0){
            Scanner scanner = new Scanner(System.in);
            board =new int[3][3];
            loc =new int[2];
            System.out.println("Please enter your custom puzzle with white spaces separating each number \n" +
                    "and hit enter to go to the next row. Use 0 to represent the blank slot.");
            for(int i=0; i<3; i++) {
                String[] row;
                String line = scanner.nextLine();
                if(line == null) {
                    System.err.println("Please enter a valid layout!");
                    exit(1);
                }
                row = line.split(" ");
                for(int j=0; j<3; j++) {
                    board[i][j] = Integer.parseInt(row[j]);
                    if (board[i][j] == 0) {
                        loc[0] = i;
                        loc[1] = j;
                    }
                }
            }

        }
        if(n==1) {
            board = new int[][]{{1, 2, 3},
                                {4, 5, 6},
                                {7, 0, 8}};
            loc = new int[]{2, 1};
//            cost=getCost();
        }

        if(n==2) {
            board = new int[][]{{1, 2, 0},
                                {4, 5, 3},
                                {7, 8, 6}};
            loc = new int[]{0, 2};
//            cost=getCost();
        }

        if(n==3) {
            board = new int[][]{{0, 1, 2},
                                {4, 5, 3},
                                {7, 8, 6}};
            loc = new int[]{0, 0};
//            cost=getCost();
        }

        if(n==4) {
            board = new int[][]{{8, 7, 1},
                                {6, 0, 2},
                                {5, 4, 3}};
            loc = new int[]{1, 1};
//            cost=getCost();
        }

        if(n==5) {
            board = new int[][]{{4, 1, 2},
                                {7, 0, 3},
                                {8, 5, 6}};
            loc = new int[]{2, 2};
//            cost=getCost();
        }
    }

    Board(int[][] b, int []l, int g){
        this.board = new int[b.length][b[0].length];
        for(int i=0; i<b.length; i++)
            for(int j=0; j<b[0].length; j++)
                this.board[i][j]=b[i][j];
        this.loc=new int[2];
        this.loc[0]=l[0];
        this.loc[1]=l[1];
        this.cost=getCost();
        this.g_n=g;

    }

    public int[][] state(){     // current board State.
        return board;
    }

    public void setG_n(int n){
        this.g_n=n;
    }
    public int getG_n(){
        return this.g_n;
    }

    public int[]getLoc(){
        return this.loc;
    }
    public void setLoc(int[]n){
        this.loc[0]=n[0];
        this.loc[1]=n[1];
    }

    public int getCost() {

        int c=0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 1)
                    c = c + i + j;
                else if (board[i][j] == 2)
                    c = c + i + Math.abs(j-1);
                    else if (board[i][j] == 3)
                        c = c + i +Math.abs(j-2);
                        else if (board[i][j] == 4)
                            c = c + Math.abs(i-1) + j;
                            else if (board[i][j] == 5)
                                c = c + Math.abs(i - 1) + Math.abs(j - 1);
                                else if (board[i][j] == 6)
                                    c = c + Math.abs(i - 1) + Math.abs(j - 2);
                                     else if (board[i][j] == 7)
                                         c = c + Math.abs(i-2) + j;
                                         else if (board[i][j] == 8)
                                             c = c + Math.abs(i-2) + Math.abs(j - 1);
            }
        }
        if(this.cost==0)
            this.cost=c;
        return c;
    }

    public Board Down() {
        if (loc[0] == 2) {
//            System.out.println("Can't go up");
            return null;
        }
        else {
            board[loc[0]][loc[1]] = board[loc[0] + 1][loc[1]];
            loc[0]++;
            board[loc[0]][loc[1]]=0;

        }
//        print_board();
        this.cost=getCost();
        return this;
    }

    public Board Up() {
        if (loc[0] == 0){
//            System.out.println("Can't go down");
            return null;
        }
        else {
            board[loc[0]][loc[1]] = board[loc[0] - 1][loc[1]];
            loc[0]--;
            board[loc[0]][loc[1]]=0;
        }
//        print_board();
        this.cost=getCost();
        return this;
    }

    public Board Left() {
        if (loc[1] == 0 ){
//            System.out.println("Can't go right");
            return null;
        }
        else {
            board[loc[0]][loc[1]] = board[loc[0]][loc[1] - 1];
            loc[1]--;
            board[loc[0]][loc[1]]=0;
        }
//        print_board();
        this.cost=getCost();
        return this;
    }

    public Board Right() {
        if (loc[1] == 2){
//           System.out.println("Can't go left");
            return null;
        }
        else {
            board[loc[0]][loc[1]] = board[loc[0]][loc[1] + 1];
            loc[1]++;
            board[loc[0]][loc[1]]=0;
        }
//        print_board();
        this.cost=getCost();
        return this;
    }

    //TODO remove print board from up-down-left-right

// _ _ _
//|_|1|2|
//|4|5|3|
//|7|8|6|

    public void print_board() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0)
                    System.out.print("_ ");
                else
                    System.out.print(board[i][j]+" ");
            }
            System.out.println();

        }
        System.out.println();

    }

    public int get_score(){
        return (this.getCost()+ this.g_n);
    }


}

