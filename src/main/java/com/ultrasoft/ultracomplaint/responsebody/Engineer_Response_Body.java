package com.ultrasoft.ultracomplaint.responsebody;

import com.ultrasoft.ultracomplaint.enums.ActiveStaus;

public class Engineer_Response_Body {

    private String engineerId;

    private String engineerEmailId;

    private String engineerMobile;

    private String engineerName;

    private String engineerWorkDiscription;

    private ActiveStaus activeStatus;

    public String getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(String engineerId) {
        this.engineerId = engineerId;
    }

    public String getEngineerEmailId() {
        return engineerEmailId;
    }

    public void setEngineerEmailId(String engineerEmailId) {
        this.engineerEmailId = engineerEmailId;
    }

    public String getEngineerMobile() {
        return engineerMobile;
    }

    public void setEngineerMobile(String engineerMobile) {
        this.engineerMobile = engineerMobile;
    }

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }

    public String getEngineerWorkDiscription() {
        return engineerWorkDiscription;
    }

    public void setEngineerWorkDiscription(String engineerWorkDiscription) {
        this.engineerWorkDiscription = engineerWorkDiscription;
    }

    public ActiveStaus getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(ActiveStaus activeStatus) {
        this.activeStatus = activeStatus;
    }
}
