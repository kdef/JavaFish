package fish;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FishDriverTest {

    FishDriver fd;

    @Before
    public void setUp() throws Exception {
        fd = new FishDriver();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreateLocalPlayer() {
        String[] choices = new String[] { "human player",
                "EASY computer player", "MED computer player",
                "HARD computer player" };

        boolean human = fd.createLocalPlayer(choices[0]) instanceof FishHumanPlayer;
        assertEquals(human, true);

        for (int i = 1; i < 4; i++) {
            boolean result = fd.createLocalPlayer(choices[i]) instanceof FishComputerPlayer;
            assertEquals(result, true);
        }
    }

}
