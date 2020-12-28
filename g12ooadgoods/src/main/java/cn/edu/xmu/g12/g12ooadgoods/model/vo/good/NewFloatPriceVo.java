package cn.edu.xmu.g12.g12ooadgoods.model.vo.good;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@ApiModel("新价格浮动传值对象")
public class NewFloatPriceVo {
    @NotNull(message = "activityPrice不得为空")
    @Min(1)
    private Long activityPrice;

    @NotNull(message = "beginTime不得为空")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    @NotNull(message = "endTime不得为空")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    @NotNull(message = "quantity")
    @Min(1)
    private Integer quantity;

    public boolean isInvalid() {
        return beginTime.isAfter(endTime);
    }
}
