package cn.edu.xmu.g12.g12ooadgoods.model.vo.groupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class NewGrouponVo {
    @NotNull(message = "name 不得为空")
    @Size(min = 1)
    private String strategy;

    @NotNull(message = "beginTime 不得为空")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime beginTime;

    @NotNull(message = "endTime 不得为空")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime endTime;

    public boolean isInvalid() {
        strategy = strategy.trim();
        if (strategy.isEmpty()) return true;
        if (!beginTime.isBefore(endTime)) return true;
        return false;
    }
}
