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
```
apply plugin: 'com.stefanhalus.jsonstring.staticizer'

jsonStringStaticizer {
    packageName 'com.desired.package.for.generated.class'
    sourceDir 'src/main/localizations' // source dir of Json files 
}
```
