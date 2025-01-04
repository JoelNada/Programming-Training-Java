import java.util.*;
import java.util.stream.Collectors;


public class SplitCode {
    public static void main(String... args) {
//        String bibleVerse;
//        String[] rangeParts = {};
//        System.out.print("Enter a bible verse [Book Name] <Chapter>:<Verses-> : ");
//        Scanner in = new Scanner(System.in);
//        bibleVerse = in.nextLine();
//        String[] filtering = bibleVerse.split("[: ]");
//        if (String.join("", filtering).contains("-")) {
//            rangeParts = filtering[2].split("-");
//            System.out.printf("The bible verse is %s chapter %s verse %s to %s", filtering[0], filtering[1], rangeParts[0], rangeParts[1]);
//        } else {
//            System.out.printf("The bible verse is %s chapter %s verse %s", filtering[0], filtering[1], filtering[2]);
//        }
        String statement = "A man, a plan, a canal: Panama";
       String raw = Arrays.stream(statement.split(": ")).map(s->s.replaceAll("[\\s,]+","")).map(String::toLowerCase).collect(Collectors.joining());
        System.out.println(raw.contentEquals(new StringBuffer(raw).reverse()));
    }
}
