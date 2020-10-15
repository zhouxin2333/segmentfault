package cool.zhouxin.q_1010000023765242;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author zhouxin
 * @since 2020/10/14 19:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "data")
public class DiscussPost {

    @Id
    private Long id;

    @Field( type = FieldType.Date,
            format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
}
