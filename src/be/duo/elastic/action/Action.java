package be.duo.elastic.action;


public class Action {

	public enum ActionType {
		EMAIL, SLACK, WEBHOOK, ELK;
	}
	
	ActionType type;
	String from, to;
	String bcc, cc;
	String subject, body;
	
	public Action type(ActionType type) {
		this.type = type;
		return this;
	}
	public Action from(String from) {
		this.from = from;
		return this;
	}
	public Action to(String to) {
		this.to = to;
		return this;
	}
	public Action bcc(String bcc) {
		this.bcc = bcc;
		return this;
	}
	public Action cc(String cc) {
		this.cc = cc;
		return this;
	}
	public Action subject(String subject) {
		this.subject = subject;
		return this;
	}
	public Action body(String body) {
		this.body = body;
		return this;
	}
	
	
	public ActionType getType() {
		return type;
	}
	public String getFrom() {
		return from;
	}
	public String getTo() {
		return to;
	}
	public String getBcc() {
		return bcc;
	}
	public String getCc() {
		return cc;
	}
	public String getSubject() {
		return subject;
	}
	public String getBody() {
		return body;
	}
	
	
		
}
