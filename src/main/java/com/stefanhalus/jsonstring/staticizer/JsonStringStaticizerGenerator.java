package com.stefanhalus.jsonstring.staticizer;

import com.google.common.base.CaseFormat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonStringStaticizerGenerator {

    void generate(File input, File outputDir, String packageName) throws IOException {
        String currentTime = new SimpleDateFormat("MM/dd/yyyy  HH:mm:ss").format(new Date());

        Type type = new TypeToken<LinkedHashMap<String, Object>>() {
        }.getType();

        LinkedHashMap<String, Object> data = new Gson().fromJson(new FileReader(input), type);
        String basename = removeExtension(input.getAbsolutePath());

        TypeSpec.Builder builder = TypeSpec.classBuilder(basename).addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        builder.addJavadoc("Generated class based on the localizations file located \n " +
                "in " + input.getPath() + "\n" +
                "Generation time: " + currentTime + "\n");

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String fieldName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, entry.getKey());
            FieldSpec.Builder field;

            if (entry.getValue() instanceof String) {
                field = FieldSpec.builder(String.class, fieldName)
                        .initializer("$S", entry.getKey());
            } else {
                throw new IllegalArgumentException("JsonStringStaticizerGenerator ERROR: Only strings are allowed in Localization file. " +
                        "Problem at entry: " + entry.getKey());
            }

            field.addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .addJavadoc("Default value: \"" + entry.getValue() + "\"\n");

            builder.addField(field.build());
        }

        JavaFile.builder(packageName, builder.build())
                .build()
                .writeTo(outputDir);
    }

    private static String removeExtension(String s) {
        String result;

        int sepIndex = s.lastIndexOf(System.getProperty("file.separator"));
        if (sepIndex == -1) {
            result = s;
        } else {
            result = s.substring(sepIndex + 1);
        }

        int extIndex = result.lastIndexOf(".");
        if (extIndex != -1) {
            result = result.substring(0, extIndex);
        }

        return result;
    }

}
