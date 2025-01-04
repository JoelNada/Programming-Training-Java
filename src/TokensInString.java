public class TokensInString {
    public static void main(String[] args) {
        String s = "rhbaasdndfsdskgbfefdbrsdfhuyatrjtcrtyytktjjt";
        String target = "hackerrank";

        boolean isPresent = containsAllCharacters(s, target);
        System.out.println(isPresent); // Output should be false
    }

    public static boolean containsAllCharacters(String s, String target) {
        // Array to store character counts for 's' and 'target'
        int[] sCount = new int[26]; // For 's'
        int[] targetCount = new int[26]; // For 'target'

        // Count frequencies in 's'
        for (char ch : s.toCharArray()) {
            sCount[ch - 'a']++;
        }

        // Count frequencies in 'target'
        for (char ch : target.toCharArray()) {
            targetCount[ch - 'a']++;
        }

        // Check if 's' has enough characters to cover 'target'
        for (int i = 0; i < 26; i++) {
            if (targetCount[i] > 0 && sCount[i] < targetCount[i]) {
                System.out.println("Not enough of '" + (char)(i + 'a') + "'. Needed: " + targetCount[i] + ", Found: " + sCount[i]);
                return false; // Not enough of this character
            }
        }

        return true; // All characters are covered
    }
}
