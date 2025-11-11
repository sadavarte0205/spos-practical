import java.io.*;

class MDT {
    String stmnt = "";
}

class MNT {
    String name;
    int addr;
    int argStart;
    int argCount;

    MNT(String name, int addr) {
        this.name = name;
        this.addr = addr;
        this.argStart = 0;
        this.argCount = 0;
    }
}

class ArgList {
    String argname;
    String value;

    ArgList(String argname) {
        this.argname = argname;
        this.value = "";
    }
}

public class MPass1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("Input.txt"));
        String line;

        MDT[] MDT = new MDT[50];
        MNT[] MNT = new MNT[20];
        ArgList[] ARGLIST = new ArgList[50];

        boolean macroStart = false, fillArgList = false;
        int mdtCount = 0, mntCount = 0, argCount = 0;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            line = line.replaceAll(",", " ");
            String[] tokens = line.trim().split("\\s+");

            if (tokens[0].equalsIgnoreCase("MACRO")) {
                macroStart = true;
                continue;
            }

            if (tokens[0].equalsIgnoreCase("MEND")) {
                MDT[mdtCount] = new MDT();
                MDT[mdtCount++].stmnt = "\tMEND";
                macroStart = false;
                fillArgList = false;
                continue;
            }

            if (macroStart) {
                MNT[mntCount] = new MNT(tokens[0], mdtCount);
                MNT[mntCount].argStart = argCount;

                for (int i = 1; i < tokens.length; i++) {
                    String arg = tokens[i];
                    if (arg.matches("&[a-zA-Z][a-zA-Z0-9]*")) {
                        ARGLIST[argCount++] = new ArgList(arg);
                    }
                }
                MNT[mntCount].argCount = argCount - MNT[mntCount].argStart;

                MDT[mdtCount] = new MDT();
                MDT[mdtCount].stmnt = "\t" + tokens[0];
                for (int i = 1; i < tokens.length; i++) {
                    MDT[mdtCount].stmnt += "\t" + tokens[i];
                }
                mdtCount++;
                mntCount++;
                fillArgList = true;
                continue;
            }

            if (!macroStart && fillArgList) {
                MDT[mdtCount] = new MDT();
                MDT[mdtCount].stmnt = "\t" + String.join("\t", tokens);

                for (int i = 0; i < argCount; i++) {
                    MDT[mdtCount].stmnt = MDT[mdtCount].stmnt.replace(ARGLIST[i].argname, "#" + i);
                }
                mdtCount++;
            }
        }
        br.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter("MNT.txt"));
        System.out.println("\n\t<<<<<<<< MACRO NAME TABLE >>>>>>>>");
        System.out.println("\n\tINDEX\tNAME\tMDT_INDEX\tARG_COUNT");
        for (int i = 0; i < mntCount; i++) {
            System.out.println("\t" + i + "\t" + MNT[i].name + "\t" + MNT[i].addr + "\t" + MNT[i].argCount);
            bw.write(i + "\t" + MNT[i].name + "\t" + MNT[i].addr + "\t" + MNT[i].argCount + "\n");
        }
        bw.close();

        bw = new BufferedWriter(new FileWriter("arglist.txt"));
        System.out.println("\n\t-------- ARGUMENT LIST --------");
        System.out.println("\n\tINDEX\tNAME");
        for (int i = 0; i < argCount; i++) {
            System.out.println("\t" + i + "\t" + ARGLIST[i].argname);
            bw.write(ARGLIST[i].argname + "\n");
        }
        bw.close();

        bw = new BufferedWriter(new FileWriter("MDT.txt"));
        System.out.println("\n\t++++++++ MACRO DEFINITION TABLE ++++++++");
        System.out.println("\n\tINDEX\tSTATEMENT");
        for (int i = 0; i < mdtCount; i++) {
            String formattedStmt = MDT[i].stmnt.replaceAll("\\s*=\\s*", "=");
            System.out.println("\t" + i + "\t" + formattedStmt);
            bw.write(formattedStmt + "\n");
        }
        bw.close();
    }
}
