package rendering.engines;

import java.util.concurrent.CountDownLatch;

import color.Color;
import geometry.Ray;
import geometry.Vector3;
import rendering.Camera;
import rendering.RayHit;
import rendering.Renderable;
import rendering.targets.Image;
import rendering.targets.RenderTarget;
import utilities.math.MathUtils;
import utilities.queue.QueueSL;
import utilities.queue.implementations.SimpleQueue;

/**
 * The primary renderer used in JavaTrace.
 * 
 * <p>Implements a ray tracing algorithm and casts multiple rays out for each pixel 
 * to approximate the real world processes that light follows in order to produce a realistically
 * lit image.
 */
public class RayTracer implements Renderer{
    private static final int DEFAULT_THREADS = 16;
    private static final int DEFAULT_MAXBOUNCES = 3;
    private static final int DEFAULT_RAYSPERPIXEL = 10;

    private final int numOfThreads;
    private final int maxBounces;
    private final int raysPerPixel;

    public RayTracer(){
        this.numOfThreads = DEFAULT_THREADS;
        this.maxBounces = DEFAULT_MAXBOUNCES;
        this.raysPerPixel = DEFAULT_RAYSPERPIXEL;
    }
    public RayTracer(int maxBounces, int raysPerPixel){
        this.numOfThreads = DEFAULT_THREADS;
        this.maxBounces = maxBounces;
        this.raysPerPixel = raysPerPixel;
    }
    public RayTracer(int numberOfThreads, int maxBounces, int raysPerPixel){
        this.numOfThreads = numberOfThreads;
        this.maxBounces = maxBounces;
        this.raysPerPixel = raysPerPixel;
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
            new RenderThread(raysPerPixel, maxBounces, queues[i], latch, through, target, subject).start();
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

    /**
     * A thread which renders a queue of ImageChunks and decrements a count down latch upon processing the entire queue
     */
    private class RenderThread extends Thread{
        public RenderThread(int raysPerPixel, int maxBounces, QueueSL<ImageChunk> renderQueue, CountDownLatch latch, 
            Camera source, RenderTarget output, Renderable subject){
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
                            Vector3 finalColor = Vector3.zero();
                            for (int i = 0; i < raysPerPixel; i++){
                                Ray r = source.getRay(x / (double)output.getXSize(), y / (double)output.getYSize());
                                RayHit hitInfo = subject.testRay(r);
                                if (!hitInfo.hit){
                                    //output.writePixel(x, y, new Color(0, 0, 0));
                                    break;
                                }

                                Vector3 color = hitInfo.emissionColor.multiply(hitInfo.emissionStrength);
                                Vector3 throughput = hitInfo.hitColor;
                                for (int b = 0; b < maxBounces; b++){
                                    r = new Ray(hitInfo.hitPoint.plus(hitInfo.bounceDirection.multiply(0.000001)), hitInfo.bounceDirection);
                                    hitInfo = subject.testRay(r);

                                    if (!hitInfo.hit)
                                        break;
                                    
                                    // Lighting logic learned from
                                    // https://blog.demofox.org/2020/05/25/casual-shadertoy-path-tracing-1-basic-camera-diffuse-emissive/
                                    color = 
                                        color.plus(hitInfo.emissionColor.multiply(hitInfo.emissionStrength).componentMultiplication(throughput));
                                    throughput = throughput.componentMultiplication(hitInfo.hitColor);
                                }
                                finalColor = finalColor.plus(color);
                            }

                            output.writePixel(x, y, new Color(finalColor.divide(raysPerPixel)));
                        }
                    }
                }

                latch.countDown();
            });
        }
    }

    /**
     * Represents a rectangular region of an image 
     */
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
