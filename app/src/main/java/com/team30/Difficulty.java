import java.util.Arrays;
import java.util.List;

/**
 * Player-facing difficulty options.
 * Each value maps to a list of level numbers that belong to it:
 * <ul>
 *   <li>{@link #EASY}   — Level 1 only.</li>
 *   <li>{@link #MEDIUM} — Levels 2, 3, 4.</li>
 *   <li>{@link #HARD}   — Levels 5, 6.</li>
 * </ul>
 */
public enum Difficulty
{
    EASY(Arrays.asList(1)),
    MEDIUM(Arrays.asList(2, 3, 4)),
    HARD(Arrays.asList(5, 6));

    private final List<Integer> levels;

    Difficulty(List<Integer> levels)
    {
        this.levels = levels;
    }

    /** Returns the level numbers covered by this difficulty. */
    public List<Integer> getLevels()
    {
        return levels;
    }
}