package utilities.fileIO;

import utilities.exceptions.ArrayTooSmallException;

/**
 * Utilities for writing data to files.
 */
public class FileWrite {
    /**
     * Writes the component bytes of an int to a byte buffer in big endian.
     * @param n The int to get the bytes of
     * @param buffer The buffer to write the bytes to
     * @throws ArrayTooSmallException If the buffer has a length less than 4
     */
    public static void intToBytesBigEndian(int n, byte[] buffer) throws ArrayTooSmallException{
        if (buffer.length < 4)
            throw new ArrayTooSmallException(4, buffer.length);

        buffer[0] = (byte)(n >> 24);
        buffer[1] = (byte)((n << 8) >> 24);
        buffer[2] = (byte)((n << 16) >> 24);
        buffer[3] = (byte)((n << 24) >> 24);
    }
    /**
     * Writes the component bytes of a long to a byte buffer in big endian.
     * @param n The long to get the bytes of
     * @param buffer The buffer to write the bytes to
     * @throws ArrayTooSmallException If the buffer has a length less than 8
     */
    public static void longToBytesBigEndian(long n, byte[] buffer) throws ArrayTooSmallException{
        if (buffer.length < 8)
            throw new ArrayTooSmallException(8, buffer.length);
        
        buffer[0] = (byte)(n >> 56);
        buffer[1] = (byte)((n << 8) >> 56);
        buffer[2] = (byte)((n << 16) >> 56);
        buffer[3] = (byte)((n << 24) >> 56);
        buffer[4] = (byte)((n << 32) >> 56);
        buffer[5] = (byte)((n << 40) >> 56);
        buffer[6] = (byte)((n << 48) >> 56);
        buffer[7] = (byte)((n << 56) >> 56);
    }
    /**
     * Writes the component bytes of an double to a byte buffer in big endian.
     * @param n The double to get the bytes of
     * @param buffer The buffer to write the bytes to
     * @throws ArrayTooSmallException If the buffer has a length less than 8
     */
    public static void doubleToBytesBigEndian(double n, byte[] buffer) throws ArrayTooSmallException{
        longToBytesBigEndian(Double.doubleToLongBits(n), buffer);
    }
    /**
     * Writes the component bytes of an float to a byte buffer in big endian.
     * @param n The float to get the bytes of
     * @param buffer The buffer to write the bytes to
     * @throws ArrayTooSmallException If the buffer has a length less than 4
     */
    public static void floatToBytesBigEndian(float n, byte[] buffer) throws ArrayTooSmallException{
        intToBytesBigEndian(Float.floatToIntBits(n), buffer);
    }

    /**
     * Writes the component bytes of an int to a byte buffer in big endian.
     * @param n The int to get the bytes of
     * @param buffer The buffer to write the bytes to
     * @param offset The offset to start insertion into the buffer
     * @throws ArrayTooSmallException If the buffer has a length less than 4
     */
    public static void intToBytesBigEndian(int n, byte[] buffer, int offset) throws ArrayTooSmallException{
        if (buffer.length < 4 + offset)
            throw new ArrayTooSmallException(4, buffer.length);

        buffer[offset] = (byte)(n >> 24);
        buffer[offset + 1] = (byte)((n << 8) >> 24);
        buffer[offset + 2] = (byte)((n << 16) >> 24);
        buffer[offset + 3] = (byte)((n << 24) >> 24);
    }
    /**
     * Writes the component bytes of a long to a byte buffer in big endian.
     * @param n The long to get the bytes of
     * @param buffer The buffer to write the bytes to
     * @param offset The offset to start insertion into the buffer
     * @throws ArrayTooSmallException If the buffer has a length less than 8
     */
    public static void longToBytesBigEndian(long n, byte[] buffer, int offset) throws ArrayTooSmallException{
        if (buffer.length < 8 + offset)
            throw new ArrayTooSmallException(8, buffer.length);
        
        buffer[offset] = (byte)(n >> 56);
        buffer[offset + 1] = (byte)((n << 8) >> 56);
        buffer[offset + 2] = (byte)((n << 16) >> 56);
        buffer[offset + 3] = (byte)((n << 24) >> 56);
        buffer[offset + 4] = (byte)((n << 32) >> 56);
        buffer[offset + 5] = (byte)((n << 40) >> 56);
        buffer[offset + 6] = (byte)((n << 48) >> 56);
        buffer[offset + 7] = (byte)((n << 56) >> 56);
    }
    /**
     * Writes the component bytes of an double to a byte buffer in big endian.
     * @param n The double to get the bytes of
     * @param buffer The buffer to write the bytes to
     * @param offset The offset to start insertion into the buffer
     * @throws ArrayTooSmallException If the buffer has a length less than 8
     */
    public static void doubleToBytesBigEndian(double n, byte[] buffer, int offset) throws ArrayTooSmallException{
        longToBytesBigEndian(Double.doubleToLongBits(n), buffer, offset);
    }
    /**
     * Writes the component bytes of an float to a byte buffer in big endian.
     * @param n The float to get the bytes of
     * @param buffer The buffer to write the bytes to
     * @param offset The offset to start insertion into the buffer
     * @throws ArrayTooSmallException If the buffer has a length less than 4
     */
    public static void floatToBytesBigEndian(float n, byte[] buffer, int offset) throws ArrayTooSmallException{
        intToBytesBigEndian(Float.floatToIntBits(n), buffer, offset);
    }

    public static void intToBytesLittleEndian(int n, byte[] buffer) throws ArrayTooSmallException{
        if (buffer.length < 4)
            throw new ArrayTooSmallException(4, buffer.length);

        buffer[3] = (byte)(n >> 24);
        buffer[2] = (byte)((n << 8) >> 24);
        buffer[1] = (byte)((n << 16) >> 24);
        buffer[0] = (byte)((n << 24) >> 24);
    }
    public static void longToBytesLittleEndian(long n, byte[] buffer) throws ArrayTooSmallException{
        if (buffer.length < 8)
            throw new ArrayTooSmallException(8, buffer.length);
        
        buffer[7] = (byte)(n >> 56);
        buffer[6] = (byte)((n << 8) >> 56);
        buffer[5] = (byte)((n << 16) >> 56);
        buffer[4] = (byte)((n << 24) >> 56);
        buffer[3] = (byte)((n << 32) >> 56);
        buffer[2] = (byte)((n << 40) >> 56);
        buffer[1] = (byte)((n << 48) >> 56);
        buffer[0] = (byte)((n << 56) >> 56);
    }
    public static void doubleToBytesLittleEndian(double n, byte[] buffer) throws ArrayTooSmallException{
        longToBytesLittleEndian(Double.doubleToLongBits(n), buffer);
    }
    public static void floatToBytesLittleEndian(float n, byte[] buffer) throws ArrayTooSmallException{
        intToBytesLittleEndian(Float.floatToIntBits(n), buffer);
    }

    public static void intToBytesLittleEndian(int n, byte[] buffer, int offset) throws ArrayTooSmallException{
        if (buffer.length < 4 + offset)
            throw new ArrayTooSmallException(4, buffer.length);

        buffer[offset + 3] = (byte)(n >> 24);
        buffer[offset + 2] = (byte)((n << 8) >> 24);
        buffer[offset + 1] = (byte)((n << 16) >> 24);
        buffer[offset] = (byte)((n << 24) >> 24);
    }
    public static void longToBytesLittleEndian(long n, byte[] buffer, int offset) throws ArrayTooSmallException{
        if (buffer.length < 8 + offset)
            throw new ArrayTooSmallException(8, buffer.length);
        
        buffer[offset + 7] = (byte)(n >> 56);
        buffer[offset + 6] = (byte)((n << 8) >> 56);
        buffer[offset + 5] = (byte)((n << 16) >> 56);
        buffer[offset + 4] = (byte)((n << 24) >> 56);
        buffer[offset + 3] = (byte)((n << 32) >> 56);
        buffer[offset + 2] = (byte)((n << 40) >> 56);
        buffer[offset + 1] = (byte)((n << 48) >> 56);
        buffer[offset] = (byte)((n << 56) >> 56);
    }
    public static void doubleToBytesLittleEndian(double n, byte[] buffer, int offset) throws ArrayTooSmallException{
        longToBytesLittleEndian(Double.doubleToLongBits(n), buffer, offset);
    }
    public static void floatToBytesLittleEndian(float n, byte[] buffer, int offset) throws ArrayTooSmallException{
        intToBytesLittleEndian(Float.floatToIntBits(n), buffer, offset);
    }

    /**
     * Gets a piece or "chunk" of an array and writes it to a buffer.
     * <p>If a chunk extends past the end of the array, the remaining space in the chunk will be filled with {@code null}.
     * @param <T> The array type
     * @param array The array to get a chunk of
     * @param buffer The array to write the chunk to
     * @param chunkSize The number of elements in the chunk
     * @param chunkIndex The index of the chunk to get
     * @throws ArrayTooSmallException If the size of the buffer is less than the chunk size.
     * @throws IndexOutOfBoundsException If the desired chunk index is greater than the number of chunks the array can be broken into.
     */
    public static <T> void chunkArray(T[] array, T[] buffer, int chunkSize, int chunkIndex) 
        throws ArrayTooSmallException, IndexOutOfBoundsException{
        if (buffer.length < chunkSize){
            throw new ArrayTooSmallException(chunkSize, buffer.length);
        }
        
        int start = chunkIndex * chunkSize;
        if (start > array.length){
            throw new IndexOutOfBoundsException(
                String.format("Array of size %d does not have %d chunks of size %d", array.length, chunkIndex + 1, chunkSize));
        }

        int index = 0;
        for (int i = 0; i < chunkSize; i++){
            index = start + i;
            buffer[i] = index < array.length ? array[index] : null;
        }
    }
    /**
     * Gets a piece or "chunk" of an array and writes it to a buffer.
     * <p>If a chunk extends past the end of the array, the remaining space in the chunk will be filled with {@code null}.
     * @param <T> The array type
     * @param array The array to get a chunk of
     * @param buffer The array to write the chunk to
     * @param chunkSize The number of elements in the chunk
     * @param chunkIndex The index of the chunk to get
     * @param bufferOffset The offset to start insertion into the buffer
     * @throws ArrayTooSmallException If the size of the buffer is less than the chunk size.
     * @throws IndexOutOfBoundsException If the desired chunk index is greater than the number of chunks the array can be broken into.
     */
    public static <T> void chunkArray(T[] array, T[] buffer, int chunkSize, int chunkIndex, int bufferOffset) 
        throws ArrayTooSmallException, IndexOutOfBoundsException{
            if (buffer.length < chunkSize + bufferOffset){
                throw new ArrayTooSmallException(chunkSize, buffer.length);
            }
            
            int start = chunkIndex * chunkSize;
            if (start > array.length){
                throw new IndexOutOfBoundsException(
                    String.format("Array of size %d does not have %d chunks of size %d", array.length, chunkIndex + 1, chunkSize));
            }
    
            int index = 0;
            for (int i = 0; i < chunkSize; i++){
                index = start + i;
                buffer[bufferOffset + i] = index < array.length ? array[index] : null;
            }
        
    }
    /**
     * Calculates the number of chunks of given size are possible for the given array size.
     * @param arraySize The size of the array
     * @param chunkSize The size of the chunks
     * @return The number of chunks possible in the array
     */
    public static int numberOfChunks(int arraySize, int chunkSize){
        return arraySize / chunkSize + (arraySize % chunkSize == 0 ? 0 : 1);
    }

}
