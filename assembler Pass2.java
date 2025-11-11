import java.io.*;
import java.util.*;

class Obj {
    String name;
    int addr;

    Obj(String name, int addr) {
        this.name = name;
        this.addr = addr;
    }
}

public class Pass2 {
    public static void main(String[] args) {
        try {
            // Read symbol table
            List<Obj> symbTable = new ArrayList<>();
            BufferedReader brSym = new BufferedReader(new FileReader("SYMTAB.txt"));
            String line;
            while ((line = brSym.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.trim().split("\\s+");
                symbTable.add(new Obj(parts[0], Integer.parseInt(parts[1])));
            }
            brSym.close();

            // Read literal table
            List<Obj> literalTable = new ArrayList<>();
            BufferedReader brLit = new BufferedReader(new FileReader("LITTAB.txt"));
            while ((line = brLit.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.trim().split("\\s+");
                literalTable.add(new Obj(parts[0], Integer.parseInt(parts[1])));
            }
            brLit.close();

            // Open Pass1 output file
            BufferedReader br = new BufferedReader(new FileReader("Output.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("MachineCode.txt"));

            System.out.println("LOCATION COUNTER\tMACHINE CODE");
            System.out.println("----------------\t------------");

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] tokens = line.trim().split("\\s+");

                String locCounter = tokens[0]; // first token is location counter
                StringBuilder machineCode = new StringBuilder();
                boolean skipLine = false;
                boolean isDS = false;

                for (int i = 1; i < tokens.length; i++) {
                    String token = tokens[i];

                    if (token.matches("\\(AD,\\d+\\)")) {
                        skipLine = true; // assembly directive, skip output
                        break;
                    } else if (token.equals("(DL,01)")) {
                        isDS = true; // DS declaration, no constant added
                    } else if (token.equals("(DL,02)")) {
                        machineCode.append("00 00 "); // DC placeholder
                    } else if (token.matches("\\(IS,\\d+\\)")) {
                        int code = Integer.parseInt(token.replaceAll("\\D", ""));
                        machineCode.append(String.format("%02d", code)).append(" ");
                    } else if (token.matches("\\(RG,\\d+\\)")) {
                        int reg = Integer.parseInt(token.replaceAll("\\D", ""));
                        machineCode.append(String.format("%01d", reg)).append(" ");
                    } else if (token.matches("\\(S,\\d+\\)")) {
                        int idx = Integer.parseInt(token.replaceAll("\\D", "")) - 1;
                        if (symbTable.get(idx).addr == 0) {
                            machineCode.append("--- ");
                        } else {
                            machineCode.append(String.format("%03d", symbTable.get(idx).addr)).append(" ");
                        }
                    } else if (token.matches("\\(L,\\d+\\)")) {
                        int idx = Integer.parseInt(token.replaceAll("\\D", "")) - 1;
                        machineCode.append(String.format("%03d", literalTable.get(idx).addr)).append(" ");
                    } else if (token.matches("\\(C,\\d+\\)")) {
                        if (!isDS) machineCode.append(token.replaceAll("\\D", "")).append(" ");
                    }
                }

                if (!skipLine) {
                    System.out.printf("%-17s\t%s\n", locCounter, machineCode.toString().trim());
                    bw.write(locCounter + "\t" + machineCode.toString().trim() + "\n");
                }
            }

            br.close();
            bw.close();

            System.out.println("\n✅ Pass 2 completed successfully. Check MachineCode.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
