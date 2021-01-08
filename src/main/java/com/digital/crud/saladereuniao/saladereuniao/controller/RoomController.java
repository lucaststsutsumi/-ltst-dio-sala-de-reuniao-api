package com.digital.crud.saladereuniao.saladereuniao.controller;

import com.digital.crud.saladereuniao.saladereuniao.exception.ResourceNotFoundException;
import com.digital.crud.saladereuniao.saladereuniao.model.Room;
import com.digital.crud.saladereuniao.saladereuniao.repository.RoomRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/rooms")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RoomController {

    private RoomRepository repository;


    @GetMapping()
    public List<Room> getAllRooms() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) throws ResourceNotFoundException {
        Room room = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found:: " + id));

        return ResponseEntity.ok().body(room);
    }

    @PostMapping()
    public Room createRoom(@RequestBody @Valid Room room) {
        return repository.save(room);
    }

    @PutMapping("/{id}")
    public Room updateRoom(@PathVariable Long id, @RequestBody @Valid Room roomDto) throws ResourceNotFoundException {
        Room room = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with this ID:: " + id));

        room.setDate(roomDto.getDate());
        room.setEndHour(roomDto.getEndHour());
        room.setId(id);
        room.setName(roomDto.getName());
        room.setStartHour(roomDto.getStartHour());

        return repository.save(room);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteRoom(@PathVariable Long id) throws ResourceNotFoundException {
        Room room = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with this ID:: " + id));

        repository.delete(room);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return response;
    }
}
