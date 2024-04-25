package unit13.haunted;

public class Area {
    private String name;
    private AreaType type;
    private String evilPresence;

    public Area(String name, AreaType type) {
        this.name = name;
        this.type = type;
        this.evilPresence = null;
    }

    public String getName() {
        return this.name;
    }

    public AreaType getType() {
        return this.type;
    }

    public boolean isHaunted() {
        return evilPresence != null;
    }

    public void haunt(String evilPresence) {
        this.evilPresence = evilPresence;
    }

    public String toString() {
        if(isHaunted()) {
            return name + " [" + type + "/" + evilPresence + "]";
        } else {
            return name + " [" + type + "]";
        }
    }

    public boolean equals(Object obj) {
        if(obj instanceof Area) {
            Area newArea = (Area)obj;
            return this.hashCode() == newArea.hashCode();
        } else {
            return false;
        }
    }

    public int hashCode() {
        return name.hashCode();
    }
}
