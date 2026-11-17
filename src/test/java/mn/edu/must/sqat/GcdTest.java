package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GcdTest {

    @Test
    @DisplayName("G1: x 13->20; y 13->15F (zero iterations)")
    void zeroSecondReturnsFirst() {
        assertEquals(7, DataFlowTargets.gcd(7, 0));
    }

    @Test
    @DisplayName("G2: x 13->16,17->16/20; y 13/18->15T/16/17,18->15F; tmp 16->18")
    void twoIterationsCoverRedefinitions() {
        // (12,8) -> (8,4) -> (4,0): the second iteration uses loop definitions.
        assertEquals(4, DataFlowTargets.gcd(12, 8));
    }

    @Test
    @DisplayName("G3: x 13->16,17->20; y 13->15T/16/17,18->15F; tmp 16->18")
    void zeroFirstBecomesSecond() {
        assertEquals(5, DataFlowTargets.gcd(0, 5));
    }
}
