import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        String word = "oneTwoThree";
        List<String> allWords = Arrays.stream(word.split("(?=[A-Z])")).toList();
        System.out.println("Hello, World!");
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        list.stream().map(nums -> nums*2).forEach(System.out::println);
        String name = "Naveen";
        System.out.println( Arrays.stream(name.split("")).map(String::toLowerCase).collect(Collectors.groupingBy(s->s,Collectors.counting())));
        String strWithVowels = "Arsenious";
        System.out.println((Arrays.stream(strWithVowels.split("")).filter("AEIOUaeiou"::contains).count()));
        String pal = "madam";
        StringBuilder palTest = new StringBuilder(pal).reverse();
//        palTest = palTest.reverse();
        if(pal.contentEquals(palTest)){
            System.out.println("Palindrome");
        }
        else{
            System.out.println("Not a palindrome");
        }
        String sentence = "The quick brown fox";
        System.out.println((Arrays.stream(sentence.split(" ")).max(Comparator.comparing(String::length))).get());

        String numStr = "Heloo6942";
        Arrays.stream(numStr.split("")).map(str->str.charAt(0)).filter(Character::isDigit).forEach(System.out::println);

        //program for anagram
        String an1="listen";
        String an2 = "silent";

        System.out.println(Arrays.stream(an1.split("")).sorted().toList().equals(Arrays.stream(an2.split("")).sorted().toList()));
//        System.out.println( );

        String sentence2 = "My name is Joel";
        String newReverse = Arrays.stream(sentence2.split(" ")).map(StringBuilder::new).map(StringBuilder::reverse).collect(Collectors.joining(" "));
        System.out.println(newReverse);


    }
}