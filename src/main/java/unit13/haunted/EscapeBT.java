package unit13.haunted;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import unit13.backtracker.Backtracker;
import unit13.backtracker.Configuration;

public class EscapeBT implements Configuration<EscapeBT> {
    private Area currentArea;
    private Map<Area, Collection<Area>> passages;
    private List<Area> pathOut;

    public EscapeBT(Area currentArea, Map<Area, Collection<Area>> routes, List<Area> pathOut) {
        this.currentArea = currentArea;
        this.passages = routes;
        this.pathOut = pathOut;
        pathOut.add(currentArea); //to print out a possible way out at the end (if possible)
    }

    @Override
    public Collection<EscapeBT> getSuccessors() {
        Collection<EscapeBT> successors = new ArrayList<>();
        for(Area neighbor : passages.get(currentArea)) {
            if(!pathOut.contains(neighbor)) { //if neighbor is not already in path
                EscapeBT successor = new EscapeBT(neighbor, passages, new ArrayList<>(pathOut));
                successors.add(successor);
            }
        }
        return successors;
    }

    @Override
    public boolean isValid() {
        return currentArea != null && !currentArea.isHaunted();
    }

    @Override
    public boolean isGoal() {
        return currentArea != null && currentArea.getType() == AreaType.EXIT;
    }

    @Override
    public String toString() {
        String pathString = "Escape path: [";
        for(Area area : pathOut) {
            pathString += area.toString() + ", ";
        }
        return pathString.substring(0, pathString.length()-2) + "]"; //removes the trailing comma
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a building floor plan .csv file: ");
        String filename = sc.next();
        System.out.println();
        BuildingFileParser fileParser = new BuildingFileParser(filename); //creates a BFP
        Area startArea = fileParser.getStartArea();
        System.out.println("You awake in the " + startArea.getName() + " [" + startArea.getType() + "] and desperately need to find a way out!");
        System.out.println();
        System.out.println("Haunted areas:");
        for(Area hauntedAreas : fileParser.getHauntedAreas()) { //prints out haunted areas
            System.out.println("\t" + hauntedAreas.toString());
        }
        System.out.println();
        EscapeBT escape = new EscapeBT(startArea, fileParser.getPassages(), new ArrayList<>());
        Backtracker<EscapeBT> backtracker = new Backtracker<>(false); //creates a backtracker
        EscapeBT solution = backtracker.solve(escape);
        if(solution != null) { //if a solution is found
            System.out.println("You have found a way out!");
            System.out.println(solution.toString());
        } else { //otherwise
            System.out.println("There is no escape. You will haunt the grounds for all eternity.");
        }
        sc.close();
    }
}
