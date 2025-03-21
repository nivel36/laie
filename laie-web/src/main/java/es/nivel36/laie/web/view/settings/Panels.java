package es.nivel36.laie.web.view.settings;

import java.util.Arrays;
import java.util.List;

import es.nivel36.laie.ejb.app.StatisticPanelType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class Panels {

    public List<StatisticPanelType> getList() {
        return Arrays.asList(StatisticPanelType.values());
    }
}
