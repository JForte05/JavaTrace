package utilities.exceptions;

public class ArrayTooSmallException extends RuntimeException{
    public ArrayTooSmallException(int expected, int recieved){
        super(String.format("Expected array of size %d, recieved array of size %d", expected, recieved));
    }
}
