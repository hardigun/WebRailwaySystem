package com.tsystems.webrailwaysystem.converters;

import com.tsystems.webrailwaysystem.entities.RouteEntity;
import com.tsystems.webrailwaysystem.entities.TrainEntity;
import com.tsystems.webrailwaysystem.services.RouteService;
import com.tsystems.webrailwaysystem.services.TrainService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 * Date: 13.10.13
 */
public class StringIdToTrainConverter implements Converter<String, TrainEntity> {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    @Autowired
    private TrainService trainService;

    @Override
    public TrainEntity convert(String id) {
        TrainEntity train = new TrainEntity();
        try {
            train = this.trainService.getTrain(Integer.parseInt(id));
        } catch(Exception exc) {
            exc.printStackTrace();
            LOGGER.warn(exc);
        }
        return train;
    }

}
