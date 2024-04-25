package unit13.haunted;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class BuildingFileParser {
    private Random RNG;
    private Collection<Area> safeAreas;
    private Collection<Area> exitAreas;
    private Collection<Area> hauntedAreas;
    private Map<Area, Collection<Area>> passages;

    public BuildingFileParser(String filename) throws IOException {
        this.RNG = new Random();
        this.safeAreas = new HashSet<>();
        this.exitAreas = new HashSet<>();
        this.hauntedAreas = new HashSet<>();
        this.passages = new HashMap<>();
        parseFile(filename);
    }

    private void parseFile(String filename) throws IOException {
        Scanner sc = new Scanner(new File(filename));
        while(sc.hasNextLine()) { //read lines in file and split them
            String line = sc.nextLine();
            String[] tokens = line.split(",");
            Area firstArea = makeArea(tokens[0]); //makes first area
            for(int i = 1; i < tokens.length; i++) {
                Area neighboringArea = makeArea(tokens[i]); //makes other areas
                if(!passages.containsKey(firstArea)) {
                    passages.put(firstArea, new HashSet<>()); //creates new area in hashmap (if doesn't already exist)
                }
                if(!passages.containsKey(neighboringArea)) {
                    passages.put(neighboringArea, new HashSet<>()); //creates new area in hashmap (if doesn't already exist)
                }
                passages.get(firstArea).add(neighboringArea); //connects firstArea to neighboringAreas
                passages.get(neighboringArea).add(firstArea);
            }
        }
        sc.close();
    }

    private Area makeArea(String token) { //makes a new area
        String[] tokens = token.split("-");
        String roomName = tokens[0];
        Area newArea = findArea(roomName);
        AreaType areaType = AreaType.valueOf(tokens[1]);

        if(newArea == null) { //if newArea could not be found in safe, exit, or haunted areas
            newArea = new Area(roomName, areaType);
            if(newArea.getType() == AreaType.EXIT) {
                exitAreas.add(newArea);
            } else {
                safeAreas.add(newArea);
            }

            //haunting rooms
            String evilPresence = EvilPresenceUtil.getRandomPresence();
            if(evilPresence != null && areaType != AreaType.EXIT) { //if haunted, add to haunted rooms
                newArea.haunt(evilPresence);
                hauntedAreas.add(newArea);
                if(safeAreas.contains(newArea)) {
                    safeAreas.remove(newArea);
                }
            }
        }
        return newArea;
    }

    private Area findArea(String roomName) { //helps find the area
        for(Area area : safeAreas) {
            if(area.getName().equals(roomName)) { //if area already exists in safeAreas, return the area
                return area;
            }
        }
        for(Area area : exitAreas) {
            if (area.getName().equals(roomName)) { //if area already exists in exitAreas, return the area
                return area;
            }
        }
        for(Area area : hauntedAreas) {
            if(area.getName().equals(roomName)) { //if area already exists in hauntedAreas, return the area
                return area;
            }
        }
        return null; //if the area does not already exist, return null
    }

    public Collection<Area> getSafeAreas() {
        return safeAreas;
    }

    public Collection<Area> getExitAreas() {
        return exitAreas;
    }

    public Collection<Area> getHauntedAreas() {
        return hauntedAreas;
    }

    public Map<Area, Collection<Area>> getPassages() {
        return passages;
    }

    public Area getStartArea() {
        int randomNum = RNG.nextInt(safeAreas.size());
        List<Area> areaList = new ArrayList<>(safeAreas);
        return areaList.get(randomNum); //returns a random safeArea
    }
}
