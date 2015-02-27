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

    private float acceleration = 0.5f;
    private float headMoveOffset = 0.6f;
    private float footMoveOffset = 1- headMoveOffset;
    private float radiusMax = 65;
    private float radiusMin = 20;
    private float radiusOffset = radiusMax - radiusMin;

    private LinearLayout tabContainer;
    private SpringView springView;
    private ViewPager viewPager;

    private List<View> tabs;

    public SpringIndicator(Context context) {
        this(context, null);
    }

    public SpringIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    private void init() {
        addPointView();
        addTabContainerView();
        addTabItems();
    }

    private void addPointView() {
        springView = new SpringView(getContext());
        addView(springView);
    }

    private void addTabContainerView() {
        tabContainer = new LinearLayout(getContext());
        tabContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        tabContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabContainer.setGravity(Gravity.CENTER);
        addView(tabContainer);
    }

    private void addTabItems() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        tabs = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            TextView textView = new TextView(getContext());
            textView.setText(viewPager.getAdapter().getPageTitle(i));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(18);
            textView.setLayoutParams(layoutParams);
            final int position = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(position);
                }
            });
            tabs.add(textView);
            tabContainer.addView(textView);
        }
    }


    private void createHeadPoint() {
        View view = tabs.get(0);
        springView.getHeadPoint().setX(view.getX() + view.getWidth() / 2);
        springView.getHeadPoint().setY(view.getY() + view.getHeight() / 2);
    }

    private void createFootPoint() {
        View view = tabs.get(0);
        springView.getFootPoint().setX(view.getX() + view.getWidth() / 2);
        springView.getFootPoint().setY(view.getY() + view.getHeight() / 2);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        createHeadPoint();
        createFootPoint();
    }


    public void setViewPager(final ViewPager viewPager) {
        this.viewPager = viewPager;
        init();
        setUpListener();
    }

    private void setUpListener(){
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < tabs.size() - 1) {

                    // radius
                    float radiusOffsetHead = 0.5f;
                    if(positionOffset < radiusOffsetHead){
                        springView.getHeadPoint().setRadius(radiusMin);
                    }else{
                        springView.getHeadPoint().setRadius(((positionOffset-radiusOffsetHead)/(1-radiusOffsetHead) * radiusOffset + radiusMin));
                    }
                    float radiusOffsetFoot = 0.5f;
                    if(positionOffset < radiusOffsetFoot){
                        springView.getFootPoint().setRadius((1-positionOffset/radiusOffsetFoot) * radiusOffset + radiusMin);
                    }else{
                        springView.getFootPoint().setRadius(radiusMin);
                    }

                    // x
                    float headX = 1f;
                    if (positionOffset < headMoveOffset){
                        float positionOffsetTemp = positionOffset / headMoveOffset;
                        headX = (float) ((Math.atan(positionOffsetTemp*acceleration*2 - acceleration ) + (Math.atan(acceleration))) / (2 * (Math.atan(acceleration))));
                    }
                    springView.getHeadPoint().setX(getTabX(position) - headX * getPositionDistance(position));
                    float footX = 0f;
                    if (positionOffset > footMoveOffset){
                        float positionOffsetTemp = (positionOffset- footMoveOffset) / (1- footMoveOffset);
                        footX = (float) ((Math.atan(positionOffsetTemp*acceleration*2 - acceleration ) + (Math.atan(acceleration))) / (2 * (Math.atan(acceleration))));
                    }
                    springView.getFootPoint().setX(getTabX(position) - footX * getPositionDistance(position));


                    if(positionOffset == 0){
                        springView.getHeadPoint().setRadius(radiusMax);
                        springView.getFootPoint().setRadius(radiusMax);
                    }

                    springView.postInvalidate();

                }
            }
        });
    }

    private float getPositionDistance(int position) {
        float tarX = tabs.get(position + 1).getX();
        float oriX = tabs.get(position).getX();
        return oriX - tarX;
    }

    private float getTabX(int position) {
        return tabs.get(position).getX() + tabs.get(position).getWidth() / 2;
    }
}
