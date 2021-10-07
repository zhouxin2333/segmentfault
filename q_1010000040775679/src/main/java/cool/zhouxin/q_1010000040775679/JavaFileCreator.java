package cool.zhouxin.q_1010000040775679;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhouxin
 * @since 2021/10/7 18:32
 */
public class JavaFileCreator {

    public static JavaFile create(Set<String> names, String className, String originalDataStr) {

        List<FieldSpec> fieldSpecList = buildFieldSpecs(names);

        List<MethodSpec> methodSpecList = names.stream()
                                               .map(JavaFileCreator::buildMethods)
                                               .flatMap(Function.identity())
                                               .collect(Collectors.toList());

        TypeSpec userTypeSpec = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addFields(fieldSpecList)
                .addMethods(methodSpecList)
                .addJavadoc("这是根据JSON数据格式自动生成的Java源文件，源数据如下：\n$N", originalDataStr)
                .build();

        JavaFile javaFile = JavaFile.builder("cool.zhouxin", userTypeSpec)
                .build();

        return javaFile;
    }

    public static JavaFile createWithLombok(Set<String> names, String className, String originalDataStr) {

        List<FieldSpec> fieldSpecList = buildFieldSpecs(names);

        TypeSpec userTypeSpec = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addFields(fieldSpecList)
                .addAnnotation(Data.class)
                .addAnnotation(NoArgsConstructor.class)
                .addJavadoc("这是根据JSON数据格式自动生成的Java源文件，源数据如下：\n$N", originalDataStr)
                .build();

        JavaFile javaFile = JavaFile.builder("cool.zhouxin", userTypeSpec)
                .build();

        return javaFile;
    }

    private static List<FieldSpec> buildFieldSpecs(Set<String> names) {
        List<FieldSpec> fieldSpecList = names.stream()
                                             .map(JavaFileCreator::buildField)
                                             .collect(Collectors.toList());
        return fieldSpecList;
    }


    private static Stream<MethodSpec> buildMethods(String name) {
        String firstUpperCaseName = toFirstUpperCase(name);
        MethodSpec getMethodSpec = MethodSpec.methodBuilder("get".concat(firstUpperCaseName))
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addStatement("return this.$N", name)
                .build();

        MethodSpec setMethodSpec = MethodSpec.methodBuilder("set".concat(firstUpperCaseName))
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(String.class, name)
                .addStatement("this.$N=$N", name, name)
                .build();

        return Stream.of(getMethodSpec, setMethodSpec);
    }

    private static FieldSpec buildField(String name) {
        FieldSpec fieldSpec = FieldSpec.builder(String.class, name, Modifier.PRIVATE)
                .build();
        return fieldSpec;
    }

    private static String toFirstUpperCase(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }

        char chars[] = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }
}
