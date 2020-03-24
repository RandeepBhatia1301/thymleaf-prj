package org.ril.hrss.model.category_hierarchy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class GraphJson {
    String _id;
    List<LinkedHashMap> result = new ArrayList<>();

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<LinkedHashMap> getResult() {
        return result;
    }

    public void setResult(List<LinkedHashMap> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "GraphJson{" +
                "_id='" + _id + '\'' +
                ", result=" + result +
                '}';
    }
}
