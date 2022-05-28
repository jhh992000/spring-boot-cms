package me.hhjeong.springbootcms.menu.ui;

import lombok.RequiredArgsConstructor;
import me.hhjeong.springbootcms.menu.application.MenuService;
import me.hhjeong.springbootcms.menu.domain.Menu;
import me.hhjeong.springbootcms.menu.dto.CreateMenuRequest;
import me.hhjeong.springbootcms.menu.dto.MenuResponse;
import me.hhjeong.springbootcms.menu.dto.UpdateMenuRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonPatch;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<MenuResponse> createMenu(@RequestBody @Valid CreateMenuRequest request) {
        MenuResponse menuResponse = menuService.createMenu(request);
        return ResponseEntity.created(URI.create("/api/menus/" + menuResponse.getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuResponse> findMenu(@PathVariable Long id) {
        Menu menu = menuService.findMenu(id);
        return ResponseEntity.ok().body(MenuResponse.of(menu));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuResponse> replaceMenu(@PathVariable Long id, @RequestBody @Valid UpdateMenuRequest request) {
        Menu menu = menuService.replaceMenu(id, request);
        return ResponseEntity.ok().body(MenuResponse.of(menu));
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<MenuResponse> patchMenu(@PathVariable Long id, @RequestBody JsonPatch patchDocument) {
        Menu menu = menuService.patchMenu(id, patchDocument);
        return ResponseEntity.ok().body(MenuResponse.of(menu));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }

}
