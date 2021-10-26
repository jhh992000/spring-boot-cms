package me.hhjeong.springbootcms.common.security.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.hhjeong.springbootcms.common.security.domain.Resources;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResourcesResponse {

    private Long id;
    private String resourceName;
    private String httpMethod;
    private int orderNum;
    private String resourceType;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    public static ResourcesResponse of(Resources resources) {
        return ResourcesResponse.builder()
            .id(resources.getId())
            .resourceName(resources.getResourceName())
            .httpMethod(resources.getHttpMethod())
            .orderNum(resources.getOrderNum())
            .resourceType(resources.getResourceType())
            .build();
    }

}
