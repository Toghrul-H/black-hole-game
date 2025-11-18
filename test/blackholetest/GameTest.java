package blackholetest;
import blackholegame.BlackHoleGame;
import blackholegame.CellKind;
import blackholegame.GameModel;
import blackholegame.Player;
import blackholegame.Pos;
import blackholegame.SlideOutcome;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Map;
/**
 * Automated tests for Black Hole Game.
 */
public class GameTest {

    @Test
    public void testInitialSetup() {
        GameModel model = new GameModel(7);
        // Center cell should be the hole
        assertEquals("Center must be hole", CellKind.HOLE, model.board[3][3]);
        // Ships should be on diagonals
        assertEquals("Top-left must be Player 1 ship", CellKind.SHIP_P1, model.board[0][0]);
        assertEquals("Top-right must be Player 2 ship", CellKind.SHIP_P2, model.board[0][6]);
    }

    @Test
    public void testMoveValidSlide() {
        GameModel model = new GameModel(5);
        Pos p = new Pos(0, 0); // Player 1 ship
        Map<Pos, SlideOutcome> slides = model.legalSlides(p);
        assertFalse("Ship must have valid slides", slides.isEmpty());
    }

    @Test
    public void testInvalidMove() {
        GameModel model = new GameModel(5);
        Pos src = new Pos(0, 0);
        Pos dest = new Pos(0, 0);
        String result = model.move(src, dest);
        assertNull("Invalid move should return null", result);
    }


    @Test
    public void testResetGame() {
        GameModel model = new GameModel(7);
        model.scoreP1 = 2;
        model.scoreP2 = 3;
        model.reset(5);
        assertEquals("Board size must be 5 after reset", 5, model.n);
        assertEquals("Scores must reset to 0", 0, model.scoreP1);
        assertEquals("Scores must reset to 0", 0, model.scoreP2);
    }

    @Test
    public void testExceptionHandlingInRestart() {
        BlackHoleGame gui = new BlackHoleGame();
        try {
            gui.restartFromUI(); // Should not throw any exception
        } catch (Exception e) {
            fail("restartFromUI() should handle exceptions safely, but threw: " + e.getMessage());
        }
    }


    @Test
    public void testHasAnyMove() {
        GameModel model = new GameModel(5);
        assertTrue("At least one move should exist at start", model.hasAnyMove(Player.P1));
    }

    @Test
    public void testTargetScore() {
        GameModel model = new GameModel(7);
        int expected = (int) Math.ceil((7 - 1) / 2.0);
        assertEquals("Target score should match formula", expected, model.targetScore());
    }

    @Test
    public void testInvalidBoardSizeException() {
        try {
            new GameModel(0);
            fail("Creating a board of size 0 should fail");
        } catch (Exception e) {
            assertTrue(true); // expected
        }
    }
}
