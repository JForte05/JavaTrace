package rendering.engines;

import rendering.Camera;
import rendering.Renderable;
import rendering.targets.RenderTarget;

public interface Renderer {
    public void render(RenderTarget target, Renderable subject, Camera through);
}
