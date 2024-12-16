class MyLinkedList<T>{
    public static class Node<T>{
        T data;
        Node<T> next;
    }

    Node<T> first,last;
    public void add(T value){
        Node<T> newnode = new Node<>();
        newnode.data=value;
        if(first==null){
            first=last=newnode;
        }
        else{
            last.next=newnode;
            last=newnode;
            newnode.next=null;
        }
    }
//--------------------------------
    public void add(T value, int index){
        Node<T> newnode = new Node<>();
        newnode.data=value;
        if(first==null){
            first=last=newnode;
        }
        else{Node<T> temp=first;
            for(int i=0;i<index-1;i++){
                temp=temp.next;
            }
            newnode.next=temp.next;
            temp.next=newnode;
        }


    }
//--------------------------
    public void printList(){
        Node<T> temp=first;
        while(temp!=null){
            System.out.print(temp.data+" ");
            temp=temp.next;
        }
        System.out.println();
    }
//-------------
    public void addFirst(T value){
        Node<T> newnode = new Node<>();
        newnode.data=value;
        if(first==null){
            first=last=newnode;
        }
        else{
            newnode.next=first;
            first=newnode;
        }
    }
//------------------
    public void addLast(T value){
        Node<T> newnode = new Node<>();
        newnode.data=value;
        if(first==null){
            first=last=newnode;
        }
        else{
            last.next=newnode;
            last=newnode;

        }
    }

}

public class DSA {
    public static void main(String[] args) {
        MyLinkedList<Integer> linkedList = new MyLinkedList<>();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.printList();
        linkedList.addFirst(11);
        linkedList.printList();
        linkedList.addLast(23);
        linkedList.printList();
        linkedList.add(55,2);
        linkedList.printList();
    }
}
