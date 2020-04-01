package com.springboot.pratice.springbootraml;

import com.springboot.practice.springbootraml.api.ItemController;
import com.springboot.practice.springbootraml.api.model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ItemControllerImpl implements ItemController {

    private List<Item> items = new ArrayList<Item>();

    @Override
    public ResponseEntity getItems() {
        return ResponseEntity.ok(items);
    }

    @Override
    public ResponseEntity createItem(Item item) {
        // overwrite if a item with the same id is present
        Optional<Item> existingItem = items.stream().filter(i -> i.getId().equals(item.getId())).findFirst();
        if (existingItem.isPresent()) {
            items.remove(existingItem.get());
            items.add(item);
            return ResponseEntity.ok().build();
        }
        int id = items.stream().map(Item::getId).sorted().findFirst().orElse(0) + 1;
        items.add(item.withId(id));
        URI uri = UriComponentsBuilder.fromPath("/api/items/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<Item> getItemById(Long id) {
        Optional<Item> item = items.stream().filter(i -> i.getId().equals(id.intValue())).findFirst();
        if (!item.isPresent()) {
            throw new ItemNotFoundException();
        }
        return ResponseEntity.<Item>ok().body(item.get());
    }
}
