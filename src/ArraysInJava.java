import java.util.Arrays;

public class ArraysInJava {

    static void printBothDiagonal(int[][] a){
        System.out.println("Program to print both diagonals : ");
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a.length;j++){
                if( i!=j && i+j!=2){
                    System.out.print(0+"  ");
                    continue;
                }
                System.out.print(a[i][j]+"  ");
            }
            System.out.println();
        }
    }


    static void isArrayPalindrome(int[]a){
        boolean isEqual=false;
        int n=a.length;
        int l=0,r=n-1;
        while(l<r){
            if(a[l]!=a[r]){
               isEqual=false;
               break;
            }
            else{
                isEqual=true;
                l++;r--;
            }
        }
        if(isEqual){
            System.out.printf("The given array %s is a palindrome",Arrays.toString(a));
        }

    }

    static void pushZeroesToEnd(int[] a) {
        int size = a.length,i=0;
        for(int nums:a){
            if(nums!=0){
                a[i++]=nums;
            }
        }
        while(i<size){
            a[i++]=0;
        }
        System.out.println(Arrays.toString(a));
    }

    public static void main(String[] args) {
        int[][]a = {{1,2,3},{4,5,6},{7,8,9}};
        printBothDiagonal(a);

        int []aWz={0,1,0,0,2,3,0,0,0,0,4,0,5};
        pushZeroesToEnd(aWz);
        //System.out.println(Arrays.deepToString(a));

        int[] a1 = {1,2,3,4,3,2,1};
        isArrayPalindrome(a1);


    }
}
