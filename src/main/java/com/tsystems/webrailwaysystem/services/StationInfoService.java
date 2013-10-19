package com.tsystems.webrailwaysystem.services;

import com.tsystems.webrailwaysystem.dao.StationInfoDAO;
import com.tsystems.webrailwaysystem.entities.StationInfoEntity;
import com.tsystems.webrailwaysystem.entities.TrainEntity;
import com.tsystems.webrailwaysystem.exceptions.RailwaySystemException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contract: execute operations with StationInfoEntity
 *
 * Date: 21.09.13
 */
@Service("stationInfoService")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRES_NEW)
public class StationInfoService {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    @Autowired
    private StationInfoDAO stationInfoDAO;

    /**
     * Contract: add StationInfo to the database
     *
     * @param stationInfo that will be add
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void addStationInfo(final StationInfoEntity stationInfo) throws RailwaySystemException {
        try {
            stationInfoDAO.save(stationInfo);

        } catch(Exception exc) {
            exc.printStackTrace();
            LOGGER.warn(exc);
            throw new RailwaySystemException();
        }
    }

    @Transactional(readOnly = true)
    public StationInfoEntity getStationInfo(int id) {
        return this.stationInfoDAO.getById(StationInfoEntity.class, id);
    }

    /**
     * Contract: return all StationInfoEntity from database
     *
      * @return
     */
    @Transactional(readOnly = true)
    public List<StationInfoEntity> getAllStationInfo() {
        return this.stationInfoDAO.getAll();
    }

    @Transactional(readOnly = true)
    public Map<Integer, StationInfoEntity> getAllStationInfoByParams(List<String> stationInfoList) {
        List<Integer> stationInfoIdList = new ArrayList<Integer>();
        for(String stationInfo : stationInfoList) {
            int stationInfoId;
            try {
                stationInfoId = Integer.parseInt(stationInfo);
                stationInfoIdList.add(stationInfoId);
            } catch(Exception exc) {
                exc.printStackTrace();
                LOGGER.warn(exc);
            }
        }
        Map<Integer, StationInfoEntity> resultMap = new HashMap<Integer, StationInfoEntity>();
        for(StationInfoEntity stationInfo : this.stationInfoDAO.getAllById(stationInfoIdList)) {
            resultMap.put(stationInfo.getId(), stationInfo);
        }
        return resultMap;
    }

}
