import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class UserProfile {
    String name;
    int age;
    String gender;
    double weight;
    double height;
    double bmi;

    UserProfile(String name, int age, String gender, double weight, double height) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        calculateBMI();
    }

    void calculateBMI() {
        bmi = weight / (height * height);
    }

    String getBMICategory() {
        if (bmi < 18.5) return "Underweight";
        else if (bmi < 25) return "Normal";
        else if (bmi < 30) return "Overweight";
        else return "Obese";
    }

    void displayProfile() {
        System.out.printf("\nName: %s\nAge: %d\nGender: %s\n", name, age, gender);
        System.out.printf("Weight: %.1f kg\nHeight: %.2f m\n", weight, height);
        System.out.printf("BMI: %.2f (%s)\n", bmi, getBMICategory());
    }
}

class WorkoutSession {
    static final int MIN_DURATION = 10;
    String workoutType;
    int duration;
    double caloriesBurned;
    char grade;
    LocalDateTime sessionTime;

    WorkoutSession(String workoutType, int duration) {
        this.workoutType = workoutType;
        this.duration = duration;
        this.sessionTime = LocalDateTime.now();
        calculateCalories();
        assignGrade();
    }

    WorkoutSession(int duration) {
        this("Cardio", duration);
    }

    void calculateCalories() {
        if (workoutType.equalsIgnoreCase("Yoga"))
            caloriesBurned = duration * 3.0;
        else if (workoutType.equalsIgnoreCase("Cardio"))
            caloriesBurned = duration * 5.5;
        else if (workoutType.equalsIgnoreCase("Strength"))
            caloriesBurned = duration * 6.5;
        else
            caloriesBurned = duration * 4.0;
    }

    void assignGrade() {
        grade = (duration >= 60) ? 'A' :
                (duration >= 40) ? 'B' :
                (duration >= 20) ? 'C' : 'D';
    }

    double calculateWaterIntake() {
        return duration * 0.03;
    }

    void workoutDescription() {
        System.out.print("Workout Info: ");
        if (workoutType.equalsIgnoreCase("Yoga"))
            System.out.println("Improves flexibility, balance, and reduces stress.");
        else if (workoutType.equalsIgnoreCase("Cardio"))
            System.out.println("Boosts heart health and burns calories fast.");
        else if (workoutType.equalsIgnoreCase("Strength"))
            System.out.println("Builds muscle strength and boosts metabolism.");
        else
            System.out.println("General fitness improvement.");
    }

    void motivationalMessage(String name) {
        String msg;
        if (grade == 'A') msg = "Excellent job, NAME! You crushed it!";
        else if (grade == 'B') msg = "Keep it up, NAME!";
        else if (grade == 'C') msg = "Not bad, NAME! Stay consistent!";
        else msg = "Come on, NAME! You can do better tomorrow!";

        System.out.println(msg.replace("NAME", name));
    }

    void displayWaterIntake() {
        System.out.printf("Suggested Water Intake: %.2f liters\n", calculateWaterIntake());
    }

    void displaySummary(String userName) {
        System.out.printf("\nWorkout Summary\n------------------\n");
        System.out.printf("Workout Type: %s\nDuration: %d minutes\n", workoutType, duration);
        System.out.printf("Calories Burned: %.2f\nGrade: %c\n", caloriesBurned, grade);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("Session Time: " + sessionTime.format(formatter));
        workoutDescription();
        displayWaterIntake();
        motivationalMessage(userName);
    }

    // Clean and match workout types
    static String cleanWorkoutType(String input) {
        input = input.trim().toLowerCase().replaceAll("[^a-z]", "");

        if (input.contains("yoga")) return "Yoga";
        else if (input.contains("cardio")) return "Cardio";
        else if (input.contains("strength")) return "Strength";
        else return "Unknown";
    }

    static WorkoutSession startSession(Scanner sc, int attempts) {
        if (attempts >= 2) {
            System.out.println("Too many failed attempts. Using default session.");
            return new WorkoutSession("Cardio", 20);
        }

        System.out.print("Enter workout type (Yoga/Cardio/Strength): ");
        String rawType = sc.next();
        String type = cleanWorkoutType(rawType);

        System.out.print("Enter duration in minutes: ");
        int dur = sc.nextInt();

        if ((type.equals("Yoga") || type.equals("Cardio") || type.equals("Strength"))
                && dur >= MIN_DURATION) {
            return new WorkoutSession(type, dur);
        } else {
            System.out.println("Invalid input. Please try again.");
            return startSession(sc, attempts + 1);
        }
    }
}

public class FitLife {
    static String userName;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to FitLife Personal Fitness Tracker");

        System.out.print("Enter your name: ");
        String rawName = sc.nextLine();
        userName = formatName(rawName);

        if (userName.length() < 3) {
            System.out.println("That's a short name! Nickname mode activated!");
        }

        System.out.print("Enter your age: ");
        int age = sc.nextInt();

        System.out.print("Enter your gender: ");
        String rawGender = sc.next();
        String gender = formatGender(rawGender);

        System.out.print("Enter your weight (kg): ");
        double weight = sc.nextDouble();

        System.out.print("Enter your height (m): ");
        double height = sc.nextDouble();

        UserProfile user = new UserProfile(userName, age, gender, weight, height);
        user.displayProfile();

        System.out.println("\nLet's log your workout session:");
        WorkoutSession session = WorkoutSession.startSession(sc, 0);
        session.displaySummary(userName);

        System.out.println("\nData saved successfully. Keep moving!");
    }

    static String formatName(String name) {
        name = name.trim().toLowerCase();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    static String formatGender(String gender) {
        gender = gender.trim();
        if (gender.equalsIgnoreCase("male")) return "Male";
        else if (gender.equalsIgnoreCase("female")) return "Female";
        else return "Other";
    }
}
