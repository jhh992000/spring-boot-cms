package me.hhjeong.springbootcms.menu.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.hhjeong.springbootcms.menu.domain.RoleMenu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("롤메뉴 서비스")
@ExtendWith(MockitoExtension.class)
class RoleMenuServiceTest {

    @InjectMocks
    private RoleMenuService roleMenuService;

    @Test
    void 롤메뉴_머지() {
        List<RoleMenu> roleMenusOne = new ArrayList<>(Arrays.asList(
            new RoleMenu(1L, 1L, 1L),
            new RoleMenu(1L, 1L, 2L),
            new RoleMenu(1L, 1L, 3L)
        ));
        List<RoleMenu> roleMenusTwo = new ArrayList<>(Arrays.asList(
            new RoleMenu(1L, 1L, 1L),
            new RoleMenu(1L, 1L, 2L),
            new RoleMenu(1L, 1L, 3L),
            new RoleMenu(1L, 1L, 4L),
            new RoleMenu(1L, 1L, 5L)
        ));

        roleMenuService.mergeRoleMenus(roleMenusOne, roleMenusTwo);

        assertThat(roleMenusOne).hasSize(5);
        assertThat(roleMenusOne).containsAnyElementsOf(Arrays.asList(
            new RoleMenu(1L, 1L, 4L),
            new RoleMenu(1L, 1L, 5L)
        ));
    }

}