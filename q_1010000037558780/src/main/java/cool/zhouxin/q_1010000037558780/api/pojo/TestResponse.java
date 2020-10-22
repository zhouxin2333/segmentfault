package cool.zhouxin.q_1010000037558780.api.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author zhouxin
 * @since 2020/10/22 10:31
 */
@Data
@NoArgsConstructor
public class TestResponse {

    @JsonFormat(pattern = "yyyy年MM月dd日")
    LocalDateTime date;
}
