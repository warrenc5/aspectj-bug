package my;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

/**
 *
 * @author wozza
 */
@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes({
    "javax.annotation.Resource"
})
public class MyAnnotationProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        System.err.println(this.getClass().getName() + " processing ");
        System.err.println("options: " + processingEnv.getOptions().keySet().toString());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.err.println("root elements :" + roundEnv.getRootElements().stream().map(r -> r.getSimpleName()).collect(toList()).toString());
        if (roundEnv.processingOver()) {
            try {
                FileObject resource;
                Filer filer = super.processingEnv.getFiler();
                resource = filer.createResource(StandardLocation.SOURCE_OUTPUT, "", "MyTestAspect.aj", null);
                System.err.println("creating resource " + resource.toUri().toString());

                InputStream in = this.getClass().getResourceAsStream("/test-aspect.txt");
                if (in == null) {
                    throw new NullPointerException("can't find test-aspect.txt");
                }

                OutputStream out;
                out = resource.openOutputStream();
                while (in.available() > 0) {
                    out.write(in.read());
                }

                in.close();
                out.flush();
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }
}
