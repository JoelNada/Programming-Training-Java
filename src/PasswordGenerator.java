import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIALS = "!@#$%^&*()-_";

    private static final SecureRandom random = new SecureRandom();

    public static String generatePassword(int length) {
        if (length < 8) throw new IllegalArgumentException("Password length must be at least 8");

        StringBuilder password = new StringBuilder();
        password.append(UPPER.charAt(random.nextInt(UPPER.length())));
        password.append(LOWER.charAt(random.nextInt(LOWER.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIALS.charAt(random.nextInt(SPECIALS.length())));
        System.out.println(password);
        String allChars = UPPER + LOWER + DIGITS + SPECIALS;
        System.out.println(allChars);
        for (int i = 4; i < length; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }
        System.out.println(password);
        // Shuffle characters in the StringBuilder
        for (int i = 0; i < password.length(); i++) {
            int randomIndex = random.nextInt(password.length());
            char temp = password.charAt(i);
            password.setCharAt(i, password.charAt(randomIndex));
            password.setCharAt(randomIndex, temp);
        }

        return password.toString();


    } public static void main(String ...args) {
//            System.out.println(generatePasswordWithStreams(10));
        System.out.println(generatePassword(8));
    }
}
