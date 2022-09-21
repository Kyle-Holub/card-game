package com.holub.kyle.game.graphics.handlers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WindowOptions {
    private boolean cullFace;
    private boolean showTriangles;
    private boolean showFps;
    private boolean compatibleProfile;
    private boolean antialiasing;
    private boolean frustumCulling;
}

