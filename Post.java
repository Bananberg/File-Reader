package dk.arbetsprov;

import java.util.HashMap;
// This superclass is mainly here to define the methods. At first I had a vague idea that it would
// enable iteration through a generic collection of Posts, however any such functionality was never
// implemented.

public abstract class Post{
    String postType;

    protected String rawData; //represents the line that is the post in the .txt file
    protected HashMap<String, DataBlock> datablocks;

    public void setDatablocks(HashMap<String, DataBlock> datablocks) {
        this.datablocks = datablocks;
    }

    public Post(String data){
        this.rawData = data;
        datablocks = new HashMap<String, DataBlock>();
    }


    protected abstract void RetrieveRequiredData();


}
//Used to tell every post where to look for each block of data in the rawData.
class DataBlock {
    int start;
    int end;

    public DataBlock(int start, int end){
        this.start = start;
        this.end = end;
    }
}
