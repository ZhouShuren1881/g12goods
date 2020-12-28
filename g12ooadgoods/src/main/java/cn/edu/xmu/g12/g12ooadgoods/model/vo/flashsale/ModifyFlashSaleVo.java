package cn.edu.xmu.g12.g12ooadgoods.model.vo.flashsale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class ModifyFlashSaleVo {
    @NotNull(message = "flashDate不得为空")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime flashDate;
}
