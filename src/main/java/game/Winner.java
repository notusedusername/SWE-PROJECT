package game;

/**
 * A lehetséges győztes állapotokat tartalmazza.
 *
 * A lehetséges győztes állapotok:
 *
 */
public enum Winner {
    /**
     * Az egyes játékos.
     */
    PLAYER1,
    /**
     * A kettes játékos.
     */
    PLAYER2,
    /**
     * Döntetlen.
     */
    TIE,
    /**
     * Nincs nyertes (még).
     */
    NONE
}
