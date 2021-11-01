package me.hhjeong.springbootcms.menu.application;

import static me.hhjeong.springbootcms.common.base.BaseConstants.PAGE_SIZE;
import static me.hhjeong.springbootcms.common.base.BaseConstants.START_PAGE_NO;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;
import javax.json.JsonPatch;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hhjeong.springbootcms.menu.domain.Menu;
import me.hhjeong.springbootcms.menu.domain.MenuRepository;
import me.hhjeong.springbootcms.menu.dto.CreateMenuRequest;
import me.hhjeong.springbootcms.menu.dto.MenuResponse;
import me.hhjeong.springbootcms.menu.dto.UpdateMenuRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final ObjectMapper objectMapper;

    public List<MenuResponse> findMenus() {
        final List<Menu> menus = menuRepository.findAll();
        return menus.stream()
            .map(MenuResponse::of)
            .collect(Collectors.toList());
    }

    public MenuResponse createMenu(CreateMenuRequest request) {
        Menu menu = menuRepository.save(request.toMenu());
        return MenuResponse.of(menu);
    }

    public Menu findById(Long id) {
        return menuRepository.findById(id)
            .orElseThrow(RuntimeException::new);
    }

    public Menu replace(Long id, UpdateMenuRequest request) {
        return menuRepository.findById(id)
            .map(menu -> {
                menu.update(request.toMenu());
                return menu;
            })
            .orElseGet(() -> {
                Menu newMenu = request.toMenu(id);
                return menuRepository.save(newMenu);
            });
    }

    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }

    public Menu patchMenu(Long id, JsonPatch jsonPatch) {
        Menu originalMenu = findById(id);
        Menu modifiedMenu = mergeMenu(originalMenu, jsonPatch);
        originalMenu.update(modifiedMenu);
        log.debug("modified menu : {}", modifiedMenu);
        return modifiedMenu;
    }

    private Menu mergeMenu(Menu originalMenu, JsonPatch jsonPatch) {
        JsonStructure target = objectMapper.convertValue(originalMenu, JsonStructure.class);
        JsonValue patchedMenu = jsonPatch.apply(target);
        return objectMapper.convertValue(patchedMenu, Menu.class);
    }

}