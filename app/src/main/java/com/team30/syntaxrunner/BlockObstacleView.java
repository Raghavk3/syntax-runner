package com.team30.syntaxrunner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class BlockObstacleView extends View {

    private final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF rect = new RectF();
    private float pulseAlpha = 0f;
    private final android.animation.ValueAnimator pulseAnim;

    public BlockObstacleView(Context context) {
        super(context);

        fillPaint.setColor(Color.parseColor("#161B22"));
        fillPaint.setStyle(Paint.Style.FILL);

        borderPaint.setColor(Color.parseColor("#FF7B72"));
        borderPaint.setAlpha(200);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(2f);

        glowPaint.setColor(Color.parseColor("#FF7B72"));
        glowPaint.setStyle(Paint.Style.FILL);

        pulseAnim = android.animation.ValueAnimator.ofFloat(0.05f, 0.25f, 0.05f);
        pulseAnim.setDuration(700);
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
        float r = 4f;
        rect.set(2, 2, w - 2, h - 2);

        canvas.drawRoundRect(rect, r, r, fillPaint);

        // Inner pulsing red overlay
        glowPaint.setAlpha((int)(pulseAlpha * 255));
        canvas.drawRoundRect(rect, r, r, glowPaint);

        canvas.drawRoundRect(rect, r, r, borderPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        pulseAnim.cancel();
    }
}
