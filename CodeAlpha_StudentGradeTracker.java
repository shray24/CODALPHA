import java.util.Scanner;
import java.util.Arrays;
import java.text.DecimalFormat;
public class StudentGradeTracker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DecimalFormat df= new DecimalFormat("#.##");
        System.out.print("Enter the number of Students: ");
        int students = sc.nextInt();
        System.out.print("Enter number of Subjects: ");
        int subjects = sc.nextInt();
        int marks[][] = new int[students][subjects];
        for (int i = 0; i < students; i++) {
            System.out.println("\nStudent " + (i + 1));
            for (int j = 0; j < subjects; j++) {
                System.out.print("Subject " + (j + 1) + " : ");
                marks[i][j] = sc.nextInt();
            }
        }
        System.out.println("\n========== STUDENT GRADE TRACKER ==========");
        for (int i = 0; i < students; i++) {
            int sum = 0;
            int sub[] = new int[subjects];
            for (int j = 0; j < subjects; j++) {
                sub[j] = marks[i][j];
                sum += sub[j];
            }
            double average = (double) sum / subjects;
            Arrays.sort(sub);
            System.out.println("\nStudent " + (i + 1));
            System.out.println("Average :"+ df.format(average));
            System.out.println("Highest : " + sub[subjects - 1]);
            System.out.println("Lowest  : " + sub[0]);
        }
        sc.close();
    }
}