package cool.zhouxin.q_1010000037558780.api.deser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 这里直接继承{@link LocalDateTimeDeserializer}，主要是因为我们形式上是处理{@link LocalDateTime}类型的字段的
 * 并且需要满足其父类{@link JSR310DateTimeDeserializerBase} 的泛型要求，还可以简化一些不必要的实现
 * @author zhouxin
 * @since 2020/10/22 16:26
 */
public class CustomLocalDateTimeDeserializer extends LocalDateTimeDeserializer {

    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private CustomLocalDateTimeDeserializer() {
        super(DEFAULT_FORMATTER);
    }

    public CustomLocalDateTimeDeserializer(DateTimeFormatter formatter) {
        super(formatter);
    }

    /**
     * 这个方法不能漏，必须覆盖，因为在{@link BeanDeserializerFactory#constructSettableProperty} 中，有一个
     * {@link DeserializationContext#findContextualValueDeserializer(JavaType, BeanProperty)}的操作
     * 即使你用了自定义的Deserializer，但是它可以根据你的{@link JsonFormat#pattern()}的值，再用下面这个方法
     * 构造一个新的Deserializer，因为我们的{@link CustomLocalDateTimeDeserializer} 又是继承的{@link LocalDateTimeDeserializer}
     * 不覆盖的话，就最终Deserializer又变成{@link LocalDateTimeDeserializer}了
     * @param formatter
     * @return
     */
    @Override
    protected LocalDateTimeDeserializer withDateFormat(DateTimeFormatter formatter) {
        return new CustomLocalDateTimeDeserializer(formatter);
    }

    /**
     * 处理方式很简单，根据当前的{@link #_formatter}再构造一个{@link LocalDateDeserializer} ，让它帮我完成
     * 转换，我们只是关注如何把{@link LocalDate}转化成{@link LocalDateTime}
     * @param p
     * @param ctxt
     * @return
     * @throws IOException
     * @throws JsonProcessingException
     */
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        LocalDate localDate = new LocalDateDeserializer(_formatter).deserialize(p, ctxt);
        LocalDateTime localDateTime = localDate.atStartOfDay();
        return localDateTime;
    }
}
