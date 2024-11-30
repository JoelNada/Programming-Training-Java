public class JavaIntro {

    int num1;
    static int num2;
    static int num3;
    static int num4;

    public static void main(String[] args) {


        JavaIntro obj = new JavaIntro();
        JavaIntro obj2 = new JavaIntro();
        obj.num1 = 10;
        obj2.num1 = 15;
        JavaIntro.num2 = 20;
        System.out.println(obj.num1);
        JavaIntro.num2=30;
        System.out.println(JavaIntro.num2);
    }
}


