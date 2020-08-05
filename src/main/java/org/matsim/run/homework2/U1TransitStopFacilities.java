package org.matsim.run.homework2;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.pt.transitSchedule.api.TransitStopArea;
import org.matsim.pt.transitSchedule.api.TransitStopFacility;
import org.matsim.utils.objectattributes.attributable.Attributes;

import java.util.Map;

public class U1TransitStopFacilities implements TransitStopFacility {
    public Id<TransitStopFacility> idStopFacility;
    public String name;
    public Id<Link> id;
    public Coord coordinate;
    public boolean isBlockingLane;

    public U1TransitStopFacilities(Id<TransitStopFacility> idStopFacility,String name, Id<Link> id, Coord coordinate) {
        this.idStopFacility = idStopFacility;
        this.name = name;
        this.id = id;
        this.coordinate = coordinate;
        this.isBlockingLane = false;
    }

    @Override
    public boolean getIsBlockingLane() {
        return false;
    }

    @Override
    public void setLinkId(Id<Link> id) {
        this.id = id;
    }

    @Override
    public void setName(String s) {
        this.name = s;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Id<TransitStopArea> getStopAreaId() {
        return null;
    }

    @Override
    public void setStopAreaId(Id<TransitStopArea> id) {

    }

    @Override
    public void setCoord(Coord coord) {
        this.coordinate = coord;
    }

    @Override
    public Id<TransitStopFacility> getId() {
        return this.idStopFacility;
    }

    @Override
    public Id<Link> getLinkId() {
        return this.id;
    }

    @Override
    public Coord getCoord() {
        return this.coordinate;
    }

    @Override
    public Map<String, Object> getCustomAttributes() {
        return null;
    }

    @Override
    public Attributes getAttributes() {
        return null;
    }
}
