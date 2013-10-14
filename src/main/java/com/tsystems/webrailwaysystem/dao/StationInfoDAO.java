package com.tsystems.webrailwaysystem.dao;

import com.tsystems.webrailwaysystem.entities.StationInfoEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Contract: DAO execute operation with database for StationInfoEntity
 *
 * Date: 17.09.13
 */
@Repository("stationInfoDAO")
public class StationInfoDAO extends AbstractDAO {

    public List<StationInfoEntity> getAll() {
        String queryStr = "FROM StationInfoEntity ORDER BY title ASC";
        return this.getSessionFactory().getCurrentSession().createQuery(queryStr).list();
    }

    public List<StationInfoEntity> getAllById(List<Integer> stationInfoIdList) {
        StringBuffer queryBuf = new StringBuffer("FROM StationInfoEntity ");
        for(int i = 0; i < stationInfoIdList.toArray().length; i++) {
            if(i == 0) {
                queryBuf.append(" WHERE id = :id_").append(i);
            } else {
                queryBuf.append(" OR id = :id_").append(i);
            }
        }
        Query query = this.getSessionFactory().getCurrentSession().createQuery(queryBuf.toString());
        for(int i = 0; i < stationInfoIdList.toArray().length; i++) {
            query.setParameter("id_" + i, stationInfoIdList.get(i));
        }
        return query.list();
    }

}
