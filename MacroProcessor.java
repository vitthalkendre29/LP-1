import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MacroProcessor {
    private static HashMap<String, Integer> mnt = new HashMap<String, Integer>(); // Macro Name Table
    private static ArrayList<String> mdt = new ArrayList<String>(); // Macro Definition Table
    private static ArrayList<String> ala = new ArrayList<String>(); // Argument List Array
    private static ArrayList<String> intermediateCode = new ArrayList<String>(); // Intermediate code without macros
    private static String fileContent;
    private static Pass1 pass1 = new Pass1();
    private static Pass2 pass2 = new Pass2();
    
    public static void main(String[] args) throws IOException {
        System.out.println("******** Two Pass Macro-Processor **********\n\n");

        fileContent = getContentFrom("C:/Users/ASUS/OneDrive/Desktop/CNS/lp/asscode.txt");
        
        pass1.start(); // Start Pass 1
        System.out.println("Contents of MNT (Macro Name Table): \n" + mnt);
        System.out.println("Contents of MDT (Macro Definition Table): \n" + mdt);
        System.out.println("Intermediate Code: \n" + intermediateCode);

        pass2.start(); // Start Pass 2
    }

    // Reading content from a file
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

class Pass1 {
    public void start() {
        System.out.println("Starting Pass 1...");

        String[] lines = MacroProcessor.getFileContent().split("\\n");
        boolean insideMacro = false;
        String macroName = null;

        for (String line : lines) {
            String[] parts = line.trim().split("\\s+");

            if (parts[0].equals("MACRO")) {
                insideMacro = true;
                continue;
            }

            if (insideMacro) {
                if (macroName == null) {
                    // First line inside macro is the macro name
                    macroName = parts[0];
                    MacroProcessor.updateMnt(macroName, MacroProcessor.getMdt().size()); // Add to MNT
                    // Process arguments and add them to ALA
                    if (parts.length > 1) {
                        String[] args = parts[1].split(",");
                        for (String arg : args) {
                            MacroProcessor.updateAla(arg);
                        }
                    }
                    continue;
                }

                if (parts[0].equals("MEND")) {
                    insideMacro = false;
                    macroName = null;
                    MacroProcessor.updateMdt("MEND"); // Add MEND to MDT
                } else {
                    // Add the macro definition lines to MDT
                    MacroProcessor.updateMdt(line);
                }
            } else {
                // Outside macro definition, add line to intermediate code
                MacroProcessor.updateIntermediateCode(line);
            }
        }

        System.out.println("Pass 1 complete.");
    }
}

class Pass2 {
    public void start() {
        System.out.println("Starting Pass 2...");

        ArrayList<String> intermediateCode = MacroProcessor.getIntermediateCode();

        for (String line : intermediateCode) {
            String[] parts = line.split("\\s+");
            String macroName = parts[0];

            if (MacroProcessor.getMnt().containsKey(macroName)) {
                // Macro found, expand it
                int mdtIndex = MacroProcessor.getMnt().get(macroName);
                expandMacro(mdtIndex);
            } else {
                // Normal line, print it
                System.out.println(line);
            }
        }

        System.out.println("Pass 2 complete.");
    }

    private void expandMacro(int mdtIndex) {
        while (!MacroProcessor.getMdt().get(mdtIndex).equals("MEND")) {
            String macroLine = MacroProcessor.getMdt().get(mdtIndex);
            System.out.println(macroLine);
            mdtIndex++;
        }
    }
}
