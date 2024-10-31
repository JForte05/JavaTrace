package rendering.engines;

import java.util.concurrent.CountDownLatch;

import color.Color;
import geometry.Ray;
import rendering.Camera;
import rendering.RayHit;
import rendering.Renderable;
import rendering.targets.Image;
import rendering.targets.RenderTarget;
import utilities.math.MathUtils;
import utilities.queue.QueueSL;
import utilities.queue.implementations.SimpleQueue;

public class RayTracer implements Renderer{
    private static final int DEFAULT_THREADS = 16;

    private final int numOfThreads;

    public RayTracer(){
        this.numOfThreads = DEFAULT_THREADS;
    }
    public RayTracer(int numberOfThreads){
        this.numOfThreads = numberOfThreads;
    }

    @Override
    public Image render(Renderable subject, Camera through, int verticalResolution) {
        CountDownLatch latch = new CountDownLatch(numOfThreads);
        Image target = through.renderTexture(verticalResolution);

        @SuppressWarnings("unchecked")
        SimpleQueue<ImageChunk>[] queues = (SimpleQueue<ImageChunk>[])new SimpleQueue[numOfThreads];

        int chunkDimension = MathUtils.greatestCommonDenominator(target.getXSize(), target.getYSize());
        int xChunks = target.getXSize() / chunkDimension;
        int yChunks = target.getYSize() / chunkDimension;
        int numChunks = xChunks * yChunks;

        int startY;
        int startX;
        int chunkYIndex;
        int chunkXIndex;

        // set up chunk queues for render threads to go through
        for (int i = 0; i < numChunks; i++){
            chunkYIndex = i / xChunks;
            chunkXIndex = i % xChunks;

            startY = chunkYIndex * chunkDimension;
            startX = chunkXIndex * chunkDimension;

            int queueIndex = i % numOfThreads;
            if (queues[queueIndex] == null)
                queues[queueIndex] = new SimpleQueue<>();
            queues[queueIndex].enqueue(new ImageChunk(startX, startX + chunkDimension, startY, startY + chunkDimension));
        }

        // start render threads
        for (int i = 0; i < numOfThreads; i++){
            new RenderThread(queues[i], latch, through, target, subject).start();
        }

        try{
            // wait until all render threads have completed their queues
            latch.await();
        } catch (Exception e){
            e.printStackTrace();
        }

        // return the image
        return target;
    }

    private class RenderThread extends Thread{
        public RenderThread(QueueSL<ImageChunk> renderQueue, CountDownLatch latch, Camera source, RenderTarget output, Renderable subject){
            super(() -> {
                if (renderQueue == null)
                    return;

                while(!renderQueue.isEmpty()){
                    ImageChunk current = renderQueue.dequeue();
                    int yMin = current.yMin;
                    int yMax = current.yMax;
                    int xMin = current.xMin;
                    int xMax = current.xMax;
                    
                    for (int y = yMin; y < yMax; y++){
                        for (int x = xMin; x < xMax; x++){
                            Ray r = source.getRay(x / (double)output.getXSize(), y / (double)output.getYSize());

                            RayHit hit = subject.testRay(r);
                            output.writePixel(x, y, hit.hit ? hit.hitColor : new Color(0, 0, 0));
                        }
                    }
                }

                latch.countDown();
            });
        }
    }

    private class ImageChunk{
        public int xMin;
        public int xMax;
        public int yMin;
        public int yMax;
        
        public ImageChunk(int xMin, int xMax, int yMin, int yMax){
            this.xMin = xMin;
            this.xMax = xMax;
            this.yMin = yMin;
            this.yMax = yMax;
        }

        @Override
        public String toString(){
            return String.format("x: (%d - %d), y: (%d - %d)", xMin, xMax, yMin, yMax);
        }
    }
}
