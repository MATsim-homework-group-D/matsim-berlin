package org.matsim.run.homework2;

import org.matsim.core.utils.misc.OptionalTime;
import org.matsim.pt.transitSchedule.api.TransitRouteStop;
import org.matsim.pt.transitSchedule.api.TransitStopFacility;

public class U1RouteStop implements TransitRouteStop {
    public TransitStopFacility transitStopFacility;
    public OptionalTime arrivalTime;
    public OptionalTime departureTime;
    public boolean aWaitTime;

    public U1RouteStop (TransitStopFacility transitStopFacility, OptionalTime arrivalTime, OptionalTime departureTime) {
        this.transitStopFacility = transitStopFacility;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.aWaitTime = true;
    }

    public U1RouteStop (TransitStopFacility transitStopFacility, double arrivalTimeInSeconds, double departureTimeInSeconds) {
        this.transitStopFacility = transitStopFacility;
        this.arrivalTime = OptionalTime.defined(arrivalTimeInSeconds);
        this.departureTime = OptionalTime.defined(departureTimeInSeconds);
        this.aWaitTime = true;
    }

    @Override
    public TransitStopFacility getStopFacility() {
        return this.transitStopFacility;
    }

    @Override
    public void setStopFacility(TransitStopFacility transitStopFacility) {
        this.transitStopFacility = transitStopFacility;
    }

    @Override
    public OptionalTime getDepartureOffset() {
        return this.departureTime;
    }

    @Override
    public OptionalTime getArrivalOffset() {
        return this.arrivalTime;
    }

    @Override
    public void setAwaitDepartureTime(boolean b) {
        this.aWaitTime = b;
    }

    @Override
    public boolean isAwaitDepartureTime() {
        return this.aWaitTime;
    }
}
