package cn.edu.xmu.g12.g12ooadgoods.model.vo.flashsale;

import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode.*;

@Data
public class ModifyFlashSaleVo {
    @NotNull(message = "flashDate不得为空")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime flashDate;

    public ResponseCode fieldCode() {
        if (LocalDateTime.now().isAfter(flashDate))
            return ACTIVITYALTER_INVALID;
        return OK;
    }
}
