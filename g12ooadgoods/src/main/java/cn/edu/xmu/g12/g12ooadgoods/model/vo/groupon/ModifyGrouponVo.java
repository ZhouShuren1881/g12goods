package cn.edu.xmu.g12.g12ooadgoods.model.vo.groupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Data
public class ModifyGrouponVo {
    @Nullable
    private String strategy;
    @Nullable
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;
    @Nullable
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
