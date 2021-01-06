package edu.northeastern.cs5500.delivery.controller;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

/**
 * status controller accepts input and converts it to commands for the model or view
 *
 * @author: Taowen Liu, Qianlin Ma, Guangpeng Wu
 */
@Singleton
@Slf4j
public class StatusController {

    /** constructor */
    @Inject
    StatusController() {
        log.info("StatusController > register");
    }

    /** get status */
    @Nonnull
    public String getStatus() {
        return "OK";
    }
}
