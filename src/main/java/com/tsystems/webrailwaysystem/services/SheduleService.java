package com.tsystems.webrailwaysystem.services;

import com.tsystems.webrailwaysystem.dao.SheduleDAO;
import com.tsystems.webrailwaysystem.entities.RouteEntity;
import com.tsystems.webrailwaysystem.entities.SheduleItemEntity;
import com.tsystems.webrailwaysystem.entities.StationEntity;
import com.tsystems.webrailwaysystem.entities.StationInfoEntity;
import com.tsystems.webrailwaysystem.exceptions.ExpiredTimeToBuyException;
import com.tsystems.webrailwaysystem.exceptions.NoAvailableSeatsException;
import com.tsystems.webrailwaysystem.exceptions.RailwaySystemException;
import com.tsystems.webrailwaysystem.filters.SheduleFilter;
import com.tsystems.webrailwaysystem.filters.StationsFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Contract: service execute operations with SheduleItems
 *
 * Date: 21.09.13
 */
@Service("sheduleService")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRES_NEW)
public class SheduleService {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    @Autowired
    private SheduleDAO sheduleDAO;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void addSheduleItem(SheduleItemEntity sheduleItem) throws RailwaySystemException {
        try {
            this.sheduleDAO.save(sheduleItem);
        } catch(Exception exc) {
            exc.printStackTrace();
            LOGGER.warn(exc);
            throw new RailwaySystemException();
        }
    }

    @Transactional(readOnly = true)
    public SheduleItemEntity getSheduleItem(int id) {
        return (SheduleItemEntity) this.sheduleDAO.getById(SheduleItemEntity.class, id);
    }

    @Transactional(readOnly = true)
    public List<SheduleItemEntity> getAllSheduleItems() {
        return this.sheduleDAO.getAll();
    }

    @Transactional(readOnly = true)
    public Map<RouteEntity, List<SheduleItemEntity>> getAllSheduleItemsGroupByRoutes() {
        Map<RouteEntity, List<SheduleItemEntity>> sheduleItemsByRoutesMap =
                new HashMap<RouteEntity, List<SheduleItemEntity>>();

        for(SheduleItemEntity sheduleItem : this.sheduleDAO.getAll()) {
            if(!sheduleItemsByRoutesMap.containsKey(sheduleItem.getRoute())) {
                List<SheduleItemEntity> sheduleItemsList = new ArrayList<SheduleItemEntity>();
                sheduleItemsList.add(sheduleItem);
                sheduleItemsByRoutesMap.put(sheduleItem.getRoute(), sheduleItemsList);
            } else {
                List<SheduleItemEntity> sheduleItemsList = sheduleItemsByRoutesMap.get(sheduleItem.getRoute());
                sheduleItemsList.add(sheduleItem);
            }
        }

        return sheduleItemsByRoutesMap;
    }

    /**
     * Contract: get some SheduleItems with associated list of tickets by routeId
     *
     * @param routeId for which SheduleItems info need
     * @return
     */
    @Transactional(readOnly = true)
    public List<SheduleItemEntity> getSheduleItemsByRouteId(int routeId) {
        return this.sheduleDAO.getByRouteId(routeId);
    }

    /**
     * Contract: get routes from shedule with StationInfo from filter; if in the filter exist date
     * then compare it with the date when train will be on the station for this route
     *
     * @param filter contains StationInfo fow which need SheduleItems info and date when
     *               train need on the station
     * @return SheduleItems that satisfy to conditions
     */
    @Transactional(readOnly = true)
    public List<SheduleItemEntity> searchByStation(SheduleFilter filter) {
        List<SheduleItemEntity> sheduleItemsList = new ArrayList<SheduleItemEntity>();

        List<StationInfoEntity> stationInfoList = new ArrayList<StationInfoEntity>();
        stationInfoList.add(filter.getStationInfo());
        for(SheduleItemEntity sheduleItem : this.sheduleDAO.getByStations(stationInfoList)) {

            StationEntity station = null;
            for(StationEntity stationInRoute : sheduleItem.getRoute().getStationsList()) {
                if(stationInRoute.getStationInfo().equals(filter.getStationInfo())) {
                    station = stationInRoute;
                }
            }

            if(station != null) {
                GregorianCalendar dateCondition = null;
                if(filter.getDate() != null) {
                    dateCondition= new GregorianCalendar();
                    dateCondition.setTime(filter.getDate());
                    dateCondition = new GregorianCalendar(
                            dateCondition.get(Calendar.YEAR),
                            dateCondition.get(Calendar.MONTH),
                            dateCondition.get(Calendar.DAY_OF_MONTH)
                    );
                }

                GregorianCalendar sheduleItemDate = null;
                if(dateCondition != null) {
                    sheduleItemDate= new GregorianCalendar();
                    sheduleItemDate.setTime(sheduleItem.getDepartureDate());
                    sheduleItemDate.add(Calendar.MINUTE, station.getTimeOffset());
                    sheduleItemDate = new GregorianCalendar(
                            sheduleItemDate.get(Calendar.YEAR),
                            sheduleItemDate.get(Calendar.MONTH),
                            sheduleItemDate.get(Calendar.DAY_OF_MONTH)
                    );
                }


                if((dateCondition != null && sheduleItemDate != null && dateCondition.equals(sheduleItemDate))
                        || dateCondition == null) {
                    try{
                        SheduleItemEntity cloneSheduleItem  = (SheduleItemEntity) sheduleItem.clone();
                        cloneSheduleItem.getRoute().getStationsList().clear();
                        cloneSheduleItem.getRoute().getStationsList().add(station);
                        sheduleItemsList.add(cloneSheduleItem);
                    } catch(CloneNotSupportedException exc) {
                        exc.printStackTrace();
                        LOGGER.warn(exc);

                    }
                }
            }
        }

        return sheduleItemsList;
    }

    /**
     * Contract: get routes from shedule with StationInfos from filter; check that arrival station
     * in selected route is after departure station; if exist start or end of date range in the filter
     * then check date conditions
     *
     * @param filter contains two StationInfo and range of dates between arrival and departure
     * @return SheduleItems that satisfy the conditions
     */
    @Transactional(readOnly = true)
    public List<SheduleItemEntity> searchBetweenStations(StationsFilter filter) {
        List<SheduleItemEntity> sheduleItemsList = new ArrayList<SheduleItemEntity>();

        List<StationInfoEntity> stationInfoList = new ArrayList<StationInfoEntity>();
        stationInfoList.add(filter.getStationInfoFrom());
        stationInfoList.add(filter.getStationInfoTo());
        for(SheduleItemEntity sheduleItem : this.sheduleDAO.getByStations(stationInfoList)) {
            StationEntity stationFrom = null;
            StationEntity stationTo = null;
            for(StationEntity station : sheduleItem.getRoute().getStationsList()) {
                if(station.getStationInfo().equals(filter.getStationInfoFrom())) {
                    stationFrom = station;
                }
                if(station.getStationInfo().equals(filter.getStationInfoTo())) {
                    stationTo = station;
                }
            }

            if((stationTo != null) && (stationFrom != null) && (stationTo.getTimeOffset() > stationFrom.getTimeOffset())) {
                GregorianCalendar depDateStationFrom = new GregorianCalendar();
                depDateStationFrom.setTime(sheduleItem.getDepartureDate());
                depDateStationFrom.add(Calendar.MINUTE, stationFrom.getTimeOffset());

                GregorianCalendar depDateStationTo = new GregorianCalendar();
                depDateStationTo.setTime(sheduleItem.getDepartureDate());
                depDateStationTo.add(Calendar.MINUTE, stationTo.getTimeOffset());

                GregorianCalendar fromDate = null;
                boolean isAfterDepDateFrom = false;
                if(filter.getStartDate() != null) {
                    fromDate = new GregorianCalendar();
                    fromDate.setTime(filter.getStartDate());
                    isAfterDepDateFrom = depDateStationFrom.after(fromDate);
                }

                GregorianCalendar toDate = null;
                boolean isBeforeDepDateTo = false;
                if(filter.getEndDate() != null) {
                    toDate = new GregorianCalendar();
                    toDate.setTime(filter.getEndDate());
                    isBeforeDepDateTo = depDateStationTo.before(toDate);
                }

                boolean needAddSheduleItem = false;
                if(fromDate != null && toDate != null && isAfterDepDateFrom && isBeforeDepDateTo) {
                    needAddSheduleItem = true;
                }
                else if(fromDate != null && toDate == null && isAfterDepDateFrom) {
                    needAddSheduleItem = true;
                }
                else if(fromDate == null && toDate != null && isBeforeDepDateTo) {
                    needAddSheduleItem = true;
                }
                else if(fromDate == null && toDate == null) {
                    needAddSheduleItem = true;
                }

                if(needAddSheduleItem) {
                    try{
                        SheduleItemEntity cloneSheduleItem  = (SheduleItemEntity) sheduleItem.clone();
                        cloneSheduleItem.getRoute().getStationsList().clear();
                        cloneSheduleItem.getRoute().getStationsList().add(stationFrom);
                        cloneSheduleItem.getRoute().getStationsList().add(stationTo);
                        sheduleItemsList.add(cloneSheduleItem);
                    } catch(CloneNotSupportedException exc) {
                        exc.printStackTrace();
                        LOGGER.warn(exc);
                    }
                }
            }
        }

        return sheduleItemsList;
    }

    /**
     * Contract: get SheduleItems that ID equals to inSheduleItem.getId();
     *           compare if the train capacity more then current tickets count on this SheduleItem;
     *           compare if the date of departure more then current date for 10 minutes
     *
     * @param inSheduleItem SheduleItem for which need checks
     * @return SheduleItem selected from database or throws exceptions
     * @throws NoAvailableSeatsException
     * @throws ExpiredTimeToBuyException
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public SheduleItemEntity checkTrainSeatsAndDate(SheduleItemEntity inSheduleItem)
            throws NoAvailableSeatsException, ExpiredTimeToBuyException {

        SheduleItemEntity sheduleItem =
                (SheduleItemEntity) this.sheduleDAO.getById(SheduleItemEntity.class, inSheduleItem.getId());
        /*check available seats*/
        List<SheduleItemEntity> sheduleItemsList = new ArrayList<SheduleItemEntity>();
        sheduleItemsList.add(sheduleItem);
        sheduleItemsList = this.sheduleDAO.getBySheduleItems(sheduleItemsList);
        if(sheduleItem.getTrain().getCapacity() == sheduleItemsList.get(0).getTicketsList().size()) {
            throw new NoAvailableSeatsException();
        }

        /*check that before departure left 10 minutes*/
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.setTime(new Date());

        GregorianCalendar departureDateMinus10 = new GregorianCalendar();
        departureDateMinus10.setTime(sheduleItem.getDepartureDate());
        departureDateMinus10.add(GregorianCalendar.MINUTE, -10);

        if(currentDate.after(departureDateMinus10)) {
            throw new ExpiredTimeToBuyException();
        }

        return sheduleItem;
    }

}
