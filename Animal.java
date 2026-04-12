//Zookeeper's Challenge Midterm CIT-63
//AlexBrush
//4-12-2026

//Animal Class defines the animals that inhabit the zoo helps with scalability

public class Animal {
    public String id;
    public String name;
    public String birthDate;
    public String color;
    public String sex;
    public String weight;
    public String origin;
    public String species;
    public String arrivalDate;

    //Constructor intitializes animal objects
    public Animal(String id, String name, String birthDate, String color, String sex, String weight, String origin, String species, String arrivalDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.color = color;
        this.sex = sex;
        this.weight = weight;
        this.origin = origin;
        this.species = species;
        this.arrivalDate = arrivalDate;
    }

    @Override
    public String toString() {
        //Ensures format of output is as requested
        return String.format("%s; %s; birth date: %s; %s; %s; %s; %s; arrived: %s", id, name, birthDate, color, sex, weight, origin, arrivalDate);
    }
}
