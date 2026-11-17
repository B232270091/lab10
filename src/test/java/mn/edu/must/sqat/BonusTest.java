package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BonusTest {

    @Test
    @DisplayName("B1 ALL-DEFS: present 35/40; streak 36/41/46; best 37/43; score 49->54->56")
    void allDefsScoreThree() {
        // present=6 < 8, best=5: score at 49 is read at 54, then returned at 56.
        // The final present day reads the reset definition streak at line 46.
        assertEquals(3, DataFlowTargets.bonus(new int[]{1, 1, 1, 1, 1, 0, 1}, 8));
    }

    @Test
    @DisplayName("B2 ALL-DEFS: score 51->56; threshold 31->50T; best 43->53F")
    void allDefsScoreFive() {
        // score at 51 reaches return without being overwritten at line 54.
        assertEquals(5, DataFlowTargets.bonus(new int[]{0, 1}, 1));
    }
}
