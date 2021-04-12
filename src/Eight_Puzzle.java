import java.util.*;


public class Eight_Puzzle {
    static int[][] goalState = {{1, 2, 3},
                                 {4, 5, 6},
                                 {7, 8, 0}};
    static int nodesExpanded = 0;
    static int maxQueueSize = 1;
    public static int Debug =0;


    static PriorityQueue<Board> queue = new PriorityQueue<>(Comparator.comparing(Board::get_score));  // Sort queue by f_n value
    static ArrayList<Board> explored = new ArrayList<>();  // previously explored states
    static Map<Board, Board> cameFrom= new HashMap<Board, Board>();
    public static void simple_game(int difficulty){
        System.out.println("Use the  W - A -S -D buttons to move the tiles into the empty space ");
        System.out.println("________________________________________");
        System.out.println("if you need a hint press 'H' ");
        System.out.println("________________________________________");
        System.out.println("to close the game press '0' ");
        System.out.println("________________________________________");
        List<Board> path = new ArrayList<>();
        Board board = new Board(difficulty);
        board.print_board();
        char key;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            key = scanner.next().charAt(0);
            if (key == 104) {
                System.out.println("this is the next best State, GOOD LUCK!");
                path = A_Star_Manhattan_Distance(simple_copy_board(board));
                board = simple_copy_board(path.get(1));
                board.print_board();
            }
            if (key == 119) {
                if (board.getLoc()[0] == 2)
                    System.out.println("Can't go up");
                else
                    board.Down();
                board.print_board();
            }
            if (key == 115){
                if (board.getLoc()[0] == 0)
                    System.out.println("Can't go down");
                else
                    board.Up();
                board.print_board();
            }
            if(key==97)
            {
                if (board.getLoc()[1] == 2)
                    System.out.println("Can't go left");
                else
                    board.Right();
                board.print_board();
            }
            if(key==100){
                if (board.getLoc()[1] == 0)
                    System.out.println("Can't go right");
                else
                    board.Left();
                board.print_board();
            }
            if(key==48)
                break;
            if(BoardEqual(board,goalState)) {
                System.out.println("You Won !!!!! ");
                break;
            }
        }
    }

    public static boolean BoardEqual(Board b, int [][] g){
        return Arrays.deepEquals(b.state(), g);
    }

    public static List<Board> A_Star_Manhattan_Distance(Board current_board){
        Board b =new Board(current_board.state(),current_board.getLoc(),0 );
        queue.add(b);
        explored.add(b);
        while (true){
            if(queue.isEmpty()) {   // failed
                System.out.println("No Possible Answer");
                return null;
            }
            ArrayList<Board> children;
            Board topBoard = queue.remove();
            if(BoardEqual(topBoard,goalState))  // successes
                return(reconstruct_path(cameFrom, topBoard));

            else {  // keep expanding
                children = getChildren(topBoard);
                if(children.size()==0) {
                    System.out.println("Top node in queue has no descendants, going to next node.\n");
                    continue;  // go to next node in queue if topNode has no descendants
                }
                nodesExpanded++;
                for(Board child : children) {
                    if(!seen(child)) {
                        queue.add(child);
                        explored.add(child);
                        if(queue.size() > maxQueueSize) maxQueueSize = queue.size();
                    }
                }
            }
        }
    }

    public static List<Board> reconstruct_path(Map <Board,Board> cameFrom, Board topBoard) {
        List<Board> ans = new ArrayList<>();
        ans.add(topBoard);
        int i=0;
        while(true){
            if( ans.get(i)==null)
                break;
            ans.add(cameFrom.get(ans.get(i)));
            i++;
        }
        Collections.reverse(ans);
        ans.remove(0);
        return ans;
    }
    public static void Print_path(List<Board> path){
        for(int i=0;i< path.size();i++)
            path.get(i).print_board();
        System.out.println("sum of moves: "+ (path.size()-1));

    }

    public static ArrayList<Board> getChildren(Board b){
        ArrayList<Board> retArray = new ArrayList<>();
        Board curBoard =hard_copy_board(b);
        int g_n = curBoard.getG_n();

        if(curBoard.Up()!=null) {
            curBoard.setG_n(g_n++);
            Board c=hard_copy_board(curBoard);
            retArray.add(c);
            cameFrom.put(c,b);
        }
        curBoard =hard_copy_board(b);
        if(curBoard.Down()!=null) {
            curBoard.setG_n(g_n++);
            Board c=hard_copy_board(curBoard);
            retArray.add(c);
            cameFrom.put(c,b);
        }
        curBoard =hard_copy_board(b);
        if(curBoard.Left()!=null) {
            curBoard.setG_n(g_n++);
            Board c=hard_copy_board(curBoard);
            retArray.add(c);
            cameFrom.put(c,b);
        }
        curBoard =hard_copy_board(b);
        if(curBoard.Right()!=null) {
            curBoard.setG_n(g_n++);
            Board c=hard_copy_board(curBoard);
            retArray.add(c);
            cameFrom.put(c,b);
        }
        return retArray;
    }

    public static boolean seen(Board b) {
        for(Board explored_board : explored) {
            if(BoardEqual(explored_board,b.state()))
                return true;
        }
        return false;
    }

    public static Board hard_copy_board(Board b){
        return (new Board(b.state(), b.getLoc(),b.getG_n()));
    }
    public static Board simple_copy_board(Board b){
        return (new Board(b.state(), b.getLoc(), 0));
    }
    public static void main(String[] args) {
        System.out.println("+=================================+");
        System.out.println("|            Welcom to            |");
        System.out.println("|          8-Puzzel Solver        |");
        System.out.println("+=================================+\n");
        System.out.println("Type 1-5 to choose difficulty or 0 for custom game  .");
        int difficulty;
        Scanner scanner = new Scanner(System.in);
        difficulty = Integer.parseInt(scanner.nextLine());
        while(difficulty<0 || difficulty>5) {
            System.err.println("Please enter a correct difficulty: 0-5!");
            difficulty = Integer.parseInt(scanner.nextLine());
        }

//        simple_game(difficulty);
        Board board = new Board(difficulty);
        board.print_board();
        System.out.println("Good Luck!!!");
        Print_path(A_Star_Manhattan_Distance(board));
//        System.out.println(nodesExpanded);
//        System.out.println("Board cost"+board.Right().getCost());

//        System.out.println("Board cost"+board.Down().getCost());

    }


}
