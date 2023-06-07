import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Rush to the opponent's row
 **/
class Player {

    static char[][] board;
    static long end;

    final static String letterDefs = "abcdefgh";

    static void initializeBoard(){
        board = new char[8][8];
        char[] enemyStart = {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'};
        char[] meStart = {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'};
        char[] empty = {' ',' ',' ',' ',' ',' ',' ',' ',};

        board[0] = enemyStart.clone();
        board[1] = enemyStart.clone();
        board[2] = empty.clone();
        board[3] = empty.clone();
        board[4] = empty.clone();
        board[5] = empty.clone();
        board[6] = meStart.clone();
        board[7] = meStart.clone();
    }

    static void printBoard(char[][] board){
        for(char[] b: board){
            System.err.println(Arrays.toString(b));
        }
    }

    static Move convertMove(String move, boolean white){
        if(move.equalsIgnoreCase("None")){
            return null;
        }

        if(white){
            String[] xd = move.split("");
            return new Move(8 - Integer.parseInt(xd[1]), letterDefs.indexOf(xd[0]), 8 - Integer.parseInt(xd[3]), letterDefs.indexOf(xd[2]), false);
        }else{
            String[] xd = move.split("");
            return new Move(Integer.parseInt(xd[1]) - 1, letterDefs.indexOf(xd[0]), Integer.parseInt(xd[3]) - 1, letterDefs.indexOf(xd[2]), false);
        }
        
    }

    static String moveToPrint(Move move, boolean white){
        String a = "";
        if(white){
            a += letterDefs.charAt(move.C[0][1]);
            a += 8 - move.C[0][0];
            a += letterDefs.charAt(move.C[1][1]);
            a += 8 - move.C[1][0];
        }else{
            a += letterDefs.charAt(move.C[0][1]);
            a += move.C[0][0] + 1;
            a += letterDefs.charAt(move.C[1][1]);
            a += move.C[1][0] + 1;
        }
        

        return a;
    }

    //Does move and returns the board before the move was made
    static char[][] doMove(char[][] brd, Move move){
        char[][] prevBoard = new char[brd.length][brd[0].length];
        for(int r = 0; r < brd.length; r++){
            for(int c = 0; c < brd[r].length; c++){
                prevBoard[r][c] = brd[r][c];
            }
        }

        char temp = brd[move.C[0][0]][move.C[0][1]];
        brd[move.C[0][0]][move.C[0][1]] = ' ';
        brd[move.C[1][0]][move.C[1][1]] = temp;

        return prevBoard;
    }

    static ArrayList<Move> findMoves(char[][] brd, boolean white){
        ArrayList<Move> moves = new ArrayList<Move>();

        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                if(white){
                    if(brd[r][c] == 'p'){
                        int r1 = r;
                        int c1 = c;

                        if(r - 1 >= 0){
                            if(brd[r - 1][c] == ' '){
                                moves.add(new Move(r1, c1, r - 1, c, false));
                            }
                            
                            if(c - 1 >= 0 && (brd[r - 1][c - 1] == ' ' || brd[r - 1][c - 1] == 'P')){
                                moves.add(new Move(r1, c1, r - 1, c - 1, brd[r - 1][c - 1] == 'P'));
                            }

                            if(c + 1 < 8 && (brd[r - 1][c + 1] == ' ' || brd[r - 1][c + 1] == 'P')){
                                moves.add(new Move(r1, c1, r - 1, c + 1, brd[r - 1][c + 1] == 'P'));
                            }
                        }
                    }
                }else{
                    if(brd[r][c] == 'P'){
                        int r1 = r;
                        int c1 = c;

                        if(r + 1 >= 0){
                            if(brd[r + 1][c] == ' '){
                                moves.add(new Move(r1, c1, r + 1, c, false));
                            }
                            
                            if(c - 1 >= 0 && (brd[r + 1][c - 1] == ' ' || brd[r + 1][c - 1] == 'p')){
                                moves.add(new Move(r1, c1, r + 1, c - 1, brd[r + 1][c - 1] == 'p'));
                            }

                            if(c + 1 < 8 && (brd[r + 1][c + 1] == ' ' || brd[r + 1][c + 1] == 'p')){
                                moves.add(new Move(r1, c1, r + 1, c + 1, brd[r + 1][c + 1] == 'p'));
                            }
                        }

                    }
                }
            }
        }
        return moves;
    }

    static void printMoveList(char[][] brd, ArrayList<Move> moves){
        for(Move move: moves){
            System.err.print(moveToPrint(move, true) + " ");
        }
    }

    //
    //
    //
    //
    //
    //MAIN!!!
    //
    //
    //
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        initializeBoard();
        boolean iamwhite = false;
        int max = 2;

        /*
        char[][] test = new char[8][8];
        test[2][4] = 'p';
        test[1][4] = 'P';
        System.err.println(spacesForwardIsEmpty(2, 4, true, test));
        */

        // game loop
        while (true) {

            long end = System.currentTimeMillis();

            String opponentMove = in.nextLine(); // last move played or "None"
            if(opponentMove.equalsIgnoreCase("None")){iamwhite = true;}
            
            Move lastMove = convertMove(opponentMove, iamwhite);
            if(lastMove != null){ doMove(board, lastMove); }
            
            int legalMoves = in.nextInt(); // number of legal moves
            for (int i = 0; i < legalMoves; i++) {
                String moveString = in.next(); // a legal move
                //System.err.print(moveString + " ");
            }
            in.nextLine();

            if(legalMoves <= 5){
                max = 4;
            }else
            if(legalMoves <= 20){
                max = 3;
            }else{
                max = 2;
            }

            ArrayList<Move> next = findMoves(board, true);
            printBoard(board);
            printMoveList(board, next);
            System.err.println();
            
            Move bestMove = null;
            ArrayList<Move> bestMoves = new ArrayList<Move>();
            int bestScore = Integer.MIN_VALUE;

            for(Move m: next){
                char[][] prevBoard = doMove(board, m);
                int score = eval(board, 0, false, max, Integer.MIN_VALUE, Integer.MAX_VALUE);
                System.err.println(moveToPrint(m, iamwhite) +" " +score);
                board = prevBoard;

                if(score > bestScore){
                    bestMoves = new ArrayList<Move>();
                    bestMoves.add(m);
                    bestScore = score;
                    bestMove = m;
                }else if(score == bestScore){
                    bestMoves.add(m);
                }
            }
            
            //printBoard(board);
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            Move chosen = bestMoves.get((int)(Math.random()*bestMoves.size()));
            System.out.println(moveToPrint(bestMove, iamwhite));
            doMove(board, bestMove);
        }
    }

    static boolean spacesForwardIsEmpty(int r, int c, boolean white, char[][] brd){
        if(white){
            boolean result = true;
            for(int rowSub = 1; r - rowSub >= 0; rowSub++){
                int startC = c - rowSub;
                int endC = c + rowSub;
                if(startC < 0){
                    startC = 0;
                }
                if(endC > 7){
                    endC = 7;
                }

                for(int col = startC; col <= endC; col++){
                    if((rowSub == 1) && (col == c)){continue;}
                    if(brd[r-rowSub][col] == 'P' || brd[r-rowSub][col] == 'p'){
                        result = false;
                        break;
                    }
                }
                if(result == false){
                    break;
                }
            }
            return result;
        }else{
            boolean result = true;
            for(int rowSub = 1; r + rowSub < 8; rowSub++){
                int startC = c - rowSub;
                int endC = c + rowSub;
                if(startC < 0){
                    startC = 0;
                }
                if(endC > 7){
                    endC = 7;
                }

                for(int col = startC; col <= endC; col++){
                    if((rowSub == 1) && (col == c)){continue;}
                    if(brd[r+rowSub][col] == 'p' || brd[r+rowSub][col] == 'P'){
                        result = false;
                        break;
                    }
                }
                if(result == false){
                    break;
                }
            }
            return result;
        }
    }

    //AI things
    static int eval(char[][] brd, int depth, boolean myTurn, int max, int al, int bl){
        int mscore = 0;
        int escore = 0;
        
        //printBoard(brd);
        //System.err.println("?");

        for(int c = 0; c < 8; c++){
            if(brd[7][c] == 'P'){
                return -100 + depth;
            }
            if(brd[0][c] == 'p'){
                return 100 - depth;
            }
        }
        
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                if(brd[r][c] == 'p'){
                    mscore++;
                }else if(brd[r][c] == 'P'){
                    escore++;
                }
            }
        }

        
        if(mscore == 0){
            return -100 + depth;
        }else if(escore == 0){
            return 100 - depth;
        }
        

        if(depth >= max){
            for(int c = 0; c < 8; c++){
                if(brd[6][c] == 'P' && spacesForwardIsEmpty(6, c, false, brd)){
                    return -50;
                }
                if(brd[1][c] == 'p' && spacesForwardIsEmpty(1, c, true, brd)){
                    return 50;
                }
                if(brd[5][c] == 'P' && spacesForwardIsEmpty(5, c, false, brd)){
                    return -25;
                }
                if(brd[2][c] == 'p' && spacesForwardIsEmpty(2, c, true, brd)){
                    return 25;
                }
            }

            
            //return (simulate(brd, myTurn)) + (mscore - escore);
            

            return mscore - escore;
        }else{
            if(myTurn){
                //Maximize
                int value = Integer.MIN_VALUE;
                ArrayList<Move> nextMoves = findMoves(brd, true);
                for(Move m: nextMoves){
                    char[][] prevBoard = doMove(brd, m);
                    value = Math.max(value, eval(brd, depth + 1, false, max, al, bl));
                    al = Math.max(al, value);
                    brd = prevBoard;
                    if(al >= bl){
                        break;
                    }
                }
                return value;
            }else{
                //Minimize
                int bestVal = Integer.MAX_VALUE;
                ArrayList<Move> nextMoves = findMoves(brd, false);
                for(Move m: nextMoves){
                    char[][] prevBoard = doMove(brd, m);
                    bestVal = Math.min(bestVal, eval(brd, depth + 1, true, max, al, bl));
                    bl = Math.min(bl, bestVal);
                    brd = prevBoard;
                    if(bl <= al){
                        break;
                    }
                }
                return bestVal;
            }
        }
    }

    static int simulate(char[][] brd, boolean isWhiteTurn) {
		
		for(int c = 0; c < 8; c++) {
			
			if(brd[7][c] == 'P') {
				return -5;
			}
			
			if(brd[0][c] == 'p') {
				return 5;
			}
		}
		
		int value = 0;
		
		if(isWhiteTurn) {
			ArrayList<Move> nextMoves = findMoves(brd, true);
			char[][] pb = doMove(brd, nextMoves.get((int) (Math.random()*nextMoves.size())));
			//System.err.println("Simulate white ");
			//printBoard(brd);
			value = simulate(brd, false);
			brd = pb;
		}else {
			ArrayList<Move> nextMoves = findMoves(brd, false);
			char[][] pb = doMove(brd, nextMoves.get((int) (Math.random()*nextMoves.size())));
			//System.err.println("Simulate black ");
			//printBoard(brd);
			value = simulate(brd, true);
			brd = pb;
		}

		return value;
	}

}

class Move {
    int[][] C;
    boolean capture;
    public Move(int r1,int c1,int r2,int c2, boolean cap){
        int[][] a = {{r1, c1}, {r2, c2}};
        this.C = a;
        this.capture = cap;
    }

    public Move(int[][] a, boolean cap){
        this.C = a;
        this.capture = cap;
    }
}
