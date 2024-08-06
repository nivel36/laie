package es.nivel36.laie.ejb.job.candidature;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class StateEventListener {

    public void onBeforeTransition(@Observes StateEvent event) {
        if ("BEFORE_TRANSITION".equals(event.getEventType())) {
            System.out.println("Event fired before transition: State ID " + event.getState());
        }
    }

    public void onAfterTransition(@Observes StateEvent event) {
        if ("AFTER_TRANSITION".equals(event.getEventType())) {
            System.out.println("Event fired after transition: State ID " + event.getState());
        }
    }
}