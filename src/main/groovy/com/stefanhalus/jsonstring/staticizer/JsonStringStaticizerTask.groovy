package com.stefanhalus.jsonstring.staticizer

import com.squareup.javapoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs

public class JsonStringStaticizerTask extends DefaultTask {
    //@InputDirectory
    File inputDir

    @OutputDirectory
    File outputDir

    @TaskAction
    public void execute(IncrementalTaskInputs inputs) {
        def packageName = project.jsonStringStaticizer.packageName
        def sourceDir = project.jsonStringStaticizer.sourceDir
        def targetJsonKey = project.jsonStringStaticizer.targetJsonKey
        if (!packageName) {
            throw new IllegalStateException('localizations.packageName is undefined!')
        }
        if(!sourceDir) {
            throw new IllegalStateException('sourceDir is not defined')
        }

        inputDir = new File(project.getProjectDir(), sourceDir)

        def localizationsGenerator = new JsonStringStaticizerGenerator()

        for (File input : inputDir.listFiles()) {
            if (!input.name.startsWith(".")) {
                TypeSpec generated = localizationsGenerator.generate(input, targetJsonKey);
                localizationsGenerator.writeToOutput(packageName, outputDir, generated)
            }
        }
    }
}