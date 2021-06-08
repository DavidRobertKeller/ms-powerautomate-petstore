package drkeller.petstore.dto;

import org.json.JSONObject;

public class PetWebhookResponse {

	String petId;
	String eventName;
	String eventValue;
	
	public String getPetId() {
		return petId;
	}
	
	public void setPetId(String petId) {
		this.petId = petId;
	}
	
	public String getEventName() {
		return eventName;
	}
	
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public String getEventValue() {
		return eventValue;
	}
	
	public void setEventValue(String eventValue) {
		this.eventValue = eventValue;
	}
	
	public JSONObject toJSON() {
		JSONObject item = new JSONObject();
		item.put("petId", petId);
		item.put("eventName", eventName);
		item.put("eventValue", eventValue);
		
		return item;
	}
}
