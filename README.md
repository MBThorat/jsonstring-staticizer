## jsonstring-staticizer [![](https://jitpack.io/v/halusstefan/jsonstring-staticizer.svg)](https://jitpack.io/#halusstefan/jsonstring-staticizer)

#### Android Gradle plugin that takes JSON files from a specified path and turns the JSON keys into java static fields at build time. 

## Adding the dependecy
```gradle
//in your root build.gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

def jsonstring = '0.0.6'
dependencies {
	...
	classpath "com.github.halusstefan:jsonstring-staticizer:$jsonstring"
}
```
## Applying the plugin
```gradle
apply plugin: 'com.stefanhalus.jsonstring.staticizer'

jsonStringStaticizer {
    packageName 'com.desired.package.for.generated.class'
    sourceDir 'src/main/localizations' // source dir of Json files 
}
```

### Input JsonFile.json
```json
{
  "keyOne" : "Some value",
  "keyTwo" : "Some other value"
}
```
### Output JsonFile.java

```java
package com.desired.package.for.generated.class;

import java.lang.String;

/**
 * Generated class based on the JSON file located 
 *  in base/src/main/localizations/JsonFile.json
 * Generation time: 11/02/2018  12:34:34
 */
public final class JsonFile {
  /**
   * Default value: "Some value"
   */
  public static final String KEY_ONE = "keyOne";

  /**
   * Default value: "Some other value"
   */
  public static final String KEY_TWO = "keyTwo";
}
```
# !!!! JSON files must contain only string values. Feel free to fork this repo and add the desired functionality. 

#### Based on https://github.com/commonsguy/cw-omnibus/tree/master/Gradle/Staticizer

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/
