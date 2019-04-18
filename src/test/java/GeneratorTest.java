import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.stefanhalus.jsonstring.staticizer.JsonStringStaticizerGenerator;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GeneratorTest {



    @Test
    public void testParser() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("eng.json").getFile());

        assertEquals(true, file.exists());

        JsonStringStaticizerGenerator generator = new JsonStringStaticizerGenerator();
        TypeSpec generated = generator.generate(file, "Localization");
        JavaFile.builder("", generated).build().writeTo(System.out);

    }
}
