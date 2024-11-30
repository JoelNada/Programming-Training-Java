public class OOPS {

    public static void main(String[] args) {
        Shape s = new Rectangle();
        s.draw();
    }
}

class Shape{
    public void draw(){
        System.out.println("Drawing a shape");
    }
}

class Rectangle extends Shape{
    public void draw(){
        System.out.println("Drawing a rectangle");
    }
}