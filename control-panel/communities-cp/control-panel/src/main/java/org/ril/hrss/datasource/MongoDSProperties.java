package org.ril.hrss.datasource;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "mongodb.primary")
public class MongoDSProperties {

    private MongoProperties primary = new MongoProperties();

    public MongoDSProperties(MongoProperties primary) {
        this.primary = primary;
    }

    public MongoProperties getPrimary() {
        return primary;
    }

    public void setPrimary(MongoProperties primary) {
        this.primary = primary;
    }
}
