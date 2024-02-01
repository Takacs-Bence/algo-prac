import java.io.*;

public class BarcodeHelper {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader("./bar.txt"));

        try {
            String first = bufferedReader.readLine();
            System.out.println(first);
            String second = bufferedReader.readLine();
            System.out.println(second);
            if (first.equals(second)) {
                System.out.println("----- first and second are the same ----");
            } else {
                System.out.println("----- error first and second are NOT the same ----");
            }


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


        bufferedReader.close();
    }

}
