package drkeller.petstore.model;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "apikey_auth")
public class DbApiKeyAuth {
	private String id;
	private String apiKey;
	private String name;
	private String tenantId;
	private String scopes;

	@Id @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "api_key", nullable = false)
	public String getApiKey() {
		return apiKey;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "tenant_id", nullable = false)
	public String getTenantId() {
		return tenantId;
	}
	
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
		
	@Column(name = "scopes", nullable = false)
	public String getScopes() {
		return scopes;
	}
	
	public void setScopes(String scopes) {
		this.scopes = scopes;
	}

	public List<String> decodeScopes() {
		return Arrays.asList(this.scopes.split(","));
	}

	public void encodeScopes(List<String> scopes) {
		this.scopes = String.join(",", scopes);
	}
	
	@Override
	public String toString() {
		return "id=" + this.id 
				+ ", name=" + this.name
				+ ", apiKey=" + this.apiKey
				+ ", tenantId=" + this.tenantId
				+ ", scopes=" + this.scopes;
	}
	
}
