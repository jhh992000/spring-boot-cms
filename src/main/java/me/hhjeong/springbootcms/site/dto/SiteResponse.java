package me.hhjeong.springbootcms.site.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.hhjeong.springbootcms.site.domain.Site;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteResponse {

    private Long id;
    private String name;
    private boolean enabled;
    private String alias;
    private boolean useLoginLock;
    private int countOfLoginFail;

    public static SiteResponse of(Site site) {
        return new SiteResponse(site.getId(), site.getName(), site.isEnabled(), site.getAlias(), site.isUseLoginLock(), site.getCountOfLoginFail());
    }
}
