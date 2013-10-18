package com.tsystems.webrailwaysystem.dao;

import com.tsystems.webrailwaysystem.entities.SheduleItemEntity;
import com.tsystems.webrailwaysystem.entities.StationInfoEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contact: DAO execute operations with the database for SheduleItemEntity
 *
 * Date: 17.09.13
 */
@Repository("sheduleDAO")
public class SheduleDAO extends AbstractDAO {

    public List<SheduleItemEntity> getAll() {
        String query = "SELECT sheduleItem FROM SheduleItemEntity AS sheduleItem" +
                " INNER JOIN sheduleItem.route AS route" +
                " INNER JOIN sheduleItem.train AS train" +
                " ORDER BY route.routeNumber";
        return this.getSessionFactory().getCurrentSession().createQuery(query).list();
    }

    /**
     * Contract: get some SheduleItems that equals sheduleItemsList
     *
     * @param sheduleItemsList that need to get
     * @return list of SheduleItemEntity that satisfy condition
     */
    public List<SheduleItemEntity> getBySheduleItems(List<SheduleItemEntity> sheduleItemsList) {
        String queryStr = "SELECT sheduleItem FROM SheduleItemEntity AS sheduleItem" +
                " INNER JOIN sheduleItem.train AS train ";
        StringBuffer queryBuf = new StringBuffer(queryStr);
        for(int i = 0; i < sheduleItemsList.size(); i++) {
            if(i == 0) {
                queryBuf.append(" WHERE sheduleItem = :shedule_item_").append(i);
            } else {
                queryBuf.append(" OR sheduleItem = :shedule_item_").append(i);
            }
        }
        Query query = this.getSessionFactory().getCurrentSession().createQuery(queryBuf.toString());
        for(int i = 0; i < sheduleItemsList.size(); i++) {
            query.setParameter("shedule_item_" + i, sheduleItemsList.get(i));
        }
        return query.list();
    }

    /**
     * Contract: get some SheduleItems that contains StationInfo from stationInfoList;
     * can work with stationInfoList size more than 1
     *
      * @param stationInfoList SheduleItems for which need to get
     * @return satisfy SheduleItems list
     */
    public List<SheduleItemEntity> getByStations(List<StationInfoEntity> stationInfoList) {
        String queryStr = "SELECT sheduleItem FROM SheduleItemEntity AS sheduleItem"
                + " INNER JOIN sheduleItem.route AS route"
                + " INNER JOIN route.stationsList AS stations";
        StringBuffer queryBuf = new StringBuffer(queryStr);
        for(int stationInfoInd = 0; stationInfoInd < stationInfoList.size(); stationInfoInd++) {
            if(stationInfoInd == 0) {
                queryBuf.append(" WHERE");
            } else {
                queryBuf.append(" OR");
            }
            queryBuf.append(" stations.stationInfo = :station_info_").append(stationInfoInd + 1);
        }

        if(stationInfoList.size() > 1) {
            queryBuf.append(" GROUP BY sheduleItem.id");
            queryBuf.append(" HAVING COUNT(stations) = ").append(stationInfoList.size());
            queryBuf.append(" ORDER BY sheduleItem.departureDate");
        }

        Query query = this.getSessionFactory().getCurrentSession().createQuery(queryBuf.toString());
        for(int stationInfoInd = 0; stationInfoInd < stationInfoList.size(); stationInfoInd++) {
            StringBuffer paramBuf = new StringBuffer();
            paramBuf.append("station_info_").append(stationInfoInd + 1);
            query.setParameter(paramBuf.toString(), stationInfoList.get(stationInfoInd));
        }
        return query.list();
    }

    public List<SheduleItemEntity> getByRouteId(int routeId) {
        String queryStr = "SELECT sheduleItem FROM SheduleItemEntity AS sheduleItem" +
                " INNER JOIN sheduleItem.route AS route" +
                " INNER JOIN sheduleItem.train AS train" +
                " WHERE sheduleItem.route.id = :routeId" +
                " ORDER BY train.trainNumber";
        Query query = this.getSessionFactory().getCurrentSession().createQuery(queryStr);
        query.setParameter("routeId", routeId);
        return query.list();
    }

}
