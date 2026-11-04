package Entity;

public class Cooldown {
    private long targetTimeMillis;
    public Cooldown() {
        targetTimeMillis = 0;
    }

    /**
     * Starting a cooldown cycle
     * @param cooldownTimeMillis Cooldown time (in Millisecond)
     */
    public void startCooldown(int cooldownTimeMillis) {
        targetTimeMillis = System.currentTimeMillis() + cooldownTimeMillis;
    }

    /**
     *
     * @return True if on cooldown, false otherwise
     */
    public boolean onCooldown() {
        return System.currentTimeMillis() < targetTimeMillis;
    }

    public double cooldownMillisLeft() {
        double secLeft = targetTimeMillis - System.currentTimeMillis();
        secLeft = (secLeft < 0)? 0 : secLeft;
        return secLeft;
    }
}
