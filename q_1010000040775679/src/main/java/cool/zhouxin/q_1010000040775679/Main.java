package cool.zhouxin.q_1010000040775679;

import com.alibaba.fastjson.JSONObject;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author zhouxin
 * @since 2021/10/7 18:00
 */
public class Main {

    public static void main(String[] args) {
        String jsonStr = "{'id':1, 'name':'zx'}";
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        Set<String> names = jsonObject.keySet();

//        JavaFile javaFile = JavaFileCreator.create(names, "User", jsonStr);
        JavaFile javaFile = JavaFileCreator.createWithLombok(names, "UserWithLombok", jsonStr);

        try {
            // 自己设置一个路径叭，最后生成的效果我也放到了项目中了
            Path path = Paths.get("C:\\tmp\\javafile");
            javaFile.writeTo(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

