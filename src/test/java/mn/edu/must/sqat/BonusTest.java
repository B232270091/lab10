package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    @DisplayName("B3 ALL-DU: present 35->50F; best 37->53F; score 49->56")
    void emptyBelowThreshold() {
        assertEquals(0, DataFlowTargets.bonus(new int[]{}, 1));
    }

    @Test
    @DisplayName("B4 ALL-DU: present 35->50T; score 51->56; attendance 31->38F")
    void emptyThresholdZero() {
        assertEquals(5, DataFlowTargets.bonus(new int[]{}, 0));
    }

    @Test
    @DisplayName("B5 ALL-DU: a 38->39F; best 37->53F; score 49->56")
    void allAbsentDays() {
        assertEquals(0, DataFlowTargets.bonus(new int[]{0, -1, 0}, 1));
    }

    @Test
    @DisplayName("B6 ALL-DU: score 51->54->56; best 43->53T; threshold 31->50T")
    void fivePresentDays() {
        assertEquals(8, DataFlowTargets.bonus(new int[]{1, 1, 1, 1, 1}, 5));
    }

    @Test
    @DisplayName("B7 ALL-DU: attendance 31->32aT; threshold short-circuited")
    void invalidNull() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(null, 0));
    }

    @Test
    @DisplayName("B8 ALL-DU: attendance 31->32aF; threshold 31->32bT")
    void invalidThreshold() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(new int[]{}, -1));
    }


    @Test
    @DisplayName("B9 M2-kill: best 43->42F/42T/53F; streak 46->41; score 49->56")
    void resetThenLongerRun() {
        // present=5, best=3, threshold=6. M2 wrongly sets best=present=5.
        assertEquals(0, DataFlowTargets.bonus(new int[]{1, 1, 0, 1, 1, 1}, 6));
    }

}
