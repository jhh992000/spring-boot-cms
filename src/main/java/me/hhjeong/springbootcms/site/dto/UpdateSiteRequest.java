package me.hhjeong.springbootcms.site.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSiteRequest {

    private String name;
    private boolean enabled;
    private String alias;
    private boolean useLoginLock;
    private int countOfLoginFail;
}
