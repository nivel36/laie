package es.nivel36.laie.web.view.settings;

import java.util.Arrays;
import java.util.List;

import es.nivel36.laie.ejb.app.StatisticsPeriodicity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class Periodicities {

    public List<StatisticsPeriodicity> getList() {
        return Arrays.asList(StatisticsPeriodicity.values());
    }

}
