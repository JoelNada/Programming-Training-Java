import java.util.Scanner;

public class NewCode {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
            String target = "hackerrank";  // The word we're looking for as a subsequence

        int t = sc.nextInt();  // Number of test cases
        sc.nextLine();  // Consume the newline after the integer input

        for (int i = 0; i < t; i++) {
            String s = sc.nextLine();  // The query string for each test case
            if (containsHackerrank(s, target)) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }

    public static boolean containsHackerrank(String s, String target) {
        int targetIndex = 0;
        int targetLength = target.length();

        // Traverse the string s and try to match the target subsequence
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == target.charAt(targetIndex)) {
                targetIndex++;
            }
            if (targetIndex == targetLength) {
                return true;  // All characters of "hackerrank" are found
            }
        }
        System.out.println(targetIndex+" "+targetLength);

        return false;  // Not all characters of "hackerrank" are found in order
    }
}
