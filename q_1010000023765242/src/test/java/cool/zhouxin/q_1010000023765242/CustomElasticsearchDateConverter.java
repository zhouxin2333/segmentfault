package cool.zhouxin.q_1010000023765242;

import org.elasticsearch.common.time.DateFormatter;
import org.elasticsearch.common.time.DateFormatters;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.core.convert.ConversionException;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhouxin
 * @since 2020/10/15 18:35
 */
public class CustomElasticsearchDateConverter {


    private static final ConcurrentHashMap<String, CustomElasticsearchDateConverter> converters = new ConcurrentHashMap<>();

    private final DateFormatter dateFormatter;

    /**
     * Creates an CustomElasticsearchDateConverter for the given {@link DateFormat}.
     *
     * @param dateFormat must not be @{literal null}
     * @return converter
     */
    public static CustomElasticsearchDateConverter of(DateFormat dateFormat) {

        Assert.notNull(dateFormat, "dateFormat must not be null");

        return of(dateFormat.name());
    }

    /**
     * Creates an CustomElasticsearchDateConverter for the given pattern.
     *
     * @param pattern must not be {@literal null}
     * @return converter
     */
    public static CustomElasticsearchDateConverter of(String pattern) {
        Assert.notNull(pattern, "pattern must not be null");

        return converters.computeIfAbsent(pattern, p -> new CustomElasticsearchDateConverter(DateFormatter.forPattern(p)));
    }

    private CustomElasticsearchDateConverter(DateFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    /**
     * Formats the given {@link TemporalAccessor} int a String
     *
     * @param accessor must not be {@literal null}
     * @return the formatted object
     */
    public String format(TemporalAccessor accessor) {

        Assert.notNull(accessor, "accessor must not be null");

        return dateFormatter.format(accessor);
    }

    /**
     * Formats the given {@link TemporalAccessor} int a String
     *
     * @param date must not be {@literal null}
     * @return the formatted object
     */
    public String format(Date date) {

        Assert.notNull(date, "accessor must not be null");

        return dateFormatter.format(Instant.ofEpochMilli(date.getTime()));
    }

    /**
     * Parses a String into an object
     *
     * @param input the String to parse, must not be {@literal null}.
     * @param type the class to return
     * @param <T> the class of type
     * @return the new created object
     */
    public <T extends TemporalAccessor> T parse(String input, Class<T> type) {
        TemporalAccessor accessor = dateFormatter.parse(input);
        try {
            Method method = type.getMethod("from", TemporalAccessor.class);
            Object o = method.invoke(null, accessor);
            return type.cast(o);
        } catch (NoSuchMethodException e) {
            throw new ConversionException("no 'from' factory method found in class " + type.getName());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ConversionException("could not create object of class " + type.getName(), e);
        }
    }

    /**
     * Parses a String into a Date.
     *
     * @param input the String to parse, must not be {@literal null}.
     * @return the new created object
     */
    public Date parse(String input) {
        return new Date(Instant.from(DateFormatters.from(dateFormatter.parse(input))).toEpochMilli());
    }
}
