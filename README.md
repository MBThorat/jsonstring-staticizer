# jsonstring-staticizer
Based on https://github.com/commonsguy/cw-omnibus/tree/master/Gradle/Staticizer

# Gradle plugin that takes JSON files from a specified path and turns the JSON keys into java static fields at build time
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	//in your root build.gradle
	def jsonstring = '0.0.5'
	dependencies {
		...
	        classpath "com.github.halusstefan:jsonstring-staticizer:$jsonstring"
	}

# Applying the plugin
```gradle
apply plugin: 'com.stefanhalus.jsonstring.staticizer'

jsonStringStaticizer {
    packageName 'com.desired.package.for.generated.class'
    sourceDir 'src/main/localizations' // source dir of Json files 
}
```

Input JsonFile.json
```json
{
  "keyOne" : "Some value",
  "keyTwo" : "Some other value"
}
```
Output JsonFile.java

```java
package com.desired.package.for.generated.class;

import java.lang.String;

/**
 * Generated class based on the localizations file located 
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
