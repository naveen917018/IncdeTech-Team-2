package com.incede.Model.recoveryParameter;

public enum RecoveryParameter {
    REMINDER_DAYS("Reminder Days", "integer"),
    WRITE_OFF_THRESHOLD("Write-off Threshold", "number"),
    RECOVERY_ESCALATION_RULE("Recovery Escalation Rule", "object");

    private final String name;
    private final String validationType;

    RecoveryParameter(String name, String validationType) {
        this.name = name;
        this.validationType = validationType;
    }

    public String getName() {
        return name;
    }

    public String getValidationType() {
        return validationType;
    }

    public static RecoveryParameter fromName(String name) {
        for (RecoveryParameter p : values()) {
        	System.out.println("a1");
        	System.out.println(p);
            if (p.name.equalsIgnoreCase(name)) {
            	System.out.println("a2");
                return p;
            }
        }
        throw new IllegalArgumentException("Unknown parameter name: " + name);
    }
}

