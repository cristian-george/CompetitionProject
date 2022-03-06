import java.util.List;

public class Main {

    public static <T> void printObjects(List<T> list) {
        for(T object : list) {
            System.out.println(object.toString());
        }
        System.out.println();
    }

    public static void main(String[] args) {

    }
}
