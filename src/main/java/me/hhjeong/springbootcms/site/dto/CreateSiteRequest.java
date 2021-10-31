package me.hhjeong.springbootcms.site.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.hhjeong.springbootcms.site.domain.Site;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSiteRequest {

    @NotEmpty
    private String name;
    @NotNull
    private boolean enabled;
    @NotEmpty
    private String alias;
    @NotNull
    private boolean useLoginLock;
    @NotNull
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
}
