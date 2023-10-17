package telran.monitoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import telran.monitoring.service.BackOfficeService;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/pulse/values")
@Validated
public class BackOfficeController {
    @Autowired
    BackOfficeService service;

    @GetMapping("/avg/{id}")
    public int getAvgValue(@PathVariable("id") long patientId,
                           @RequestParam(name = "from", required = false) LocalDateTime from,
                           @RequestParam(name = "to", required = false) LocalDateTime to) {
        return service.getAvgValue(patientId, from, to);
    }

    @GetMapping("/max/{id}")
    public int getMaxValue(@PathVariable("id") long patientId,
                           @RequestParam(name = "from", required = false) LocalDateTime from,
                           @RequestParam(name = "to", required = false) LocalDateTime to) {
        return service.getMaxValue(patientId, from, to);
    }

    @GetMapping("/all/{id}")
    public List<Integer> getAllValues(@PathVariable("id") long patientId,
                                      @RequestParam(name = "from", required = false) LocalDateTime from,
                                      @RequestParam(name = "to", required = false) LocalDateTime to) {
        return service.getAllValues(patientId, from, to);
    }
}
