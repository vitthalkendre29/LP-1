import java.util.*;

public class vk{
    public static void fifo(int[] pages, int capacity){
        Set<Integer> s =new HashSet<>(capacity);
        Queue<Integer> indexes =new LinkedList<>();
        int pageFaults=0;
        for(int i=0; i < pages.length; i++){
            if(!s.contains(pages[i])){
                if(s.size()==capacity){
                    int val=indexes.poll();
                    s.remove(val);
                }
                s.add(pages[i]);
                indexes.add(pages[i]);
                pageFaults++;
            }
        }
        System.out.println("FIFO Page Faults: " + pageFaults);
    }

    public static void lru(int[] pages,int capacity){
        Set<Integer> s = new HashSet<>(capacity);
        Queue<Integer> indexes = new LinkedList<>();
        int pagefaults=0;
        for(int i=0;i<pages.length;i++){
            if(!s.contains(pages[i])){
                if(s.size()==capacity){
                    int val=indexes.poll();
                    s.remove(val);
                }
                s.add(pages[i]);
                indexes.add(pages[i]);
                pagefaults++;
            }
            System.out.println(pagefaults);
        }
    }
    public static void main(String[] args){
            Scanner input=new Scanner(System.in);
            System.out.println("Enter number of pages: ");
            int n=input.nextInt();
            int[] pages = new int[n];
            System.out.println("Enter reference string (pages): ");
            for (int i = 0; i < n; i++) {
                pages[i] = input.nextInt();
            }

            System.out.print("Enter the number of frames (capacity): ");
            int capacity = input.nextInt();
            
            System.out.println("\nSimulating Page Replacement Algorithms:");
            fifo(pages, capacity);
            input.close();
    }
}
