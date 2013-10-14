package com.tsystems.webrailwaysystem.services;

import com.tsystems.webrailwaysystem.dao.TrainDAO;
import com.tsystems.webrailwaysystem.entities.TrainEntity;
import com.tsystems.webrailwaysystem.exceptions.RailwaySystemException;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Contract: service make operations with TrainEntity
 *
 * Date: 21.09.13
 */
@Service("trainService")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRES_NEW)
public class TrainService {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    @Autowired
    private TrainDAO trainDAO;

    /**
     * Contract: add train to the database
     *
     * @param train that need to add to the database
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void addTrain(final TrainEntity train) throws RailwaySystemException {
        try {
            this.trainDAO.save(train);

        } catch(Exception exc) {
            exc.printStackTrace();
            LOGGER.warn(exc);
            throw new RailwaySystemException();
        }
    }

    @Transactional
    public TrainEntity getTrain(int id) {
        return (TrainEntity) this.trainDAO.getById(TrainEntity.class, id);
    }

    /**
     * Contract: return all train from the database
     *
      * @return list of TrainEntity
     */
    @Transactional(readOnly = true)
    public List<TrainEntity> getAllTrains() {
        return trainDAO.getAll();
    }

    /**
     * Contract: compare all trains from database and trains that include SheduleItems
     *
     * @return return list of TrainEntity that not include any SheduleItems
     */
    @Transactional(readOnly = true)
    public List<TrainEntity> getUnusedTrains() {
        List<TrainEntity> returnTrainsList = new ArrayList<TrainEntity>();
        List<Integer> usedTrainsIdList = this.trainDAO.getAllUsedTrainsId();
        List<TrainEntity> allTrainsList = this.trainDAO.getAll();
        for(TrainEntity train : allTrainsList) {
            if(!usedTrainsIdList.contains(train.getId())) {
                returnTrainsList.add(train);
            }
        }
        return returnTrainsList;
    }

}
