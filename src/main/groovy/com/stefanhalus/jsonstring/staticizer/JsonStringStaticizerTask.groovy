package com.stefanhalus.jsonstring.staticizer

import com.squareup.javapoet.TypeSpec
import org.apache.commons.io.filefilter.FileFileFilter
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs

import java.util.function.Consumer

public class JsonStringStaticizerTask extends DefaultTask {
    //@InputDirectory
    File inputDir

    @OutputDirectory
    File outputDir

    @TaskAction
    public void execute(IncrementalTaskInputs inputs) {
        def packageName = project.jsonStringStaticizer.packageName
        def sourceDir = project.jsonStringStaticizer.sourceDir
        def FileConfig[] files = project.jsonStringStaticizer.fileConfigList
        if (!packageName) {
            throw new IllegalStateException('localizations.packageName is undefined!')
        }
        if (!sourceDir) {
            throw new IllegalStateException('sourceDir is not defined')
        }

        inputDir = new File(project.getProjectDir(), sourceDir)

        def localizationsGenerator = new JsonStringStaticizerGenerator()

        if (files != null) {
            for(FileConfig fileConfig : files) {
                def inputFile = new File(project.getProjectDir(), sourceDir + "/" + fileConfig.fileName)
                TypeSpec generated = localizationsGenerator.generate(inputFile, fileConfig.targetJsonKey)
                localizationsGenerator.writeToOutput(packageName, outputDir, generated)
            }
        } else {
            for (File input : inputDir.listFiles()) {
                if (input.isFile()) {
                    TypeSpec generated = localizationsGenerator.generate(input, null)
                    localizationsGenerator.writeToOutput(packageName, outputDir, generated)
                }
            }
        }
    }
}