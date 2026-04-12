//Zookeeper's Challenge Midterm CIT-63
//AlexBrush
//4-12-2026

import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

//Zookeeper's Challenge Application
// logic for proccessing ariving animals and providing output

public class ZookeeperChallenge {

    //Static counters tracking population for unique ID generation
    private static int numOfHyenas = 0;
    private static int numOfLions = 0;
    private static int numOfTigers = 0;
    private static int numOfBears = 0;

    //Extracts and stores names by species from animalNames.tx
    private static Map<String, Queue<String>> speciesNames = new HashMap<>();

    public static void main(String[] args) {

        System.out.println("\n\n******Welcome to Zookeeper Challenge******\n\n");
        try {
            //Application requests files using GUI for scalability
            System.out.println("\n\n Please provide a text file of arriving animals\n\n");
            File animalFile = getFileFromUser("Select arrivingAnimals.txt");
            System.out.println("\n\n Please provide a text file of potential animal names\n\n");
            File namesFile = getFileFromUser("Select animalNames.txt");

            if (animalFile == null || namesFile == null) return;

            // Load the name lists
            loadAnimalNames(namesFile);

            //Prepare the habitats
            Map<String, List<Animal>> habitats = new LinkedHashMap<>();
            habitats.put("Hyena", new ArrayList<>());
            habitats.put("Lion", new ArrayList<>());
            habitats.put("Tiger", new ArrayList<>());
            habitats.put("Bear", new ArrayList<>());

            //Parse animal data
            BufferedReader reader = new BufferedReader(new FileReader(animalFile));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;


                Animal animal = processLine(line);
                if (animal != null) {
                    habitats.get(animal.species).add(animal);
                }
            }
            reader.close();

            //Generate report
            writeReport(habitats);
            JOptionPane.showMessageDialog(null, "Processing Complete! Report Generated!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static File getFileFromUser(String title) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(title);
        int result = chooser.showOpenDialog(null);
        return (result == JFileChooser.APPROVE_OPTION) ? chooser.getSelectedFile() : null;
    }

    private static void loadAnimalNames(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        String currentSpecies = "";
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.contains("Names:")) {
                currentSpecies = line.split(" ")[0];//extracts species
                speciesNames.put(currentSpecies, new LinkedList<>());
            } else if (!line.isEmpty()) {
                String[] names = line.split(", ");
                for (String name : names) speciesNames.get(currentSpecies).add(name);
            }
        }
        reader.close();
    }

    private static Animal processLine(String line) {
        //Parse animal specific information
        String[] parts = line.split(", ");
        if (parts.length < 3) return null;

        String[] info = parts[0].split(" ");
        if (info.length < 5) return null;

        try {
            int age = Integer.parseInt(info[0]);
            String sex = info[3];
            String species = info[4].substring(0, 1).toUpperCase() + info[4].substring(1);

            String birthDate = genBirthDay(age, parts[1]);
            String id = getUniqueID(species);
            String name = (speciesNames.containsKey(species) && !speciesNames.get(species).isEmpty()) ? speciesNames.get(species).poll() : "New Arrival";

            String color = parts[2];
            String weight = parts[3];
            String origin = parts[4] + (parts.length > 5 ? ", " + parts[5] : "");

            return new Animal(id, name, birthDate, color, sex, weight, origin, species, LocalDate.now().toString());
        } catch (Exception e) {
            return null;
        }

    }

    private static String genBirthDay(int age, String season) {
        int year = 2026 - age;//Uses current year
        String monthDay = "01-01"; //Default for unknown birth season
        if (season.contains("spring")) monthDay = "03-21";
        else if (season.contains("summer")) monthDay = "06-21";
        else if (season.contains("fall")) monthDay = "09-21";
        else if (season.contains("winter")) monthDay = "12-21";
        return year + "-" + monthDay;
    }

    private static String getUniqueID(String species) {
        if (species.equalsIgnoreCase("Hyena")) return String.format("Hy%02d", ++numOfHyenas);
        if (species.equalsIgnoreCase("Lion")) return String.format("Li%02d", ++numOfLions);
        if (species.equalsIgnoreCase("Tiger")) return String.format("Ti%02d", ++numOfTigers);
        if (species.equalsIgnoreCase("Bear")) return String.format("Be%02d", ++numOfBears);
        return "XX00";
    }

    private static void writeReport(Map<String, List<Animal>> habitats) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter("zooPopulation.txt"));
        for (String habitat : habitats.keySet()) {
            writer.println(habitat + " Habitat:");
            for (Animal a : habitats.get(habitat)) {
                writer.println(a.toString());
            }
            writer.println();
        }
        writer.close();
        System.out.println("\n\n Thank you for using Zookeeper Challenge! Your report is ready\n\n");
    }
}



