package creatures;

import huglife.*;

import java.awt.Color;
import java.util.Map;
import java.util.List;

public class Clorus extends Creature {
    /** red color. */
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;

    private static final double moveLost = 0.03;
    private static final double stayLost = 0.01;
    private static final double repEnergyRetained = 0.5;
    private static final double repEnergyGiven = 0.5;

    /** creates plip with energy equal to E. */
    public Clorus(double e) {
        super("clorus");
        g = 0;
        r = 34;
        b = 231;
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    public Color color() {
        return color(r, g, b);
    }

    public void move() {
        energy -= moveLost;
    }

    public void stay() {
        energy -= stayLost;
    }

    public void attack(Creature c) {
        this.energy += c.energy();
    }

    public Clorus replicate() {
        Clorus child = new Clorus(this.energy / 2);
        this.energy = this.energy / 2;
        return child;
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");
        if (empties.size() <= 0) {
            return new Action(Action.ActionType.STAY);
        } else if (plips.size() > 0) {
            Direction attackDir = HugLifeUtils.randomEntry(plips);
            return new Action(Action.ActionType.ATTACK, attackDir);
        } else if (this.energy >= 1) {
                Direction replicateDir = HugLifeUtils.randomEntry(empties);
                return new Action(Action.ActionType.REPLICATE, replicateDir);
            }
        Direction moveDir = HugLifeUtils.randomEntry(empties);
        return new Action(Action.ActionType.MOVE, moveDir);
    }
}
