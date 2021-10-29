package com.reddate.did.sdk.protocol.response;

import java.io.Serializable;

/**
 * Base credential data structure
 * 
 *
 */
public class BaseCredentialData implements Serializable {

    /**
     * Document No
     */
    private String id;

    /**
     * Creation time
     */
    private String created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
