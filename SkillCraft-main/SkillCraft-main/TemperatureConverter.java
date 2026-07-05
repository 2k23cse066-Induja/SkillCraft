import java.util.Scanner;

public class TemperatureConverter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Temperature Converter");
        System.out.println("1. Celsius");
        System.out.println("2. Fahrenheit");
        System.out.println("3. Kelvin");

        System.out.print("Convert from (1/2/3): ");
        int choice = sc.nextInt();

        System.out.print("Enter temperature value: ");
        double temp = sc.nextDouble();

        switch (choice) {
            case 1:
                double fahrenheit = (temp * 9 / 5) + 32;
                double kelvin = temp + 273.15;

                System.out.printf("%.2f Celsius = %.2f Fahrenheit%n", temp, fahrenheit);
                System.out.printf("%.2f Celsius = %.2f Kelvin%n", temp, kelvin);
                break;

            case 2:
                double celsius = (temp - 32) * 5 / 9;
                kelvin = celsius + 273.15;

                System.out.printf("%.2f Fahrenheit = %.2f Celsius%n", temp, celsius);
                System.out.printf("%.2f Fahrenheit = %.2f Kelvin%n", temp, kelvin);
                break;

            case 3:
                celsius = temp - 273.15;
                fahrenheit = (celsius * 9 / 5) + 32;

                System.out.printf("%.2f Kelvin = %.2f Celsius%n", temp, celsius);
                System.out.printf("%.2f Kelvin = %.2f Fahrenheit%n", temp, fahrenheit);
                break;

            default:
                System.out.println("Invalid choice!");
        }

        sc.close();
    }
}