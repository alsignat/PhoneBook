import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine().toLowerCase();
        int index = str.indexOf("the");
        System.out.println(index);
        int numb = scanner.nextInt();
        System.out.println(10/numb);
    }
}