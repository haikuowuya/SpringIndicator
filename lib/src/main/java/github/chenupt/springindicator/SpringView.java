/*
 * Copyright 2015 chenupt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package github.chenupt.springindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chenupt@gmail.com on 2015/1/31.
 * Description TODO
 */
public class SpringView extends View {

    public static final float DEFAULT_RADIUS = 200;
    private Paint paint;
    private Path path;
    private float radius = DEFAULT_RADIUS;

    private Point headPoint;
    private Point footPoint;

    public SpringView(Context context) {
        this(context, null);
    }

    public SpringView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpringView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        headPoint = new Point();
        footPoint = new Point();

        path = new Path();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.RED);
    }

    private void makePath(){
        path.reset();
//        path.moveTo(headPoint.getX(), headPoint.getY());
//        path.quadTo(anchorX, anchorY, x2, y2);
//        path.lineTo(footPoint.getX(), footPoint.getY());
//        path.quadTo(anchorX, anchorY, x4, y4);
//        path.lineTo(x1, y1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        makePath();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
//        canvas.drawPath(path, paint);
        canvas.drawCircle(headPoint.getX(), headPoint.getY(), headPoint.getRadius(), paint);
        canvas.drawCircle(footPoint.getX(), footPoint.getY(), footPoint.getRadius(), paint);
        super.onDraw(canvas);
    }

    public Point getHeadPoint() {
        return headPoint;
    }

    public Point getFootPoint() {
        return footPoint;
    }
}
