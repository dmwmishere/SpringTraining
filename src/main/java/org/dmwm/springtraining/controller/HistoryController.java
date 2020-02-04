package org.dmwm.springtraining.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.dmwm.springtraining.data.history.AccountHistoryRepository;
import org.dmwm.springtraining.model.Client;
import org.dmwm.springtraining.model.history.AccountHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("history")
@Api(tags = "Контроллер по логированию операций")
@Log
public class HistoryController {

    @Autowired
    AccountHistoryRepository historyRepository;

    @ApiOperation(value = "Отчет об операциях за период времени")
    @RequestMapping(value = "operationReport", method = RequestMethod.GET)
    public List<AccountHistory> operationReport(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                                @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return historyRepository.findByTimeBetween(from, to);
    }


}
