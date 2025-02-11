import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Practice {

    static String input;

 /// String compression program
    public static void compressString(){
        if(input.isEmpty()){
            System.out.println("Input can't be empty, please enter again !!");
            main(null);
        }else{
            System.out.println(input);
            List<String> inputArray = Arrays.stream(input.split("")).toList();
            //System.out.println(inputArray.stream().collect(Collectors.groupingBy(s->s,Collectors.counting())));
            inputArray.stream().sorted(Comparator.naturalOrder())
                    .collect(Collectors.groupingBy(s->s,Collectors.counting()))
                    .forEach((k,v)-> System.out.printf("%s%s",v,k));
        }
    }

    public static void main(String... args) {
        System.out.print("Enter input : ");
        Scanner in = new Scanner(System.in);
        input=in.nextLine();
        compressString();
    }


}
