package com.team30.syntaxrunner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class SpikeObstacleView extends View {

    private final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path path = new Path();
    private float pulseAlpha = 0.3f;
    private final android.animation.ValueAnimator pulseAnim;

    public SpikeObstacleView(Context context) {
        super(context);

        fillPaint.setColor(Color.parseColor("#FF7B72"));
        fillPaint.setStyle(Paint.Style.FILL);

        glowPaint.setColor(Color.parseColor("#FF7B72"));
        glowPaint.setStyle(Paint.Style.FILL);

        pulseAnim = android.animation.ValueAnimator.ofFloat(0.2f, 0.6f, 0.2f);
        pulseAnim.setDuration(600);
        pulseAnim.setRepeatCount(android.animation.ValueAnimator.INFINITE);
        pulseAnim.addUpdateListener(a -> {
            pulseAlpha = (float) a.getAnimatedValue();
            invalidate();
        });
        pulseAnim.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = getWidth(), h = getHeight();

        // Triangle pointing upward
        path.reset();
        path.moveTo(w / 2f, 2f);       // top center (tip)
        path.lineTo(w - 2f, h - 2f);   // bottom right
        path.lineTo(2f, h - 2f);        // bottom left
        path.close();

        // Glow shadow (slightly larger, semi-transparent)
        glowPaint.setAlpha((int)(pulseAlpha * 255));
        canvas.save();
        canvas.scale(1.2f, 1.2f, w / 2f, h / 2f);
        canvas.drawPath(path, glowPaint);
        canvas.restore();

        canvas.drawPath(path, fillPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        pulseAnim.cancel();
    }
}
