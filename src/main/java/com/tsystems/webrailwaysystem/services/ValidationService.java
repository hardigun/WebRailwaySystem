package com.tsystems.webrailwaysystem.services;

import com.tsystems.webrailwaysystem.entities.RouteEntity;
import com.tsystems.webrailwaysystem.entities.SheduleItemEntity;
import com.tsystems.webrailwaysystem.entities.StationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

/**
 * Date: 12.10.13
 */
@Service("validationService")
public class ValidationService {

    @Autowired
    private Validator validator;

    public String validateRoute(RouteEntity route, Model uiModel) {
        StringBuffer errorMessageBuffer = new StringBuffer();
        Set<ConstraintViolation<RouteEntity>> violations = validator.validate(route);
        if(violations.size() > 0) {
            for(ConstraintViolation<RouteEntity> violation : violations) {
                if(violation.getPropertyPath().toString().contains("routeNumber")) {
                    if(uiModel != null) {
                        uiModel.addAttribute("routeNumberErrors", violation.getMessage());
                    }
                    errorMessageBuffer.append(violation.getMessage()).append("<br/>");
                }
                if(violation.getPropertyPath().toString().contains("stationsList")) {
                    if(uiModel != null) {
                        uiModel.addAttribute("routeStationsErrors", violation.getMessage());
                    }
                    errorMessageBuffer.append(violation.getMessage()).append("<br/>");
                }
            }
        }
        return errorMessageBuffer.toString();
    }

    public String validateStation(StationEntity station) {
        StringBuffer errorMessageBuffer = new StringBuffer();
        Set<ConstraintViolation<StationEntity>> violations = validator.validate(station);
        if(violations.size() > 0) {
            for(ConstraintViolation<StationEntity> violation : violations) {
                if(violation.getPropertyPath().toString().contains("timeOffset")) {
                    errorMessageBuffer.append(violation.getMessage());
                }
            }

        }
        return errorMessageBuffer.toString();
    }

}
