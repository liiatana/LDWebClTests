package ru.lanit.ld.wc.model;

public class InstructionType {

    private int id;
    private String name;
    private int operationID;
    private String operation;
    private int interval;
    private String reportFlag;
    private boolean redirectAsControl;
    private boolean useControl;
    private boolean active;
    private int checkReportTypeNegative;
    private int checkReportTypePositive;
    private String templateText;

    public int[] receiverTypes;

    public InstructionType() {
    }

    public InstructionType(InstructionType type) {
        this.id = type.id;
        this.name = type.name;
        this.operationID = type.operationID;
        this.operation = type.operation;
        this.interval = type.interval;
        this.reportFlag = type.reportFlag;
        this.redirectAsControl = type.redirectAsControl;
        this.useControl = type.useControl;
        this.active = type.active;
        this.checkReportTypeNegative = type.checkReportTypeNegative;
        this.checkReportTypePositive = type.checkReportTypePositive;
        this.receiverTypes = type.receiverTypes;
        this.templateText=type.templateText;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOperationID() {
        return operationID;
    }

        public void setOperationID(int operationID) {
        this.operationID = operationID;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(String reportFlag) {
        this.reportFlag = reportFlag;
    }

    public boolean isRedirectAsControl() {
        return redirectAsControl;
    }

    public void setRedirectAsControl(boolean redirectAsControl) {
        this.redirectAsControl = redirectAsControl;
    }

    public boolean getUseControl() {
        return useControl;
    }

    public void setUseControl(boolean useControl) {
        this.useControl = useControl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getCheckReportTypeNegative() {
        return checkReportTypeNegative;
    }

    public void setCheckReportTypeNegative(int checkReportTypeNegative) {
        this.checkReportTypeNegative = checkReportTypeNegative;
    }

    public int getCheckReportTypePositive() {
        return checkReportTypePositive;
    }

    public void setCheckReportTypePositive(int checkReportTypePositive) {
        this.checkReportTypePositive = checkReportTypePositive;
    }

    public int[] getReceiverTypes() {
        return receiverTypes;
    }

    public void setReceiverTypes(int[] receiverTypes) {
        this.receiverTypes = receiverTypes;
    }

    public String getTemplateText() {
        return templateText;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }

    @Override
    public String toString() {
        return "InstructionType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public String getTemplateTextE() {
        if(this.getTemplateText()==null)return "";
        return templateText;
    }



}
