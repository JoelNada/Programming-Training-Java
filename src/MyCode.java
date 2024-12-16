import java.util.Arrays;

public class MyCode {
    public static void test(int... data){
        System.out.println(Arrays.stream(data).boxed().toList());
    }

    public static void maskNumber(long num){
        System.out.println(String.valueOf(num/100000)+"XXXXX");
    }

    public static void reverseNumber(int num){
        int temp=num,rev=0;
        while(num!=0){
            rev=(rev*10)+num%10;
            num=num/10;
        }
        System.out.println(rev);
    }

    public static void sumOfAllDigits(int num){
        int temp=num,sum=0;
        while (num!=0){
            sum=sum+(num%10);
            num/=10;
        }
        System.out.println(sum);
    }

    private static void generateFibonacci(int n) {
        int first=0,second=1,next=0;
        //System.out.printf("%s %s ",first,second);
        for(int i=0;i<n;i++){
            System.out.printf("%s ",first);
            next=first+second;
            first=second;
            second=next;
        }
    }

    public static void main(String... args) {
        test(1,2,3,4);
        maskNumber(7032023213L);
        reverseNumber(12345);
        sumOfAllDigits(1234);
        generateFibonacci(7);

    }


}
