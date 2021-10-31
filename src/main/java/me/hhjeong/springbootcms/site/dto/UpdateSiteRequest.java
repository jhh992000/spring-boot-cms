package me.hhjeong.springbootcms.site.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.hhjeong.springbootcms.site.domain.Site;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSiteRequest {

    private String name;
    private boolean enabled;
    private String alias;
    private boolean useLoginLock;
    private int countOfLoginFail;

    public Site toSite() {
        return Site.builder()
            .name(name)
            .enabled(enabled)
            .alias(alias)
            .useLoginLock(useLoginLock)
            .countOfLoginFail(countOfLoginFail)
            .build();
    }

    public Site toSite(Long id) {
        return Site.builder()
            .id(id)
            .name(name)
            .enabled(enabled)
            .alias(alias)
            .useLoginLock(useLoginLock)
            .countOfLoginFail(countOfLoginFail)
            .build();
    }
}
