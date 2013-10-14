package com.tsystems.webrailwaysystem.converters;

import com.tsystems.webrailwaysystem.entities.StationInfoEntity;
import com.tsystems.webrailwaysystem.services.StationInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;


/**
 * Date: 13.10.13
 */
public class StringIdToStationInfoConverter implements Converter<String, StationInfoEntity> {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    @Autowired
    private StationInfoService stationInfoService;

    @Override
    public StationInfoEntity convert(String id) {
        StationInfoEntity stationInfoEntity = new StationInfoEntity();
        try {
            stationInfoEntity = this.stationInfoService.getStationInfo(Integer.parseInt(id));
        } catch(Exception exc) {
            exc.printStackTrace();
            LOGGER.warn(exc);
        }
        return stationInfoEntity;
    }

}
