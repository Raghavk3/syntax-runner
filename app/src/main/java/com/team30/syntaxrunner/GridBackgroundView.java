package com.team30.syntaxrunner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class GridBackgroundView extends View {

    private final Paint gridPaint = new Paint();
    private final Paint horizonPaint = new Paint();
    private final Paint groundFadePaint = new Paint();

    private int scrollOffset = 0;
    private int gridSpacingPx;
    private String horizonColor = "#6C63FF";

    public GridBackgroundView(Context context) {
        super(context);
        init();
    }

    public GridBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        float density = getResources().getDisplayMetrics().density;
        gridSpacingPx = (int)(40 * density);

        gridPaint.setColor(Color.WHITE);
        gridPaint.setAlpha(20);
        gridPaint.setStrokeWidth(1f);
        gridPaint.setAntiAlias(false);

        horizonPaint.setAntiAlias(true);
    }

    public void setScrollOffset(int offset) {
        this.scrollOffset = offset;
        invalidate();
    }

    public void setHorizonColor(String color) {
        this.horizonColor = color;
        invalidate();
    }

    /** Returns the Y coordinate of the ground line — same value used in onDraw(). */
    public float getGroundY() {
        return getHeight() * 0.70f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();
        if (w == 0 || h == 0) return;

        // Ground at 70% from top
        float groundY = h * 0.70f;
        // Vanishing point
        float vpX = w * 0.5f;
        float vpY = h * 0.08f;

        // --- Horizon glow ---
        try {
            int hColor = Color.parseColor(horizonColor);
            int r = Color.red(hColor);
            int g = Color.green(hColor);
            int b = Color.blue(hColor);
            horizonPaint.setShader(new LinearGradient(
                    0, groundY * 0.4f, 0, groundY,
                    Color.argb(0, r, g, b),
                    Color.argb(40, r, g, b),
                    Shader.TileMode.CLAMP
            ));
            canvas.drawRect(0, groundY * 0.4f, w, groundY, horizonPaint);
        } catch (Exception ignored) {}

        // --- Above-ground perspective lines (converging to vanishing point) ---
        int numRadials = 10;
        for (int i = 0; i <= numRadials; i++) {
            float bx = w * (float) i / numRadials;
            canvas.drawLine(vpX, vpY, bx, groundY, gridPaint);
        }

        // Perspective horizontal lines (non-linear spacing — quadratic)
        int numHLines = 14;
        float scrollFrac = (scrollOffset % gridSpacingPx) / (float) gridSpacingPx;
        for (int i = 0; i < numHLines; i++) {
            float t = ((float)(i + 1) / numHLines + scrollFrac / numHLines) % 1.0f;
            // Quadratic: lines bunch near vanishing point
            float y = vpY + (groundY - vpY) * (t * t);
            canvas.drawLine(0, y, w, y, gridPaint);
        }

        // --- Below-ground flat scrolling grid ---
        int scrollY = scrollOffset % gridSpacingPx;
        int scrollX = (scrollOffset / 2) % gridSpacingPx;

        // Horizontal lines
        for (float y = groundY + scrollY % gridSpacingPx; y <= h + gridSpacingPx; y += gridSpacingPx) {
            canvas.drawLine(0, y, w, y, gridPaint);
        }

        // Vertical lines
        for (float x = -scrollX % gridSpacingPx; x <= w + gridSpacingPx; x += gridSpacingPx) {
            canvas.drawLine(x, groundY, x, h, gridPaint);
        }
    }
}
