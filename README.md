# SpringIndicator
An indicator like Morning Routine guide.It was originally based on [BezierDemo](https://github.com/chenupt/BezierDemo).

The sample app: [click me](https://github.com/chenupt/SpringIndicator/raw/master/img/springindicator_1.0.0.apk)

![gif](https://raw.githubusercontent.com/chenupt/SpringIndicator/master/img/si_1.0.0.gif)

#Usage
---
Add the dependency to your build.gradle.
```
maven {
    url "https://oss.sonatype.org/content/repositories/snapshots/"
}

dependencies {
    compile 'com.github.chenupt.android:springindicator:1.0.0-SNAPSHOT@aar'
}
```
Add the indicator to your layout.

```
<github.chenupt.springindicator.SpringIndicator
    xmlns:app="http://schemas.android.com/apk/res-auto"
    app:textSize="18sp"
    app:indicatorColors="@array/indicator_colors"
    app:textColor="@color/colorPrimaryDark"
    app:selectedTextColor="@android:color/white"
    app:indicatorColor="@color/colorPrimary"
    android:id="@+id/indicator"
    android:layout_width="match_parent"
    android:layout_height="56dp"/>
```
Setup with your viewPager.
```
springIndicator.setViewPager(viewPager);
```
[All xml attributes.](https://github.com/chenupt/SpringIndicator/blob/master/lib%2Fsrc%2Fmain%2Fres%2Fvalues%2Fattrs.xml)

Developed By
---
 * Chenupt - <chenupt@gmail.com>
 * G+ [chenupt](https://plus.google.com/u/0/109194013506774756478)
 * 微博：[chenupt](http://weibo.com/p/1005052159173535/home)
 * QQ：753785666

License
---

    Copyright 2015 chenupt

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


