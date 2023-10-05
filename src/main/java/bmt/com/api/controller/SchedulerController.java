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
    private ShipRepository repository;

    @PostMapping("/start")
    @Transactional
    public void extract(@RequestBody FirstAccess url) throws Exception {
        List<VoyageDetail> voyageDetails = new SealandBot().extractSchedules(url.url());
        new SealandBot().parserInfos(voyageDetails);
        for(VoyageDetail voyageDetail : voyageDetails){
            repository.save(voyageDetail);
        }
    }

    @GetMapping("/search")
    public Page<VoyageDetail> list(@RequestParam(required = false) String param, @PageableDefault(size = 10) Pageable pages){
        return repository.findByGenericParam(param, pages);
    }
}
