package bmt.com.api.controller;

import bmt.com.api.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("scheduler")
public class SchedulerController {

    @Autowired
    private SchedulerRepository repository;

    @PostMapping("/start")
    @Transactional
    public void extract(@RequestBody FirstAccess url) throws Exception {
        List<SchedulerDetail> schedulerDetails = new SealandBot().extractSchedules(url.url());
        new SealandBot().parserInfos(schedulerDetails);
        for(SchedulerDetail schedulerDetail : schedulerDetails){
            repository.save(schedulerDetail);
        }
    }

    @GetMapping("/search")
    public Page<SchedulerDetail> list(@RequestParam(required = false) String param, @PageableDefault(size = 10) Pageable pages){
        return repository.findByGenericParam(param, pages);
    }
}
