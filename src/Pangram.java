import java.util.Arrays;
import java.util.*;
import java.util.stream.Collectors;

public class Pangram {
    public static void main(String[] args) {
        String str = "The quick brown fox jumps over the lazy dog";
        String result = Arrays.stream(str.split("")).map(String::trim).collect(Collectors.joining());
        List<Character> allChars = Arrays.stream(result.split("")).map(String::toLowerCase).map(s->s.charAt(0)).toList();
        Set<Character> allUniqueChars = new HashSet<>();
        for(char c:allChars){
            if(c>='a' && c<='z'){
                allUniqueChars.add(c);
            }
        }
        if(allUniqueChars.size()==26){
            System.out.println("pangram");
        }else{
            System.out.println("not pangram");
        }
        String one="acxz",tw0="bcxz";
        String oneRev="";

        for(int i=one.length()-1;i>=0;i--){
            oneRev=oneRev + one.charAt(i);
        }

        List<Integer>asciis1=new ArrayList<>();
        List<Integer>asciis2 = new ArrayList<>();
        for(char c:one.toCharArray()){
            asciis1.add((int)c);
        }

        for(char c:oneRev.toCharArray()){
            asciis2.add((int)c);
        }

//        System.out.println(asciis1+", "+asciis2);

        List<Integer>res1=new ArrayList<>();
        for(int i=0;i<asciis1.size()-1;i++){
            res1.add(asciis1.get(i+1)-asciis1.get(i));
        }
        System.out.println(res1);

    }
}
