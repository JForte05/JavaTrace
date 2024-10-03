package utilities.json;

public class JSONEntry{
    private final String data;

    protected JSONEntry(String data){
        this.data = data;
    }
    
    protected String data(){
        return data;
    }

    @Override
    public String toString(){
        return data();
    }
}
