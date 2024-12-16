import java.util.*;
import java.util.stream.Collectors;

public class DecimalToBinary {

    public static int power(int num, int power){
        if(power==0)
            return 1;
        if(power==1)
            return 2;
        else return num*power(num,power-1);

    }

    private static String toBinary(int number) {
        int temp=number;
        if(number==0)
        {
            System.out.println(number);
        }
        StringBuilder binary = new StringBuilder();
        while(number!=0){ int remainder = 0;
            remainder=number%2;
            binary.append(remainder);
            number/=2;}
       return binary.reverse().toString();

}
    private static int toDecimal(String binary) {
        int length = binary.length(),res=0;
        System.out.println("length : "+length);
        StringBuilder bin = new StringBuilder(binary).reverse();
      int num = Integer.parseInt(Arrays.stream(bin.toString().split("")).map(s->(Character)s.charAt(0)).filter(Character::isDigit).map(s -> String.valueOf((char) s)).collect(Collectors.joining()));
      while(num!=0){
          res=res+(num%10*power(2,length-1));
          length-=1;num=num/10;
      }
        return res;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter a number : ");
        int number = in.nextInt();

        String binaryNumber = toBinary(number);
        System.out.printf("The binary format of %s is %s\n",number,binaryNumber);
        System.out.print("Do you want to convert it back to decimal form ? (Y/N) : ");
        String choice = in.next().trim();
        if(choice.matches("[Yy]")){
            int decimal = toDecimal(binaryNumber);
            System.out.println(decimal);
        }
        else{
            System.out.println("Have a nice day, See you later !!");
        }
    }




}
