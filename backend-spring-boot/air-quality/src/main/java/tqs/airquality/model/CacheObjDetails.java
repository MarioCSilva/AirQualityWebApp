package tqs.airquality.model;

public class CacheObjDetails {
    Boolean found;
    Object returnValue;

    public CacheObjDetails(boolean found, Object returnValue) {
        this.found = found;
        this.returnValue = returnValue;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }
}