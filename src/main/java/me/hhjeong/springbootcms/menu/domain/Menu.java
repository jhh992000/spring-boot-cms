package me.hhjeong.springbootcms.menu.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.hhjeong.springbootcms.common.domain.BaseEntity;
import me.hhjeong.springbootcms.site.domain.Site;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode
@DynamicUpdate
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_menu_site"), nullable = false)
    private Site site;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "upper_menu_id", nullable = false)
    private Long upperMenuId;

    @Column(name = "depth", nullable = false)
    private Long depth;

    @Column(name = "sort_no", nullable = false)
    private Long sortNo;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MenuType type;

    @Column(name = "link_url")
    private String linkUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "open_type", nullable = false)
    private MenuOpenType openType;

    @Column(name = "hide", nullable = false)
    private Boolean hide;

    @Column(name = "enable", nullable = false)
    private Boolean enable;

    @Column(name = "content_id")
    private Long contentId;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "program_id")
    private String programId;


}
