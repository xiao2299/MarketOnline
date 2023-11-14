package com.example.library.APIModel;

import java.io.Serializable;
import java.util.UUID;

public class GetProductResponseItemAPIModel implements Serializable {
    private UUID id;
    private String name;
    private String ulr;
    private String groupName;

    public UUID getID() { return id; }
    public void setID(UUID value) { this.id = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getulr() { return ulr; }
    public void setulr(String value) { this.ulr = value; }

    public String getgroupName() { return groupName; }
    public void setgroupName(String value) { this.groupName = value; }
}
