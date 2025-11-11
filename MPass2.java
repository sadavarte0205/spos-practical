import java.io.*;

class MDT {
    String stmnt;
    public MDT() {
        this.stmnt = "";
    }
    public MDT(String stmnt) {
        this.stmnt = stmnt;
    }
}

class MNT {
    String name;
    int addr;
    int argCount;
    int argStart;
    public MNT(String name, int addr) {
        this.name = name;
        this.addr = addr;
    }
}

class ArgList {
    String argname;
    String value;
    public ArgList(String argname) {
        this.argname = argname;
        this.value = "";
    }
}

public class MPass2 {
    public static void main(String[] args) throws IOException {
        MDT[] MDT = new MDT[50];
        MNT[] MNT = new MNT[20];
        ArgList[] formal_parameter = new ArgList[50];
        int mdt_cnt = 0, mnt_cnt = 0, formal_arglist_cnt = 0;

        BufferedReader br = new BufferedReader(new FileReader("MNT.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\s+");
            if (parts.length < 4) continue;
            MNT[mnt_cnt] = new MNT(parts[1], Integer.parseInt(parts[2]));
            MNT[mnt_cnt].argCount = Integer.parseInt(parts[3]);
            MNT[mnt_cnt].argStart = formal_arglist_cnt;
            formal_arglist_cnt += MNT[mnt_cnt].argCount;
            mnt_cnt++;
        }
        br.close();

        br = new BufferedReader(new FileReader("arglist.txt"));
        int arg_cnt = 0;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\s+");
            formal_parameter[arg_cnt] = new ArgList(parts[0]);
            if (parts.length > 1) formal_parameter[arg_cnt].value = parts[1];
            arg_cnt++;
        }
        br.close();

        br = new BufferedReader(new FileReader("MDT.txt"));
        while ((line = br.readLine()) != null) {
            MDT[mdt_cnt] = new MDT();
            MDT[mdt_cnt].stmnt = line;
            mdt_cnt++;
        }
        br.close();

        System.out.println("\n<<<<<<<< MACRO NAME TABLE >>>>>>>>");
        System.out.println("INDEX\tNAME\tMDT_INDEX\tARG_COUNT");
        for (int i = 0; i < mnt_cnt; i++) {
            System.out.println(i + "\t" + MNT[i].name + "\t" + MNT[i].addr + "\t" + MNT[i].argCount);
        }

        System.out.println("\n-------- ARGUMENT LIST --------");
        System.out.println("INDEX\tNAME");
        for (int i = 0; i < formal_arglist_cnt; i++) {
            System.out.println(i + "\t" + formal_parameter[i].argname);
        }

        System.out.println("\n++++++++ MACRO DEFINITION TABLE ++++++++");
        System.out.println("INDEX\tSTATEMENT");
        for (int i = 0; i < mdt_cnt; i++) {
            System.out.println(i + "\t" + MDT[i].stmnt.replace(" = ", "=").replaceAll("\\s+", "\t"));
        }

        br = new BufferedReader(new FileReader("Input.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("Output.txt"));
        boolean insideMacroDef = false;

        System.out.println("\n::::::::: EXPANDED MACRO PROGRAM ::::::::");

        while ((line = br.readLine()) != null) {
            line = line.replace(",", " ").trim();
            if (line.isEmpty()) continue;
            String[] tokens = line.split("\\s+");

            if (tokens[0].equalsIgnoreCase("MACRO")) {
                insideMacroDef = true;
                continue;
            }
            if (tokens[0].equalsIgnoreCase("MEND")) {
                insideMacroDef = false;
                continue;
            }
            if (insideMacroDef) continue;

            int macro_call = -1;
            for (int i = 0; i < mnt_cnt; i++) {
                if (tokens[0].equalsIgnoreCase(MNT[i].name)) {
                    macro_call = i;
                    break;
                }
            }

            if (macro_call == -1) {
                bw.write(line + "\n");
                System.out.println(line);
            } else {
                String[] actualArgs = new String[MNT[macro_call].argCount];
                for (int i = 0; i < MNT[macro_call].argCount; i++) {
                    actualArgs[i] = tokens[i + 1];
                    if (actualArgs[i].contains("=")) {
                        actualArgs[i] = actualArgs[i].substring(actualArgs[i].indexOf('=') + 1);
                    }
                }
                int mdt_ptr = MNT[macro_call].addr + 1;
                while (!MDT[mdt_ptr].stmnt.trim().equalsIgnoreCase("MEND")) {
                    String stmt = MDT[mdt_ptr].stmnt;
                    for (int i = 0; i < MNT[macro_call].argCount; i++) {
                        stmt = stmt.replace("#" + (MNT[macro_call].argStart + i), actualArgs[i]);
                    }
                    bw.write(stmt + "\n");
                    System.out.println(stmt);
                    mdt_ptr++;
                }
            }
        }
        br.close();
        bw.close();

        System.out.println("\n________ EXPANSION COMPLETE ________");
        System.out.println("Macro Pass2 Successfully Done");
    }
}
