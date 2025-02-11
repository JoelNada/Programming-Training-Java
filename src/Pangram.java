import java.util.*;
import java.util.stream.Collectors;

public class Pangram {
    public static void main(String[] args) {
        String str = "The quick brown fox jumps over the lazy dog";
//        String result = Arrays.stream(str.split("")).map(String::trim).collect(Collectors.joining());
//        List<Character> allChars = Arrays.stream(result.split("")).map(String::toLowerCase).map(s->s.charAt(0)).toList();
//        Set<Character> allUniqueChars = new HashSet<>();
//        for(char c:allChars){
//            if(c>='a' && c<='z'){
//                allUniqueChars.add(c);
//            }
//        }
//        if(allUniqueChars.size()==26){
//            System.out.println("pangram");
//        }else{
//            System.out.println("not pangram");
//        }
//        String one="acxz",tw0="bcxz";
//        String oneRev="";
//
//        for(int i=one.length()-1;i>=0;i--){
//            oneRev=oneRev + one.charAt(i);
//        }
//
//        List<Integer>asciis1=new ArrayList<>();
//        List<Integer>asciis2 = new ArrayList<>();
//        for(char c:one.toCharArray()){
//            asciis1.add((int)c);
//        }
//
//        for(char c:oneRev.toCharArray()){
//            asciis2.add((int)c);
//        }
//
////        System.out.println(asciis1+", "+asciis2);
//
//        List<Integer>res1=new ArrayList<>();
//        for(int i=0;i<asciis1.size()-1;i++){
//            res1.add(asciis1.get(i+1)-asciis1.get(i));
//        }
//        System.out.println(res1);
//i added this part
       String pangram = Arrays.stream(str.replaceAll("\\s+","")
                       .split(""))
               .map(String::toLowerCase)
               .distinct()
               .sorted()
               .collect(Collectors.joining());

        String alphabets = "abcdefghijklmnopqrstuvwxyz";
        System.out.println(pangram.equals(alphabets));

        String mixtureOfChars = "Hello@123$#%asdfJOEL";

        Map<String,List<Character>> groupedMixture =  Arrays.stream(mixtureOfChars.split("")).map(s->s.charAt(0))
                .collect(Collectors.groupingBy(s->
                {
                    if(Character.isDigit(s)){
                        return "numbers";
                    }
                    if(Character.isAlphabetic(s)){
                        return "alphabets";
                    }
                    else{
                        return "specialChars";
                    }
                }));
         /// this groupingBy works well !!
        groupedMixture.forEach((k,v)-> {
            if(Objects.equals(k, "alphabets")){
                System.out.println(k+" : "+v.stream().sorted().collect(Collectors.groupingBy(s->{
                    if(Character.isLowerCase(s)){
                        return "lower";
                    }
                    else{
                        return "upper";
                    }
                })));
            }
            else{
                System.out.println(k+" : "+v);
            }
        });
        ///List<Object> objects = List.of("1","Hello");
        String mixture = "Hello1234";
        System.out.println(Arrays.stream(mixture.split(""))
                .map(s->s.charAt(0))
                .filter(Character::isDigit)
                .map(s->s-'0')
                //.reduce(0, Integer::sum)
                .mapToInt(s -> s).sum()
                );


    }
}
