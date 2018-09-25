package dk.arbetsprov;

import java.util.HashMap;

/**
 * This class only holds the definition for how to read a file. It should do nothing else.
 */

public class fileDefinition {
    private HashMap<String, DataBlock> instructions_GenOP;
    private HashMap<String, DataBlock> instructions_GenPP;
    private HashMap<String, DataBlock> instructions_GenEP;

    private String name;
    private String filePath;

    private int nrOfCharsForPostType;

    public void setFilePath(String filePath) {
        this.filePath = filePath;


    }

    public String getFilePath() {
        return filePath;
    }
    public int getPostType(){
        return nrOfCharsForPostType;
    }

    public fileDefinition(String name, int nrOfCharsForPostType) {
        this.name = name;

        this.nrOfCharsForPostType = nrOfCharsForPostType;

        instructions_GenEP = null;
        instructions_GenPP = null;
        instructions_GenOP = null;
    }

    public HashMap<String, DataBlock> getInstructions(String type) {
        if (type == "Gen_OP") {
            return instructions_GenOP;
        }
        if (type == "Gen_PP") {
            return instructions_GenPP;
        }
        if (type == "Gen_EP") {
            return instructions_GenEP;
        } else {
            System.out.println("Specified type of instructions doesn't exist.");
            return null;
        }
    }

    public void setInstructions_GenEP(HashMap<String, DataBlock> instructions_GenEP) {
        this.instructions_GenEP = instructions_GenEP;
    }

    public void setInstructions_GenOP(HashMap<String, DataBlock> instructions_GenOP) {
        this.instructions_GenOP = instructions_GenOP;
    }

    public void setInstructions_GenPP(HashMap<String, DataBlock> instructions_GenPP) {
        this.instructions_GenPP = instructions_GenPP;
    }
}

