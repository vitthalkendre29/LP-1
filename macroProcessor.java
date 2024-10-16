import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class macroProcessor {
    private static HashMap<String, Integer> mnt = new HashMap<String, Integer>(); // Macro Name Table
    private static ArrayList<String> mdt = new ArrayList<String>(); // Macro Definition Table
    private static ArrayList<String> ala = new ArrayList<String>(); // Argument List Array
    private static ArrayList<String> intermediateCode = new ArrayList<String>(); // Intermediate code without macros
    private static String fileContent;
    private static Pass1 pass1 = new Pass1();

    public static void main(String[] args) throws IOException {
        System.out.println("******** Two Pass Macro-Processor **********\n\n");

        // Specify the file path to read the source code
        fileContent = getContentFrom("C:/Users/ASUS/OneDrive/Desktop/shubham/shubham.txt");
        
        pass1.start(); // Start Pass 1
        System.out.println("Contents of MNT (Macro Name Table): \n" + mnt);
        System.out.println("Contents of MDT (Macro Definition Table): \n" + mdt);
        System.out.println("Intermediate Code: \n" + intermediateCode);
    }

    // Read content from the specified file
    private static String getContentFrom(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();
        return content.toString();
    }

    public static String getFileContent() {
        return fileContent;
    }

    public static void updateMnt(String name, int mdtIndex) {
        mnt.put(name, mdtIndex);
    }

    public static void updateMdt(String line) {
        mdt.add(line);
    }

    public static void updateAla(String arg) {
        ala.add(arg);
    }

    public static void updateIntermediateCode(String line) {
        intermediateCode.add(line);
    }

    public static HashMap<String, Integer> getMnt() {
        return mnt;
    }

    public static ArrayList<String> getMdt() {
        return mdt;
    }

    public static ArrayList<String> getIntermediateCode() {
        return intermediateCode;
    }
}
