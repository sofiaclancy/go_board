import java.util.Scanner;
public class go_lab {
    
    static String[][] board = new String[9][9];
    
    static boolean blackPiece = true;
    static boolean playing = true;

    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);
        
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
            
            if (blackPiece == true) {
                System.out.println("Black piece's turn! Enter row and column from 0-8");
            } else {
                System.out.println("White piece's turn! Enter row and column from 0-8");
            }
            

            int row = scn.nextInt();
            int col = scn.nextInt();
            if (row < 0 || row >= 9 || col < 0 || col >= 9)
                System.out.println("Out of bounds - ERROR");
            else if (board[row][col] != null)
                System.out.println("Overlap - ERROR");
            else {
                if (blackPiece == true) {
                    board[row][col] = "B";
                } else {
                    board[row][col] = "W";
                
                }
                blackPiece = !blackPiece;
                
                }

        }
         
        }}

        

