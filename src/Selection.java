import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Selection <E extends Comparable<E>> {
    int k;
    ArrayList<E> input ; // this holds the values of type E from which your code will find kth largest.
    // constructors

    public Selection(ArrayList<E> list, int k){
        this.input = list;
        this.k = k;
    }

//    public Selection(int range, int k){
//        Random rand = new Random();
//        this.input = Arrays.stream(IntStream.range(0, range).map(i -> ThreadLocalRandom.current().nextInt()).toArray()).boxed().toArrayList();
//
//    }

    // algorithm 1 methods -- implement 1B

    public E algorithm1B(){
        long start_time = System.currentTimeMillis();
        ArrayList<E> subList = new ArrayList<E>(this.input.subList(0, this.k));
        Iterator<E> remaining  = (new ArrayList<E>(this.input.subList(this.k, this.input.size()))).iterator();
        Collections.sort(subList, Collections.reverseOrder());
        while(remaining.hasNext()){
            E kth = subList.get(this.k - 1);
            E temp = remaining.next();
            if(kth.compareTo(temp) < 1){
                int ind = Collections.binarySearch(subList, temp, Collections.reverseOrder());
                subList.remove(this.k - 1);
                if (ind < 0) {
                    ind = -ind - 1;
                }
                subList.add(ind, temp);
            }
            else{
                continue;
            }
        }
        long end_time = System.currentTimeMillis();
        System.out.println("Algorithm 1B: " + (end_time - start_time));
        return subList.get(this.k - 1);
    }

    // algorithm 2 methods -- 6A -- change the algorithm to do kth largest not kth smallest that is described here
    public E algorithm6A(){
        long start_time = System.currentTimeMillis();
        PriorityQueue<E> mheap = new PriorityQueue<>(Comparator.reverseOrder());
        for(E i: this.input){
            mheap.add(i);
        }
        for(int i = 0; i < this.k - 1; i++){
            mheap.remove();
        }
        long end_time = System.currentTimeMillis();
        System.out.println("Algorithm 6A: " + (end_time - start_time));
        return mheap.remove();
    }

    // algorithm 3 methods – 6B
    public E algorithm6B(){
        long start_time = System.currentTimeMillis();
        PriorityQueue<E> mheap = new PriorityQueue<>();
        for(int i = 0; i < this.k; i++){
            mheap.add(this.input.get(i));
        }
        Iterator<E> remaining  = (new ArrayList<E>(this.input.subList(this.k, this.input.size()))).iterator();
        while(remaining.hasNext()){
            E l = mheap.peek();
            E temp = remaining.next();
            if(l.compareTo(temp) < 1){
                mheap.remove();
                mheap.add(temp);
            }
        }
        long end_time = System.currentTimeMillis();
        System.out.println("Algorithm 6B: " + (end_time - start_time));
        return mheap.peek();
    }

    public static void main(String[] args) {
        ArrayList<Integer> a1 = new ArrayList<>(Arrays.asList(1,5,10,7,3,2,17,18));
        System.out.println(a1);
        Selection s1 = new Selection(a1, 4);
        System.out.println(s1.algorithm1B());
        System.out.println(s1.algorithm6A());
        System.out.println(s1.algorithm6B());

        ArrayList<Integer> num_list = new ArrayList<>();
        Random rand = new Random();
        Iterator<Integer> s = rand.ints(10000000).iterator();
        while(s.hasNext()){
            num_list.add(s.next());
        }
        Selection s2 = new Selection(num_list, 1000000);
        System.out.println(s2.algorithm1B());
        System.out.println(s2.algorithm6A());
        System.out.println(s2.algorithm6B());
    }
}