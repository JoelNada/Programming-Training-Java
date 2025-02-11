import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.util.stream.Collectors;


public class CollectionsCode {

    public static void main(String[] args) {
        int []a ={1,2,33,4,4,5};
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        Scanner in = new Scanner(System.in);
        List<String> names = new ArrayList<>();
        for(int i=1;i<=4;i++){
            String name = in.nextLine();
            names.add(name);
        }



        System.out.println(Arrays.toString(names.toArray()));

//        System.out.println(list);
//        System.out.println(list.stream().sorted(Comparator.reverseOrder()).skip(1).limit(1).toList());
//Map<String, Integer> count=Map.of("Joel",1,"Naveen",1, "Eswar",2);
//        System.out.println(count);
        //list.forEach(System.out::println);
//        System.out.println(  "Stream operation " + list.stream().map(num -> num*2).toList());
//        System.out.println("Filter even numbers "+ list.stream().filter(num -> num%2==0).toList());
        //names.forEach(System.out::println);
        System.out.println(names);
        System.out.println(names.stream().collect(Collectors.groupingBy(s->s,Collectors.counting())));
    }
}
