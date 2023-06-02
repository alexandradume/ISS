package model;

import java.io.Serializable;

public interface Enity<ID> extends Serializable {

    public ID getId();
    public void setId(ID id);
}
