package cool.zhouxin.q_1010000037558780.api.pojo;

import cool.zhouxin.q_1010000037558780.api.deser.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author zhouxin
 * @since 2020/10/22 10:31
 */
@Data
@NoArgsConstructor
public class TestRequest {

    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    LocalDateTime date;
}
