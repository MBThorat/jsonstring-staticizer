package com.stefanhalus.jsonstring.staticizer

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.DynamicFeaturePlugin
import com.android.build.gradle.FeaturePlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class JsonStringStaticizerPlugin implements Plugin<Project> {

    @Override
    public void apply(Project target) {

        def isApp = target.plugins.hasPlugin AppPlugin
        def isLib = target.plugins.hasPlugin LibraryPlugin
        def isFeature = target.plugins.hasPlugin FeaturePlugin
        def isDynamicFeature = target.plugins.hasPlugin DynamicFeaturePlugin

        if (!isApp && !isLib && !isFeature && !isDynamicFeature) {
            throw new IllegalStateException("This plugin depends upon the com.android.application / com.android.library / com.android.feature plugins")
        }

        target.extensions.create('jsonStringStaticizer', PluginConfig)

        def variants

        if (isApp) {
            variants = target.android.applicationVariants
        } else {
            variants = target.android.libraryVariants
        }

        variants.all { variant ->
            def task = createTaskForVariant(target, variant)

            variant.javaCompileProvider.get().dependsOn task
            variant.javaCompileProvider.get().source += target.fileTree(task.outputDir)
            variant.registerJavaGeneratingTask(task, task.outputDir)
        }
    }

    private Task createTaskForVariant(Project target, BaseVariant variant) {
        def task = target.tasks.create(
                "generate${variant.name.capitalize()}StaticFromJsonString",
                JsonStringStaticizerTask)

        task.outputDir = new File("${target.buildDir}/generated/source/jsonstring/${variant.name}")
        task.group = "jsonstring"
        task.description = "Generate ${variant.name} Java code from JSON"
        task
    }
}

class PluginConfig {
    def String packageName
    def String sourceDir
    def FileConfig[] fileConfigList
}
class FileConfig {
    def String fileName
    def String targetJsonKey
}