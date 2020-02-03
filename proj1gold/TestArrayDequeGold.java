import static org.junit.Assert.*;
import org.junit.Test;


public class TestArrayDequeGold {
//    @Test
//    public void testArrayDeque(){
//        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
//        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
//
//        // addFirst test!
//        for (int i = 0; i < 10; i++){
//            int random = StdRandom.uniform(100);
//            sad.addFirst(random);
//            ads.addFirst(random);
//        }
//        for (int i = 0; i < 10; i++){
//            int actual = ads.get(i);
//            int expected = sad.get(i);
//            assertEquals("Something went wrong in addFirst()," +
//                    "\n Hope to get " + actual + " but got " + expected, expected, actual);
//        }
//
//        //addLast test!
//        for (int i = 0; i < 10; i++){
//            int random = StdRandom.uniform(100);
//            sad.addLast(random);
//            ads.addLast(random);
//        }
//        for (int i = 0; i < 10; i++){
//            int actual = ads.get(i);
//            int expected = sad.get(i);
//            assertEquals("Something went wrong in addLast()," +
//                    "\n Hope to get " + actual + " but got " + expected, expected, actual);
//        }
//
//        //RemoveFirst Test!
//
//        for (int i = 0; i < 10; i++){
//            if(ads.size() > 0 && sad.size() > 0) {
//                Integer expected = ads.removeFirst();
//                Integer actual = sad.removeFirst();
//                assertEquals("Something went wrong in removeFirst()," +
//                        "\n Hope to get " + actual + " but got " + expected, expected, actual);
//            }
//        }
//
//        for (int i = 0; i < 10; i++){
//            int actual = ads.get(i);
//            int expected = sad.get(i);
//            assertEquals("Something went wrong in addLast()," +
//                    "\n Hope to get " + actual + " but got " + expected, expected, actual);
//        }
//
//        //RemoveLast Test!
//        for (int i = 0; i < 10; i++){
//            if(ads.size() > 0 && sad.size() > 0) {
//                Integer expected = ads.removeLast();
//                Integer actual = sad.removeLast();
//                assertEquals("Something went wrong in removeLast()," +
//                        "\n Hope to get " + actual + " but got " + expected, expected, actual);
//            }
//        }
//
//        for (int i = 0; i < 10; i++){
//            int actual = ads.get(i);
//            int expected = sad.get(i);
//            assertEquals("Something went wrong in addLast()," +
//                    "\n Hope to get " + actual + " but got " + expected, expected, actual);
//        }
//
//    }
//
//}


    @Test

    public void testArrayDeque2() {

        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();

        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();

        int random = StdRandom.uniform(100);

        ads.addFirst(random);

        sad.addFirst(random);

        assertEquals("addFirst(" + random + ")", ads.get(0), sad.get(0));

        System.out.println("addFirst(" + random + ")");


        random = StdRandom.uniform(100);

        ads.addLast(random);

        sad.addLast(random);

        assertEquals("addLast(" + random + ")", ads.get(1), sad.get(1));

        System.out.println("addLast(" + random + ")");


        int actual = ads.removeFirst();

        int expected = ads.removeFirst();

        assertEquals("removeFirst()", actual, expected);

        System.out.println("removeFirst()");


        actual = ads.removeLast();

        expected = sad.removeLast();

        assertEquals("removeLast()", actual, expected);

        System.out.println("removeLast()");

    }
}
