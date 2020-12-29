package cn.edu.xmu.g12.g12ooadgoods.model.vo.flashsale;

import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode.ACTIVITYALTER_INVALID;
import static cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode.OK;

@Data
@ApiModel("新FlashSale传值对象")
public class NewFlashSaleVo {
    @NotNull(message = "flashDate 不得为空")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime flashDate;

    /**
     * 不允许增加过去和今天的秒杀
     */
    public boolean isInvalid() {
        var now = LocalDateTime.now();
        if (now.getYear() > flashDate.getYear() ||
                now.getYear() == flashDate.getYear() && now.getDayOfYear() >= flashDate.getDayOfYear())
            return true;
        return false;
    }
}
