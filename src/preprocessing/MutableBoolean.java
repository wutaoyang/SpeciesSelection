package preprocessing;

/**
 * Wrapper for a boolean that allows a boolean to be passed by reference and for
 * the value of that boolean to be changed
 * @author mre16utu
 */
public class MutableBoolean {

    private boolean value;

    public MutableBoolean(boolean value) {
        this.value = value;
    }

    public void setFalse() {
        this.value = false;
    }

    public void setTrue() {
        this.value = true;
    }

    public boolean isTrue() {
        return value;
    }

}
