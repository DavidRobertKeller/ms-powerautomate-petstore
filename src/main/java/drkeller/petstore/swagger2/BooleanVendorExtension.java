package drkeller.petstore.swagger2;

import java.util.Objects;

import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.VendorExtension;

public class BooleanVendorExtension implements VendorExtension<Boolean> {
	private String name;
	private String value;

	public BooleanVendorExtension(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Boolean getValue() {
		return Boolean.valueOf(value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		StringVendorExtension that = (StringVendorExtension) o;
		return Objects.equals(name, that.getName()) && Objects.equals(value, that.getValue());
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, value);
	}

	@Override
	public String toString() {
		  return new StringBuffer(this.getClass().getSimpleName())
		        .append("{")
		        .append("name").append(name).append(", ")
		        .append("value").append(value).append(", ")
		        .append("}").toString();
	}
}