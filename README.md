# EasyVideoPicker
[![](https://jitpack.io/v/deepakkumardk/EasyVideoPicker.svg)](https://jitpack.io/#deepakkumardk/EasyVideoPicker)

An Video Picker library for Android, written purely in [Kotlin](http://kotlinlang.org).


## Usages
project/build.gradle
```groovy
allprojects {
	repositories {
	    maven { url 'https://jitpack.io' }
	}
}
```

app/build.gradle
```groovy
dependencies {
        implementation 'com.github.deepakkumardk:EasyVideoPicker:$latest-version'
}
```

### Activity

**No need** to specify the **READ_EXTERNAL_STORAGE** & **WRITE_EXTERNAL_STORAGE** permission in your manifest file, library will handle this permission internally.

Requesting the library for VideoPicker is as easy as wiring a one line of code like this:
```kotlin
    EasyVideoPicker().startPickerForResult(this, VideoPickerItem(), 3000)  //RequestCode
```

If you want to start this library from a fragment just pass the fragment context in the above method.


### Customization
```kotlin
    val item = VideoPickerItem().apply {
        showIcon = true
        debugMode = true
        themeResId = R.style.CustomTheme
        timeLimit = TimeUnit.MINUTES.toMillis(20)   //(Long) max time of video in milliseconds
        sizeLimit = 100 * 1024 * 1024       // max. size in Bytes
        selectionMode = SelectionMode.Multiple  //Other modes are Single & Custom(limit:Int)
        gridDecoration = Triple(2, 20, true)    //(spanCount,spacing,includeEdge)
        placeholder = R.color.colorPlaceholder
        limitMessage = "Please select less than %s pictures"
        showDuration = true
        selectionStyle = SelectionStyle.Large
    }       //see VideoPickerItem class for defaults value. these are all the variables that exists for customization of the library
    EasyVideoPicker().startPickerForResult(this, item, 3000)
```

#### Handing Results
```kotlin
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 3000) {
            val list = EasyVideoPicker.getSelectedVideos(data)  //ArrayList<VideoModel>
            //Handle this list
        }
    }
```

### Fetch Only Video list
If you want to fetch only video list from media store and/or you don't want to use the default UI from this
library you can also do that with this method.

```kotlin
    EasyVideoPicker.getAllVideos(this) { videoList - >
        //Handle the videoList : MutableList<VideoModel>
    }
```

## License

```
 Copyright 2019 Deepak Kumar

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   ```
