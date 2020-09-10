package Model;

import java.io.Serializable;

public class Message implements Serializable {
    //Data fields: includes sender, data, option.
    private String sender;
    private String typeOfAction;
    private Object information;

    public Message() {

    }

    public Message(String sender, String typeOfAction, Object information) {
        this.sender = sender;
        this.typeOfAction = typeOfAction;
        this.information = information;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTypeOfAction() {
        return typeOfAction;
    }

    public void setTypeOfAction(String typeOfAction) {
        this.typeOfAction = typeOfAction;
    }

    public Object getInformation() {
        return information;
    }

    public void setInformation(Object information) {
        this.information = information;
    }
}