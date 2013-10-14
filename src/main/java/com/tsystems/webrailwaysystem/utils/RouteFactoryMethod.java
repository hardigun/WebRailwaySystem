package com.tsystems.webrailwaysystem.utils;

import com.tsystems.webrailwaysystem.entities.RouteEntity;
import com.tsystems.webrailwaysystem.entities.SheduleItemEntity;
import com.tsystems.webrailwaysystem.entities.StationEntity;
import com.tsystems.webrailwaysystem.entities.StationInfoEntity;
import com.tsystems.webrailwaysystem.services.RouteService;
import com.tsystems.webrailwaysystem.services.StationInfoService;
import com.tsystems.webrailwaysystem.services.TrainService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Map;

/**
 * Date: 12.10.13
 */
public class RouteFactoryMethod {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    /**
     * Contract: using request arrays with ids for return route with stations list and StationInfoEntity
     *
     * @param route contains routeNumber
     * @param stationsInfoArray contains StationInfo titles
     * @param timeoffsetArray contains timeOffsets for StationEntity
     * @return
     */
    public static RouteEntity getRouteWithStationsFromIdArrays(
            RouteEntity route, String[] stationsInfoArray, String[] timeoffsetArray, StationInfoService stationInfoService
    ) {
        RouteEntity resultRoute = new RouteEntity();
        resultRoute.setRouteNumber(route.getRouteNumber());

        if(stationsInfoArray == null || timeoffsetArray == null
                || stationsInfoArray.length != timeoffsetArray.length) {
            return resultRoute;
        }
        /*get full StationInfoEntity for ids from selects*/
        Map<Integer, StationInfoEntity> stationInfoMap =
                stationInfoService.getAllStationInfoByParams(Arrays.asList(stationsInfoArray));

        for(int i = 0; i < stationsInfoArray.length; i++) {
            StationEntity station = new StationEntity();
            StationInfoEntity stationInfo = new StationInfoEntity();

            try{
                stationInfo= stationInfoMap.get(Integer.parseInt(stationsInfoArray[i]));
                station.setTimeOffset(Integer.parseInt(timeoffsetArray[i]));
            } catch(NumberFormatException exc) {
                /*will be cut off with the model constraints*/
                station.setTimeOffset(-1);
                exc.printStackTrace();
                LOGGER.debug("Errors in the adding RouteEntity in the stations list");
            }

            station.setStationInfo(stationInfo);
            resultRoute.getStationsList().add(station);
        }

        return resultRoute;
    }

}
