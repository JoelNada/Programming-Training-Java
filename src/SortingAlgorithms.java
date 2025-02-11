import java.util.Arrays;

public class SortingAlgorithms {

    public static int[] bubbleSort(int []a){
        int temp=0;
        for(int i= a.length - 1 ;i > 0; i--){
            for(int j=0;j<i;j++){
                if(a[j] > a[j+1]){
                    temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
        }
     return a;
    }

    public static int[] selectionSort(int []a){
       for(int i=0;i<a.length;i++){
           int min=i;
           for(int j=i+1;j<a.length;j++){
               if(a[j]<a[min]){
                   min=j;
               }
           }
           int temp=a[i];
           a[i]=a[min];
           a[min]=temp;
       }
        return a;
    }


    public static void binarySearch(int[]a, int ele){
        //bubbleSort(a);
        int l=a[0],r=a.length-1,mid=0,flag=0;
        System.out.println(l+" "+r);
        while(l<=r){
            mid=(l+r)/2;
            if(ele==a[mid]){
                flag=1;
                break;
            }
            if(a[mid]<ele){
                l=mid+1;

            }
            if(a[mid]>ele){
                r=mid-1;
            }
            else{
                flag=-1;
            }
        }
        if(flag==1){
            System.out.println("Found!");
        }
        else{
            System.out.println("Not found !!");
        }
    }


    public static void main(String[] args) {

        int []arr = {2,6,1,5,7,4,3};
        System.out.println("Bubble Sort : "+Arrays.toString(bubbleSort(arr)));
        System.out.println("Selection Sort : "+Arrays.toString(selectionSort(arr)));
        binarySearch(arr,7);
    }

}
