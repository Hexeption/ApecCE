package org.apecce.apecce.events;

import com.mojang.blaze3d.vertex.PoseStack;

public record Render2D(int width, int height, PoseStack poseStack, float delta) {
}