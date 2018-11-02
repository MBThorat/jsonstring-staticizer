# jsonstring-staticizer

// Gradle plugin that Takes a specified files and turns the JSON keys into java static fields
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

	def version = '0.0.5'
	dependencies {
	        implementation "com.github.halusstefan:jsonstring-staticizer:$version"
	}

Applying the plugin

apply plugin: 'com.stefanhalus.jsonstring.staticizer'

jsonStringStaticizer {
    packageName 'com.desired.package.for.generated.class'
    sourceDir 'src/main/localizations' // source dir of Json files 
}