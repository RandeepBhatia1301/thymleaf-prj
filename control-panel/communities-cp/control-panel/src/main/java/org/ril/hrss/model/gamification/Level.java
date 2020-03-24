package org.ril.hrss.model.gamification;

import javax.persistence.Embeddable;
import java.util.Arrays;

@Embeddable
public class Level {

    private String name;
    private String[] counter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getCounter() {
        return counter;
    }

    public void setCounter(String[] counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "Level{" +
                "name='" + name + '\'' +
                ", counter=" + Arrays.toString(counter) +
                '}';
    }
}
