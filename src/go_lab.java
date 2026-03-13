import java.util.Scanner;
public class go_lab {
    
    // my board!!! 9 x 9 directly implemented verison
    static String[][] board = {
        {null, null, null, null, null, null, null, null, null},
        {null, "W",  "W",  "W",  null, null, null, null, null},
        {null, "W",  null, "W",  null, null, null, null, null},
        {null, "W",  "W",  "W",  null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null}
    };

    // each of these will initialize to false
    static boolean[][] lives = new boolean[9][9]; // tracks if a piece is alive t or f
    static boolean[][] territory = new boolean[9][9]; // keeps track of empty spots already scored t or f
    static boolean[][] beenChecked = new boolean[9][9]; // what intersections have already been checked? t or f

    static boolean blackPiece = true; // starts with blacks turn
    static boolean playing = true; // game starts out playing until user types -1

    static boolean touchBlack = false; // starts out as not noting black until black is detected
    static boolean touchWhite = false; //  starts out as not noting white until white is detected
    static int squareCount = 0; // empty square counting

    static int blackScore = 0; // score tracking
    static int whiteScore = 0; // score tracking    

    // method for finding empty spaces aka liberties next to a stone
    public static int getLiberties(int r, int c, String color) {
        // boundary check
        if (r < 0 || r >= 9 || c < 0 || c >= 9) return 0;

        // alredy visited check, not counting the liberties of those already visited
        if (beenChecked[r][c] == true) return 0;

        // empty spot returns one liberty 
        if (board[r][c] == null) return 1;

        // if it equals the opposite color then it is not a liberty 
        if (!board[r][c].equals(color)) return 0;

        // 5. If we reach here, it's a FRIENDLY stone.
        // Mark it "visited" so we don't count it again, then jump to its neighbors.
        beenChecked[r][c] = true;
        lives[r][c] = true;
        
        // recursive method!!!!
        int count = 0;
        count += getLiberties(r + 1, c, color); // Check Right
        count += getLiberties(r - 1, c, color); // Check Left
        count += getLiberties(r, c + 1, color); // Check Down
        count += getLiberties(r, c - 1, color); // Check Up
        
        return count;
    }
    // clearing stone off the board
    public static void removeGroup(int r, int c, String color) {
        // boundary check
        if (r < 0 || r >= 9 || c < 0 || c >= 9) return;
        
        // checks if spot is empty or is the opposite color
        if (board[r][c] == null || !board[r][c].equals(color)) return;

        // if white stone removed, add a point to black score
        if (color.equals("W")) {
            blackScore = blackScore + 1;
        } else {
            whiteScore = whiteScore + 1; // if black stone is removed, add a point to white score
        }

        board[r][c] = null; // removing stone
        lives[r][c] = false; // area is now non living
        
        // recursive method!!!! look at the neighbors and tell them to disappear too
        removeGroup(r + 1, c, color);
        removeGroup(r - 1, c, color);
        removeGroup(r, c + 1, color);
        removeGroup(r, c - 1, color);
    }

    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);
        
        // printing the board
        while (playing == true) {
            for(int i = 0 ; i<board.length; i++){
                for (int j = 0; j<board.length; j++){
                    if (board[i][j] == null){
                        System.out.print( "+");
                    }else{
                        System.out.print(board[i][j]);
                    }
                }
                System.out.println();
            }

            // score summary
            System.out.println("Black Score: " + blackScore + " White Score: " + whiteScore);


            
            if (blackPiece == true) {
                System.out.println("Black piece's turn! Enter row and column from 0-8");
            } else {
                System.out.println("White piece's turn! Enter row and column from 0-8");
            }
            
            System.out.println("Enter row (or -1 to quit)");
            int row = scn.nextInt();

            if (row == -1) {
                playing = false; // typing -1 stops game and playing is false, so everything under while playing doesn't repeat
                continue;
            }

            System.out.println("Enter column");
            int col = scn.nextInt();
            
            // placing a stone
            if (row < 0 || row >= 9 || col < 0 || col >= 9) // boundary check
                System.out.println("Out of bounds - ERROR"); 
            else if (board[row][col] != null) // overlap check
                System.out.println("Overlap - ERROR");
            else { // placing the stone
                if (blackPiece == true) {
                    board[row][col] = "B";
                } else {
                    board[row][col] = "W";
                
                }
            
            

            // setting the player and enemy values
            String playerColor = "";
            String enemyColor = "";
            if (blackPiece == true) {
                playerColor = "B";
                enemyColor = "W";
            } else {
                playerColor = "W";
                enemyColor = "B";
            }
            
            // checking upwards
            int upRow = row - 1;

            // checking if its on the board, if there is a stone already there, and if its the enemy
            if (upRow >= 0 && upRow < 9 && board[upRow][col] != null && board[upRow][col].equals(enemyColor)) {
                beenChecked = new boolean[9][9];
                if (getLiberties(upRow, col, enemyColor) == 0) {
                    lives[upRow][col] = false; 
                    
                    removeGroup(upRow, col, enemyColor);
                } else {
                    lives[upRow][col] = true;
                }

            }
            

            // checking down
            int downRow = row + 1;

            if (downRow >= 0 && downRow < 9 && board[downRow][col] != null && board[downRow][col].equals(enemyColor)) {
                beenChecked = new boolean[9][9];
                if (getLiberties(downRow, col, enemyColor) == 0) {
                    lives[downRow][col] = false;

                    removeGroup(downRow, col, enemyColor); 
                } else {
                    lives[downRow][col] = true;
                }
            }


            // checking right
            int rightCol = col + 1;

            if (rightCol >= 0 && rightCol < 9 && board[row][rightCol] != null && board[row][rightCol].equals(enemyColor)) {
                beenChecked = new boolean[9][9];
                if (getLiberties(row, rightCol, enemyColor) == 0) {
                    lives[row][rightCol] = false;

                    removeGroup(row, rightCol, enemyColor);
                } else {
                    lives[row][rightCol] = true;
                }
            }

            // checking left
            int leftCol = col - 1;

            if (leftCol >= 0 && leftCol < 9 && board[row][leftCol] != null && board[row][leftCol].equals(enemyColor)) {
                beenChecked = new boolean[9][9];
                if (getLiberties(row, leftCol, enemyColor) == 0) {
                    lives[row][leftCol] = false;

                    removeGroup(row, leftCol, enemyColor); // Capture 
                } else {
                    lives[row][leftCol] = true;
                }
            }

            // suicide check, placing a stone where it would have 0 liberties is an illegal move
            beenChecked = new boolean[9][9];
            int mySpaces = getLiberties(row, col, playerColor);
            if (mySpaces == 0) {
                System.out.println("Illegal suicide move. Try another spot");
                board[row][col] = null;
                continue;
            }

                // changes turns
                blackPiece = !blackPiece;
                
            }
        }

        beenChecked = new boolean[9][9];
        // looks at empty squares to figure out who they belong to
        for (int r = 0; r < 9; r++) { // checks rows
            for (int c = 0; c < 9; c++) { // checks columns

                if (board[r][c] == null && territory[r][c] == false) {
                    beenChecked = new boolean[9][9];
                    touchBlack = false;
                    touchWhite = false;
                    squareCount = 0;

                    territoryCheck(r, c);

                    // calculates points
                    if (touchBlack == true && touchWhite == false) {
                        blackScore += squareCount;
                    } else if (touchWhite == true && touchBlack == false) {
                        whiteScore += squareCount;
                    } 
                        

                    }
                    
                }
            }
        
        // prints points
        System.out.println("FINAL SCORE");
        System.out.println("Black score: " + blackScore);
        System.out.println("White score: " + whiteScore);
    }

    
    public static void territoryCheck(int row, int col) {
            
            // boundary check
        if (row < 0 || row >= 9 || col < 0 || col >= 9) {
            return;
        }

            // visited check
        if (beenChecked[row][col] == true) {
            return;
        }

        beenChecked[row][col] = true;

        if (board[row][col] == null) {
            territory[row][col] = true;
        }

        if (board[row][col] != null) {
            if (board[row][col].equals("B")) {
                touchBlack = true;
            } else {
                touchWhite = true;
            }
            return;
        }
        squareCount = squareCount + 1;
        territoryCheck(row - 1, col); // checking up
        territoryCheck(row + 1, col); // checking down
        territoryCheck(row, col - 1); // checking left
        territoryCheck(row, col + 1); // checking right
    }
}

        

