package se.tyboni.perf;

import cern.colt.map.OpenLongObjectHashMap;
import gnu.trove.map.hash.TLongIntHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntRBTreeMap;
import org.junit.Test;

public class MapTest {

    @Test
    public void fastUtil() {

        long multiple = 10002428294482l;

        Long2IntRBTreeMap map = new Long2IntRBTreeMap();
        long time = System.nanoTime();
        for(int i = 0; i < 10000000; i++) {
            map.put(i*multiple, i);
        }
        time = System.nanoTime() - time;

        System.out.println(String.format("fastutil: Avg. time put was %s ns", (time/ 10000000)));


        long totalTime = 0;
        int totalIterations = 0;
        for(int testIterations = 0; testIterations < 100; testIterations++) {
            for(int i = 50; i < 1000; i++) {
                time = System.nanoTime();
                int val = map.get(24*testIterations*i*multiple);
                time = System.nanoTime()-time;
                totalTime += time;
                totalIterations++;
            }
        }
        System.out.println(String.format("fastutil: Total time get was %s ns", totalTime));
        System.out.println(String.format("fastutil: Avg. get time get was %s ns", (totalTime / totalIterations)));
        System.out.println(String.format("fastutil: Total gets per second is %s", (1000000000 / (totalTime / totalIterations))));



    }

    @Test
    public void trove() {
        long multiple = 10002428294482l;


        TLongIntHashMap map = new TLongIntHashMap();
        long time = System.nanoTime();
        for(int i = 0; i < 10000000; i++) {
            map.put(i*multiple, i);
        }
        time = System.nanoTime() - time;

        System.out.println(String.format("Trove: Avg. time put was %s ns", (time/ 10000000)));


        long totalTime = 0;
        int totalIterations = 0;
        for(int testIterations = 0; testIterations < 100; testIterations++) {
            for(int i = 50; i < 1000; i++) {
                time = System.nanoTime();
                int val = map.get(24*testIterations*i*multiple);
                time = System.nanoTime()-time;
                totalTime += time;
                totalIterations++;
            }
        }
        System.out.println(String.format("Trove: Total time get was %s ns", totalTime));
        System.out.println(String.format("Trove: Avg. get time get was %s ns", (totalTime / totalIterations)));
        System.out.println(String.format("Trove: Total gets per second is %s", (1000000000 / (totalTime / totalIterations))));


    }

    @Test
    public void colt() {
        long multiple = 10002428294482l;


        OpenLongObjectHashMap map = new OpenLongObjectHashMap();
        long time = System.nanoTime();
        for(int i = 0; i < 10000000; i++) {
            map.put(i*multiple, i);
        }
        time = System.nanoTime() - time;

        System.out.println(String.format("Colt: Avg. time put was %s ns", (time/ 10000000)));


        long totalTime = 0;
        int totalIterations = 0;
        for(int testIterations = 0; testIterations < 100; testIterations++) {
            for(int i = 50; i < 1000; i++) {
                time = System.nanoTime();
                int val = (Integer)map.get(24*testIterations*i*multiple);
                time = System.nanoTime()-time;
                totalTime += time;
                totalIterations++;
            }
        }
        System.out.println(String.format("Colt: Total time get was %s ns", totalTime));
        System.out.println(String.format("Colt: Avg. get time get was %s ns", (totalTime / totalIterations)));
        System.out.println(String.format("Colt: Total gets per second is %s", (1000000000 / (totalTime / totalIterations))));

    }



}
