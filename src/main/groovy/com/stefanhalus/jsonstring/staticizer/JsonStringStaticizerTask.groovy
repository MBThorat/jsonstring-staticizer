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
        def FileConfig[] files = project.jsonStringStaticizer.fileConfigList
        if (!packageName) {
            throw new IllegalStateException('localizations.packageName is undefined!')
        }

        def localizationsGenerator = new JsonStringStaticizerGenerator()

        for(FileConfig fileConfig : files) {
            def inputFile = new File(project.getProjectDir(), fileConfig.fileName)
            TypeSpec generated = localizationsGenerator.generate(inputFile, fileConfig.targetJsonKey, fileConfig.outputFileName)
            localizationsGenerator.writeToOutput(packageName, outputDir, generated)
        }
    }
}