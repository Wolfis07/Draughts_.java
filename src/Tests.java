import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class Tests {



    /**
     * Test that the grid is initialized correctly when a new GameDesk is created.
     */
    @org.junit.jupiter.api.Test
    public void testBoardInitialization() {
        GameDesk gameDesk = new GameDesk();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (i <= 2 && (j + i) % 2 == 0) {
                    assertTrue(gameDesk.grid[i][j].isColor());
                }

                else if (i >= 5 && (j + i) % 2 == 0) {
                    assertFalse(gameDesk.grid[i][j].isColor());
                }

                else {
                    assertEquals(Stone.Empty(), gameDesk.grid[i][j]);
                }
            }
        }
    }



    /**
     * Test that the generatePossibleMoves method returns an empty list when there are no possible moves.
     */
    @org.junit.jupiter.api.Test
    public void testGeneratePossibleMoves_NoMoves() {
        GameDesk game = new GameDesk();
        game.grid[0][0] = Stone.Black();
        game.grid[1][1] = Stone.White();
        ArrayList<Move> moves = game.generatePossibleMoves(0, 0);
        assertEquals(0, moves.size());
    }




    /**
     * Test that the isValidMove method returns false when trying to move a stone to a occupied square.
     */
    @org.junit.jupiter.api.Test
    public void testValidMove() {
        GameDesk gameDesk = new GameDesk();

        assertTrue(gameDesk.isValidMove(3, 3, 4, 4, true));
    }

    /**
     * Test that the isValidMove method returns false for an invalid move.
     * In this case, the move from (3, 3) to (5, 5) is invalid because it is not a valid move in the game of Go.
     */
    @org.junit.jupiter.api.Test
    public void testInvalidMove() {
        GameDesk gameDesk = new GameDesk();

        assertFalse(gameDesk.isValidMove(3, 3, 5, 5, true));
    }
    /**
     * Test that the winner method correctly returns the winner of the game when one player has won.
     */
    @org.junit.jupiter.api.Test
    public void testWinner_BlackWins() {
        GameDesk gameDesk = new GameDesk();


        gameDesk.grid[0][0] = Stone.Black();
        gameDesk.grid[0][1] = Stone.Black();
        gameDesk.grid[0][2] = Stone.Black();
        gameDesk.grid[0][3] = Stone.Black();
        gameDesk.grid[0][4] = Stone.Black();


        assertEquals("Black won", gameDesk.winner());
    }


    }



