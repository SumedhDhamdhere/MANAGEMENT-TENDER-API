package com.fresco.tenderManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fresco.tenderManagement.model.BiddingModel;
import com.fresco.tenderManagement.service.BiddingService;

@RestController
@RequestMapping("/bidding")
public class BiddingController {

    private static final Logger logger = LoggerFactory.getLogger(BiddingController.class);

    @Autowired
    private BiddingService biddingService;

    @PostMapping("/add")
    public ResponseEntity<Object> postBidding(@RequestBody BiddingModel biddingModel) {
        logger.info("Method [postBidding] started in BiddingController class.".toUpperCase());
        ResponseEntity<Object> response = biddingService.postBidding(biddingModel);
        return response;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getBidding(@RequestParam double bidAmount) {
        logger.info("Method [getBidding] started in BiddingController class.".toUpperCase());
        ResponseEntity<Object> response = biddingService.getBidding(bidAmount);
        return response;
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> updateBidding(@PathVariable int id, @RequestBody BiddingModel biddingModel) {
        logger.info("Method [updateBidding] started in BiddingController class for id: ".toUpperCase() + id + "".toUpperCase());
        ResponseEntity<Object> response = biddingService.updateBidding(id, biddingModel);
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteBidding(@PathVariable int id) {
        logger.info("Method [deleteBidding] started in BiddingController class for id: ".toUpperCase() + id + "".toUpperCase());
        ResponseEntity<Object> response = biddingService.deleteBidding(id);
        return response;
    }
}
