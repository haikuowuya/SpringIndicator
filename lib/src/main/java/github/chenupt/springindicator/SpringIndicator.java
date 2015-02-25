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
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenupt@gmail.com on 2015/1/31.
 * Description TODO
 */
public class SpringIndicator extends FrameLayout {

    private LinearLayout container;
    private SpringView springView;

    private List<View> tabs;

    public SpringIndicator(Context context) {
        this(context, null);
    }

    public SpringIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        tabs = new ArrayList<>();

        addPoints();
        container = new LinearLayout(getContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setGravity(Gravity.CENTER);
        addView(container);
        addItems();
    }

    private void addPoints() {
        springView = new SpringView(getContext());
        addView(springView);
    }

    private void addItems() {
        for (int i = 0; i < 4; i++) {
            TextView textView = new TextView(getContext());
            textView.setText(String.valueOf(i));
            textView.setTextSize(18);
            textView.setPadding(100, 0, 100, 0);
            tabs.add(textView);
            container.addView(textView);
        }
    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private void setHead() {
        View view = tabs.get(0);
        springView.getHeadPoint().setX(view.getX() + view.getWidth() / 2);
        springView.getHeadPoint().setY(view.getY() + view.getHeight() / 2);
    }

    private void setFoot() {
        View view = tabs.get(0);
        springView.getFootPoint().setX(view.getX() + view.getWidth() / 2);
        springView.getFootPoint().setY(view.getY() + view.getHeight() / 2);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        setHead();
        setFoot();
    }


    public void setViewPager(final ViewPager viewPager) {

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < tabs.size() - 1) {

                    float accelerateTemp = 0.5f;
                    float headOffset = 0.6f;
                    float footOffset = 1-headOffset;
                    float radio = 30;

                    // radio
                    float radioOffsetHead = 0.5f;
                    if(positionOffset < radioOffsetHead){
                        springView.getHeadPoint().setRadius(radio);
                    }else{
                        springView.getHeadPoint().setRadius(((positionOffset-radioOffsetHead)/(1-radioOffsetHead) * radio + radio));
                    }
                    float radioOffsetFoot = 0.5f;
                    if(positionOffset < radioOffsetFoot){
                        springView.getFootPoint().setRadius((1-positionOffset/radioOffsetFoot) * radio + radio);
                    }else{
                        springView.getFootPoint().setRadius(radio);
                    }

                    // x
                    float headX = 1f;
                    if (positionOffset < headOffset){
                        float positionOffsetTemp = positionOffset / headOffset;
                        headX = (float) ((Math.atan(positionOffsetTemp*accelerateTemp*2 - accelerateTemp ) + (Math.atan(accelerateTemp))) / (2 * (Math.atan(accelerateTemp))));
                    }
                    springView.getHeadPoint().setX(getTabX(position) - headX * getDistance(position));
                    float footX = 0f;
                    if (positionOffset > footOffset){
                        float positionOffsetTemp = (positionOffset-footOffset) / (1-footOffset);
                        footX = (float) ((Math.atan(positionOffsetTemp*accelerateTemp*2 - accelerateTemp ) + (Math.atan(accelerateTemp))) / (2 * (Math.atan(accelerateTemp))));
                    }
                    springView.getFootPoint().setX(getTabX(position) - footX * getDistance(position));



                    if(positionOffset == 0){
                        springView.getHeadPoint().setRadius(radio * 2);
                        springView.getFootPoint().setRadius(radio * 2);
                    }

                    springView.postInvalidate();

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private float getDistance(int position) {
        float tarX = tabs.get(position + 1).getX();
        float oriX = tabs.get(position).getX();
        return oriX - tarX;
    }

    private float getTabX(int position) {
        return tabs.get(position).getX() + tabs.get(position).getWidth() / 2;
    }
}
