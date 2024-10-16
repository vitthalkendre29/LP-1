
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
